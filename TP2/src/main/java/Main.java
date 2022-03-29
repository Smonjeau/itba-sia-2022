
import Implementations.Pairing.MultiplePairingTwoPoints;
import Implementations.Pairing.SimplePairing;
import Implementations.Pairing.UniformPairing;
import Implementations.Selection.BoltzmannSelection;
import Implementations.Selection.EliteSelection;
import Implementations.Selection.TruncatedSelection;
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
            default:
                System.err.println("Invalid selection method");
                exit(1);
        }

        List<Individual> currentPopulation = generatePopulation(P, maxItemsInBag, items);

        for (int i = 0; i < maxIterations; i++) {
            List<Individual> newPopulation = new ArrayList<>();

            for (int j = 0; j < currentPopulation.size(); j += 2) {
                // TODO change pairing selection method
                Individual[] newIndividuals = pairingMethod.matchIndividuals(currentPopulation.get(j), currentPopulation.get(j + 1));

                for (Individual individual : newIndividuals)
                    individual.mutate(mutationProb);

                newPopulation.addAll(Arrays.asList(newIndividuals));
                newPopulation.add(currentPopulation.get(j));
                newPopulation.add(currentPopulation.get(j+1));
            }

            currentPopulation = selectionMethod.select(newPopulation);

        }

        currentPopulation.sort(Comparator.comparingDouble(Individual::getFitness).reversed());
        System.out.println(currentPopulation.get(0));



    }

    private static List<Individual> generatePopulation(int p,int n,List<Item> items) {
        int i=0;
        Random random = new Random(System.currentTimeMillis());
        List<Individual> population = new ArrayList<>();
        while(i<p){
            boolean[] bag = new boolean[n];
            for(int j=0;j<n;j++){
                bag[j] = random.nextBoolean();
            }
            population.add(new Individual(bag));
            i++;

        }

        return population;
    }

}
