import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Dermenzhy
 */

public class Main {
    static List<BigInteger> resList = new ArrayList<>();

    public static void main(String[] args) {
        int b1 = 2000;
        final int C = b1 / 2;
        int lim = 2000;
        BigInteger n = new BigInteger("1331915742633769");
        NumberAlgorithms numAlg = new NumberAlgorithms();
        int[] sieve = numAlg.sieveOfEratosthenes(b1);
    }

    /*
     TODO this method doesn't work properly. Requires to be fixed. Actually problem is particularly in other parts of code also it is needed to be refactored with using of single factorization method
     */

    /**
     * Method that should factorize number until we get all the prime divisors
     *
     * @param b1    boundary
     * @param lim   limit of curves used after which boundary is increased
     * @param sieve array of primes less than boundary b1 that are received as result of Eratosthenes algorithm
     */
    private static void fullFactorization(int b1, int lim, int[] sieve) {
        int compCounter = 1;
        while (compCounter > 0) {
            boolean check = true;
            for (int j = 0; j < resList.size(); j++) {
                if (resList.get(j).isProbablePrime(1)) {
                    compCounter--;
                    System.out.println("res list j= " + j + "; value is: " + resList.get(j) + " is prime");
                    System.out.println(compCounter);
                } else {
                    while (check) {
                        System.out.println(resList);
                        //System.out.println(e);
                        for (int i = 0; i < lim; i++) {
                            int sizeBefore = resList.size();
                            ECM(b1, resList.get(j), sieve);
                            if (resList.size() != sizeBefore) {
                                check = false;
                                System.out.println("Check");
                                compCounter++;
                                break;
                            }
                        }
                        b1 = b1 / 2 + b1;
                    }
                    System.out.println(resList);
                }
            }
        }
    }

    /**
     * Method that performs single try of factorization by using elliptic curves
     *
     * @param b1    boundary
     * @param n     the composite number to factorize
     * @param sieve array of primes less than boundary b1 that are received as result of Eratosthenes algorithm
     */
    private static void ECM(int b1, BigInteger n, int[] sieve) {
        try {
            EllipticArithmetic ea = new EllipticArithmetic();
            PointOnEC p = PointOnEC.generateRandomPoint(n);
            EllipticCurve ec = ea.generateCurve(n, p);
            int alfa;
            outerloop:
            for (int prime : sieve) {
                alfa = 1;
                while ((Math.pow(prime, alfa)) <= b1) {
                    alfa++;
                }
                alfa = alfa - 1;
                for (int i = 0; i < alfa; i++) {
                    try {
                        p = ea.ellipticMultiply(ec, prime, p);
                    } catch (ArithmeticException e) {
                        break outerloop;
                    }
                }
            }
        } catch (GCDException e) {
            return;
        }
    }

    /**
     * @param b1    boundary
     * @param n     the composite number to factorize
     * @param sieve array of primes less than boundary b1 that are received as result of Eratosthenes algorithm
     * @param C     the constant, which defines how should boundary be increased after each time when we go over the limit of curves
     * @param lim   limit of curves used after which boundary is increased
     */
    public static void singleFactorization(int b1, BigInteger n, int[] sieve, int C, int lim) {
        int counter = 0;
        resList.clear();
        resList.add(n);
        while (resList.size() == 1) {
            ECM(b1, n, sieve);
            counter++;
            if (counter > lim) {
                b1 = C + b1;
                counter = 0;
            }
        }
    }
    /* Some method that was used for particular research, shouldn't be very interesting for whoever else :D
    public static void numberStructureResearch(int b1, int[] sieve, int C, int lim) {
        BigInteger[] compArr = new BigInteger[50];
        try {
            BufferedReader reader = new BufferedReader(new FileReader("results/input.txt"));
            for (int i = 0; i < compArr.length; i++) {
                String line = reader.readLine();
                compArr[i] = new BigInteger(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        SaveToTxt st = new SaveToTxt("", "NumberResearch1-50");
        for (int i = 0; i < 50; i++) {
            for (int j = 0; j < 50; j++) {
                long start = System.currentTimeMillis();
                singleFactorization(b1, compArr[i], sieve, C, lim);
                long timeSpent = System.currentTimeMillis() - start;
                String resString = resList.get(0).toString() + "\t" + timeSpent + "\n";
                st.save(resString);
            }
        }
        st.close();
    }
    */
}
