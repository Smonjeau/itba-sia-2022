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


def quantity_graph(X, W, k):
    quantity = np.zeros((k, k))
    groups = [None for _ in range(k**2)]
    for i in range(len(X)):
        winner = np.argmin(np.linalg.norm(W - X[i], axis=1))
        if groups[winner] is None:
            groups[winner] = list()
        groups[winner].append(i)
        quantity[winner%k, winner//k] += 1

    sns.heatmap(quantity, linewidth=0.5, annot=True)
    # plt.show()
    plt.savefig('quantity.png')

    return groups



def __main__():
    # Initial Steps
    df = pd.read_csv('europe.csv')
    # Standardize variables
    X = StandardScaler().fit_transform(df.iloc[:,1:8])
    neurons = 3

    # Run Kohonen algorithm
    W = kohonen(X, neurons, alpha=0.01)

    # Show Heat Map of weights
    weight_heat_map(W, neurons)

    # Show quantity graph
    groups = quantity_graph(X, W, neurons)
    for group in groups:
        if group is None:
            continue

        countries = []
        for item in group:
            countries.append(df.iloc[item]['Country'])
        print("Group of countries: {}".format(', '.join(countries)))

if __name__ == '__main__':
    __main__()