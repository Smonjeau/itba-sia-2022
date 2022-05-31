import pandas as pd
import numpy as np
import random
import math
import matplotlib.pyplot as plt
import seaborn as sns


#------------ Get Neighbours ------------#
def get_neighbours(W, k, R, pos):
    neighbours = []
    # Get distance between two points
    for i in range(len(W)):
        dist = math.dist(pos, [i%k, i//k])
        if dist <= R and pos != [i%k, i//k]:
            neighbours.append(i)
            
    return neighbours


#------------ Kohonen Algorithm ------------#
def kohonen(X, k, iterations=None, R=None, alpha=0.9, constant_alpha=False, constant_R=False):
    # Initialize weights with random values from X
    X2D = [item for sublist in X for item in sublist]
    W = [random.choices(X2D, k=len(X[0])) for _ in range(k**2)] # is a vector

    if iterations is None:
        iterations = (k**2)*500
    iter0 = iterations

    alpha0 = alpha
    alpha_rate = 2*(alpha - 0.0001)/iterations

    # Initialize radius to matrix full size
    if R is None:
        R = int((2*k**2)**(1/2))
    radius_rate = R/iterations

    while iterations > 0:
        # Randomly select a point from X
        i = random.randint(0, len(X) - 1)
        x = X[i]

        # Find the closest neuron
        winner = np.argmin(np.linalg.norm(W - x, axis=1))

        # Finde the neighbours of the winner neuron
        neighbours = get_neighbours(W, k, R, [winner%k, winner//k])

        # Update radius
        if R > 1 and not constant_R:
            R -= radius_rate

        if alpha > 0.0001 and not constant_alpha:
            alpha -= alpha_rate

        # Update weights
        for index in neighbours:
            W[index] += alpha * (x - W[index])

        iterations -= 1

    return W, iter0, alpha0
    
