from operator import itemgetter
import pandas as pd
import numpy as np
import matplotlib.pyplot as plt
from sklearn.preprocessing import StandardScaler

from Oja import Oja



def calculate_error(ans,expected):
    ans.sort(reverse=True,key=itemgetter(0))
    expected.sort(reverse=True,key=itemgetter(0))
    sum=0
    for i in range(len(ans)):
        # test1=ans[i][1]
        # test2=expected[i][1]
        sum+=(ans[i][1]-expected[i][1])**2
    return sum/len(ans)

def __main__():
    # Initial Steps
    df = pd.read_csv('TP4/europe.csv')
    # Standardize variables
    X = StandardScaler().fit_transform(df.iloc[:,1:8])
    

    expected=[["Luxembourg",3.400088],
    ["Switzerland",3.380378],
    ["Norway",2.092484],
    ["Netherlands",1.903029],
    ["Ireland", 1.903029],
    ["Iceland",1.431186],
    ["Austria", 1.011233],
    ["Denmark", 0.983107],
    ["Italy",  0.825655],
    ["Sweden",  0.747759],
    ["Belgium",0.698651],
    ["Germany",  0.523719],
    ["United Kingdom", 0.298205],
    ["Czech Republic", 0.249000 ],
    [" Finland", 0.147139] ,
    ["Slovenia", 0.117822],
    ["Spain",  0.085145],
    ["Portugal" ,-0.428077],
    ["Greece",-0.703306],
    ["Slovakia" ,-0.771452],
    ["Croatia",-1.031940],
    ["Hungary", -1.369848],
    ["Lithuania", -1.516254],
    ["Poland", -1.539187],
    ["Latvia", -2.316856],
    ["Estonia", -2.455120],
    ["Bulgaria", -2.535369],
    [" Ukraine", -5.076902] ]
   
    # Initialize and run Oja algorithm
    cumulative_error = list()
    oja = Oja(learning_rate=0.01,inputs=X)
    for i in range(100):
        oja.execute(iterations=1)
        result=oja.test(names=df['Country'])
        cumulative_error.append(calculate_error(result,expected))

    
    primera_componente=oja.weights
    lista=["Area","GDP","Inflation","Life.expect","Military","Pop.growth","Unemployment"]
    for i in range(len(primera_componente)):

        print("{}={}".format(lista[i],primera_componente[i])) 

    # Pretty up the results
    result.sort(reverse=True,key=lambda y:y[1])
    aux=pd.DataFrame(result,columns=('Country','Value'))
    
    print(aux)

    print("---------------------------")
    print(cumulative_error)

    plt.plot(range(100),cumulative_error , '-o')
    plt.xlabel("Iteration")
    plt.ylabel("Avg Total Error")
    plt.savefig('quantity.png')


if __name__ == '__main__':
    __main__()

