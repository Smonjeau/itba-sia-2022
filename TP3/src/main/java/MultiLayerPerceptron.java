import java.util.ArrayList;
import java.util.List;

public class MultiLayerPerceptron implements PerceptronInterface{

    private Layer[] layers;
    private double learningRate;


    public MultiLayerPerceptron(int[] layersSize, double learningRate) {

        layers = new Layer[layersSize.length];

        for (int i = 0; i <layers.length ; i++) {
            if(i==0)
                layers[i] = new Layer(layersSize[i],0);
            else
                layers[i] = new Layer(layersSize[i],layersSize[i-1]);


        }
        this.learningRate = learningRate;
    }

    @Override
    public List<Layer> train(double[] inputs, double[] output) {
        return null;
    }

    @Override
    public double eval(double[] inputs) {
        return 0;
    }
}
