import numpy as np
import scipy.optimize as sco
import random
import matplotlib.pyplot as plt
import pandas as pd

class Autoencoder:
    def __init__(self, neurons_per_layer, train_set, output_set, beta, max_epochs=1000, tol=1e-5):
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
        self.max_epochs = max_epochs
        self.tol = tol

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
                self.weights[i] = np.array([np.random.uniform(-1, 1, size=(len(self.train_set[0]))) for _ in range(self.neurons_per_layer[i])])
            else:
                self.weights[i] = np.array([np.random.uniform(-1, 1, size=(self.neurons_per_layer[i-1])) for _ in range(self.neurons_per_layer[i])])

        # Add output layer of decoder
        self.weights.append(np.array([]))
        self.weights[-1] = np.array([np.random.uniform(-1, 1, size=(self.neurons_per_layer[-1])) for _ in range(len(self.output_set[0]))])

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

    def evaluate(self, input, weights):
        for i in range(len(weights)):
            activation = np.dot(input, weights[i].T)
            if i == len(weights) - 1:
                input = np.tanh(activation)
            elif i != int(len(weights)/2):
                input = np.where(activation > 0, activation, 0)

        return input

    def decode(self,input_set):
        output=np.array(input_set).reshape(1,2)
        for i in range(self.latent_index+1,len(self.weights)):
            activation = np.dot(output, self.weights[i].T)
            output = self.activation_functions[i](activation)
        for i in range(len(output)):
            for j in range(len(output[i])):
                
                if output[i][j] > 0.5 : 
                    output[i][j]=1 
                else :
                    output[i][j]=0
        return output

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
            self.errors_per_step.append(self.error(weights)*2/len(self.train_set))
            print("Epoch: {} Error promedio: {}".format(str(self.epoch), self.errors_per_step[-1]))
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
            options={'maxiter': self.max_epochs}, 
            tol=self.tol
        ).x

        return trained_weights