import java.util.ArrayList;
import java.util.List;

public class Neuron2 {

    private double value;
    private double[] weights;

    public Neuron2(int prevLayerSize) {
        this.weights = new double[prevLayerSize];

    }

    public double getValue() {
        return value;
    }

    public void setValue(double value) {
        this.value = value;
    }

    public double[] getWeights() {
        return weights;
    }

    public void setWeights(double[] weights) {
        this.weights = weights;
    }
}
