import numpy as np
import scipy.optimize as sco
import random

class Autoencoder:
    def __init__(self, neurons_per_layer, train_set, output_set, beta):
        self.neurons_per_layer = neurons_per_layer
        self.train_set = train_set
        self.output_set = output_set
        self.weights = []
        self.activation_functions = []
        self.init_weights()
        self.latent_index = int(len(self.neurons_per_layer)/2)
        self.beta = beta
        self.errors_per_step = []
        self.epoch = 0

        for i in range(len(self.weights)):
            if i == len(self.weights) - 1:
                self.activation_functions.append(lambda x: np.tanh(x))
            elif i == self.latent_index:
                self.activation_functions.append(lambda x: x)
            else:
                self.activation_functions.append(lambda x: np.where(x > 0, x, 0))

    def init_weights(self):
        self.weights = [np.array([]) for _ in range(len(self.neurons_per_layer))]

        for i in range(len(self.neurons_per_layer)):
            if i == 0:
                self.weights[i] = np.array([np.random.randn(len(self.train_set[0])) for _ in range(self.neurons_per_layer[i])])
            else:
                self.weights[i] = np.array([np.random.randn(self.neurons_per_layer[i-1]) for _ in range(self.neurons_per_layer[i])])

        # Add output layer of decoder
        self.weights.append(np.array([]))
        self.weights[-1] = np.array([np.random.randn(self.neurons_per_layer[-1]) for _ in range(len(self.output_set[0]))])

    def error(self, weights):
        error = 0
        weights = self.unflatten_weights(weights)
        expected = np.array(self.output_set)
        
        output = self.train_set
        for i in range(len(weights)):
            activation = np.dot(output, weights[i].T)
            output = self.activation_functions[i](activation)

        error = np.sum(np.power(output - expected, 2))

        return error/2

    def unflatten_weights(self, array):
        aux = []
        i = 0
        for layer in self.weights:
            curr_size = layer.size
            flatted = np.array(array[i:i+curr_size])
            aux.append(flatted.reshape(layer.shape))
            i += curr_size
        return np.array(aux, dtype=object)

    def callback(self,weights):
        self.epoch += 1
        if self.epoch % 10 == 0:
            print("Epoch: {} Error: {}".format(str(self.epoch), self.error(weights)*2/len(self.train_set)))
        else:
            print("Epoch: {}".format(self.epoch))

    def train(self):
        flattened_weights = np.array([])
        for layer in self.weights:
            flattened_weights =  np.append(flattened_weights, layer.flatten())
        flattened_weights = flattened_weights.flatten() 

        trained_weights = sco.minimize(
            self.error, 
            flattened_weights, 
            method='Powell', 
            callback=self.callback, 
            options={'maxiter': 100}, 
            tol=1e-30
        ).x

        return trained_weights


font_3 = np.array([
   [0x04, 0x04, 0x02, 0x00, 0x00, 0x00, 0x00],   # 0x60, `
   [0x00, 0x0e, 0x01, 0x0d, 0x13, 0x13, 0x0d],   # 0x61, a
   [0x10, 0x10, 0x10, 0x1c, 0x12, 0x12, 0x1c],   # 0x62, b
   [0x00, 0x00, 0x00, 0x0e, 0x10, 0x10, 0x0e],   # 0x63, c
   [0x01, 0x01, 0x01, 0x07, 0x09, 0x09, 0x07],   # 0x64, d
   [0x00, 0x00, 0x0e, 0x11, 0x1f, 0x10, 0x0f],   # 0x65, e
   [0x06, 0x09, 0x08, 0x1c, 0x08, 0x08, 0x08],   # 0x66, f
   [0x0e, 0x11, 0x13, 0x0d, 0x01, 0x01, 0x0e],   # 0x67, g
   [0x10, 0x10, 0x10, 0x16, 0x19, 0x11, 0x11],   # 0x68, h
   [0x00, 0x04, 0x00, 0x0c, 0x04, 0x04, 0x0e],   # 0x69, i
   [0x02, 0x00, 0x06, 0x02, 0x02, 0x12, 0x0c],   # 0x6a, j
   [0x10, 0x10, 0x12, 0x14, 0x18, 0x14, 0x12],   # 0x6b, k
   [0x0c, 0x04, 0x04, 0x04, 0x04, 0x04, 0x04],   # 0x6c, l
   [0x00, 0x00, 0x0a, 0x15, 0x15, 0x11, 0x11],   # 0x6d, m
   [0x00, 0x00, 0x16, 0x19, 0x11, 0x11, 0x11],   # 0x6e, n
   [0x00, 0x00, 0x0e, 0x11, 0x11, 0x11, 0x0e],   # 0x6f, o
   [0x00, 0x1c, 0x12, 0x12, 0x1c, 0x10, 0x10],   # 0x70, p
   [0x00, 0x07, 0x09, 0x09, 0x07, 0x01, 0x01],   # 0x71, q
   [0x00, 0x00, 0x16, 0x19, 0x10, 0x10, 0x10],   # 0x72, r
   [0x00, 0x00, 0x0f, 0x10, 0x0e, 0x01, 0x1e],   # 0x73, s
   [0x08, 0x08, 0x1c, 0x08, 0x08, 0x09, 0x06],   # 0x74, t
   [0x00, 0x00, 0x11, 0x11, 0x11, 0x13, 0x0d],   # 0x75, u
   [0x00, 0x00, 0x11, 0x11, 0x11, 0x0a, 0x04],   # 0x76, v
   [0x00, 0x00, 0x11, 0x11, 0x15, 0x15, 0x0a],   # 0x77, w
   [0x00, 0x00, 0x11, 0x0a, 0x04, 0x0a, 0x11],   # 0x78, x
   [0x00, 0x11, 0x11, 0x0f, 0x01, 0x11, 0x0e],   # 0x79, y
   [0x00, 0x00, 0x1f, 0x02, 0x04, 0x08, 0x1f],   # 0x7a, z
   [0x06, 0x08, 0x08, 0x10, 0x08, 0x08, 0x06],   # 0x7b, [
   [0x04, 0x04, 0x04, 0x00, 0x04, 0x04, 0x04],   # 0x7c, |
   [0x0c, 0x02, 0x02, 0x01, 0x02, 0x02, 0x0c],   # 0x7d, ]
   [0x08, 0x15, 0x02, 0x00, 0x00, 0x00, 0x00],   # 0x7e, ~
   [0x1f, 0x1f, 0x1f, 0x1f, 0x1f, 0x1f, 0x1f]   # 0x7f, DEL
   ])

def to_bin_array(encoded_caracter):
    bin_array = np.zeros((7, 5), dtype=int)
    for row in range(0, 7):
        current_row = encoded_caracter[row]
        for col in range(0, 5):
            bin_array[row][4-col] = current_row & 1
            current_row >>= 1
    return bin_array.flatten()

def main():
    train_set = [[1, 1], [1, 0], [0, 1], [0, 0]]
    output_set = [[1, 1], [1, 0], [0, 1], [0, 0]]

    neurons_per_layer = [20, 10, 5, 10, 20]
    all_inputs = []

    for font in font_3:
        all_inputs.append(to_bin_array(font))

    input = all_inputs[:10]
    
    # get 10 items random from all_inputs
    input = random.sample(all_inputs, 10)

    autoencoder = Autoencoder(neurons_per_layer, input, input, 1)

    # for i in range(len(autoencoder.weights)):
    #     print("Layer {}, weight size {}\n".format(i+1, len(autoencoder.weights[i])))

    weights = autoencoder.train()
    final_weights = autoencoder.unflatten_weights(weights)

    print(final_weights)
    print(autoencoder.error(weights)*2/len(input))


main()