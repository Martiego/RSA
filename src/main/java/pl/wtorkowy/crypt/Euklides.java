package pl.wtorkowy.crypt;

import java.math.BigInteger;

public class Euklides {

    private static BigInteger algorithmEuclidean(BigInteger m, BigInteger n) {
        BigInteger temp;

        while (!n.toString().equals("0")) {
            temp = m.mod(n);
            m = n;
            n = temp;
        }
        return m;
    }

    private static int algorithmEuclidean(int m, int n) {
        int temp;

        while(n != 0) {
            temp = m%n;
            m = n;
            n = temp;
        }

        return m;
    }

    public static BigInteger extentAlgorithmEuclidean(BigInteger n, BigInteger m) {
        BigInteger u, w, x, z, q;
        BigInteger zero = new BigInteger("0");
        BigInteger one = new BigInteger("1");

        q = zero;
        u = one;
        x = zero;
        w = n;
        z = m;


        while(w.compareTo(zero) != 0) {
            if(w.compareTo(z) == -1) {
                q = u;
                u = x;
                x = q;
                q = w;
                w = z;
                z = q;
            }
            q = w.divide(z);
            u = u.subtract(q.multiply(x));
            w = w.subtract(q.multiply(z));
        }

        if(z.compareTo(one) == 0) {
            if(x.compareTo(zero) == -1) {
                x = x.add(m);
            }

            return x;
        }
        return x;
    }

    public static boolean isRelativelyPrime (BigInteger m, BigInteger n) {
        return algorithmEuclidean(m, n).toString().equals("1");
    }

    public static boolean isRelativelyPrime (int m, int n) {
        return algorithmEuclidean(m, n) == 1;
    }
}
