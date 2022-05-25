import numpy as np


class Hopfield:

    def __init__(self, size, patterns):
        self.size = size
        self.stored_patterns = []
        self.stored_patterns.extend(patterns)
        self.weights = np.zeros((size, size))
        self.neurons_states = []
        self.calculate_weights()

    def calculate_weights(self):
        k = np.column_stack(self.stored_patterns)
        self.weights = (k * (1 / (self.size * self.size)))
        self.weights = np.matmul(self.weights, np.transpose(k))
        n = self.weights.shape[0]
        self.weights[range(n), range(n)] = 0

    def test(self, test_pattern):

        self.neurons_states = test_pattern
        global converged
        counter = 0
        while True:
            counter += 1
            if counter > 1:
                prev = []
                prev.extend(self.neurons_states)

                self.neurons_states = np.sign(np.matmul(self.weights, self.neurons_states))
                converged = True
                for i in range(len(prev)):
                    if prev[i] != self.neurons_states[i]:
                        converged = False
                        break

                if converged:
                    break

            else:
                self.neurons_states = np.sign(np.matmul(self.weights, self.neurons_states))

            print(counter)
        print(counter)
