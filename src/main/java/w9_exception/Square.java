public class Square extends Expression {
    private Expression expression;

    public Square(Expression expression) {
        this.expression = expression;
    }

    public String toString() {
        return "(" + expression + ") ^ 2";
    }

    @Override
    public double evaluate() {
        return expression.evaluate() * expression.evaluate();
    }
}
