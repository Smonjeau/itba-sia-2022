import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class NotLinealPerceptron {

    private List<Row> rows;
    private final int dim;
    private final double learningRate;
    private static final double DELTA = 0.00001;
    private final int limit;
    private final double beta = 1;
    private double minError = 1;

    public NotLinealPerceptron(int dim, double learningRate, int limit) {
        this.dim = dim;
        this.learningRate = learningRate;
        this.limit = limit;
    }

    public double train(List<Row> rows) {
        this.rows = rows;
        Random random = new Random(System.currentTimeMillis());
        Double error = 1.0;
        int count = 0;

        List<Double> weights = new ArrayList<>();
        List<Double> minWeights = new ArrayList<>();

        // Initialize weights with 0 value for first epoch
        for (int i = 0; i < dim; i++)
            weights.add(0.0);

        while (error > DELTA && count < limit) {
            int run = random.nextInt(rows.size());
            Row activeRow = rows.get(run);

            double h = 0.0;
            for (int i = 0; i < dim; i++) {
                h += activeRow.getValues().get(i) * weights.get(i);
            }

            for (int i = 0; i < dim; i++) {
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
        System.out.println(weights);
        System.out.println("err total " + calculateDenormalizedError(weights));
        System.out.println("err promedio " + calculateDenormalizedError(weights)/rows.size());
        return calculateDenormalizedError(weights);
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
    private double calculateDenormalizedError(List<Double> weights) {
        double error = 0.0;
        for (Row row : rows) {
            Double O = 0.0;
            for (int j = 0; j < dim; j++) {
                O += weights.get(j) * row.getValues().get(j);
            }

            O = g(O);

            O = (O + 1)*(99.3834 - 0.1558)/2 + 0.1558;
            double expected=(row.getExpectedValue() + 1)*(99.3834 - 0.1558)/2 + 0.1558;
            error += Math.pow(expected - O, 2);
        }

        return error;
    }

    private double g(double value) {
        return Math.tanh(value*beta);
    }
}
