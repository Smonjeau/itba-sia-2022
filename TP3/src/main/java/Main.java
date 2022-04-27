import com.opencsv.CSVParser;
import com.opencsv.CSVParserBuilder;
import com.opencsv.CSVReader;
import com.opencsv.CSVReaderBuilder;
import com.opencsv.exceptions.CsvValidationException;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class Main {


    public static void main(String[] args) {
        Ej2(args);
    }
    public static void Ej1(){
        int[][]input={{-1, 1}, {1,-1}, {-1,-1}, {1, 1}};
        int[]output={1,1,-1,-1};
//        int[]output={-1,-1,-1,1};
        Perceptron perceptron=new Perceptron(0.5,0.1,output,input);
        List<Double> doubleList=perceptron.eval();
        System.out.println(doubleList);
    }
    public static void Ej2(String[] args){
        CSVParser parser = new CSVParserBuilder().withSeparator(',').build();
        List<Double>outputs=new ArrayList<>();
        List<double[]>inputs=new ArrayList<>();

        try (BufferedReader br = Files.newBufferedReader(Paths.get(args[0]));
             CSVReader reader = new CSVReaderBuilder(br).withCSVParser(parser).build()) {
            String[] values;
            while ((values = reader.readNext()) != null) {


//                values= (String[]) Arrays.stream(values).filter(a -> !a.equals(" ")).toArray();
                double [] arr= new double[3];

                arr[0]=Double.parseDouble(values[1]);
                arr[1]=Double.parseDouble(values[2]);
                arr[2]=Double.parseDouble(values[3]);
                inputs.add(arr);
            }
        } catch (IOException | CsvValidationException e) {
            throw new RuntimeException(e);
        }

        try (BufferedReader br = Files.newBufferedReader(Paths.get(args[1]));
             CSVReader reader = new CSVReaderBuilder(br).withCSVParser(parser).build()) {
            String[] values;
            while ((values = reader.readNext()) != null) {
                outputs.add(Double.parseDouble(values[0]));
            }
        } catch (IOException | CsvValidationException e) {
            throw new RuntimeException(e);
        }

        double[]output=new double[outputs.size()];
        double[][]input=new double[outputs.size()][];
        for (int i = 0; i < outputs.size(); i++) {
            output[i]=outputs.get(i);
            input[i]=inputs.get(i);
        }
        PerceptronLineal perceptron=new PerceptronLineal(0,1,output,input);
        List<Double> doubleList=perceptron.eval();
        System.out.println(doubleList);

    }

}
