from operator import ne
import numpy as np
from qiskit.algorithms.optimizers import ADAM
class Autoencoder:
    def __init__(self, input_size, hidden_layers_size,latent_space_size,activation_function):
        self.input_size = input_size
        self.hidden_layers_size = hidden_layers_size
        self.latent_space_size = latent_space_size
        self.output_size = input_size
        self.activation_function = activation_function
        self.neurons_values = [ [] for i in range(len(hidden_layers_size)*2 + 3)]
        self.latent_space_idx = len(self.hidden_layers_size) +1
        self.weights = self.init_weights()


   
    def init_weights(self):
        weights = []
        #la parte del encoder
        for i in range(len(self.hidden_layers_size)):
            if i == 0:
                weights.append(np.random.randn(self.input_size, self.hidden_layers_size[i]+1))
            else:
                weights.append(np.random.randn(self.hidden_layers_size[i], self.hidden_layers_size[i-1]))

        #espacio latente
        if len(weights) == 0:
            weights.append(np.random.randn(self.input_size, self.latent_space_size+1))
        else:
            # weights.append(np.random.randn(self.hidden_layers_size[-1], self.latent_space_size))
            weights.append(np.random.randn(self.latent_space_size, self.hidden_layers_size[-1]))

        #la parte del decoder
        for i in range(len(self.hidden_layers_size)):
            weights.append(np.transpose(weights[self.latent_space_idx - i - 1]))

        weights.append(np.transpose(weights[0]))  

        return weights

    #Armar funcion q dado un input devueva el output. Ves todo el dataset. Con eso armas la funcion de error
    #En la funcion de error vas sumarizando. La funcion de error depende de los pesos, y es lo q le pasas a ADAM
    #para q minimze. ADAM al terminar te va a devolver los pesos optimos. Y ahi deberia estar entrenada la red

    def encode(self, input_data, bias):

        first_layer = [bias]
        first_layer.extend(input_data)

        self.neurons_values[0] = first_layer

        for layer_idx in range(1, len(self.hidden_layers_size) + 1):
            self.neurons_values[layer_idx] = [[] for _ in range(self.hidden_layers_size[layer_idx -  1])]
            self.neurons_values[-layer_idx - 1] = [[] for _ in range(self.hidden_layers_size[layer_idx - 1])]
        self.neurons_values[-1] = [[] for _ in range(self.input_size)]

        #iterate until latent_space_size
        for layer_idx in range(1, len(self.hidden_layers_size)+1):
            
            aux = []
            for i in range(len(self.neurons_values[layer_idx])):
                accum = 0

                for j in range(len(self.neurons_values[layer_idx-1])):
                    accum += self.weights[layer_idx-1][i][j] * self.neurons_values[layer_idx-1][j]

                aux.append(self.activation_function(accum))
            self.neurons_values[layer_idx] = aux


        #latent space

        aux = []
        for i in range(self.latent_space_size):
            accum = 0

            for j in range(len(self.neurons_values[self.latent_space_idx-1])):
                accum += self.weights[self.latent_space_idx-1][i][j] * self.neurons_values[self.latent_space_idx-1][j]

            aux.append(self.activation_function(accum))
        self.neurons_values[self.latent_space_idx] = aux
            
    
    
    def decode(self):

        #iterate until latent_space_size
        for layer_idx in range(self.latent_space_idx + 1, self.latent_space_idx + len(self.hidden_layers_size) + 1):
            
            aux = []
            for i in range(len(self.neurons_values[layer_idx])):
                accum = 0

                for j in range(len(self.neurons_values[layer_idx-1])):
                    accum += self.weights[layer_idx-1][i][j] * self.neurons_values[layer_idx-1][j]

                aux.append(self.activation_function(accum))
            self.neurons_values[layer_idx] = aux

        aux = []
        for i in range(len(self.neurons_values[-1])):
            accum = 0

            for j in range(len(self.neurons_values[-2])):
                accum += self.weights[-2][i][j] * self.neurons_values[-2][j]

            aux.append(self.activation_function(accum))
        self.neurons_values[-1] = aux
                    


        



    #def train(self,inputs,outputs):

        #result=ADAM().optimize(,Wrapper,initial_point = self.init_weights)
        



    #def Wrapper(weights):

