import java.util.List;

public interface PerceptronInterface {

    double train(List<Row> rows, double[][] output);
    double[] eval(double[]inputs);
}
