
import pandas as pd
import numpy as np
from sklearn.decomposition import PCA
from sklearn.model_selection import train_test_split
from sklearn.preprocessing import StandardScaler
from sklearn.linear_model import LogisticRegression


df = pd.read_csv('europe.csv')
x=df.iloc[:,1:8]
x=StandardScaler().fit_transform(x)
# print(x)

pca=PCA(n_components=7)
PC = pca.fit_transform(x)


eigenvalues = pca.explained_variance_
# print(eigenvalues)
n_components = 7
for i in range(len(eigenvalues)):
    if sum(eigenvalues[:i+1])/sum(eigenvalues)>=0.95 and n_components > i+1:
        n_components=i+1
        break
# print(n_components)

pca=PCA(n_components=n_components)
PC = pca.fit_transform(x)

principalDF=pd.DataFrame(data=PC,columns=['pc'+str(i) for i in range(1,n_components+1)])
finalDf = pd.concat([principalDF, df[['Country']]], axis = 1)

# print(finalDf)

PCloadings = pca.components_.T * np.sqrt(pca.explained_variance_)
components=df.columns.tolist()
components=components[1:8]
loadingdf=pd.DataFrame(PCloadings,columns=('Y'+str(i) for i in range(1,n_components+1)))
loadingdf["variable"]=components
print(loadingdf)