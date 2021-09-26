package w3;

public class Fibonacci {
    /**
     * compute the fibonacci of $n.
     *
     * @param n
     * @return
     */
    public static long fibonacci(long n) {
        long fiboN1 = 0; // n=0
        long fiboN2 = 1; // n=1
        long fiboN = 0;
        if (n < 0) {
            return -1;
        }
        if (n == 0) {
            return 0;
        }
        if (n == 1) {
            return 1;
        }
        for (long i = 2; i <= n; i++) {
            fiboN = fiboN2 + fiboN1;
            fiboN1 = fiboN2;
            fiboN2 = fiboN;
        }
        if (fiboN < 0L) {
            return Long.MAX_VALUE;
        } else {
            return fiboN;
        }
    }

    /**
     * print all prime numbers from 0 to $n use Sieve of Eratosthenes algorithm.
     * useful link https://en.wikipedia.org/wiki/Sieve_of_Eratosthenes.
     * notice that print each number in a line.
     * @param n .
     */
    public void sieveEratosthenes(int n) {

    }

    public static void main(String[] args) {

    }
}
