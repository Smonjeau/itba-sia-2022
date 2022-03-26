package Implementations.Selection;

import entities.Individual;
import interfaces.Selection;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Random;

public class TruncatedSelection implements Selection {
    @Override
    public List<Individual> select(List<Individual> generation,int WeightLimit) {
        int winnerSize=generation.size()/2;

        //remove weight violations
        generation.removeIf((Individual i1)->i1.getWeightSum()>WeightLimit);

        //sort by fitness
        generation.sort(Comparator.comparingInt(Individual::getBenefitSum));


        Random random=new Random(System.currentTimeMillis());
        List<Individual>newGeneration=new ArrayList<>(winnerSize);

        //truncation value must be smaller than winner size or there wont be enough individuals next generation
        int truncate= random.nextInt(winnerSize);

        for (int i = 0; i < winnerSize; i++) {
            newGeneration.add(generation.get(random.nextInt(truncate)));
            generation.remove(random.nextInt(truncate));
            truncate--;
        }
        return newGeneration;
    }
}
