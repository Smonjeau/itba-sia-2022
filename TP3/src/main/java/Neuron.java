import java.util.List;

public class Neuron {
    private int id;
    private double umbral;


    List<Connection> inputConnections;
    List<Connection> outputConnections;


    protected double excitation;

    public Neuron(List<Connection> inputConnections, List<Connection> outputConnections,double umbral){
        this.inputConnections=inputConnections;
        this.outputConnections=outputConnections;

        excitation = 0.0;

        this.umbral = umbral;
    }

    public void calculateExcitation(){
        double sum = 0.0;
        for(Connection connection : inputConnections){
            sum += connection.getWeight()*connection.getFrom().getActivation();
        }
        this.excitation=sum;

    }


    public double getActivation(){
        return excitation - umbral >= 0 ? 1.0 : -1.0;
    }

}

