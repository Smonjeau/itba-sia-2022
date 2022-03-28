package entities;

import java.util.Arrays;
import java.util.List;

public class Individual {
    boolean[] bag;
    private int weightSum;
    private int benefitSum;
    private final List<Item> items;

    public Individual(boolean[] bag, List<Item> items) {
        this.bag = Arrays.copyOf(bag, bag.length);
        this.items = items;
        calculateFitness();
        calculateWeightSum();
    }

    public int getWeightSum() {
        return weightSum;
    }

    public int getBenefitSum() {
        return benefitSum;
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
        calculateFitness();
        calculateWeightSum();
    }
    private void calculateFitness(){
        benefitSum = 0;
        for (int i =0; i < bag.length; i++){
            if (bag[i])
                benefitSum += items.get(i).getBenefit();
        }
    }

    private void calculateWeightSum(){
        weightSum = 0;
        for (int i =0; i < bag.length; i++){
            if (bag[i])
                weightSum += items.get(i).getWeight();
        }

    }

    @Override
    public String toString() {
        return "bag=" + Arrays.toString(bag) + " benefit: " + benefitSum + " weight: " + weightSum + "\n";
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Individual that = (Individual) o;
        return Arrays.equals(bag, that.bag);
    }
}
