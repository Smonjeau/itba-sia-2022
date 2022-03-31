package Implementations.Selection;
import entities.Individual;
import interfaces.Selection;
import java.util.ArrayList;
import java.util.List;

public class BoltzmannSelection implements Selection {

    private final double T0;
    private final double TC = 0;
    private final double k;
    private int t = 0;

    public BoltzmannSelection(double T, double k) {
        this.T0 = T;
        this.k = k;
    }

    @Override
    public List<Individual> select(List<Individual> generation) {
        double T = getT();

        double sum = generation.stream().mapToDouble(i -> Math.exp(i.getFitness() / T)).sum();

        generation.forEach(i -> i.setFitness(Math.exp(i.getFitness()/T)/ sum));

        RouletteWheelSelection rouletteWheelSelection = new RouletteWheelSelection();
        List<Individual> newGeneration = rouletteWheelSelection.select(generation);


        t++;

        return newGeneration;
    }

    private double getT() {
        return TC + (T0 - TC)*Math.exp(-k*t);
    }
}



