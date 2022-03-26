package interfaces;

import entities.Individual;

import java.util.List;

@FunctionalInterface
public interface Selection {
    List<Individual> select(List<Individual> generation,int WeightLimit);
}
