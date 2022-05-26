def print_letter(letter_list):
    for bit in range(len(letter_list)):
        if bit > 0 and bit % 5 == 0:
            print("")
        if letter_list[bit] == 1.0:
            print(" * ", end="")
        else:
            print("   ", end="")
    print('\n')


abcdario = ["a", "b", "c", "d", "e", "f", "g", "h", "i", "j", "k", "l", "m", "n", "o", "p", "q", "r", "s", "t", "u",
            "v", "w", "x", "y", "z"]
qty = 4
sqrt_size = 5


def get_letters_dict(file):
    aux = []
    patterns_dict = {}

    i = 0
    while True:

        line = file.readline()
        if not line:
            break

        if line[-1] == '\n':
            line = line[:-1]

        elements = line.split(" ", 5)
        for elem in elements:
            aux.append(float(elem))

        if len(aux) == sqrt_size ** 2:
            patterns_dict[abcdario[i]] = aux
            aux = []
            i += 1
    return patterns_dict
