public class ExpressionTest {
    /**
     * (10^2 + -3 + 5*-3)^2
     * ((((10) ^ 2 + -3) + (5 * -3))) ^ 2;
     * @param args .
     */
    public static void main(String[] args) {
        Expression a = new Numeral(10);
        Expression b = new Numeral(-3);
        Expression add = new Addition(new Square(a), b);
        Expression mul = new Multiplication(new Numeral(5), new Numeral(-3));
        Expression add2 = new Addition(add, mul);
        Expression sq = new Square(add2);

        System.out.println(sq);
        System.out.println(sq.evaluate());
    }
}
