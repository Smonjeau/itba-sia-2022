package Implementations.Selection;

import entities.Individual;
import interfaces.Selection;

import java.util.*;

public class TournamentSelection implements Selection {

    private static Double tournamentChance=null;

//    public TournamentSelection(double tournamentChance) {
//        this.tournamentChance=tournamentChance;
//    }

    public static void setTournamentChance(Double tournamentChance) {
        TournamentSelection.tournamentChance = tournamentChance;
    }

    @Override
    public List<Individual> select(List<Individual> generation) {
        if (tournamentChance==null)
            throw new RuntimeException("No tournament Chance selected");

        int winnerSize=generation.size()/2;
        List<Individual> newGeneration=new ArrayList<>(winnerSize);
        Random random=new Random(System.currentTimeMillis());
        List<Individual> candidateList=new ArrayList<>(4);
        List<Individual> winnerList=new ArrayList<>(4);
        List<Individual> loserList=new ArrayList<>(4);
        while (newGeneration.size()<winnerSize){
            for (int j = 0; j < 2; j++) {
                for (int i = 0; i < 2; i++) {
                    candidateList.add(generation.get(0));
                    generation.remove(0);

                }
                candidateList.sort(Comparator.comparingDouble(Individual::getFitness));
                if (random.nextDouble()>tournamentChance){
                    winnerList.add(candidateList.get(0));
                    loserList.add(candidateList.get(1));
                    candidateList.clear();
                }

                else {
                    loserList.add(candidateList.get(0));
                    winnerList.add(candidateList.get(1));
                    candidateList.clear();

                }
            }
            if (random.nextDouble()>tournamentChance){
                newGeneration.add(winnerList.get(0));
                loserList.add(winnerList.get(1));
                winnerList.clear();
            }

            else {
                newGeneration.add(winnerList.get(1));
                loserList.add(winnerList.get(0));
                winnerList.clear();

            }
            generation.addAll(loserList);
            loserList.clear();

        }
        return newGeneration;
    }
}
