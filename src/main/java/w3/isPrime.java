package w3;

public class isPrime {
    /**
     * is a integer a prime?.
     * @param n .
     * @return true or false.
     */
    public static boolean isPrime(int n) {
        if (n < 2) {
            return false;
        }
        for (int i = 2; i < Math.sqrt(n); i++) {
            if (n % i == 0) {
                return false;
            }
        }
        return true;
    }
}
