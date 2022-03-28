package Implementations.Selection;

import entities.Individual;
import interfaces.Selection;

import java.util.*;
import java.util.stream.Collectors;

public class EliteSelection implements Selection {
    
    @Override
    public List<Individual> select(List<Individual> generation, int WeightLimit) {

        int P = generation.size() / 2;

        List<Individual> newGeneration = generation.stream().filter(i -> i.getWeightSum() <= WeightLimit).collect(Collectors.toList());
        newGeneration.sort(Comparator.comparingInt(Individual::getBenefitSum).reversed().thenComparing(Individual::getWeightSum));

        if (newGeneration.size() < P) {
            // Add elements that have bigger weight than the limit
            generation.sort(Comparator.comparingInt(Individual::getBenefitSum).reversed().thenComparing(Individual::getWeightSum));
            Iterator<Individual> iterator = generation.iterator();
            while (newGeneration.size() < P && iterator.hasNext()) {
                newGeneration.add(iterator.next());
            }
        } else if (newGeneration.size() > P) {
            // Get the top P elements from newGeneration that have the best aptitude
            newGeneration = newGeneration.stream().limit(P).collect(Collectors.toList());
        }

        return newGeneration;
    }
}
