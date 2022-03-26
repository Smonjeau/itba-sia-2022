package interfaces;

import entities.Individual;

@FunctionalInterface
public interface Pairing {
    Individual matchIndividuals(Individual i1, Individual i2);

}
