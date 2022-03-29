package Implementations.Selection;

import entities.Individual;
import interfaces.Selection;

import java.util.*;
import java.util.stream.Collectors;

public class EliteSelection implements Selection {
    
    @Override
    public List<Individual> select(List<Individual> generation) {

        int P = generation.size() / 2;

        List<Individual> newGeneration = generation;
        newGeneration.sort(Comparator.comparingDouble(Individual::getFitness).reversed());

        newGeneration = newGeneration.stream().limit(P).collect(Collectors.toList());

        return newGeneration;
    }
}
