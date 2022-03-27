package entities;

public class Item {
    private final int weight;
    private final int benefit;

    public Item(int benefit, int weight) {
        this.weight = weight;
        this.benefit = benefit;
    }

    public int getWeight() {
        return weight;
    }

    public int getBenefit() {
        return benefit;
    }

    @Override
    public String toString() {
        return "Item{" +
                "weight=" + weight +
                ", benefit=" + benefit +
                '}';
    }
}
