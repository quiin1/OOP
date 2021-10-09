package w3;

public class Solution {
    private int numerator;
    private int denominator = 1;

    public int getNumerator() {
        return numerator;
    }

    public void setNumerator(int numerator) {
        this.numerator = numerator;
    }

    public int getDenominator() {
        return denominator;
    }

    /**
     * denominator must != 0.
     *
     * @param denominator .
     */
    public void setDenominator(int denominator) {
        if (denominator != 0) {
            this.denominator = denominator;
        }
    }

    /**
     * Constructor with numerator.
     *
     * @param numerator cst.
     */
    public Solution(int numerator) {
        this.numerator = numerator;
    }

    /**
     * Constructor 2.
     *
     * @param numerator   tu so.
     * @param denominator mau so.
     */
    public Solution(int numerator, int denominator) {
        if (denominator != 0) {
            this.numerator = numerator;
            this.denominator = denominator;
        }
    }

    /**
     * Reduce fraction.
     *
     * @return fraction after reduce.
     */
    public Solution reduce() {
        if (this.numerator * this.denominator < 0) {
            this.numerator = -Math.abs(this.numerator);
            this.denominator = Math.abs(this.denominator);
        }
        int ucln = gcd(this.numerator, this.denominator);
        this.numerator /= ucln;
        this.denominator /= ucln;
        return this;
    }

    /**
     * compute greatest common divisor of $a and $b.
     * ⚠
     * -> abs(Integer.MIN_VALUE) = 2^31 > Integer.MAX_VALUE.
     * -> vượt khỏi phạm vi kiểu int.
     * ( tuy nhiên cách đệ quy lại qua đc 4 test case intMin & intMax.
     * , từ chối hiểu sự vi diệu này :)) ).
     *
     * @param a .
     * @param b .
     * @return gcd(a, b).
     */
    public static int gcd(int a, int b) {
        a = Math.abs(a);
        b = Math.abs(b);
        if (b == 0) {
            return a;
        }
        return gcd(b, a % b);
    }

    /**
     * LCM of 2 int.
     *
     * @param a .
     * @param b .
     * @return lcm(a, b) = bcnn(a,b).
     */
    public static int lcm(int a, int b) {
        return a * b / gcd(a, b);
    }

    /**
     * sum 2 fraction to fraction 1.
     *
     * @param s .
     */
    public Solution add(Solution s) {
        int bcnn = lcm(this.denominator, s.denominator);
        int a = bcnn / this.denominator;
        int b = bcnn / s.denominator;
        this.numerator = this.numerator * a + s.numerator * b;
        this.denominator = bcnn;
        return reduce();
    }

    /**
     * fraction 1 - fraction 2 => fraction 1.
     *
     * @param s .
     */
    public Solution subtract(Solution s) {
        s.numerator *= -1;
        return add(s);
    }

    /**
     * fraction 1 * fraction 2 => fraction 1.
     *
     * @param s .
     */
    public Solution multiply(Solution s) {
        s.reduce(); // cho dau - len tu neu co
        this.numerator = this.numerator * s.numerator;
        this.denominator = this.denominator * s.denominator;
        return reduce();
    }

    /**
     * fraction 1 / fraction 2 (!=0) => fraction 1.
     *
     * @param s .
     */
    public Solution divide(Solution s) {
        if (s.numerator != 0) {
            int tran = s.numerator;
            s.numerator = s.denominator;
            s.denominator = tran;
            return multiply(s);
        }
        return this;
    }

    /**
     * is 2 obj is 2 equal fraction?.
     *
     * @param obj .
     * @return .
     */
    public boolean equals(Object obj) {
        if (obj instanceof Solution) {
            Solution other = (Solution) obj;
            Solution f = this.reduce();
            other.reduce();
            return f.numerator == other.numerator && f.denominator == other.denominator;
        }
        return false;
    }

    public void print() {
        System.out.print(getNumerator() + "/" + getDenominator() + "\n");
    }

    public static void main(String[] args) {
        Solution s = new Solution(1, 0);
        Solution f = new Solution(1, 1);
        s.print();
        s.setNumerator(0);
        s.setDenominator(0);
        s.print();
        s.setNumerator(0);
        s.setDenominator(0);
        s.print();
        f.print();

        Solution frac = new Solution(6, -8);
        frac.reduce();
        frac.print();
        frac.subtract(new Solution(7, 5));
        frac.print();
        frac.add(new Solution(14, 11));
        frac.print();
        frac.multiply(new Solution(2, 7));
        frac.print();
        frac.divide(new Solution(9, -770));
        frac.print();
        System.out.println(frac.equals(new Solution(386, 18)));
        System.out.println(frac.equals(new Solution(-386, 18)));
    }
}
