
import Implementations.Pairing.MultiplePairingTwoPoints;
import Implementations.Pairing.SimplePairing;
import Implementations.Pairing.UniformPairing;
import Implementations.Selection.*;
import com.opencsv.CSVParser;
import com.opencsv.CSVParserBuilder;
import com.opencsv.CSVReader;
import com.opencsv.CSVReaderBuilder;
import com.opencsv.exceptions.CsvException;
import entities.Environment;
import entities.Individual;
import entities.Item;
import interfaces.Pairing;
import interfaces.Selection;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;
import java.util.stream.Collectors;

import static java.lang.System.exit;

public class Main {

    public static void main(String[] args) throws CsvException, IOException, ParseException {

        if(args.length != 1) {
            System.err.println("data file must be passed as program argument");
            exit(1);
        }


        CSVParser parser = new CSVParserBuilder().withSeparator(' ').build();


        int maxItemsInBag;
        int maxWeight;
        List<Item> items = new ArrayList<>();
        try (BufferedReader br = Files.newBufferedReader(Paths.get(args[0]));
             CSVReader reader = new CSVReaderBuilder(br).withCSVParser(parser)
                     .build()) {

            String[] values;
            values = reader.readNext();
            if(values==null) {
                System.err.println("wrong data file");
                exit(1);
            }
            maxItemsInBag = Integer.parseInt(values[0]);
            Environment.weightLimit = Integer.parseInt(values[1]);

            while ((values = reader.readNext()) != null) {
                items.add(new Item(Integer.parseInt(values[0]),Integer.parseInt(values[1])));
            }
        }

        if(maxItemsInBag != items.size()){
            System.err.println("wrong data file");
            exit(1);
        }

        Environment.items = items;

        // Begin Parsing config.json
        FileReader fr = new FileReader("src/main/resources/config.json");
        //FileReader fr = new FileReader("C:\\Users\\gusta\\Desktop\\itba-sia-2022\\TP2\\src\\main\\resources\\config.json");
        JSONObject json = (JSONObject) new JSONParser().parse(fr);

        String pairingMethodStr = (String) json.get("pairing");
        double mutationProb = (double) json.get("mutation_probability");
        String selectionMethodStr = (String) json.get("selection");
        int P = Math.toIntExact((Long) json.get("P"));
        int maxIterations = Math.toIntExact((Long) json.get("max_iterations"));

        Pairing pairingMethod = null;
        Selection selectionMethod = null;

        switch(pairingMethodStr) {
            case "simple":
                pairingMethod = new SimplePairing();
                break;
            case "multiple":
                pairingMethod = new MultiplePairingTwoPoints();
                break;
            case "uniform":
                pairingMethod = new UniformPairing();
                break;
            default:
                System.err.println("Invalid pairing method");
                exit(1);
        }

        switch(selectionMethodStr) {
            case "boltzmann":
                double k = (double) json.get("k");
                double T0 = ((Long) json.get("T0")).doubleValue();
                selectionMethod = new BoltzmannSelection(T0, k);
                break;
            case "elite":
                selectionMethod = new EliteSelection();
                break;
            case "truncated":
                selectionMethod = new TruncatedSelection();
                break;
            case "tournament":
                double tournamentProbability = (double) json.get("tournament_prob");
                selectionMethod = new TournamentSelection(tournamentProbability);
                break;
            case "roulette":
                selectionMethod = new RouletteWheelSelection();
                break;
            case "rank":
                selectionMethod = new RankSelection();
                break;
            default:
                System.err.println("Invalid selection method");
                exit(1);
        }

        List<Individual> currentPopulation = generatePopulation(P, maxItemsInBag, items);

        for (int i = 0; i < maxIterations; i++) {
            List<Individual> newPopulation = new ArrayList<>();

            for (int j = 0; j < currentPopulation.size()/2; j++) {
                Individual[] individuals = pickIndividuals(currentPopulation);

                Individual[] newIndividuals = pairingMethod.matchIndividuals(individuals[0], individuals[1]);
                for (Individual individual : newIndividuals)
                    individual.mutate(mutationProb);

                newPopulation.addAll(Arrays.asList(newIndividuals));
                newPopulation.addAll(Arrays.asList(individuals));
            }

            currentPopulation = selectionMethod.select(newPopulation);
        }

        currentPopulation.sort(Comparator.comparingDouble(Individual::getFitness).reversed());
        System.out.println(currentPopulation.get(0));

    }

    private static List<Individual> generatePopulation(int p,int n,List<Item> items) {
        int i=0;
        int aux;
        Random random = new Random(System.currentTimeMillis());
        List<Individual> population = new ArrayList<>();

        while(i<p){
            boolean[] bag = new boolean[n];
            for(int j=0;j<n;j++){
                aux = random.nextInt(5);
                bag[j] = aux > 3;
            }

            Individual ind=new Individual(bag);

            while (ind.getWeightSum()>Environment.weightLimit){
                boolean erasure=false;
                for (int j = random.nextInt(bag.length); !erasure; j++) {
                    if (j==bag.length){
                        j=0;
                    }
                    if (bag[j]){
                        bag[j]=false;
                        erasure=true;
                        ind=new Individual(bag);
                    }
                }
            }

            population.add(ind);

            i++;

        }



        return population;
    }

    private static Individual[]  pickIndividuals (List<Individual> population) {
        List<Double> aux = population.stream().map(Individual::getFitness).collect(Collectors.toList());
        Double minFit = aux.stream().min(Double::compare).get();

        List<Double> fitnesses;
        if(minFit<0.0)
            fitnesses =aux.stream().map(elem->elem - minFit +1).collect(Collectors.toList());
        else
            fitnesses = aux;

        double fitnessSum = fitnesses.stream().mapToDouble(e -> e).sum();

        Random random = new Random(System.currentTimeMillis());
        Set<Integer> selected = new HashSet<>();
        Individual[] ret = new Individual[2];

        //seleccion
        double rand = random.nextDouble();


        for (int qtyOfIndividual = 0; qtyOfIndividual < 2; qtyOfIndividual++) {
            double acum=0.0;
            double nextAcum=0.0;
            boolean found = false;
            for (int i = 0;!found; i++) {
                //   System.out.println("pickeando");
                //       if(selected.contains(i)) {

                //            continue;
                //       }
                //double qj = getSumOfProbabilities(i, fitnesses, fitnessSum);
//                System.out.println(i);
                nextAcum = acum + (fitnesses.get(i) / fitnessSum);


                if (acum < rand && rand <= nextAcum) {
                    rand = random.nextDouble();
                    found=true;
                    ret[qtyOfIndividual] = population.get(i);
                    selected.add(i);
                }

                acum=nextAcum;

            }


        }

        return ret;
    }





}
