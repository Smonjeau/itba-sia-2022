from algorithm import Autoencoder
import pandas as pd
import numpy as np
import random
import matplotlib.pyplot as plt
from utils import get_input_fonts, load_csv, dump_csv

def main():

    neurons_per_layer = [20, 10, 5, 10, 20]
    all_inputs = get_input_fonts()

    input = all_inputs[:10]
    
    # get 10 items random from all_inputs
    input = random.sample(all_inputs, 10)

    autoencoder = Autoencoder(neurons_per_layer, input, input, 1)

    # for i in range(len(autoencoder.weights)):
    #     print("Layer {}, weight size {}\n".format(i+1, len(autoencoder.weights[i])))

    weights = autoencoder.train()
    final_weights = autoencoder.unflatten_weights(weights)

    dump_csv(final_weights)

    print(final_weights)
    print(autoencoder.error(weights)*2/len(input))

def error_vs_latent_size():

    # Format input neurons
    all_inputs = []
    for font in font_3:
        all_inputs.append(to_bin_array(font))

    max_size = 10

    # create a pandas dataframe with columns latent size and error
    df = pd.DataFrame(columns=['Latent Size', 'Error'])

    for size in range(2, max_size+1, 2):

        print("Running for latent size {}...".format(size))
        neurons_per_layer = [20, 10, size, 10, 20]

        autoencoder = Autoencoder(neurons_per_layer, all_inputs, all_inputs, 1, max_epochs=500, tol=1e-5)

        flatten_weights = autoencoder.train()
        error = autoencoder.error(flatten_weights)*2/len(all_inputs)
        df.loc[len(df)] = [size, error]

    df.to_csv('error_vs_latent_size.csv')

def error_vs_input_size():
    # Format input neurons
    all_inputs = []
    for font in font_3:
        all_inputs.append(to_bin_array(font))


    # create a pandas dataframe with columns latent size and error
    df = pd.DataFrame(columns=['Input Size', 'Error'])

    for size in range(4, len(all_inputs)+1, 4):
        input = all_inputs[:size]
        print("Running for input size {}...".format(size))
        neurons_per_layer = [20, 10, 2, 10, 20]

        autoencoder = Autoencoder(neurons_per_layer, input, input, 1, max_epochs=500, tol=1e-5)

        flatten_weights = autoencoder.train()
        error = autoencoder.error(flatten_weights)*2/len(input)
        print("Error: "+str(error))
        df.loc[len(df)] = [size, error]

    df.to_csv('error_vs_input_size.csv')

def error_vs_epoch():
    all_inputs = []
    for font in font_3:
        all_inputs.append(to_bin_array(font))


    # create a pandas dataframe with columns latent size and error
    df = pd.DataFrame(columns=['Epoch', 'Error'])
    neurons_per_layer = [20, 10, 2, 10, 20]

    autoencoder = Autoencoder(neurons_per_layer, all_inputs, all_inputs, 1, max_epochs=500, tol=1e-5)

    flatten_weights = autoencoder.train()
    error = autoencoder.error(flatten_weights)*2/len(all_inputs)
    print("Error: "+str(error))
    error_per_step = autoencoder.errors_per_step

    for (index, error) in enumerate(error_per_step):
        df.loc[len(df)] = [(index+1)*10, error]
        
    df.to_csv('error_vs_epoch.csv')
    dump_csv(autoencoder.unflatten_weights(flatten_weights), filename='weights.csv')

def graph_error_vs_latent_size():
    df = pd.read_csv('error_vs_latent_size.csv', sep=',')
    plt.plot(df['Latent Size'], df['Error'])
    plt.xlabel('Tamaño espacio latente')
    plt.ylabel('Error promedio')
    plt.title('Error promedio vs Tamaño espacio latente')
    plt.show()

def graph_error_vs_input_size():
    df = pd.read_csv('error_vs_input_size.csv', sep=',')
    plt.plot(df['Input Size'], df['Error'])
    plt.xlabel('Tamaño de entrada')
    plt.ylabel('Error promedio')
    plt.title('Error promedio vs Tamaño de entrada')
    plt.show()

def graph_error_vs_epoch():
    df = pd.read_csv('error_vs_epoch_1.csv', sep=',')
    plt.plot(df['Epoch'], df['Error'])
    plt.xlabel('Epocas')
    plt.ylabel('Error promedio')
    plt.title('Error promedio vs Epocas')
    plt.show()

def graph_letters_error():
    weights = load_csv()

    Autoencoder(neurons_per_layer, train_set, output_set, beta)

# error_vs_latent_size()
# error_vs_input_size()
error_vs_epoch()
# graph_error_vs_latent_size()
# graph_error_vs_input_size()
# graph_error_vs_epoch()