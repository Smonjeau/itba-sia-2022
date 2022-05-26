import random
from random import randint

from hopfield import Hopfield
from utils import print_letter, qty, get_letters_dict, sqrt_size

file = open('patterns.txt', 'r')
patterns = []

train_set_size = qty

aux = []

patterns_dict = get_letters_dict(file)

print(patterns_dict)
test_pattern = []
probability = 0.1

train_patterns = [patterns_dict['f'], patterns_dict['j'], patterns_dict['l'], patterns_dict['x']]
aux = train_patterns[randint(0, train_set_size - 1)]
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

if found:
    print("El patrón que dió es {}".format(pattern_idx))

for i in range(len(train_patterns)):
    print_letter(train_patterns[i])

print(" ------------------------------  ")
print_letter(test_pattern)
print(" ------------------------------  ")
print_letter(hop.neurons_states)
