package entities;

import java.util.List;
import java.util.Random;

public class Individual {
    boolean[] bag;
    private int weightSum;
    private int benefitSum;
    private List<Item> items;
    private double mutationChance;

    public Individual(int size, List<Item> items,double mutationChance) {
        this.bag = new boolean[size];
        this.items = items;
        this.mutationChance=mutationChance;
        calculateFitness();
        calculateWeightSum();
    }

    public int getWeightSum() {
        return weightSum;
    }


    public int getBenefitSum() {
        return benefitSum;
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
        for(int i =0;i<bag.length;i++){

        }
    }

    private void calculateWeightSum(){


    }

    public boolean[] getBag() {
        return bag;
    }

    public List<Item> getItems() {
        return items;
    }

    public double getMutationChance() {
        return mutationChance;
    }
}
