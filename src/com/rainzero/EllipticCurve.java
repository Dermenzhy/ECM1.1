package com.rainzero;

import java.math.BigInteger;

/**
 * Class EllipticCurve contains parameters of curve and methods to get this parameters
 *
 * @author Dermenzhy
 */
public class EllipticCurve {
    private BigInteger a;
    private BigInteger b;
    private BigInteger n;

    /**
     * Elliptic curve constructor
     *
     * @param a parameter "a" of curve equation y^2=x^3+a*x+b
     * @param b parameter "b" of curve equation y^2=x^3+a*x+b
     * @param n field characteristic over which curve is constructed
     */
    public EllipticCurve(BigInteger a, BigInteger b, BigInteger n) {
        this.a = a;
        this.b = b;
        this.n = n;
    }

    /**
     * @return parameter "a" of the curve
     */
    public BigInteger getA() {
        return a;
    }

    /**
     * @return parameter "b" of the curve
     */
    public BigInteger getB() {
        return b;
    }

    /**
     * @return field characteristic
     */
    public BigInteger getN() {
        return n;
    }

    /**
     * Simple toString method override to print curve parameters
     *
     * @return String with curve parameters and field characteristic
     */
    @Override
    public String toString() {
        return "EllipticCurve{" +
                "a=" + a +
                ", b=" + b +
                ", over field n=" + n +
                '}';
    }
}
