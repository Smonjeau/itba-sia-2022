package Implementations.Pairing;

import entities.Individual;
import interfaces.Pairing;

import java.util.Arrays;

public class MultiplePairingTwoPoints implements Pairing {
    private final int p1 = 7;
    private final int p2 = 50 ;

    @Override
    public Individual[] matchIndividuals(Individual i1, Individual i2) {


        boolean[] bag1 = new boolean[i1.getBag().length];
        boolean[] bag2 = new boolean[i2.getBag().length];

        int i;
        for(i=0;i<p1;i++){
            bag1[i] = i1.getBag()[i];
            bag2[i] = i2.getBag()[i];
        }
        for(;i<p2;i++){
           bag1[i] = i2.getBag()[i];
           bag2[i] = i1.getBag()[i];
        }
        for(;i<i2.getBag().length;i++){
            bag1[i] = i1.getBag()[i];
            bag2[i] = i2.getBag()[i];
        }

        Individual toReturn1 = new Individual(bag1,i1.getItems());
        Individual toReturn2 = new Individual(bag2,i2.getItems());
        return new Individual[]{toReturn1,toReturn2};
    }
}
