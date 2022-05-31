import random
from random import randint
import matplotlib.pyplot as plt
import numpy as np

from hopfield import Hopfield
from utils import print_letter, qty, get_letters_dict, sqrt_size

file = open('patterns.txt', 'r')
patterns = []

train_set_size = qty

aux = []

patterns_dict = get_letters_dict(file)

results = []
probability = 0.6
probs_array = []
#qty_of_experiments = 100
#while probability < 1:
#    aux_results = []
#    for i in range(qty_of_experiments):

train_patterns = [patterns_dict['x'], patterns_dict['f'], patterns_dict['l'], patterns_dict['j']]
test_pattern = []
test_idx = randint(0, train_set_size - 1)
aux = train_patterns[test_idx]
for idx in range(len(aux)):

    if random.random() < probability:
        multiplier = -1
    else:
        multiplier = 1
    test_pattern.append(multiplier * aux[idx])

hop = Hopfield(sqrt_size, train_patterns)

hop.test(test_pattern)

found = True
for pattern_idx in range(len(train_patterns)):
    found = True
    for i in range(sqrt_size ** 2):
        if hop.neurons_states[i] != train_patterns[pattern_idx][i]:
            found = False
            break
    if found:
        break

if not found:
    print("ESPUREO")
#    aux_results.append("ESPUREO")
elif found and pattern_idx == test_idx:
    # print("El patrón que dió es {} y el original fue {}".format(pattern_idx, test_idx))
    print("OK")
#    aux_results.append("OK")
else:
    print("PATRON EQUIVOCADO")
#    aux_results.append("PATRON_EQUIVOCADO")
#results.append(aux_results)
#probs_array.append(str(probability * 100)[0:4])
#probability += 0.1

#print(results)

#ok_list = []
#espureo_list = []
#wrong_pattern_list = []
#for res_list in results:
#    ok_count = 0
#    esp_count = 0
#    wrong_count = 0
#    for res in res_list:
#        if res == "OK":
#            ok_count += 1
#        elif res == "PATRON_EQUIVOCADO":
#            wrong_count += 1
#        elif res == "ESPUREO":
#            esp_count += 1
#    ok_list.append(ok_count / qty_of_experiments * 100)
#    espureo_list.append(esp_count / qty_of_experiments * 100)
#    wrong_pattern_list.append(wrong_count / qty_of_experiments * 100)

#data = [ok_list, espureo_list, wrong_pattern_list]
#X = np.arange(len(ok_list))

#print(probs_array)
#figure_size = plt.gcf().get_size_inches()
#factor = 1.7
# plt.gcf().set_size_inches(factor * figure_size)
# plt.bar(X + 0.00, data[0], color='b', width=0.25)
# plt.bar(X + 0.25, data[1], color='g', width=0.25)
# plt.bar(X + 0.5, data[2], color='r', width=0.25)
# plt.xticks(X, probs_array)
# plt.gca().xaxis.label.set_size(20)
# plt.gca().yaxis.label.set_size(20)
# plt.tick_params(axis='x', labelsize=17)
# plt.tick_params(axis='y', labelsize=17)
# plt.legend(fontsize = 13,loc="best", bbox_to_anchor=(0.59,1), labels=["PATRON CORRECTO", "ESTADO ESPUREO", "PATRON INCORRECTO"])
# plt.ylabel("Patrón obtenido (%)")
# plt.xlabel("Probabilidad de cambiar un bit (%)")
# plt.savefig("letras_peor_patron_100.png")

for i in range(len(train_patterns)):
    print_letter(train_patterns[i])
print("-------------------------------")
print("El camino fue")
for pat in hop.history:
    print_letter(pat)

print("El patron presentado fue")
print(" ------------------------------  ")
print_letter(test_pattern)
print(" ------------------------------  ")
print("El resultado obtenido fue")
print_letter(hop.neurons_states)
print("------------------------")
print("La letra original fue")
print_letter(aux)