package interfaces;

import entities.Individual;

import java.util.List;

@FunctionalInterface
public interface Pairing {
    Individual[] matchIndividuals(Individual i1, Individual i2);

}
