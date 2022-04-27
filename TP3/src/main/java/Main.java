import java.util.Arrays;
import java.util.List;

public class Main {


    public static void main(String[] args) {
        Perceptron perceptron=new Perceptron(0.1,0.1);
        List<Double> doubleList=perceptron.eval();
        System.out.println(doubleList);
    }

}
