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

    sns.heatmap(matrix, linewidth=0.5)
    # plt.show()
    plt.savefig('weights.png')


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

def kohonen_error(groups, data):
    error = 0

    df_ranking = pd.read_csv('ranking.csv')

    for group in groups:
        if group is None:
            continue

        countries = []
        sum = 0
        for i in range(len(group) - 1):
            country_1 = data.iloc[group[i]]['Country']
            country_2 = data.iloc[group[i+1]]['Country']
            value_1 = df_ranking.loc[df_ranking['country'] == country_1]['value'].values[0]
            value_2 = df_ranking.loc[df_ranking['country'] == country_2]['value'].values[0]
            sum += abs(value_1 - value_2)
        
        error += sum/len(group)

    return error


def __main__():
    # Initial Steps
    df = pd.read_csv('europe.csv')
    # Standardize variables
    X = StandardScaler().fit_transform(df.iloc[:,1:8])
    neurons = 4

    # Run Kohonen algorithm
    W = kohonen(X, neurons, alpha=0.01, iterations=8**2*1000)

    # Show Heat Map of weights
    weight_heat_map(W, neurons)

    # matplot clean up
    plt.clf()

    # Show quantity graph
    groups, quantity = quantity_analisis(X, W, neurons)
    sns.heatmap(quantity, linewidth=0.5, annot=True)
    # plt.show()

    print(quantity)
    plt.savefig('quantity.png')

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
        # print("Group of countries: {}".format(', '.join(countries)))
        str_countries.append("{}\n{}".format(len(group), '\n'.join(countries)))

    # transform array to matrix for heatmap
    str_countries = np.array(str_countries)
    str_countries = str_countries.reshape(neurons, neurons)

    plt.clf()
    fig, ax = plt.subplots(figsize=(13,10)) 
    sns.heatmap(quantity, linewidth=0.5, annot=str_countries, fmt = '', ax=ax)
    plt.savefig('quantity_with_countries.png')
    # plt.show()

    print(kohonen_error(groups, df))

if __name__ == '__main__':
    __main__()