import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class Main {


    private static String INPUT_FILE_NAME_EJ2 = "src/main/resources/TP3-ej2-Conjunto-entrenamiento.txt";
//    private static String INPUT_FILE_NAME_EJ2 = "src/main/resources/test_input.txt";
    private static String EXPECTED_FILE_NAME_EJ2 = "src/main/resources/TP3-ej2-Salida-deseada.txt";
//    private static String EXPECTED_FILE_NAME_EJ2 = "src/main/resources/test_output.txt";
    private static String INPUT_FILE_NAME_EJ3 = "TP3-ej3-mapa-de-pixeles-digitos-decimales.txt";



     /*
    private static String INPUT_FILE_NAME_EJ2 = "C:\\Users\\gusta\\Desktop\\itba-sia-2022\\TP3\\src\\main\\resources\\TP3-ej2-Conjunto-entrenamiento.txt";
    private static String EXPECTED_FILE_NAME_EJ2 = "C:\\Users\\gusta\\Desktop\\itba-sia-2022\\TP3\\src\\main\\resources\\TP3-ej2-Salida-deseada.txt";
    private static String INPUT_FILE_NAME_EJ3 = "C:\\Users\\gusta\\Desktop\\itba-sia-2022\\TP3\\src\\main\\resources\\TP3-ej3-mapa-de-pixeles-digitos-decimales.txt";


      */
    public static void main(String[] args) throws IOException {
        //Ej1();
       // Ej2Lineal();
       // Ej2NotLineal();
      //  Ej3PartOne();
        Ej3PartTwo();
       // Ej3PartThree();
    }

    public static void Ej1(){
        Double[][]input={{-1.0, 1.0}, {1.0,-1.0}, {-1.0,-1.0}, {1.0, 1.0}};
        Double[]output={1.0,1.0,-1.0,-1.0};
//        Double[]output={-1.0,-1.0,-1.0,1.0};

        List<Row> rows = new ArrayList<>();

        for (int i = 0; i < input.length; i++) {
            rows.add(new Row(Arrays.asList(input[i]), output[i]));
        }


        StepPerceptron perceptron = new StepPerceptron(2, 0.1, 0.5);
//                new StepPerceptron(0.5,0.1,output,input);
        List<Double> doubleList = perceptron.train(rows);
        System.out.println(doubleList);
    }


    private static void Ej2Lineal() throws IOException {
        List<Double> outputs = readExpectedValues(EXPECTED_FILE_NAME_EJ2);

        Double min = outputs.stream().min(Double::compare).orElse(0.0);
        Double max = outputs.stream().max(Double::compare).orElse(0.0);

        for (int i = 0; i < outputs.size(); i++) {
            outputs.set(i, 2*(outputs.get(i) - min)/(max - min) - 1);
        }

        List<List<Double>> inputs = readInputValues(INPUT_FILE_NAME_EJ2);

        if (inputs.size() != outputs.size()) {
            System.out.println("Invalid amount of inputs and expected outputs");
            return;
        }

        List<Row> rows = new ArrayList<>();

        for (int i = 0; i < inputs.size(); i++) {
            rows.add(new Row(inputs.get(i), outputs.get(i)));
        }

//        PLineal perceptron = new PLineal(rows, inputs.get(0).size(), 0.001);
        LinealPerceptron perceptron = new LinealPerceptron(inputs.get(0).size(), 0.001);
        List<Double> weights = perceptron.train(rows);

        System.out.println(weights);
    }

    private static void Ej2NotLineal() throws IOException {
        List<Double> outputs = readExpectedValues(EXPECTED_FILE_NAME_EJ2);

        Double min = outputs.stream().min(Double::compare).orElse(0.0);
        Double max = outputs.stream().max(Double::compare).orElse(0.0);

        for (int i = 0; i < outputs.size(); i++) {
            outputs.set(i, 2*(outputs.get(i) - min)/(max - min) - 1);
        }

        List<List<Double>> inputs = readInputValues(INPUT_FILE_NAME_EJ2);

        if (inputs.size() != outputs.size()) {
            System.out.println("Invalid amount of inputs and expected outputs");
            return;
        }

        List<Row> rows = new ArrayList<>();

        for (int i = 0; i < inputs.size(); i++) {
            rows.add(new Row(inputs.get(i), outputs.get(i)));
        }

        NotLinealPerceptron perceptron = new NotLinealPerceptron(inputs.get(0).size(), 0.01);
        List<Double> weights = perceptron.train(rows);

        System.out.println(weights);
    }

    private static void Ej3PartOne(){

        Double[][]input={{-1.0, 1.0}, {1.0,-1.0}, {-1.0,-1.0}, {1.0, 1.0}};
        double[][]output={{1.0}, {1.0}, {-1.0}, {-1.0}};

        MultiLayerPerceptron multiLayerPerceptron = new MultiLayerPerceptron(new int[]
                {input[0].length,5,1},0.001);

        List<Row> rows = new ArrayList<>();

        for (int i = 0; i < input.length; i++) {
            rows.add(new Row(Arrays.asList(input[i]), output[i][0]));
        }
        for (int i = 0; i < 100000; i++) {
            double err = multiLayerPerceptron.train(rows,output);
            System.out.println(err);
        }


        System.out.println(Arrays.toString(multiLayerPerceptron.eval(new double[]{1,1})));

    }

    private static void Ej3PartTwo() throws IOException {
        double[][] outputs = new double[][]{{1.0},{-1.0},{1.0},{-1.0},{1.0},{-1.0},{1.0},{-1.0},{1.0},{-1.0}};
        List<List<Double>> inputsList = readInputValues(INPUT_FILE_NAME_EJ3);

        Double [][] pixelMap = new Double[10][7*5];

        for (int i = 0; i < pixelMap.length; i++) {
            int aux = 0;

            for (int j = 0; j < 7 ; j++) {
                //esto es pq esta el umbral como input
                for (int k = 1; k < 6; k++) {
                    pixelMap[i][aux++] = inputsList.get(j+7*i).get(k);
                }
            }

        }
        MultiLayerPerceptron multiLayerPerceptron = new MultiLayerPerceptron(new int[]
                {pixelMap[0].length,5,1},0.001);

        List<Row> rows = new ArrayList<>();

        for (int i = 0; i < pixelMap.length; i++)
            rows.add(new Row(Arrays.asList(pixelMap[i]), outputs[i][0]));



        //validacion cruzada
        Collections.shuffle(rows);
        int k = 5;
        List<List<Row>> subLists = new ArrayList<>();
        int subListSize = rows.size() / k;
        int aux = 0;
        for(int i =0;i<k;i++)
            subLists.add(rows.subList(subListSize*aux++,subListSize*aux));
        List<Row> trainList;
        List<Row> testList ;

        for (int i = 0; i <k ; i++) {
            testList = subLists.get(i);
            trainList = new ArrayList<>();
            for (int j = 0; j <k ; j++) {
                if (i != j)
                    trainList.addAll(subLists.get(j));
            }
            double err=0;
            for (int epoch = 0; epoch < 10000; epoch++) {
                err = multiLayerPerceptron.train(trainList,outputs);
                //System.out.println(err);
            }
            for(Row r: testList){
                System.out.println(r.getExpectedValue());
                double[] auxInput = new double[r.getValues().size()];
                for (int j = 0; j < r.getValues().size(); j++) {
                    auxInput[j] = r.getValues().get(j);
                }
                System.out.println(Arrays.toString(multiLayerPerceptron.eval(auxInput)));
            }
        }




    }



    private static void Ej3PartThree() throws IOException {
        double[][] outputs = new double[][]{
                {1.0,-1.0,-1.0,-1.0,-1.0,-1.0,-1.0,-1.0,-1.0,-1.0},
                {-1.0,1.0,-1.0,-1.0,-1.0,-1.0,-1.0,-1.0,-1.0,-1.0},
                {-1.0,-1.0,1.0,-1.0,-1.0,-1.0,-1.0,-1.0,-1.0,-1.0},
                {-1.0,-1.0,-1.0,1.0,-1.0,-1.0,-1.0,-1.0,-1.0,-1.0},
                {-1.0,-1.0,-1.0,-1.0,1.0,-1.0,-1.0,-1.0,-1.0,-1.0},
                {-1.0,-1.0,-1.0,-1.0,-1.0,1.0,-1.0,-1.0,-1.0,-1.0},
                {-1.0,-1.0,-1.0,-1.0,-1.0,-1.0,1.0,-1.0,-1.0,-1.0},
                {-1.0,-1.0,-1.0,-1.0,-1.0,-1.0,-1.0,1.0,-1.0,-1.0},
                {-1.0,-1.0,-1.0,-1.0,-1.0,-1.0,-1.0,-1.0,1.0,-1.0},
                {-1.0,-1.0,-1.0,-1.0,-1.0,-1.0,-1.0,-1.0,-1.0,1.0}

        };
        List<List<Double>> inputsList = readInputValues(INPUT_FILE_NAME_EJ3);

        Double [][] pixelMap = new Double[10][7*5];

        for (int i = 0; i < pixelMap.length; i++) {
            int aux = 0;

            for (int j = 0; j < 7 ; j++) {
                //esto es pq esta el umbral como input
                for (int k = 1; k < 6; k++) {
                    pixelMap[i][aux++] = inputsList.get(j+7*i).get(k);
                }
            }

        }
        MultiLayerPerceptron multiLayerPerceptron = new MultiLayerPerceptron(new int[]
                {pixelMap[0].length,3,10},0.01);

        List<Row> rows = new ArrayList<>();

        for (int i = 0; i < pixelMap.length; i++)
            rows.add(new Row(Arrays.asList(pixelMap[i]), outputs[i][0]));

        for (int i = 0; i < 400000; i++) {
            double err = multiLayerPerceptron.train(rows,outputs);
            System.out.println(err);
        }


        for (int i = 0; i <10 ; i++) {
            double[] auxInput = new double[rows.get(i).getValues().size()];
            for (int j = 0; j < rows.get(i).getValues().size(); j++) {
                auxInput[j] = rows.get(i).getValues().get(j);
            }
            System.out.println(Arrays.toString(multiLayerPerceptron.eval(auxInput)));
        }
    }
    private static List<List<Double>> readInputValues(String fileName) throws IOException {
        File file = new File(fileName);
        BufferedReader br = new BufferedReader(new FileReader(file));

        String st;
        List<List<Double>>inputs=new ArrayList<>();

        while ((st = br.readLine()) != null) {
            st = st.replaceAll("[ ]+", " ").trim().replaceAll("[ ]", ",");
            String[] strArr = st.split(",");
            List<Double> arr = new ArrayList<>();

            arr.add(-1.0);
            for (String s : strArr) {
                arr.add(Double.parseDouble(s));
            }

            inputs.add(arr);
        }

        return inputs;
    }

    private static List<Double> readExpectedValues(String fileName) throws IOException {
        File file = new File(fileName);
        BufferedReader br = new BufferedReader(new FileReader(file));

        List<Double> outputs = new ArrayList<>();
        String st;
        while ((st = br.readLine()) != null) {
            st = st.trim();
            outputs.add(Double.parseDouble(st));
        }

        return outputs;
    }

}
