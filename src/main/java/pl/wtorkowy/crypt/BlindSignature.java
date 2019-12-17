package pl.wtorkowy.crypt;

import java.math.BigInteger;

public class BlindSignature {
    private RSA rsa;
    private BigInteger k;
    private BigInteger n;
    private BigInteger reverseK;

    public BlindSignature(BigInteger p, BigInteger q) {
        rsa = new RSA(p, q);
        n = rsa.getN();
        k = Generator.generateLessThan(n);
        reverseK = Euklides.extentAlgorithmEuclidean(k, n);
    }

    public BigInteger blindMessage(BigInteger m) {
        return m.multiply(k.modPow(rsa.getE(), n));
    }

    public BigInteger cypherSign(BigInteger t) {
        return t.modPow(rsa.getD(), n);
    }

    public BigInteger getBlindSignature(BigInteger s) {
        return (s.multiply(reverseK).mod(n));
    }

    public BigInteger unblindSign(BigInteger c) {
        return c.modPow(rsa.getE(), n);
    }

    public BigInteger getK() {
        return k;
    }

    public BigInteger getN() {
        return n;
    }
}
