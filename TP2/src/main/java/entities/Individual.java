package entities;

import java.util.Arrays;
import java.util.List;

public class Individual {
    boolean[] bag;
    private int weightSum;
    private double fitness;
    private final List<Item> items;

    public Individual(boolean[] bag, List<Item> items) {
        this.bag = Arrays.copyOf(bag, bag.length);
        this.items = items;
        calculateWeightSum();
        calculateFitness();
    }

    public int getWeightSum() {
        return weightSum;
    }

    public double getFitness() {
        return fitness;
    }

    public void setFitness(double fitness) {
        this.fitness = fitness;
    }

    public boolean[] getBag() {
        return bag;
    }

    public List<Item> getItems() {
        return items;
    }

    public void mutate(double mutationChance){
        for (int i=0; i < bag.length; i++){
            if (Math.random() < mutationChance){
                bag[i]= !bag[i];
            }
        }
        calculateWeightSum();
        calculateFitness();
    }
    private void calculateFitness(){
        fitness = 0;
        for (int i =0; i < bag.length; i++){
            if (bag[i])
                fitness += items.get(i).getBenefit();
        }

        fitness = weightSum > Environment.weightLimit ? fitness - ((weightSum/ (double)Environment.weightLimit) - 1)*fitness*2 : fitness;
    }

    private void calculateWeightSum(){
        weightSum = 0;
        for (int i =0; i < bag.length; i++){
            if (bag[i])
                weightSum += items.get(i).getWeight();
        }
    }

    private int calculateBenefit() {
        int aux = 0;
        for (int i =0; i < bag.length; i++){
            if (bag[i])
                aux += items.get(i).getBenefit();
        }
        return aux;
    }

    @Override
    public String toString() {

        return "bag=" + Arrays.toString(bag) + "\n benefit: " + calculateBenefit() + "\n weight: " + weightSum + "\n";
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Individual that = (Individual) o;
        return Arrays.equals(bag, that.bag);
    }
}
