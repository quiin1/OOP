package w3;

public class gcd {
    /**
     * compute greatest common divisor of $a and $b.
     * ⚠
     * pham vi kieu int : −2^31 đến 2^31 −1 (-2,147,483,648 đến 2,147,483,647)
     * -> abs(Integer.MIN_VALUE) = 2^31 se bi tran ra khoi pham vi int !!!
     * -> lưu ý để xử lý với các test case có đầu vào là Integer.MIN_VALUE || Integer.MAX_VALUE
     * .
     *
     * @param x .
     * @param y .
     * @return gcd(a, b).
     */
    public static int gcd(int x, int y) {
        long a = Math.abs((long) x); // ! must have (long) int abs()
        long b = Math.abs((long) y); // because Math.abs(n) -> data with the same as n's data type
        //                              but abs(intMin) > intMax, and belong to long data type
        while (a != b && a != 0 && b != 0) {
            if (a > b) {
                a %= b;
            } else {
                b %= a;
            }
        }
        if (a != 0) {
            return (int) a;
        }
        return (int) b;
    }

    public static int gcd1(int a, int b) { // may cho cach nay qua dc test case oasis 1 cach vi dieu :))
        a = Math.abs(a);
        b = Math.abs(b);
        if (b == 0) {
            return a;
        }
        return gcd1(b, a % b);
    }

    public static void main(String[] args) {
        System.out.println(gcd1(Integer.MAX_VALUE, Integer.MIN_VALUE));
        System.out.println(gcd1(Integer.MIN_VALUE, Integer.MAX_VALUE));
        System.out.println(gcd1(Integer.MAX_VALUE, Integer.MAX_VALUE));
        System.out.println(gcd1(Integer.MIN_VALUE, Integer.MIN_VALUE));

//        System.out.println(gcd2(Integer.MAX_VALUE, Integer.MIN_VALUE));

    }
}
