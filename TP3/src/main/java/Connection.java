public class Connection {

    private double weight;

    private Neuron from;
    private Neuron to;

    public Connection(double weight, Neuron from, Neuron to) {
        this.weight = weight;
        this.from = from;
        this.to = to;
    }

    public double getWeight() {
        return weight;
    }

    public Neuron getFrom() {
        return from;
    }

    public Neuron getTo() {
        return to;
    }

    public void setWeight(double weight) {
        this.weight = weight;
    }
}
