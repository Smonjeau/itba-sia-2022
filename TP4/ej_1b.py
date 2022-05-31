from operator import itemgetter
import pandas as pd
import numpy as np
import matplotlib.pyplot as plt
from sklearn.preprocessing import StandardScaler

from Oja import Oja



def adjust(input):
    return input*-1

def calculate_error(ans,expected):
    ans.sort(reverse=True,key=itemgetter(0))
    expected.sort(reverse=True,key=itemgetter(0))
    sum=0
    for i in range(len(ans)):
        # test1=ans[i][1]
        # test2=expected[i][1]
        sum+=(abs(ans[i][1])-abs(expected[i][1]))**2
    return sum/len(ans)

def __main__():
    # Initial Steps
    df = pd.read_csv('TP4/europe.csv')
    # Standardize variables
    X = StandardScaler().fit_transform(df.iloc[:,1:8])
    

    # expected=[["Luxembourg",3.400088],
    # ["Switzerland",3.380378],
    # ["Norway",2.092484],
    # ["Netherlands",1.903029],
    # ["Ireland", 1.903029],
    # ["Iceland",1.431186],
    # ["Austria", 1.011233],
    # ["Denmark", 0.983107],
    # ["Italy",  0.825655],
    # ["Sweden",  0.747759],
    # ["Belgium",0.698651],
    # ["Germany",  0.523719],
    # ["United Kingdom", 0.298205],
    # ["Czech Republic", 0.249000 ],
    # ["Finland", 0.147139] ,
    # ["Slovenia", 0.117822],
    # ["Spain",  0.085145],
    # ["Portugal" ,-0.428077],
    # ["Greece",-0.703306],
    # ["Slovakia" ,-0.771452],
    # ["Croatia",-1.031940],
    # ["Hungary", -1.369848],
    # ["Lithuania", -1.516254],
    # ["Poland", -1.539187],
    # ["Latvia", -2.316856],
    # ["Estonia", -2.455120],
    # ["Bulgaria", -2.535369],
    # ["Ukraine", -5.076902] ]
   
    # expected=[["Luxembourg",-6.363435575109156],
    # ["Switzerland",-6.003321083995637],
    # ["Norway", -3.853642843624793],
    # ["Netherlands",-3.366186645344115],
    # ["Ireland", -3.309226942911436],
    # ["Iceland",-2.8972507524483198],
    # ["Austria",  -1.978945024504229],
    # ["Denmark",-1.747422382479515],
    # ["Italy",  -1.5608846459450767],
    # ["Sweden",  -1.619208260398774],
    # ["Belgium", -1.2459908781638975],
    # ["Germany",  -1.0837226777009388],
    # ["United Kingdom", -0.6234936135694673],
    # ["Czech Republic",-0.30589240693767106 ],
    # ["Finland", -0.3852034310510026] ,
    # ["Slovenia", 0.12356359199531769],
    # ["Spain", -0.29959464304460837],
    # ["Portugal" , 0.9631648886321189],
    # ["Greece",1.8302595626856784],
    # ["Slovakia" ,1.4323549459725617],
    # ["Croatia",2.323605434448649],
    # ["Hungary",  2.555480418353694],
    # ["Lithuania", 2.799158909582901],
    # ["Poland", 2.6924573981749313],
    # ["Latvia",4.2186962417313945],
    # ["Estonia", 4.5510532711909635],
    # ["Bulgaria",  4.7745023017097274],
    # ["Ukraine", 8.379124842750832] ]

    expected=[["Luxembourg",-3.47843681325094],
["Switzerland",-3.2815869603573686],
["Norway",-2.10651080280626],
["Netherlands",-1.8400536946672936],
["Ireland",-1.8089185134317805],
["Iceland",-1.5837209605731468],
["Austria",-1.0817481784252954],
["Denmark",-0.9551912033705993],
["Sweden",-0.8851050643105283],
["Italy",-0.8532236320991302],
["Belgium",-0.6810942730107582],
["Germany",-0.5923932084184644],
["United Kingdom",-0.34081919742055267],
["Finland",-0.21056280352654735],
["Czech Republic",-0.1672094682528944],
["Spain",-0.16376660326220815],
["Slovenia",0.06754382214457848],
["Portugal",0.526493784390154],
["Slovakia",0.7829655924696075],
["Greece",1.0004738552628427],
["Croatia",1.2701496907425562],
["Hungary",1.3968983827716654],
["Poland",1.4717741596661917],
["Lithuania",1.5300998527875236],
["Latvia",2.3060593532212246],
["Estonia",2.487735587635774],
["Bulgaria",2.6098797232970505],
["Ukraine",4.5802675727946704]]


    

    expected_adjusted=list()
    for i in range(len(expected)):
        expected_adjusted.append((expected[i][0],adjust(expected[i][1])))
    expected_adjusted.sort(reverse=True,key=itemgetter(1))

    # Initialize and run Oja algorithm
    cumulative_error_iter  = [[0 for _ in range(20)] for _ in range(9)]
    for i in range(20,200,20):
        for round in range(0,20):
            oja = Oja(learning_rate=0.01,inputs=X)
            oja.execute(iterations=i)
            result=oja.test(names=df['Country'])
            cumulative_error_iter[i//50 -1][round]=calculate_error(result,expected_adjusted)

    cumulative_error_learningrate  = [[0 for _ in range(20)] for _ in range(0,10)]
    for i in range(0,10):
        for round in range(0,20):
            oja = Oja(learning_rate=(i+1)*0.001,inputs=X)
            oja.execute(iterations=50)
            result=oja.test(names=df['Country'])
            cumulative_error_learningrate[i][round]=calculate_error(result,expected_adjusted)

    cumulative_error  = list()
    oja = Oja(learning_rate=0.005,inputs=X)
    for i in range(50):
        oja.execute(iterations=1)
        result=oja.test(names=df['Country'])
        cumulative_error.append(calculate_error(result,expected_adjusted))

    
    primera_componente=oja.weights
    labels = ["Area","GDP","Inflation","Life.expect","Military","Pop.growth","Unemployment"]
    for i in range(len(primera_componente)):
        print("{}={}".format(labels[i],primera_componente[i])) 

    # Pretty up the results
    # result.sort(reverse=True,key=lambda y:y[1])
    # aux=pd.DataFrame(result,columns=('Country','Value'))
    

    plt.rcParams.update({'font.size': 28})

    fig, bars= plt.subplots(nrows=1, ncols=2, figsize=(30, 18))

    primera_componente_lib=[
        0.124874,
        -0.500506,
        0.406518,
        -0.482873,
        0.188112,
        -0.475704,
        0.271656
    ]

    primera_componente_lib_adjusted=list()
    for i in range(len(primera_componente_lib)):
        primera_componente_lib_adjusted.append((adjust(primera_componente_lib[i])))


    bars[0].barh(labels,primera_componente_lib)
    bars[0].set_xlim([-1,1])
    bars[1].barh(labels,primera_componente)
    bars[1].set_xlim([-1,1])
    fig.savefig('PrimeraComponenteBar.png')
    # print("---------------------------")
    # print(cumulative_error)

    plt.clf()
    # plt.plot(range(100),cumulative_error , '-o')

    plt.rcParams.update({'font.size': 28})
    avgs=list()
    stds=list()
    for i in range(len(cumulative_error_iter)):
        avgs.append(np.mean(cumulative_error_iter[i]))
        stds.append(np.std(cumulative_error_iter[i]))
    plt.errorbar(range(20,200,20), avgs, yerr=stds)
    plt.yscale('log')
    # plt.errorbar(range(10), avgs, yerr=stds)
    plt.xlabel("Iteraciones")
    plt.ylabel("Error cuadratico total promedio")
    plt.savefig('Oja_Error_Iterations.png')
    plt.clf()

    plt.rcParams.update({'font.size': 28})
    avgs=list()
    stds=list()
    x=[0.001,0.002,0.003,0.004,0.005,0.006,0.007,0.008,0.009,0.01]
    for i in range(len(cumulative_error_learningrate)):
        avgs.append(np.mean(cumulative_error_learningrate[i]))
        stds.append(np.std(cumulative_error_learningrate[i]))
    plt.errorbar(x, avgs, yerr=stds)
    # plt.errorbar(range(10), avgs, yerr=stds)
    plt.xlabel("Learning Rate")
    plt.ylabel("Error cuadratico total promedio")
    #matplotlib increase label font
    plt.savefig('Oja_Error_learning_rate.png')
    plt.clf()


    plt.rcParams.update({'font.size': 28})
    plt.plot(range(50),cumulative_error , '-o')
    plt.xlabel("Iteracion")
    plt.ylabel("Error cuadratico total promedio")
    plt.savefig('Oja_Error_normal.png')

if __name__ == '__main__':
    __main__()

