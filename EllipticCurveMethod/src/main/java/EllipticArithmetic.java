import java.math.BigInteger;
import java.util.Random;

/**
 * Class that contains methods which realises elliptic curve arithmetic
 *
 * @author Dermenzhy
 */

public class EllipticArithmetic {
    private NumberAlgorithms numAlg = new NumberAlgorithms();

    /**
     * @param n     - field characteristic
     * @param point - point on curve with  random coordinates(x,y)
     * @return new EllipticCurve object with random "a" parameter, over field modulo "n", and calculated param "b"
     * generating "a" randomly in range from 0 to n-1
     * calculating "b" as function dependent on coordinates of point [x,y] and value of "a"
     * calculating curve discriminant g
     * do this calculations while wont get params such that g!=n
     * Throw exception if we found divisor in such way
     * found divisor as gcd of discriminant of curve and composite n
     */
    public EllipticCurve generateCurve(BigInteger n, PointOnEC point) throws GCDException {
        Random rnd = new Random();
        BigInteger a;
        BigInteger b;
        BigInteger g;
        BigInteger x = point.getX();
        BigInteger y = point.getY();
        do {
            a = new BigInteger(n.bitLength(), rnd);
            b = (((y.pow(2)).subtract(x.pow(3))).subtract(a.multiply(x))).mod(n);
            g = numAlg.gcd(((BigInteger.valueOf(4).multiply(a.pow(3))).add((BigInteger.valueOf(27)).multiply(b.pow(2)))), n);
            /*
            TODO: test this exception
             */
            if ((g.compareTo(BigInteger.valueOf(1))) > 0) {
                (Main.resList).add(g);
                (Main.resList).remove(n);
                throw new GCDException("Found divisor as gcd of discriminant of curve and composite n", g, n);
            }
        } while (g.equals(n));
        return new EllipticCurve(a, b, n);
    }

    /**
     * @param curve curve on which multiplied point is
     * @param k     integer constant on which curve point is multiplied
     * @param P     point on chosen elliptic curve which is multiplied by some constant
     * @return new point that is the product of constant k multiply by point P (kP)
     * @throws ArithmeticException case when its impossible to find inverse element in chosen field modulo n actually
     *                             signalizes that we found divisor, in such case exception is threw
     *                             in case if constant k equals 0, return point in infinity (point with coordinates [0,1,0]
     *                             if k==1, return point as itself
     */
    public PointOnEC ellipticMultiply(EllipticCurve curve, int k, PointOnEC P) throws ArithmeticException {

        if (k == 0) {
            return new PointOnEC(BigInteger.valueOf(0), BigInteger.valueOf(1), BigInteger.valueOf(0));
        }
        if (k == 1) {
            return P;
        }
        BigInteger x = P.getX();
        BigInteger y = P.getY();
        BigInteger z = P.getZ();
        PointOnEC Q = new PointOnEC(x, y, z);
        // get the binary string from constant value to use additive-subtractive multiplying algorithm
        String binN = Integer.toBinaryString(k);
        String bin3N = Integer.toBinaryString((k * 3));
        while (binN.length() < bin3N.length()) {
            binN = "0" + binN;
        }
        //additive-subtractive multiplying algorithm, to multiply point by k
        for (int j = 1; j < binN.length() - 1; j++) {
            Q = ellipticSum(curve, Q, Q);
            if ((Character.getNumericValue(binN.charAt(j))) == 0 && (Character.getNumericValue(bin3N.charAt(j)) == 1)) {
                Q = ellipticSum(curve, Q, P);
            }
            if ((Character.getNumericValue(binN.charAt(j))) == 1 && (Character.getNumericValue(bin3N.charAt(j)) == 0)) {
                Q = ellipticDiff(curve, Q, P);
            }
        }
        return Q;
    }

    /**
     * @param curve on which two point are built
     * @param P1    first point with coordinates [x1, y1, z1]
     * @param P2    second point with coordinates [x2, y2, z2]
     * @return new point, that represents sum of two points P1 and P2 on the chosen curve
     * @throws ArithmeticException case when its impossible to find inverse element in chosen field modulo n actually
     *                             signalizes that we found divisor, in such case exception is threw
     */
    public PointOnEC ellipticSum(EllipticCurve curve, PointOnEC P1, PointOnEC P2) throws ArithmeticException {
        BigInteger x1 = P1.getX();
        BigInteger x2 = P2.getX();
        BigInteger y1 = P1.getY();
        BigInteger y2 = P2.getY();
        BigInteger z1 = P1.getZ();
        BigInteger z2 = P2.getZ();
        BigInteger n = curve.getN();
        BigInteger a = curve.getA();
        x1 = x1.mod(n);
        y1 = y1.mod(n);
        x2 = x2.mod(n);
        y2 = y2.mod(n);
        BigInteger m;
        // if one points have z coordinate equals 0, then it is the point at infinity, at such case just return another
        // point as a result
        if (z1.equals(BigInteger.valueOf(0))) {
            return P2;
        }
        if (z2.equals(BigInteger.valueOf(0))) {
            return P2;
        }
         /* d is part of curve angular coefficient formula, the mod inverse element in field modulo n of d gives an
          angular coefficient, due to elliptic curve arithmetic this mod inverse operation is not possible if d is
          divisor of n */
        BigInteger d;
        /* there is two cases of elliptic sum, general sum of two different points, and specific case of doubling the
           same point */
        if (x1.equals(x2)) {
            /* case when two points have same x coordinates and opposite y coordinates, means this points are inverse,
              thus their sum equals point on infinity */
            if ((y1.add(y2)).equals(BigInteger.valueOf(0))) {
                return new PointOnEC(BigInteger.valueOf(0), BigInteger.valueOf(1), BigInteger.valueOf(0));
            }
            d = ((((BigInteger.valueOf(3)).multiply(x1.pow(2))).add(a)).multiply((BigInteger.valueOf(2)).multiply(y1)));
            m = calculateAngularCoefficient(n, d);
        } else {
            d = y2.subtract(y1).multiply(x2.subtract(x1));
            m = calculateAngularCoefficient(n, d);

        }
        /* if calculating of angular coefficient was successful, then calculate new point coordinates [x3, y3], which
         represents the sum of given two points */
        BigInteger x3;
        BigInteger y3;
        x3 = (m.pow(2)).subtract(x1).subtract(x2);
        y3 = (m.multiply((x1.subtract(x3)))).subtract(y1);
        x3 = x3.mod(n);
        y3 = y3.mod(n);
        return new PointOnEC(x3, y3, BigInteger.valueOf(1));
    }

    /**
     * @param n field characteristic
     * @param d is part of curve angular coefficient formula, the mod inverse element in field modulo n of d gives an
     *          angular coefficient
     * @return angular coefficient m
     * @throws ArithmeticException due to elliptic curve arithmetic this mod inverse operation is not possible if d is
     *                             divisor of n
     */
    private BigInteger calculateAngularCoefficient(BigInteger n, BigInteger d) throws ArithmeticException {
        BigInteger m;
        try {
            m = d.modInverse(n);
        } catch (ArithmeticException e) {
            BigInteger div = numAlg.gcd(d, n);
            (Main.resList).add(div);
            (Main.resList).add(n.divide(div));
            (Main.resList).remove(n);
            throw e;
        }
        return m;
    }

    /**
     * @param curve curve on which two points are built
     * @param P1    firs point on curve
     * @param P2    second point on curve
     * @return new point on chosen curve, that represents the difference between points P1 and P2
     */
    public PointOnEC ellipticDiff(EllipticCurve curve, PointOnEC P1, PointOnEC P2) {
        return ellipticSum(curve, P1, ellipticNegate(P2));
    }

    /**
     * @param P point on EC
     * @return point inverse to given point P ( actually same coordinates except y which is inverted)
     */
    public PointOnEC ellipticNegate(PointOnEC P) {
        return new PointOnEC(P.getX(), (P.getY()).negate(), P.getZ());
    }
}
