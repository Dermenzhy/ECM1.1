package com.rainzero;

import java.math.BigInteger;

/**
 * Class that describes point in 3-dimension
 * @author Dermenzhy
 */
public class PointOnEC {
    /**
     * Point coordinates x,y,z
     */
    private BigInteger x;
    private BigInteger y;
    private BigInteger z;

    public BigInteger getX() {
        return x;
    }

    public BigInteger getY() {
        return y;
    }

    public BigInteger getZ() {
        return z;
    }

    public PointOnEC(BigInteger x, BigInteger y, BigInteger z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }

    @Override
    public String toString() {
        return "PointOnEC{" +
                x +
                ", " + y +
                '}';
    }
}
