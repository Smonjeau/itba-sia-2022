import java.util.List;

public class Row {

    private List<Double> values;
    private Double expectedValue;

    public Row(List<Double> values, Double expectedValue) {
        this.values = values;
        this.expectedValue = expectedValue;
    }

    public List<Double> getValues() {
        return values;
    }

    public void setValues(List<Double> values) {
        this.values = values;
    }

    public Double getExpectedValue() {
        return expectedValue;
    }

    public void setExpectedValue(Double expectedValue) {
        this.expectedValue = expectedValue;
    }

    @Override
    public String toString() {
        return "Row{" +
                "values=" + values +
                ", expectedValue=" + expectedValue +
                '}';
    }
}
