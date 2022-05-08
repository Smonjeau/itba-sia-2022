import java.util.List;

public class StepPerceptron extends LinealPerceptron  {
    private final double bias;


    public StepPerceptron(int dim, double learningRate, double bias, int limit) {
        super(dim, learningRate, limit);
        this.bias = bias;
    }

    @Override
    public double predict(Row row, List<Double> weights) {
        return super.predict(row, weights) - bias;
    }

    @Override
    public double g(double value) {
        return value >= 0 ? 1 : -1;
    }
}
