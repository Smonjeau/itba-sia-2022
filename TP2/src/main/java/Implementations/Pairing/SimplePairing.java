package Implementations.Pairing;

import entities.Individual;
import interfaces.Pairing;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class SimplePairing implements Pairing {
    @Override
    public List<Individual> matchIndividuals(Individual i1, Individual i2) {

        List<Individual>individualList=new ArrayList<>(2);
        boolean[] bag1 = i1.getBag();
        boolean[] bag2 = i2.getBag();
        int breakpoint=new Random(System.currentTimeMillis()).nextInt(bag1.length);
        Individual newIndividual1=new Individual(bag1.length, i1.getItems(),i1.getMutationChance());
        Individual newIndividual2=new Individual(bag1.length, i1.getItems(),i1.getMutationChance());

        boolean[] newbag1 = newIndividual1.getBag();
        boolean[] newbag2 = newIndividual2.getBag();
        java.lang.System.arraycopy(bag1,0,newbag1,0,breakpoint);
        java.lang.System.arraycopy(bag2,0,newbag2,0,breakpoint);
        java.lang.System.arraycopy(bag2,breakpoint,newbag1,breakpoint,bag1.length-breakpoint);
        java.lang.System.arraycopy(bag1,breakpoint,newbag2,breakpoint,bag2.length-breakpoint);

        individualList.add(newIndividual1);
        individualList.add(newIndividual2);

        return individualList;
    }
}
