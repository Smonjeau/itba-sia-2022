package Implementations.Selection;

import entities.Individual;
import interfaces.Selection;

import java.util.*;

import java.util.stream.Collectors;

public class RouletteWheelSelection implements Selection {
    @Override
    public List<Individual> select(List<Individual> generation) {
        int nextGenSize=generation.size()/2;
        int genSize = generation.size();


//        List<Double> aux = generation.stream().map(Individual::getFitness).collect(Collectors.toList());
//        Double minFit = aux.stream().min(Double::compare).get();

        List<Double> fitnesses = generation.stream().map(Individual::getFitness).collect(Collectors.toList());
//        if(minFit<0.0)
//            fitnesses =aux.stream().map(elem->elem - minFit +1).collect(Collectors.toList());
//        else
//            fitnesses = aux;

        List<Individual> toReturn = new ArrayList<>();
        int i = 0;
        Set<Integer> selected = new HashSet<>();
        Random random = new Random(System.currentTimeMillis());
        double fitnessSum = fitnesses.stream().mapToDouble(e -> e).sum();

        //seleccion
        double rand = random.nextDouble();
        double acum=0.0;
        double nextAcum=0.0;

        while(toReturn.size()<nextGenSize){

            //esto es para evitar repes
            //
            //if(selected.contains(i)) {
            //   i++;
            //  continue;
            //}
            //double qj = getSumOfProbabilities(i,fitnesses,fitnessSum);
            nextAcum = acum + (fitnesses.get(i) / fitnessSum);

            if((acum<rand && rand<=nextAcum)){
                rand = random.nextDouble();
                toReturn.add(generation.get(i));
                selected.add(i);
                i=-1;
                acum = 0.0;
                nextAcum=0.0;
            }

            acum = nextAcum;

            i++;

        }

        return toReturn;
    }

}
