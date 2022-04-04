package Implementations.Selection;

import entities.Individual;
import interfaces.Selection;

import java.util.*;


public class RankSelection implements Selection {
    @Override
    public List<Individual> select(List<Individual> generation) {

        int nextGenSize=generation.size()/2;
        int genSize = generation.size();

        generation.sort(Comparator.comparingDouble(Individual::getFitness).reversed());

        double sum = (double)genSize / 2;
        sum -=0.5;
        List<Individual> toReturn = new ArrayList<>();
        int i = 0;

        Set<Integer> selected = new HashSet<>();
        Random random = new Random(System.currentTimeMillis());
        double rand =  random.nextDouble();
        double acum = 0.0;
        double nextAcum =0.0;
        while(toReturn.size()<nextGenSize){
            // if(selected.contains(i)) {
            //    i++;

//
            //              continue;
            //        }


            double proxSelectJ = ((double)(genSize -i -1)/genSize);
            nextAcum = acum + proxSelectJ / sum;


            if(acum<rand && rand<=nextAcum){
                toReturn.add(generation.get(i));
                rand = random.nextDouble();
                selected.add(i);
                acum = 0.0;
                i=-1;
                nextAcum=0.0;
            }


            acum = nextAcum;

            i++;

        }

        return toReturn;
    }


}
