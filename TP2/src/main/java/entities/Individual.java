package entities;
import java.util.Arrays;

public class Individual {
    boolean[] bag;
    private int weightSum;
    private double fitness;

    public Individual(boolean[] bag) {
        this.bag = Arrays.copyOf(bag, bag.length);
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
        if (weightSum > Environment.weightLimit) {
//            System.out.println(weightSum);
            fitness = 1;
            return;
        }

        fitness = 0;
        for (int i =0; i < bag.length; i++){
            if (bag[i])
                fitness += Environment.items.get(i).getBenefit();
        }

//        fitness = weightSum > Environment.weightLimit ? fitness - ((weightSum/ (double)Environment.weightLimit))*fitness*2 : fitness;
    }

    private void calculateWeightSum(){
        weightSum = 0;
        for (int i =0; i < bag.length; i++){
            if (bag[i])
                weightSum += Environment.items.get(i).getWeight();
        }
    }

    public int calculateBenefit() {
        int aux = 0;
        for (int i =0; i < bag.length; i++){
            if (bag[i])
                aux += Environment.items.get(i).getBenefit();
        }
        return aux;
    }


    @Override
    public String toString() {
        return "bag=" + Arrays.toString(bag) + "\n benefit: " + calculateBenefit() + " pro_benefit: " + fitness + "\n weight: " + weightSum + "\n";
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Individual that = (Individual) o;
        return Arrays.equals(bag, that.bag);
    }
}
