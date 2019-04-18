import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Dermenzhy
 */

public class Main {
    static List<BigInteger> resList = new ArrayList<>();

    public static void main(String[] args) {
        int b1 = 500;
        int lim = 1000;
        BigInteger n = new BigInteger("123371273668");
        NumberAlgorithms numAlg = new NumberAlgorithms();
        int[] sieve = numAlg.sieveOfEratosthenes(b1);
        resList.add(n);

        //fullFactorization(b1, lim, sieve);
        ECM(b1, n, sieve);

    }
    /*
          TODO this method doesn't work properly. Requires to be fixed. Actually problem is particularly in other parts of code
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

    private static void ECM(int b1, BigInteger n, int[] sieve) {
        EllipticArithmetic ea = new EllipticArithmetic();
        PointOnEC p = PointOnEC.generateRandomPoint(n);
        try {
            EllipticCurve ec = ea.generateCurve(n, p);
            int alfa;
            // System.out.println(Arrays.toString(sieve));
            outerloop:
            for (int prime : sieve) {
                //System.out.println("Current prime is: " + prime);
                alfa = 1;
                while ((Math.pow(prime, alfa)) <= b1) {
                    alfa++;
                }
                alfa = alfa - 1;
                //  System.out.println("Number is: " + prime);
                //  System.out.println("And its grades: ");
                for (int i = 0; i < alfa; i++) {
                    try {
                        p = ea.ellipticMultiply(ec, prime, p);
                    } catch (ArithmeticException e) {
                        System.out.println("Divisor was found: " + Main.resList.get(resList.size() - 1));
                        break outerloop;
                    }
                }
                // System.out.print("\n");
            }
        } catch (GCDException e) {
            return;
        }
    }

}
