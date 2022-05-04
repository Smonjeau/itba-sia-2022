import java.util.Random;

public class Neuron {

    private double value;
    private double[] weights;
    private double delta;
    public Neuron(int prevLayerSize) {
        this.weights = new double[prevLayerSize];
        Random r = new Random(System.currentTimeMillis());
        for (int i = 0; i < prevLayerSize ; i++) {
            weights[i] = r.nextDouble()*0.2;
        }
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

    public double getDelta() {
        return delta;
    }

    public void setDelta(double delta) {
        this.delta = delta;
    }
}
