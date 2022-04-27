import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Perceptron {

    private List<InputNeuron> inputNeuronList;

    private Neuron outputNeuron;
    private double umbral;
    private double learningRate;
    private int[]expectedOutputs;
    private int[][]expectedInputs;

    public Perceptron(double umbral,double learningRate,int[]expectedOutputs,int[][]expectedInputs){
        this.umbral=umbral;
        inputNeuronList=new ArrayList<>();
        for (int i = 0; i < expectedInputs[0].length; i++) {
            inputNeuronList.add(new InputNeuron(umbral,learningRate));
        }
        List<Connection> connections= new ArrayList<>();
        for (InputNeuron inputNeuron : inputNeuronList) {
            connections.add(new Connection(0, inputNeuron));
        }
        outputNeuron=new Neuron(connections,umbral,learningRate);
        connections.forEach(c ->c.setTo(outputNeuron));
        for (int i = 0; i < inputNeuronList.size(); i++) {
            //TODO hacer este lookup de forma segura
            inputNeuronList.get(i).outputConnections.add(outputNeuron.inputConnections.get(i));
        }
        this.expectedOutputs=expectedOutputs;
        this.expectedInputs=expectedInputs;
    }

    public List<Double> eval(){
        List<Double>weightList=new ArrayList<>();
        int error = 1;
        int errorMin = expectedInputs.length * 2;
        int count=0;
        Random random=new Random(System.currentTimeMillis());
        while (error>0&& count< 1000){
            int run=random.nextInt(expectedInputs.length);
            for (int i = 0; i < expectedInputs[0].length; i++) {
                inputNeuronList.get(i).setExcitation(expectedInputs[run][i]);
            }
            outputNeuron.calculateExcitation();
            if (outputNeuron.getActivation()!=expectedOutputs[run]){
                for (int i = 0; i < expectedInputs[run].length; i++) {
                    outputNeuron.adjustWeight(expectedOutputs[run],i);
                }
            }


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
        if(count==1000)
            System.out.println("exit due to iteration limit");
        return weightList;
    }


    public int calculateError(){
        int error=0;

        for (int i = 0; i < expectedInputs.length; i++) {
            for (int j = 0; j < expectedInputs[i].length; j++) {

                inputNeuronList.get(j).setExcitation(expectedInputs[i][j]);
            }
            outputNeuron.calculateExcitation();
            error+=Math.abs(expectedOutputs[i]-outputNeuron.getActivation())/2;

        }
        return error;
    }



}
