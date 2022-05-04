import java.util.List;
import java.util.Objects;

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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Row row = (Row) o;
        return Objects.equals(values, row.values) && Objects.equals(expectedValue, row.expectedValue);
    }

    @Override
    public int hashCode() {
        return Objects.hash(values, expectedValue);
    }
}
