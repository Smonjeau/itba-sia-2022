import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class LinealPerceptron {
    private List<Row> rows;
    private final int dim;
    private final double learningRate;
    private static final double DELTA = 0.001;
    private static final int LIMIT = 10000;

    public LinealPerceptron(int dim, double learningRate) {
        this.dim = dim;
        this.learningRate = learningRate;
    }

    public List<Double> train(List<Row> rows) {
        this.rows = rows;
        Random random = new Random(System.currentTimeMillis());
        double error = rows.size() * 2;
        int count = 0;
        double minError = error;

        List<Double> minWeights = new ArrayList<>();
        List<Double> weights = new ArrayList<>();

        // Initialize weights with 0 value for first epoch
        for (int i = 0; i < dim; i++)
            weights.add(0.0);

        while (error > DELTA && count < LIMIT) {
            int run = random.nextInt(rows.size());
            Row activeRow = rows.get(run);

            double prediction = g(predict(activeRow, weights));

            double localError = activeRow.getExpectedValue() - prediction;

            // calculate new weight for each initial value
            for (int i = 0; i < dim; i++) {
                weights.set(i, weights.get(i) + deltaW(localError, activeRow.getValues().get(i)));
            }

            error = calculateError(weights);

            if (error < minError) {
                minError = error;
                minWeights.clear();
                minWeights.addAll(weights);
            }

            count++;
        }

        System.out.println("Error promedio: " +minError/rows.size());
        return minWeights;
    }


    public Double deltaW(double localError, double value) {
        return learningRate * localError * value;
    }

    public double predict(Row row, List<Double> weights) {
        double activation = 0;
        for (int i = 0; i < dim; i++) {
            activation += row.getValues().get(i) * weights.get(i);
        }

        return activation;
    }

    private double calculateError(List<Double> weights) {
        double error = 0.0;
        for (Row row : rows) {
            Double O = 0.0;
            O += g(predict(row, weights));

            error += Math.pow(row.getExpectedValue() - O, 2);
        }

        return error;
    }

    public double g(double value) {
        return value;
    }
}
