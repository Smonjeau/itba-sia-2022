package Implementations.Pairing;

import entities.Individual;
import interfaces.Pairing;

import java.util.Arrays;
import java.util.Random;

public class MultiplePairingTwoPoints implements Pairing {


    @Override
    public Individual[] matchIndividuals(Individual i1, Individual i2) {


        boolean[] bag1 = new boolean[i1.getBag().length];
        boolean[] bag2 = new boolean[i2.getBag().length];

        Random random = new Random(System.currentTimeMillis());
        int p1 = random.nextInt(101);
        int p2 = p1;
        while(p2==p1){
            p2 = random.nextInt(101);
        }
        if(p1>p2) {
            int aux = p2;

            p2 = p1;

            p1 = aux;
        }
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

        Individual toReturn1 = new Individual(bag1);
        Individual toReturn2 = new Individual(bag2);
        return new Individual[]{toReturn1,toReturn2};
    }
}
