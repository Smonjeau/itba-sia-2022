import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class PerceptronLineal {

    private final List<InputNeuron> inputNeuronList;

    private final NeuronLineal outputNeuron;

    private double umbral;

    private final int N = 3;
    private double learningRate;
    private final double[] expectedOutputs;
    private final double[][] inputValues;

    public PerceptronLineal(double umbral, double learningRate, double[] expectedOutputs, double[][] inputValues){
        this.umbral = umbral;
        inputNeuronList = new ArrayList<>();

        for (int i = 0; i < inputValues[0].length; i++) {
            inputNeuronList.add(new InputNeuron(umbral, learningRate));
        }
        
        // For each input neuron a connection is created from de inputNeuron to ...
        List<Connection> connections= new ArrayList<>();
        for (InputNeuron inputNeuron : inputNeuronList) {
            connections.add(new Connection(0, inputNeuron));
        }
        
        // Output neuron created with the connections of the input neurons
        outputNeuron=new NeuronLineal(connections, umbral, learningRate);
        
        // Added to value for the connections previously created and set it to output neuron
        connections.forEach(c ->c.setTo(outputNeuron));
        
        // For each input neuron add their corresponding output connection that is in output neuron
        for (int i = 0; i < inputNeuronList.size(); i++) {
            //TODO hacer este lookup de forma segura
            inputNeuronList.get(i).outputConnections.add(outputNeuron.inputConnections.get(i));
        }
        
        this.expectedOutputs = expectedOutputs;
        this.inputValues = inputValues;
    }
//    public double g(double input,double beta){
//        return Math.tanh(input*beta);
//    }

    public List<Double> eval(){
        List<Double> weightList = new ArrayList<>();
        double error = 1;
        double errorMin = Double.MAX_VALUE;
        int count = 0;
        Random random = new Random(System.currentTimeMillis());

        while (error > 0 && count < 1000000){

            int run = random.nextInt(inputValues.length);

            for (int i = 0; i < inputValues[0].length; i++) {
                inputNeuronList.get(i).setExcitation(inputValues[run][i]);
            }

            outputNeuron.calculateExcitation();

            for (int i = 0; i < inputValues[run].length; i++) {
                outputNeuron.adjustWeight(expectedOutputs[run],i);
            }



//            inputNeuronList.get(x).adjustWeight();
//            outputNeuron.getActivation();
            error = calculateError();
            if (error < errorMin){
                errorMin = error;
                weightList.clear();
                for (Connection c:outputNeuron.inputConnections) {
                    weightList.add(c.getWeight());
                }
            }
            count++;
        }
        if(count == 1000000){
            System.out.println("exit due to iteration limit");

        }

        System.out.println(error);
        return weightList;
    }




    public double calculateError(){
        double error=0;

        for (int i = 0; i < inputValues.length; i++) {
            for (int j = 0; j < inputValues[i].length; j++) {

                inputNeuronList.get(j).setExcitation(inputValues[i][j]);
            }
            outputNeuron.calculateExcitation();
//            error+=Math.abs(outputNeuron.getActivation()-NeuronG.g(expectedOutputs[i],1))/2;
            error+=Math.pow(expectedOutputs[i]-outputNeuron.getActivation(),2);
        }
        return error;
    }



}
