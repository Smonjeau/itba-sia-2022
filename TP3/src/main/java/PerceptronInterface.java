import java.util.List;

public interface PerceptronInterface {

    List<Layer> train(double[]inputs, double[] output);
    double eval(double[]inputs);
}
