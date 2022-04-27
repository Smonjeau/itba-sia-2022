import java.util.List;

public class NeuronLineal extends Neuron{
    public NeuronLineal(List<Connection> inputConnections, double umbral, double learningRate) {
        super(inputConnections, umbral, learningRate);
    }

    @Override
    public double getActivation() {
        return excitation;
    }
    @Override
    public void adjustWeight(double expectedResult,int index){
        Connection connection=inputConnections.get(index);
        double weight = connection.getWeight();
        weight += learningRate * ((expectedResult - getActivation())) * connection.getFrom().getActivation();
        connection.setWeight(weight);
    }
}
