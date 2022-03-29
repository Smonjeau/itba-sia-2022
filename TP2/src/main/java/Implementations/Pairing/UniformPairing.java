package Implementations.Pairing;

import entities.Individual;
import interfaces.Pairing;

import java.util.Arrays;

public class UniformPairing implements Pairing {

    private static final double PAIRING_PROBABILITY = 0.5;

    @Override
    public Individual[] matchIndividuals(Individual i1, Individual i2) {

        boolean[] b1 = Arrays.copyOf(i1.getBag(), i1.getBag().length);
        boolean[] b2 = Arrays.copyOf(i2.getBag(), i2.getBag().length);

        boolean aux;

        for (int i = 0; i < b1.length; i++) {
            if (Math.random() <= PAIRING_PROBABILITY) {
                aux = b1[i];
                b1[i] = b2[i];
                b2[i] = aux;
            }
        }

        return new Individual[]{new Individual(b1, i1.getItems()), new Individual(b2, i2.getItems())};
    }
}
