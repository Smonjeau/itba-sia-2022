import pandas as pd
from sklearn.preprocessing import StandardScaler

from Oja import Oja



def __main__():
    # Initial Steps
    df = pd.read_csv('TP4/europe.csv')
    # Standardize variables
    X = StandardScaler().fit_transform(df.iloc[:,1:8])
    

    # Initialize and run Oja algorithm
    oja = Oja(learning_rate=0.01,inputs=X)
    oja.execute(iterations=1000)
    result=oja.test(names=df['Country'])

    # Pretty up the results
    result.sort(reverse=True,key=lambda y:y[1])
    aux=pd.DataFrame(result,columns=('Country','Value'))
    print(aux)


if __name__ == '__main__':
    __main__()