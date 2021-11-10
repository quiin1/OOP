public class Numeral extends Expression {
    private double value;

    public Numeral(double value) {
        this.value = value;
    }

    public Numeral() {
    }

    @Override
    public String toString() {
        return value == (int) value ? (int) value + "" : value + "";
    }

    @Override
    public double evaluate() {
        return value;
    }
}
