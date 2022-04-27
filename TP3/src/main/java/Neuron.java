import java.util.ArrayList;
import java.util.List;

public class Neuron {
    private int id;
    private double umbral;
    private double learningRate;


    List<Connection> inputConnections;
    List<Connection> outputConnections;


    protected double excitation;

    public Neuron(List<Connection> inputConnections,double umbral, double learningRate){
        this.inputConnections=inputConnections;
        this.outputConnections=new ArrayList<>();

        excitation = 0.0;

        this.umbral = umbral;
        this.learningRate=learningRate;
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

    public void adjustWeight(double expectedResult,int index){
        //        ∆w = η ∗ (y [i x] − O).x[i x]
        Connection connection=inputConnections.get(index);
        double weight = connection.getWeight();
        weight += learningRate * (expectedResult - getActivation()) * connection.getFrom().getActivation();
        connection.setWeight(weight);



    }

}

