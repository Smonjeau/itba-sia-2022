package Implementations.Pairing;

import entities.Individual;
import interfaces.Pairing;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class SimplePairing implements Pairing {
    @Override
    public Individual[] matchIndividuals(Individual i1, Individual i2) {

        boolean[] bag1 = i1.getBag();
        boolean[] bag2 = i2.getBag();
        int breakpoint=new Random(System.currentTimeMillis()).nextInt(bag1.length);

        boolean[] newbag1 = new boolean[bag1.length];
        boolean[] newbag2 = new boolean[bag1.length];


        java.lang.System.arraycopy(bag1,0,newbag1,0,breakpoint);
        java.lang.System.arraycopy(bag2,0,newbag2,0,breakpoint);
        java.lang.System.arraycopy(bag2,breakpoint,newbag1,breakpoint,bag1.length-breakpoint);
        java.lang.System.arraycopy(bag1,breakpoint,newbag2,breakpoint,bag2.length-breakpoint);

        Individual newIndividual1=new Individual(newbag1, i1.getItems());
        Individual newIndividual2=new Individual(newbag2, i1.getItems());

        return new Individual[]{newIndividual1, newIndividual2};
    }
}
