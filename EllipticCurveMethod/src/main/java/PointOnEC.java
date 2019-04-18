import java.math.BigInteger;
import java.util.Random;

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
    /**
     * @param n field characteristic
     * @return new point generated over "n"-characteristic field
     */
    public static PointOnEC generateRandomPoint(BigInteger n) {
        Random rnd = new Random();
        return new PointOnEC(new BigInteger(n.bitLength(), rnd), new BigInteger(n.bitLength(), rnd), new BigInteger("1"));
    }
    @Override
    public String toString() {
        return "PointOnEC{" +
                x +
                ", " + y +
                '}';
    }
}
