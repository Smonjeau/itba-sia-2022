package entities;

import java.util.List;

public class Individual {
    boolean[] bag;
    private int weightSum;
    private int benefitSum;
    private List<Item> items;

    public Individual(int size, List<Item> items) {
        this.bag = new boolean[size];
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


    public void mutate(){
        calculateFitness();
        calculateWeightSum();
    }
    private void calculateFitness(){
        for(int i =0;i<bag.length;i++){

        }
    }

    private void calculateWeightSum(){


    }


}
