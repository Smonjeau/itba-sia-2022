package Implementations.Selection;
import entities.Individual;
import interfaces.Selection;
import java.util.ArrayList;
import java.util.List;

public class BoltzmannSelection implements Selection {

    private double T;

    public BoltzmannSelection(int T) {
        this.T = T;
    }

    @Override
    public List<Individual> select(List<Individual> generation, int WeightLimit) {
        Double sum = generation.stream().mapToDouble(i -> Math.exp(i.getBenefitSum() / T)).sum();

        List<Individual> newGeneration = new ArrayList<>();

        for (Individual individual : generation) {
            double probability = Math.exp(individual.getBenefitSum()/T)/ sum;
            if (Math.random() >= probability) {
                newGeneration.add(individual);
            }
        }

        T -= 10;

        return newGeneration;
    }
}



