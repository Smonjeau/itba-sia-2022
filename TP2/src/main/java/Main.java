
import Implementations.Selection.RankSelection;
import com.opencsv.CSVParser;
import com.opencsv.CSVParserBuilder;
import com.opencsv.CSVReader;
import com.opencsv.CSVReaderBuilder;
import com.opencsv.exceptions.CsvException;
import com.opencsv.exceptions.CsvValidationException;
import entities.Individual;
import entities.Item;
import interfaces.Selection;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Scanner;

import static java.lang.System.exit;

public class Main {
    public static void main(String[] args) throws CsvException, IOException {

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
            maxWeight = Integer.parseInt(values[1]);

            while ((values = reader.readNext()) != null) {
                items.add(new Item(Integer.parseInt(values[0]),Integer.parseInt(values[1])));
            }
        }
        if(maxItemsInBag != items.size()){
            System.err.println("wrong data file");
            exit(1);
        }



        List<Individual> populationZero = generatePopulation(20,maxItemsInBag,items);



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
            population.add(new Individual(bag,items));
            i++;

        }

        return population;
    }

}
