import numpy as np
import random

class Oja:
    def __init__(self,learning_rate,inputs):
        self.size=inputs.shape[1]
        
        X2D = [item for sublist in inputs for item in sublist]
        self.weights = random.choices(X2D, k=self.size) # is a vector
        # print(self.weights)
       
        # self.weights=np.ones(self.size);


        # for i in range(self.size):
        #     self.weights[i]*=np.random.uniform(0.5,1)
        self.inputs=inputs
        self.learning_rate=learning_rate;

    def calculate_activation(self,inputs):
        sum=0;
        for i in range(self.size):
            sum+=inputs[i]*self.weights[i];
        return sum;

    def update_weights(self,activation,inputs):
        for i in range(len(self.weights)):
            self.weights[i]+=self.learning_rate*(activation*inputs[i]-activation**2*self.weights[i])

    def execute(self,iterations):
        for iter in range(iterations):
            for i in range(self.inputs.shape[0]):
                activation=self.calculate_activation(inputs=self.inputs[i])
                self.update_weights(activation=activation,inputs=self.inputs[i])
    
    def test(self,names):
        result=list()
        for i in range(self.inputs.shape[0]):
            result.append((names[i],self.calculate_activation(self.inputs[i])))
        return result;

    
