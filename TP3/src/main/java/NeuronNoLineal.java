import java.util.List;

public class NeuronNoLineal extends Neuron{
    public NeuronNoLineal(List<Connection> inputConnections, double umbral, double learningRate) {
        super(inputConnections, umbral, learningRate);
    }

    @Override
    public double getActivation() {
        return NeuronNoLineal.g(excitation,1);
    }

    public static double g(double value,double beta){
        return Math.tanh(value*beta);
    }

    public static double gDeriv(double value,double beta){
        return beta*(1-Math.pow(g(value,beta),2));
    }

    @Override
    public void adjustWeight(double expectedResult,int index){
        Connection connection=inputConnections.get(index);
        double weight = connection.getWeight();
        weight += learningRate * ((expectedResult - getActivation())) *gDeriv(excitation,1)* connection.getFrom().getActivation();
        connection.setWeight(weight);
//        double weight=0;
//        for (int i = 0; i <inputConnections.size() ; i++) {
//            weight += learningRate*(expectedResult / g(excitation,1));
//        }
//        weight *= gDeriv(expectedResult,1)*connection.getFrom().getActivation();
//        weight+=connection.getWeight();
//        connection.setWeight(weight);
    }
}
