import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class PerceptronLineal {

    private List<InputNeuron> inputNeuronList;

    private NeuronLineal outputNeuron;

    private double umbral;

    private int N=3;
    private double learningRate;
    private double[]expectedOutputs;
    private double[][]expectedInputs;

    public PerceptronLineal(double umbral, double learningRate, double[]expectedOutputs, double[][]expectedInputs){
        this.umbral=umbral;
        inputNeuronList=new ArrayList<>();
        for (int i = 0; i < expectedInputs[0].length; i++) {
            inputNeuronList.add(new InputNeuron(umbral,learningRate));
        }
        List<Connection> connections= new ArrayList<>();
        for (InputNeuron inputNeuron : inputNeuronList) {
            connections.add(new Connection(0, inputNeuron));
        }
        outputNeuron=new NeuronLineal(connections,umbral,learningRate);
        connections.forEach(c ->c.setTo(outputNeuron));
        for (int i = 0; i < inputNeuronList.size(); i++) {
            //TODO hacer este lookup de forma segura
            inputNeuronList.get(i).outputConnections.add(outputNeuron.inputConnections.get(i));
        }
        this.expectedOutputs=expectedOutputs;
        this.expectedInputs=expectedInputs;
    }
//    public double g(double input,double beta){
//        return Math.tanh(input*beta);
//    }

    public List<Double> eval(){
        List<Double>weightList=new ArrayList<>();
        double error=1;
        double errorMin = Double.MAX_VALUE;
        int count=0;
        Random random=new Random(System.currentTimeMillis());
        while (error>0&& count< 1000000){
            int run=random.nextInt(expectedInputs.length);
            for (int i = 0; i < expectedInputs[0].length; i++) {
                inputNeuronList.get(i).setExcitation(expectedInputs[run][i]);
            }
            outputNeuron.calculateExcitation();

            for (int i = 0; i < expectedInputs[run].length; i++) {
                outputNeuron.adjustWeight(expectedOutputs[run],i);
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
        if(count==1000000){
            System.out.println("exit due to iteration limit");

        }

        System.out.println(error);
        return weightList;
    }




    public double calculateError(){
        double error=0;

        for (int i = 0; i < expectedInputs.length; i++) {
            for (int j = 0; j < expectedInputs[i].length; j++) {

                inputNeuronList.get(j).setExcitation(expectedInputs[i][j]);
            }
            outputNeuron.calculateExcitation();
//            error+=Math.abs(outputNeuron.getActivation()-NeuronG.g(expectedOutputs[i],1))/2;
            error+=Math.pow(expectedOutputs[i]-outputNeuron.getActivation(),2);
        }
        return error;
    }



}
