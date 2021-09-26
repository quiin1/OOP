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
     * Constructor 1.
     */
    public Solution() {
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
        Solution res = new Solution(this.numerator, this.denominator);
        int ucln = gcd(this.numerator, this.denominator);
        this.numerator /= ucln;
        this.denominator /= ucln;
        return res;
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
        Solution res = new Solution(this.numerator * a + s.numerator * b, bcnn);
        return res.reduce();
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
        Solution res = new Solution(this.numerator * s.numerator, this.denominator * s.denominator);
        return res.reduce();
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
     * @return
     */
    public boolean equals(Object obj) {
        if (obj instanceof Solution) {
            Solution other = (Solution) obj;
            Solution f = this.reduce();
            other = other.reduce();
            if (f.numerator == other.numerator && f.denominator == other.denominator) {
                return true;
            }
        }
        return false;
    }

    public void print() {
        System.out.print(getNumerator() + "/" + getDenominator() + "\n");
    }

    public static void main(String[] args) {
        Solution s = new Solution(1, 0);
        Solution f = new Solution(1, 10);
//        System.out.println(s.numerator + " / " + s.denominator);
//        s.setNumerator(0);
//        s.setDenominator(0);
//        System.out.println(s.numerator + " / " + s.denominator);
//        s.setNumerator(0);
//        s.setDenominator(0);
//        System.out.println(s.numerator + " / " + s.denominator);

//        System.out.println(s.numerator + " / " + s.denominator);
//        System.out.println(f.numerator + " / " + f.denominator);
//        System.out.println(s.equals(f));
    }
}
