package entities;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.Random;

public class Individual {
    boolean[] bag;
    private int weightSum;
    private int benefitSum;
    private final List<Item> items;
    private static double mutationChance;

    public Individual(boolean[] bag, List<Item> items) {
        this.bag = Arrays.copyOf(bag, bag.length);
        this.items = items;
        calculateFitness();
        calculateWeightSum();
    }

    public static void setMutationChance(double mutationChance){
        Individual.mutationChance =mutationChance;
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

    public void mutate(){
        Random random=new Random(System.currentTimeMillis());
        for (int i=0;i< bag.length;i++){
            if (random.nextDouble()<mutationChance){
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
        this.benefitSum = sum;
    }

    private void calculateWeightSum(){
        weightSum = 0;
        for (int i =0; i < bag.length; i++){
            if (bag[i])
                weightSum += items.get(i).getWeight();
        }

    }

    public double getMutationChance() {
        return mutationChance;
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
