import java.math.BigInteger;

/**
 * Class that contents different number algorithms methods, which are general and can be used not only with elliptic
 * curves
 *
 * @author Dermenzhy
 */
public class NumberAlgorithms {

    /**
     * Method that finds greatest common divisor of two numbers
     *
     * @param a the first number
     * @param b the second number
     * @return greatest common divisor of two chosen numbers
     */
    public BigInteger gcd(BigInteger a, BigInteger b) {
        BigInteger biZero = new BigInteger("0");
        if ((b.compareTo(biZero)) == 0) return a;
        if(b.compareTo(biZero)<0){
            b = b.negate();
        }
        BigInteger x = a.mod(b);
        return gcd(b, x);
    }

    /**
     * @param n the primes boundary
     * @return a boolean array "prime[0..n]" and initialize all entries it as true. A value in prime[i] will finally be
     * false if i is Not a prime, else true.
     */
    public int[] sieveOfEratosthenes(int n) {
        if (n<2){
            int [] sieve = {};
            return  sieve;
        }
        boolean[] prime = new boolean[n + 1];
        for (int i = 0; i < n + 1; i++)
            prime[i] = true;
        for (int p = 2; p * p <= n; p++) {
            /**
             * If prime[p] is not changed, then it is a prime
             */
            if (prime[p]) {
                //Update all multiples of p
                for (int i = p * p; i <= n; i += p)
                    prime[i] = false;
            }
        }
        int trueCount = 0;
        for (int i = 0; i < n + 1; i++) {
            if (prime[i]) {
                trueCount++;
            }
        }
        //System.out.println("True count: " +trueCount);
        int j = 0;
        int[] Sieve = new int[trueCount - 2];
        for (int i = 2; i <= n; i++) {
            if (prime[i]) {
                Sieve[j] = i;
                // System.out.println(Sieve[j]);
                j++;
            }
        }
        return Sieve;
    }
}
