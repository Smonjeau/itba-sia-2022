import java.util.Arrays;
import java.util.List;

public class MultiLayerPerceptron implements PerceptronInterface{

    private Layer[] layers;
    private double learningRate;

    private static double beta = 1;
    public static double g(double val){
        return Math.tanh(val*beta);
    }

    public static double gDeriv(double val){
        return beta*(1- Math.pow(g(val),2));
    }

    public MultiLayerPerceptron(int[] layersSize, double learningRate) {

        this.learningRate = learningRate;
        layers = new Layer[layersSize.length];

        for (int i = 0; i <layers.length ; i++) {
            if(i==0)
                layers[i] = new Layer(layersSize[i]+1,0);
            else
                layers[i] = new Layer(layersSize[i]+1,layersSize[i-1]+1);


        }
        this.learningRate = learningRate;
    }

    @Override
    public double train(List<Row> input, double[][]expectedOutputs) {
        int outputIdx = 0;
        double[][] calculatedOutputs = new double[input.size()][];

        for(Row row : input) {
            double[] auxInput = new double[row.getValues().size()];
            for (int i = 0; i < row.getValues().size(); i++) {
                auxInput[i] = row.getValues().get(i);
            }

            //TODO revisar los inputs
            calculatedOutputs[outputIdx] = this.eval(auxInput);
            for (int i = 0; i < layers[layers.length - 1].getLayerSize() - 1 ; i++) {
                double delta = delta(expectedOutputs[outputIdx][i]-calculatedOutputs[outputIdx][i] ,
                        calculatedOutputs[outputIdx][i]);
                layers[layers.length - 1].getNeurons()[i+1].setDelta(delta);
            }

            //Recorremos desde la primer capa intermedia para abajo
            for (int layer = layers.length-2; layer >=0 ; layer--) {
                //vamos propagando el delta
                for(int weightIdx = 1;weightIdx<layers[layer].getLayerSize();weightIdx++){
                    double errorSum = 0.0;
                    for(int neuronIdx = 1; neuronIdx < layers[layer + 1].getLayerSize(); neuronIdx++)
                        errorSum += layers[layer + 1].getNeurons()[neuronIdx].getDelta()
                                * layers[layer + 1].getNeurons()[neuronIdx].getWeights()[weightIdx];
                    layers[layer].getNeurons()[weightIdx].setDelta(errorSum*gDeriv(layers[layer].
                            getNeurons()[weightIdx].getValue()));
                }
                //actualizamos pesos
                for(int neuronIdx=1;neuronIdx<layers[layer+1].getLayerSize();neuronIdx++){
                    for(int i=0;i<layers[layer].getLayerSize();i++){
                        layers[layer+1].getNeurons()[neuronIdx].getWeights()[i] +=
                                learningRate * layers[layer+1].getNeurons()[neuronIdx].getDelta()
                                * layers[layer].getNeurons()[i].getValue();

                    }
                }
            }
            outputIdx++;
        }
        //vemos el error
        double error = 0;
        for (int i =0;i<calculatedOutputs.length;i++) {
            for (int j = 0; j < expectedOutputs[i].length; j++) {
                error += Math.pow(expectedOutputs[i][j] - calculatedOutputs[i][j], 2) / 2;
            }

        }


        return error;



    }

    @Override
    public double[] eval(double[] inputs) {


        double[] toReturn = new double[layers[layers.length-1].getLayerSize()-1];



        setBiases(-1);
        // Seteamos el input
        for(int i = 1; i < layers[0].getLayerSize(); i++)
            layers[0].getNeurons()[i].setValue(inputs[i-1]);


        for(int k = 1; k < layers.length; k++) {

            //salteamos la primera porque es la del umbral
            for(int i = 1; i < layers[k].getLayerSize(); i++) {

                double newValue = 0.0;
                for(int j = 0; j < layers[k - 1].getLayerSize(); j++)
                    newValue += layers[k].getNeurons()[i].getWeights()[j] * layers[k - 1].getNeurons()[j].getValue();

                layers[k].getNeurons()[i].setValue(g(newValue));
            }
        }


        // Get output
        for(int i = 1; i < layers[layers.length - 1].getLayerSize(); i++) {
            toReturn[i-1] = layers[layers.length - 1].getNeurons()[i].getValue();
        }

        return toReturn;
    }

    private void setBiases(double bias) {
        for (Layer layer : layers) {
            layer.getNeurons()[0].setValue(bias);
        }
    }

    private double delta(double error,double val){
        return error * gDeriv(val);
    }
}
