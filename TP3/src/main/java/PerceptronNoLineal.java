import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class PerceptronNoLineal {

    private final List<InputNeuron> inputNeuronList;

    private final NeuronNoLineal outputNeuron;

    private double umbral;

    private final int N = 3;
    private double learningRate;
    private final double[] expectedOutputs;
    private final double[][] inputValues;
    private static final double DELTA=0.001;
    private static final int LIMIT=500000;

    public PerceptronNoLineal(double umbral, double learningRate, double[] expectedOutputs, double[][] inputValues){
        this.umbral = umbral;
        inputNeuronList = new ArrayList<>();
        this.learningRate=learningRate;

        for (int i = 0; i < inputValues[0].length; i++) {
            inputNeuronList.add(new InputNeuron(umbral, learningRate));
        }

        // For each input neuron a connection is created from de inputNeuron to ...
        List<Connection> connections= new ArrayList<>();
        for (InputNeuron inputNeuron : inputNeuronList) {
            connections.add(new Connection(1, inputNeuron));
        }

        // Output neuron created with the connections of the input neurons
        outputNeuron=new NeuronNoLineal(connections, umbral, learningRate);

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

        while (error > DELTA && count < LIMIT){

            int run = random.nextInt(inputValues.length);

            for (int i = 0; i < inputValues[0].length; i++) {
                inputNeuronList.get(i).setExcitation(inputValues[run][i]);
            }

            outputNeuron.calculateExcitation();


            if (Math.abs(outputNeuron.getActivation()-expectedOutputs[run])>DELTA){
                for (int i = 0; i < inputValues[run].length; i++) {
                    outputNeuron.adjustWeight(expectedOutputs[run],i);
                }

//                for (int k = 0; k <inputValues[0].length ; k++) {
//                    double suma=0;
//                    for (int i = 0; i < inputValues.length; i++) {
//                        double h=0;
//                        for (int j = 0; j < inputValues[i].length; j++) {
//                            inputNeuronList.get(k).setExcitation(inputValues[i][j]);
//                        }
//                        outputNeuron.calculateExcitation();
//                        double gh=outputNeuron.getActivation();
//                        double gDeriv=NeuronNoLineal.gDeriv(gh,1);
//                        suma+=learningRate*(expectedOutputs[i]-gh)*gDeriv*inputValues[i][k];
////                        System.out.println(suma);
//                    }
//                    suma+=outputNeuron.inputConnections.get(k).getWeight();
//                    outputNeuron.inputConnections.get(k).setWeight(suma);
//                }


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
        if(count == LIMIT){
            System.out.println("exit due to iteration limit");

        }
        System.out.println(errorMin);
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
            error+=0.5*Math.pow(expectedOutputs[i]-outputNeuron.getActivation(),2);
        }
        return error;
    }



}
