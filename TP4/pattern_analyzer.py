import numpy as np
import itertools
from utils import print_letter, abcdario, qty
from utils import get_letters_dict
file = open('patterns.txt', 'r')

patterns_dict = get_letters_dict(file)

possible_patterns_set = itertools.combinations(abcdario, r=qty)

avg_dot_prod = {}
for patterns_set in possible_patterns_set:

    pat_set = np.array([v for k, v in patterns_dict.items() if k in patterns_set])
    matrix = np.matmul(pat_set, np.transpose(pat_set))
    n = matrix.shape[0]
    matrix[range(n), range(n)] = 0

    print(patterns_set)
    print(matrix)

    aux = 0
    for row in range(len(matrix)):
        for col in range(len(matrix[row])):
            aux += abs(matrix[row][col])

    avg_dot_prod[patterns_set] = (aux / ((qty ** 2) - qty))

min_key = ('a', 'b', 'c', 'd')
max_key = ('a', 'b', 'c', 'd')

min_avg = avg_dot_prod[min_key]
max_avg = avg_dot_prod[max_key]

for key in avg_dot_prod:
    if min_avg > avg_dot_prod[key]:
        min_avg = avg_dot_prod[key]
        min_key = key
    if max_avg < avg_dot_prod[key]:
        max_avg = avg_dot_prod[key]
        max_key = key
print("El mejor patron es {} que dio un promdeio de producto escalar {}".format(min_key, min_avg))
print("El peor patron es {} que dio un promdeio de producto escalar {}".format(max_key, max_avg))

for letter in min_key:
    print(letter+":")
    print_letter(patterns_dict[letter])
print("-----------------------------------------------------------------------------")
for letter in max_key:
    print(letter+":")
    print_letter(patterns_dict[letter])
