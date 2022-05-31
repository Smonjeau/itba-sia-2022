import math
import matplotlib.pyplot as plt
import seaborn as sns
import pandas as pd
from sklearn.preprocessing import StandardScaler
from kohonen import *


#------------ Weight Visualization ------------#
def weight_heat_map(W, k):
    matrix = np.zeros((k, k))

    for i in range(len(W)):
        x = i%k
        y = i//k

        sum = 0
        cant = 0
        for j in range(len(W)):
            if math.dist([x, y], [j%k, j//k]) <= 1 and [x, y] != [j%k, j//k]:
                sum += np.linalg.norm(W[i] - W[j])
                cant += 1

        matrix[x, y] = sum/cant

    return matrix

#-------------- Quantity Analisis --------------#
def quantity_analisis(X, W, k):
    quantity = np.zeros((k, k))
    groups = [None for _ in range(k**2)]
    for i in range(len(X)):
        winner = np.argmin(np.linalg.norm(W - X[i], axis=1))
        if groups[winner] is None:
            groups[winner] = list()
        groups[winner].append(i)
        quantity[winner%k, winner//k] += 1

    return groups, quantity

#------------- Error Calculation -------------#
def calculate_error(X, W, k, df, group=None):
    df_ranking = pd.read_csv('ranking.csv')

    if group == None:
        group, _ = quantity_analisis(X, W, k)

    # group to ranking
    for i in range(len(group)):
        if group[i] is None:
            continue
        for j in range(len(group[i])):
            country = df.iloc[group[i][j]]['Country']
            group[i][j] = df_ranking.loc[df_ranking['country'] == country]['rank'].values[0]


    errors = []
    for p in range(len(group)):
        i = p%k
        j = p//k

        if group[p] is None:
            errors.append(0)
            continue

        aux_group = []
        aux_group.extend(group[p])
        for q in range(len(group)):
            if group[q] is None:
                continue

            x = q%k
            y = q//k
            if math.dist([i, j], [x, y]) <= 1 and [i, j] != [x, y]:
                aux_group.extend(group[q])

        aux_group.sort()

        val = 0
        for q in range(len(aux_group) - 1):
            val += abs(aux_group[q+1] - aux_group[q] - 1)

        errors.append(val)

    return np.array(errors).reshape(k, k)

#----------------- Full Analysis -----------------#
def full_analysis(neurons, alpha):
    # Initial Steps
    df = pd.read_csv('europe.csv')
    # Standardize variables
    X = StandardScaler().fit_transform(df.iloc[:,1:8])

    # Run Kohonen algorithm
    W, iter, alpha = kohonen(X, neurons, alpha=alpha)

    # Show Heat Map of weights
    weights = weight_heat_map(W, neurons)
    sns.heatmap(weights, linewidth=0.5)
    plt.title('Matriz de U')
    plt.savefig('weights_{}_{}t_{}a.png'.format(neurons, iter, alpha))

    # matplot clean up
    plt.clf()

    # Quantity graph with annotations of members
    groups, quantity = quantity_analisis(X, W, neurons)
    df_ranking = pd.read_csv('ranking.csv')
    str_countries = []
    for group in groups:
        if group is None:
            str_countries.append('0')
            continue

        countries = []
        for item in group:
            country = df.iloc[item]['Country']
            countries.append("{}. {}".format(df_ranking.loc[df_ranking['country'] == country]['rank'].values[0], country))
        str_countries.append("{}\n{}".format(len(group), '\n'.join(countries)))

    # transform array to matrix for heatmap
    str_countries = np.array(str_countries)
    str_countries = str_countries.reshape(neurons, neurons).transpose()

    plt.clf()
    fig, ax = plt.subplots(figsize=(10,7)) 
    sns.heatmap(quantity, linewidth=0.5, annot=str_countries, fmt = '', ax=ax)
    plt.title('Cantidad de países por neurona')
    plt.savefig('quantity_with_countries_{}_{}t_{}a.png'.format(neurons, iter, alpha))

    # Error graph
    plt.clf()
    errors = calculate_error(X, W, neurons, df).transpose()
    fig, ax = plt.subplots(figsize=(5,4)) 
    sns.heatmap(errors, linewidth=0.5, annot=True, ax=ax)
    plt.title('Error por cada neurona')
    plt.savefig('errors_{}_{}t_{}a.png'.format(neurons, iter, alpha))

def iteration_error_stats():

    iterations = [1000, 2000, 4000, 8000, 10000, 12000, 14000, 16000, 18000, 20000]
    samples = 20
    df = pd.read_csv('europe.csv')
    # Standardize variables
    X = StandardScaler().fit_transform(df.iloc[:,1:8])
    neurons = 4

    avg_values = []
    std_values = []

    for item in iterations:
        errors = []
        for _ in range(samples):
            W, iter, alpha = kohonen(X, neurons, alpha=0.005, iterations=item)
            errors.append(np.sum(calculate_error(X, W, neurons, df).transpose()))
        print(errors)
        avg_values.append(np.mean(errors))
        std_values.append(np.std(errors))

        print("Average: {}".format(np.mean(errors)))
        print("Standard Deviation: {}".format(np.std(errors)))

    plt.errorbar(iterations, avg_values, yerr=std_values)
    plt.xlabel('Iteraciones')
    plt.ylabel('Error Promedio')
    plt.show()

def alpha_error_stats():

    alphas = [0.9, 0.1, 0.05, 0.01, 0.005, 0.001, 0.0005]
    samples = 20
    neurons = 4
    df = pd.read_csv('europe.csv')
    # Standardize variables
    X = StandardScaler().fit_transform(df.iloc[:,1:8])

    avg_values = []
    std_values = []

    for item in alphas:
        errors = []
        for _ in range(samples):
            W, iter, alpha = kohonen(X, neurons, alpha=item)
            errors.append(np.sum(calculate_error(X, W, neurons, df).transpose()))
        print(errors)
        avg_values.append(np.mean(errors))
        std_values.append(np.std(errors))

        print("Average: {}".format(np.mean(errors)))
        print("Standard Deviation: {}".format(np.std(errors)))

    plt.errorbar(alphas, avg_values, yerr=std_values)
    plt.xlabel('Tasa de Aprendizaje')
    plt.ylabel('Error Promedio')
    plt.xscale('log')
    plt.show()



if __name__ == '__main__':
    full_analysis(8, 0.005)
    # iteration_error_stats()
    # alpha_error_stats()