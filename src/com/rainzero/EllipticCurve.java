package com.rainzero;

import java.math.BigInteger;

/**
 * Class EllipticCurve contains parameters of curve and methods to get this parameters
 *
 * @author Dermenzhy
 */
public class EllipticCurve {
    /**
     * Parameter "a" of curve equation y^2=x^3+a*x+b
     */
    private BigInteger a;
    /**
     * Parameter "b" of curve equation y^2=x^3+a*x+b
     */
    private BigInteger b;
    /**
     * Characteristic of the field modulo over which curve is constructed n (also the number that factorized)
     */
    private BigInteger n;

    /**
     * Elliptic curve constructor
     *
     * @param a parameter "a" of curve
     * @param b parameter "b" of curve
     * @param n field characteristic
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
