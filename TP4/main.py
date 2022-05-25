import random
from random import randint

from hopfield import Hopfield

file = open('patterns.txt', 'r')
patterns = []
sqrt_size = 5
train_set_size = 4
aux = []

abcdario = ["a", "b", "c", "d", "e", "f", "g", "h", "i", "j", "k", "l", "m", "n", "o", "p", "q", "r", "s", "t", "u",
            "v", "w", "x", "y", "z"]

def print_letter(letter_list):
    for bit in range(len(letter_list)):
        if bit > 0 and bit % 5 == 0:
            print("")
        if letter_list[bit] == 1.0:
            print(" * ", end="")
        else:
            print("   ", end="")
    print('\n')


while True:

    line = file.readline()
    if not line:
        break

    if line[-1] == '\n':
        line = line[:-1]

    elements = line.split(" ", sqrt_size)
    for elem in elements:
        aux.append(float(elem))

    if len(aux) == sqrt_size ** 2:
        patterns.append(aux)
        aux = []

test_pattern = []
probability = 0.02

random.shuffle(patterns)
train_patterns = patterns[0:train_set_size]
aux = train_patterns[randint(0, train_set_size-1)]
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
        if hop.neurons_states[i] != patterns[pattern_idx][i]:
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
