import java.util.List;

public class NeuronNoLineal extends Neuron{
    public NeuronNoLineal(List<Connection> inputConnections, double umbral, double learningRate) {
        super(inputConnections, umbral, learningRate);
    }

    @Override
    public double getActivation() {
        return NeuronNoLineal.g(excitation - umbral,1);
    }

    public static double g(double value,double beta){
        return Math.tanh(value*beta);
    }
}
