import java.math.BigInteger;

public class GCDException extends Exception {
    private BigInteger g;
    private BigInteger n;

    public BigInteger getG() {
        return g;
    }

    public BigInteger getN() {
        return n;
    }

    public GCDException(String message, BigInteger g, BigInteger n) {
        super(message);
        this.g = g;
        this.n = n;
    }
}
