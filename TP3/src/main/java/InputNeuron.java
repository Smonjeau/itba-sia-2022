import java.util.List;

public class InputNeuron extends Neuron{

    public InputNeuron(double umbral,double learningRate) {
        super(null, umbral,learningRate);
    }


    @Override
    public double getActivation(){
        return excitation;
    }


    public void setExcitation(double excitation) {
        this.excitation=excitation;
    }
}
