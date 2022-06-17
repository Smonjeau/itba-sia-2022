from operator import ne
import numpy as np
from qiskit.algorithms.optimizers import ADAM
class Autoencoder:
    def __init__(self, input_size, hidden_layers_size,latent_space_size,activation_function):
        self.input_size = input_size
        self.hidden_layers_size = hidden_layers_size
        self.latent_space_size = latent_space_size
        self.output_size = input_size
        self.weights = self.init_weights()
        self.activation_function = activation_function
        self.neurons_values = [ [] for i in range(len(hidden_layers_size)*2 + 3)]
        self.latent_space_idx = len(self.hidden_layers_size) +1


   
    def init_weights(self):
        weights = []
        #la parte del encoder
        for i in range(len(self.hidden_layers_size)-1):
            if i == 0:
                weights.append(np.random.randn(self.input_size+1, self.hidden_layers_size[i]))
            
            else:
                weights.append(np.random.randn(self.hidden_layers_size[i], self.hidden_layers_size[i+1]))

        #espacio latente
        if len(weights) == 0:
            weights.append(np.random.randn(self.input_size+1, self.latent_space_size))
        else:
            weights.append(np.random.randn(self.hidden_layers_size[-1], self.latent_space_size))

        #la parte del decoder
        for i in range(len(self.hidden_layers_size)):
            weights.append(np.transpose(weights[len(weights)-1-i]))

        weights.append(np.transpose(weights[0]))    
        #weights.append(np.random.randn(self.hidden_size[-1], self.output_size))
        return weights

    #Armar funcion q dado un input devueva el output. Ves todo el dataset. Con eso armas la funcion de error
    #En la funcion de error vas sumarizando. La funcion de error depende de los pesos, y es lo q le pasas a ADAM
    #para q minimze. ADAM al terminar te va a devolver los pesos optimos. Y ahi deberia estar entrenada la red

    def encode(self, input_data,bias):

        first_layer = [bias]
        first_layer.extend(input_data)

        self.neurons_values[0] = first_layer

        #iterate until latent_space_size 
        for layer_idx in range(1,len(self.hidden_layers_size)+1):

         #   aux2 = [len(first_layer)]
         #   aux2.extend(first_layer)    
            aux = []  
            for neuron_idx in range(self.hidden_layers_size[layer_idx]):
                
                accum = 0.0
                for weight_idx in range(len(self.weights[layer_idx-1][neuron_idx])):
                    accum += self.weights[layer_idx][neuron_idx][weight_idx] * self.neurons_values[layer_idx-1][neuron_idx]

                aux.append(self.activation_function(accum))  
            self.neurons_values[layer_idx+1] = aux


        #latent space
     #   aux = []  

     #   for neuron_idx in range(self.latent_space_size):
     #       accum = 0.0
        
    #        for weight_idx in range(len(self.weights[self.latent_space_idx][neuron_idx])):
    #            accum += self.weights[self.latent_space_idx][neuron_idx][weight_idx] * self.neurons_values[self.latent_space_idx -1][neuron_idx]

     #       aux.append(self.activation_function(accum))  
     #   self.neurons_values[self.latent_space_idx] = aux
            
    
    
    def decode(self):

        for layer_idx in range(len(self.hidden_layers_size)):
            aux = []  
            for neuron_idx in range(self.hidden_layers_size[-layer_idx - 1]):
                accum = 0.0
                for weight_idx in range(len(self.weights[layer_idx + self.latent_space_idx + 1][neuron_idx])):
                    accum += self.weights[layer_idx + self.latent_space_idx + 1][neuron_idx][weight_idx] * self.neurons_values[-layer_idx - 1][neuron_idx]

                aux.append(self.activation_function(accum))  
            self.neurons_values[layer_idx + self.latent_space_idx + 1 + 1] = aux


        #last layer
        aux = []  

        for neuron_idx in range(self.input_size):
            accum = 0.0
        
            for weight_idx in range(len(self.weights[-1][neuron_idx])):
                accum += self.weights[-1][neuron_idx][weight_idx] * self.neurons_values[-2][neuron_idx]

            aux.append(self.activation_function(accum))  
        self.neurons_values[-1] = aux
                    


        



    #def train(self,inputs,outputs):

        #result=ADAM().optimize(,Wrapper,initial_point = self.init_weights)
        



    #def Wrapper(weights):

