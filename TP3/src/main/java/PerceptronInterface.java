import java.util.List;

public interface PerceptronInterface {

    double train(Row row, double[] output);
    double[] eval(double[]inputs);
}
