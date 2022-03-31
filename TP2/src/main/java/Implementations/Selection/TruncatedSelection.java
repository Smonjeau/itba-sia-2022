package Implementations.Selection;
import entities.Individual;
import interfaces.Selection;
import java.util.*;

public class TruncatedSelection implements Selection {
    @Override
    public List<Individual> select(List<Individual> generation) {
        int winnerSize=generation.size()/2;

        generation.sort(Comparator.comparingDouble(Individual::getFitness).reversed());
        Random random=new Random(System.currentTimeMillis());


        int truncate=random.nextInt(winnerSize);

        //get rid of worst candidates
        List<Individual>shuffledList=(generation.subList(0, generation.size()-truncate));
        //random select from the survivors
        Collections.shuffle(shuffledList);
        return shuffledList.subList(0,winnerSize);
    }
}
