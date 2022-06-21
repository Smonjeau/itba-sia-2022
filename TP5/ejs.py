from cmath import tanh
import numpy as np
import matplotlib.pyplot as plt
import random
from utils import get_input_fonts, load_csv, dump_csv, font_3,to_bin_array
from algorithm import Autoencoder

def ej1a4():

    neurons_per_layer = [20, 10, 2, 10, 20]
    all_inputs = get_input_fonts()
    autoencoder = Autoencoder(neurons_per_layer, all_inputs, all_inputs, 1)
    # autoencoder = Autoencoder(len(all_inputs[0]),[20,10],2,lambda x: tanh(x))

    autoencoder.weights= load_csv('TP5/weights.csv')


    generateLatentSpaceFigure(1,1,autoencoder)

def ej1b():
    neurons_per_layer = [20, 10, 5, 10, 20]
    all_inputs = get_input_fonts()
    autoencoder = Autoencoder(neurons_per_layer, all_inputs, all_inputs, 1)

    autoencoder.weights= load_csv('TP5/weights(2).csv')

    total_errors=[]
    for i in range(0, 10):
        noise=i/(10*2)
        error = 0
        expected = np.array(autoencoder.output_set)
        output=[]
        aux=[]
        for font in range(len(font_3)):
            aux.append(all_inputs[font])
            for char in range(7):
                for char_row in range(5):
                    if random.random()<noise:
                        if aux[font][char*5+char_row]==1:
                            aux[font][char*5+char_row]=0
                        else:
                            aux[font][char*5+char_row]=1
                        
        
        output=aux
        for i in range(len(autoencoder.weights)):
            activation = np.dot(output,autoencoder.weights[i].T)
            output = autoencoder.activation_functions[i](activation)
        
        for font in range(len(font_3)):
            for char in range(7):
                for char_row in range(5):
                    if random.random()<noise:
                        if output[font][char*5+char_row]>0.5:
                            output[font][char*5+char_row]=1
                        else:
                            output[font][char*5+char_row]=0

        error = np.sum(np.power(output - expected, 2))
        total_errors.append(error)


    noise_levels = [i/(10*2) for i in range(0, 10)]
    plt.clf()
    plt.plot( noise_levels,total_errors)
    plt.xlabel('Noise')
    plt.ylabel('Total Error')
    plt.title('Noise vs Total Error')
    plt.show()

def generateLatentSpaceFigure(x,y,autoencoder):
    
    output=autoencoder.decode([x,y])
    output=np.array(output)
    output=output.reshape(7,5)
    plt.clf()
    plt.imshow(output, cmap='gray')
    plt.show()

