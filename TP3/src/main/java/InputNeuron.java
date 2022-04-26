import java.util.List;

public class InputNeuron extends Neuron{

    public InputNeuron(List<Connection> inputConnections, List<Connection> outputConnections, double umbral) {
        super(inputConnections, outputConnections, umbral);
    }


    @Override
    public double getActivation(){
        return excitation;
    }


    public void setExcitation(double excitation) {
        this.excitation=excitation;
    }
}
