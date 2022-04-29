import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class NotLinealPerceptron {

    private List<Row> rows;
    private final int dim;
    private final double learningRate;
    private static final double DELTA = 0.001;
    private static final int LIMIT = 10000;
    private final double beta = 1;
    private double minError = 1;

    public NotLinealPerceptron(int dim, double learningRate) {
        this.dim = dim;
        this.learningRate = learningRate;
    }

    public List<Double> train(List<Row> rows) {
        this.rows = rows;
        Random random = new Random(System.currentTimeMillis());
        Double error = 1.0;
        int count = 0;

        List<Double> weights = new ArrayList<>();
        List<Double> minWeights = new ArrayList<>();

        // Initialize weights with 0 value for first epoch
        for (int i = 0; i < dim; i++)
            weights.add(0.0);

        while (error > DELTA && count < LIMIT) {
            int run = random.nextInt(rows.size());
            Row activeRow = rows.get(run);

            double h = 0.0;
            for (int i = 0; i < dim; i++) {
                h += activeRow.getValues().get(i) * weights.get(i);
            }

            for (int i = 0; i < dim; i++) {
                // calculate new weight
                weights.set(i, weights.get(i) + deltaW(activeRow, i, h));
            }

            error = calculateError(weights);

            if (error < minError) {
                minError = error;
                minWeights.clear();
                minWeights.addAll(weights);
            }

            count++;
        }

        System.out.println("Error promedio: "+minError*(1.0/ rows.size()));
        return minWeights;
    }


    private Double deltaW(Row row, int index, double h) {
        return learningRate*(row.getExpectedValue() - g(h))* row.getValues().get(index)*beta*(1-Math.pow(g(h), 2));
    }

    private double calculateError(List<Double> weights) {
        double error = 0.0;
        for (Row row : rows) {
            Double O = 0.0;
            for (int j = 0; j < dim; j++) {
                O += weights.get(j) * row.getValues().get(j);
            }

            O = g(O);

            error += Math.pow(row.getExpectedValue() - O, 2);
        }

        return error;
    }

    private double g(double value) {
        return Math.tanh(value*beta);
    }
}
