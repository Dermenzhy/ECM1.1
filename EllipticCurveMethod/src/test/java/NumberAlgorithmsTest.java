import org.junit.Test;

import java.math.BigInteger;

import static org.junit.Assert.*;

public class NumberAlgorithmsTest {

    @Test
    public void gcd() {
        NumberAlgorithms numAlg = new NumberAlgorithms();
        assertEquals((numAlg.gcd(BigInteger.valueOf(4), BigInteger.valueOf(137))), BigInteger.valueOf(1));
        assertEquals((numAlg.gcd(BigInteger.valueOf(1071), BigInteger.valueOf(462))), BigInteger.valueOf(21));
        assertEquals((numAlg.gcd(BigInteger.valueOf(24), BigInteger.valueOf(24))), BigInteger.valueOf(24));
        assertEquals((numAlg.gcd(BigInteger.valueOf(0), BigInteger.valueOf(0))), BigInteger.valueOf(0));
        assertEquals((numAlg.gcd(BigInteger.valueOf(0), BigInteger.valueOf(15))), BigInteger.valueOf(15));
        assertEquals((numAlg.gcd(BigInteger.valueOf(5), BigInteger.valueOf(0))), BigInteger.valueOf(5));
        assertEquals((numAlg.gcd(BigInteger.valueOf(-15), BigInteger.valueOf(10))), BigInteger.valueOf(5));
        assertEquals((numAlg.gcd(BigInteger.valueOf(15), BigInteger.valueOf(-5))), BigInteger.valueOf(5));
    }

    @Test
    public void sieveOfEratosthenes() {
        int[] expectedSequenceTo100={2,3,5,7,11,13,17,19,23,29,31,37,41,43,47,53,59,61,67,71,73,79,83,89,97};
        NumberAlgorithms numAlg = new NumberAlgorithms();
        int[] seqTo0 = numAlg.sieveOfEratosthenes(0);
        int[] seqToNeg = numAlg.sieveOfEratosthenes(-123978);
        int[] seqTo1 = numAlg.sieveOfEratosthenes(1);
        int[] seqTo100 = numAlg.sieveOfEratosthenes(100);
        assertTrue(seqTo0.length==0);
        assertTrue(seqToNeg.length==0);
        assertTrue(seqTo1.length==0);
        assertArrayEquals(expectedSequenceTo100, seqTo100);

    }

}