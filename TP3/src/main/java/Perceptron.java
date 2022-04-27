import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Perceptron {

    private List<InputNeuron> inputNeuronList;

    private Neuron outputNeuron;

    private double umbral;

    private int N=4;
    private double learningRate;
    private  int[]expectedValues={1,-1,-1,-1};

    public Perceptron(double umbral,double learningRate){
        this.umbral=umbral;
        inputNeuronList=new ArrayList<>();
        for (int i = 0; i < N; i++) {
            inputNeuronList.add(new InputNeuron(umbral,learningRate));
        }
        List<Connection> connections= new ArrayList<>();
        for (InputNeuron inputNeuron : inputNeuronList) {
            connections.add(new Connection(0, inputNeuron));
        }
        outputNeuron=new Neuron(connections,umbral,learningRate);
        connections.forEach(c ->c.setTo(outputNeuron));
        inputNeuronList.forEach(n->n.outputConnections.add(new Connection(0,n,outputNeuron)));
    }

    public List<Double> eval(){
        List<Double>weightList=new ArrayList<>();
        int error = N;
        int errorMin = N * 2;
        int count=0;
        Random random=new Random(System.currentTimeMillis());
        while (error>0&& count< 1000){
            int x=random.nextInt(N);
            for (int i = 0; i < inputNeuronList.size(); i++) {
                if (i!=x){
                    inputNeuronList.get(i).setExcitation(0);
                }else {
                    inputNeuronList.get(i).setExcitation(1);
                }
            }
            outputNeuron.calculateExcitation();
            outputNeuron.adjustWeight(expectedValues[x],x);
//            inputNeuronList.get(x).adjustWeight();
//            outputNeuron.getActivation();
            error=calculateError();
            if (error<errorMin){
                errorMin=error;
                weightList.clear();
                for (Connection c:outputNeuron.inputConnections) {
                    weightList.add(c.getWeight());
                }
            }
            count++;
        }
        return weightList;
    }

/* i = 0
w = zeros(N+1, 1)
error = 1
error min = p * 2
while error > 0 ∧ i < COTA
Tomar un n ́umero i x al azar entre 1 y p
Calcular la exitaci ́on h = x[i x].w
Calcular la activaci ́on O = signo(h)
∆w = η ∗ (y [i x] − O).x[i x]
w = w + ∆w
error = CalcularError(x, y, w, p)
if error < error min
error min = error
w min = w
end
i = i + 1
end*/


    public int calculateError(){
        int error=0;

        for (int i = 0; i < inputNeuronList.size(); i++) {
            for (int j = 0; j < inputNeuronList.size(); j++) {
                if (j!=i){
                    inputNeuronList.get(j).setExcitation(0);
                }else {
                    inputNeuronList.get(j).setExcitation(1);
                }
            }
            outputNeuron.calculateExcitation();
            error+=Math.abs(outputNeuron.getActivation()-expectedValues[i]);

        }
        return error;
    }



}
