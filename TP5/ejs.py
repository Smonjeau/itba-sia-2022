import numpy as np
import matplotlib.pyplot as plt
import random
from utils import get_input_fonts, load_csv, dump_csv, font_3

def ej1a4():
    generateLatentSpaceFigure()

def ej1b():
    neurons_per_layer = [20, 10, 5, 10, 20]
    all_inputs = get_input_fonts()
    autoencoder = Autoencoder(neurons_per_layer, all_inputs, all_inputs, 1)

    total_errors=[]
    for i in range(0, 10):
        noise=i/(10*2)
        error = 0
        expected = np.array(autoencoder.output_set)
        output=[]
        aux=[]
        for font in range(len(font_3)):
            aux.append(to_bin_array(font))
            for char in range(len(font_3[font])):
                for char_row in range(len(font_3[font][char])):
                    if random.random()<noise:
                        if aux[font][char][char_row]==1:
                            aux[font][char][char_row]=0
                        else:
                            aux[font][char][char_row]=1
                        
        
        output=aux
        for i in range(len(autoencoder.weights)):
            activation = np.dot(output,autoencoder.weights[i].T)
            output = autoencoder.activation_functions[i](activation)

        error = np.sum(np.power(output - expected, 2))

    plt.clf()
    total_errors.append(error)
    plt.plot(total_errors,np.arange(0,5,0.5))
    plt.xlabel('Noise')
    plt.ylabel('Total Error')
    plt.title('Noise vs Total Error')
    plt.show()

def generateLatentSpaceFigure(x,y,autoencoder):
    
    output=autoencoder.decode(x,y)
    # output=[0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1,1,1,1,1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0]
    # print(len(output))
    output=np.array(output)
    output=output.reshape(7,5)
    plt.clf()
    plt.imshow(output, cmap='gray')
    plt.show()