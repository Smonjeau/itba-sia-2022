import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class Main {

    private static String INPUT_FILE_NAME_EJ2 = "src/main/resources/TP3-ej2-Conjunto-entrenamiento.txt";
    private static String EXPECTED_FILE_NAME_EJ2 = "src/main/resources/TP3-ej2-Salida-deseada.txt";
    private static String INPUT_FILE_NAME_EJ3 = "TP3-ej3-mapa-de-pixeles-digitos-decimales.txt";

    public static void main(String[] args) throws IOException {
        Ej2();
    }

    public static void Ej1(){
        int[][]input={{-1, 1}, {1,-1}, {-1,-1}, {1, 1}};
        int[]output={1,1,-1,-1};
//        int[]output={-1,-1,-1,1};
        Perceptron perceptron=new Perceptron(0.5,0.1,output,input);
        List<Double> doubleList=perceptron.eval();
        System.out.println(doubleList);
    }

    public static void Ej2() throws IOException {

        List<Double> outputs = readExpectedValues(EXPECTED_FILE_NAME_EJ2);
        List<double[]> inputs = readInputValues(INPUT_FILE_NAME_EJ2);


        double[] output = new double[outputs.size()];
        double[][] input = new double[outputs.size()][];

        for (int i = 0; i < outputs.size(); i++) {
            output[i] = outputs.get(i);
            input[i] = inputs.get(i);
        }

        PerceptronLineal perceptron = new PerceptronLineal(0,1, output, input);
        List<Double> doubleList = perceptron.eval();

        System.out.println(doubleList);
    }

    private static List<double[]> readInputValues(String fileName) throws IOException {
        File file = new File(fileName);
        BufferedReader br = new BufferedReader(new FileReader(file));

        String st;
        List<double[]>inputs=new ArrayList<>();

        while ((st = br.readLine()) != null) {
            st = st.replaceAll("[ ]+", " ").trim().replaceAll("[ ]", ",");
            String[] strArr = st.split(",");
            double [] arr= new double[3];

            arr[0]=Double.parseDouble(strArr[0]);
            arr[1]=Double.parseDouble(strArr[1]);
            arr[2]=Double.parseDouble(strArr[2]);
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
