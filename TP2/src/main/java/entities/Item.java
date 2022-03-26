package entities;

public class Item {
    private int weight;
    private int benefit;

    public Item(int weight, int benefit) {
        this.weight = weight;
        this.benefit = benefit;
    }

    public int getWeight() {
        return weight;
    }

    public int getBenefit() {
        return benefit;
    }
}
