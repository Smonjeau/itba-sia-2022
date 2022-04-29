import java.util.ArrayList;
import java.util.List;

public class MultiLayerPerceptron implements PerceptronInterface{

    private Layer[] layers;
    private double learningRate;

    private static double beta = 1;
    public static double g(double val){
        return Math.tanh(val*beta);
    }

    public MultiLayerPerceptron(int[] layersSize, double learningRate) {

        this.learningRate = learningRate;
        layers = new Layer[layersSize.length];

        for (int i = 0; i <layers.length ; i++) {
            if(i==0)
                layers[i] = new Layer(layersSize[i]+1,0);
            else
                layers[i] = new Layer(layersSize[i]+1,layersSize[i-1]);


        }
        this.learningRate = learningRate;
    }

    @Override
    public double train(Row input, double[] expectedOutputs) {

//
//        double error;
//        double[] aux = new double[input.getValues().size()];
//        for (int k = 0; k < aux.length ; k++)
//            aux[k] = input.getValues().get(k);
//
//        double[] result = eval(aux);
//
//        //error
//        for (int j = 0; j <layers[layers.length-1].getLayerSize() ; j++) {
//
//        }
    return 0;

    }

    @Override
    public double[] eval(double[] inputs) {


        double[] toReturn = new double[layers[layers.length-1].getLayerSize()];


        // Seteamos el input
        double bias = -1;
        layers[0].getNeurons()[0].setValue(bias);
        for(int i = 1; i < layers[0].getLayerSize(); i++)
            layers[0].getNeurons()[i].setValue(inputs[i]);


        // Execute - hiddens + output
        for(int k = 1; k < layers.length; k++)
        {
            for(int i = 0; i < layers[k].getLayerSize(); i++)
            {

                double newValue = 0.0;
                for(int j = 0; j < layers[k - 1].getLayerSize(); j++)
                    newValue += layers[k].getNeurons()[i].getWeights()[j] * layers[k - 1].getNeurons()[j].getValue();

                //bias
                newValue += layers[k].getNeurons()[0].getValue();

                layers[k].getNeurons()[i].setValue(g(newValue));
            }
        }


        // Get output
        for(int i = 0; i < layers[layers.length - 1].getLayerSize(); i++)
        {
            toReturn[i] = layers[layers.length - 1].getNeurons()[i].getValue();
        }

        return toReturn;
    }

}
