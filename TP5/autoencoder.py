from operator import ne
from scipy.optimize import minimize
import numpy as np
# from qiskit.algorithms.optimizers import ADAM
class Autoencoder:
    def __init__(self, input_size, hidden_layers_size,latent_space_size,activation_function):
        self.input_size = input_size
        self.hidden_layers_size = hidden_layers_size
        self.latent_space_size = latent_space_size
        self.output_size = input_size
        self.activation_function = activation_function
        self.neurons_values = [ [] for _ in range(len(hidden_layers_size)*2 + 3)]
        self.latent_space_idx = len(self.hidden_layers_size) +1
        self.weights = self.init_weights()

        weights_qty = 0
        for weight in self.weights:
            weights_qty += len(weight)*len(weight[0])
        self.weights_qty = weights_qty    


   
    def init_weights(self):
        weights = []
        #la parte del encoder
        for i in range(len(self.hidden_layers_size)):
            if i == 0:
                weights.append(np.random.randn(self.input_size+1, self.hidden_layers_size[i]))
            else:
                weights.append(np.random.randn(self.hidden_layers_size[i-1], self.hidden_layers_size[i]))

        #espacio latente
        if len(weights) == 0:
                weights.append(np.random.randn(self.input_size+1,self.latent_space_size))
        else:
            # weights.append(np.random.randn(self.hidden_layers_size[-1], self.latent_space_size))
            weights.append(np.random.randn(self.hidden_layers_size[-1],self.latent_space_size))

        #la parte del decoder
        for i in range(len(self.hidden_layers_size)):
            weights.append(np.transpose(weights[self.latent_space_idx - i - 1]))

  
        #remove last column of w[0]
        aux = []
        aux.extend(weights[0])
        aux = np.delete(aux, 1, 0)
        weights.append(np.transpose(aux))  
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
            
            #aux = []
            #for i in range(len(self.neurons_values[layer_idx])):
            #    accum = 0

             #   for j in range(len(self.neurons_values[layer_idx-1])):
              #      accum += self.weights[layer_idx-1][i][j] * self.neurons_values[layer_idx-1][j]

           # print(self.neurons_values[layer_idx-1])
           # print(self.weights[layer_idx-1])
           # print(len(self.neurons_values[layer_idx]))
            aux = np.matmul(self.neurons_values[layer_idx-1], (self.weights[layer_idx-1]))    
            
            #apply activation function to all elements of aux
            for i in range(len(aux)):
                aux[i] = self.activation_function(aux[i])
            self.neurons_values[layer_idx] = aux


        #latent space

    #    aux = []
#        for i in range(self.latent_space_size):
#            accum = 0
#            for j in range(len(self.neurons_values[self.latent_space_idx-1])):
#                accum += self.weights[self.latent_space_idx-1][i][j] * self.neurons_values[self.latent_space_idx-1][j]

        aux = np.matmul(self.neurons_values[self.latent_space_idx-1],(self.weights[self.latent_space_idx-1]))
        for i in range(len(aux)):
            aux[i] = self.activation_function(aux[i])
           # aux.append(self.activation_function(accum))
        self.neurons_values[self.latent_space_idx] = aux
            
    
    
    def decode(self):

        #iterate until latent_space_size
        for layer_idx in range(self.latent_space_idx + 1, self.latent_space_idx + len(self.hidden_layers_size) + 1):
            
            #aux = []
            #for i in range(len(self.neurons_values[layer_idx])):
            #    accum = 0

            #    for j in range(len(self.neurons_values[layer_idx-1])):
            #        accum += self.weights[layer_idx-1][i][j] * self.neurons_values[layer_idx-1][j]

            #    aux.append(self.activation_function(accum))
            aux = np.matmul(self.neurons_values[layer_idx-1], (self.weights[layer_idx-1]))
            for i in range(len(aux)):
                aux[i] = self.activation_function(aux[i])
            self.neurons_values[layer_idx] = aux

        #aux = []

        #for i in range(len(self.neurons_values[-1])): 

    #        for j in range(len(self.neurons_values[-2])):
    #            accum += self.weights[-1][i][j] * self.neurons_values[-2][j]

    #        aux.append(self.activation_function(accum))
        aux = np.matmul(self.neurons_values[-2], (self.weights[-1]))
        for i in range(len(aux)):
            aux[i] = np.tanh(aux[i])#self.activation_function(aux[i])
        self.neurons_values[-1] = aux
                    


        


    def Wrapper(self,weights_1D):
  
      #  print("1D quedo", weights_1D)
        self.weights = self.array1D_tomatrix3D(weights_1D)
       # print("Dsp de la conversion")
       # print(self.weights)        
        error_accum = 0
        for sample in range(len(self.inputs)):
           # print(self.inputs[sample])
            self.encode(self.inputs[sample], -1)
            self.decode()
            for output_idx in range(len(self.outputs[sample])):
                error_accum += ((self.outputs[sample][output_idx] - self.neurons_values[-1][output_idx]) ** 2)/2

        return error_accum            
    def train(self,inputs,outputs):    

        self.inputs = inputs
        self.outputs = outputs



        #result=ADAM().optimize(self.weights_qty,self.Wrapper,initial_point = self.matrix3D_to_array1D())
        result = minimize(self.Wrapper,self.matrix3D_to_array1D(),method='Powell')
        print(result.x)
        #print(len(result[0]))
        self.weights = self.array1D_tomatrix3D(result.x)
       # print("Valor óptimo para método ADAM {}".format(result[0]))
        print("Error {}".format(self.Wrapper(result.x)))
        
 


        for inp in self.inputs:
            self.encode(inp, -1)
            self.decode()
            print("Outputs: {}".format(self.neurons_values[-1]))


        


    def matrix3D_to_array1D(self):
       # print("Antes de la conversion")
     #   print(self.weights)
        array = []
        for layer in self.weights:
            for row in layer:
                for element in row:
                    array.append(element)
        return array

    def array1D_tomatrix3D(self,array):
        ret = []
        aux = 0
        for i in range(len(self.weights)):
            ret.append([])
            for j in range(len(self.weights[i])):
                ret[i].append([])
                for k in range(len(self.weights[i][j])):
                    ret[i][j].append(array[k+aux])
                aux += len(ret[i][j])    

        return ret

    #def Wrapper(weights):

