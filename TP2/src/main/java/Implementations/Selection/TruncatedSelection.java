package Implementations.Selection;

import entities.Individual;
import interfaces.Selection;

import java.util.*;

public class TruncatedSelection implements Selection {
    @Override
    public List<Individual> select(List<Individual> generation,int WeightLimit) {
        int winnerSize=generation.size()/2;

        //remove weight violations but checking not to remove too many
        generation.sort(Comparator.comparingInt(Individual::getBenefitSum).reversed());
        Iterator<Individual>iterator=generation.iterator();
        int genSize= generation.size();
        while (iterator.hasNext()&&genSize>winnerSize){
            Individual ind=iterator.next();
            if (ind.getWeightSum()>WeightLimit){
                iterator.remove();
                genSize--;
            }
        }

//        reorder from best to worst
        generation.sort(Comparator.comparingInt(Individual::getBenefitSum));
        Random random=new Random(System.currentTimeMillis());

        //truncation value must be smaller than winner size or there wont be enough individuals next generation
        int truncate;
        if (genSize==winnerSize)
            truncate=0;
        else
            truncate=random.nextInt(genSize-winnerSize);

        //get rid of worst candidates
        List<Individual>shuffledList=(generation.subList(0,genSize-truncate));
        //random select from the survivors
        Collections.shuffle(shuffledList);
        return shuffledList.subList(0,winnerSize);
    }
}
