public class Connection {

    private double weight;

    private Neuron from;
    private Neuron to;

    public Connection(double weight, Neuron from, Neuron to) {
        this.weight = weight;
        this.from = from;
        this.to = to;
    }

    public Connection(double weight, Neuron from) {
        this.weight = weight;
        this.from = from;
        this.to = null;
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
        if (weight<0)
            this.weight=0;
        else if (weight>1)
            this.weight=1;
        else
            this.weight = weight;
    }

    public void setTo(Neuron to) {
        this.to = to;
    }
}
