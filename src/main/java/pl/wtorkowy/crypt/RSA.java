package pl.wtorkowy.crypt;

import java.math.BigInteger;

public class RSA {
    private BigInteger n;
    private BigInteger eulerFunction;
    private BigInteger e;
    private BigInteger d;

    public RSA(BigInteger p, BigInteger q) {
        n = p.multiply(q);
        eulerFunction = (p.subtract(BigInteger.ONE)).multiply(q.subtract(BigInteger.ONE));
        e = Generator.generateLessThan(eulerFunction);
        d = Euklides.extentAlgorithmEuclidean(e, eulerFunction);
    }

    public BigInteger encrypt(BigInteger m) {
        return m.modPow(e, n);
    }

    public BigInteger decrypt(BigInteger c) {
        return c.modPow(d, n);
    }

    public BigInteger getN() {
        return n;
    }

    public BigInteger getEulerFunction() {
        return eulerFunction;
    }

    public BigInteger getE() {
        return e;
    }

    public BigInteger getD() {
        return d;
    }

}
