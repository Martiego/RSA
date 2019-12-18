package pl.wtorkowy;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import pl.wtorkowy.cast.ToTab;
import pl.wtorkowy.crypt.RSA;

import java.math.BigInteger;

public class AppTest {
    @Test
    public void rsa() {
        RSA rsa = new RSA(new BigInteger("3"), new BigInteger("5"));
        Assertions.assertEquals(rsa.getN().toString(), "15");
        Assertions.assertEquals(rsa.getEulerFunction().toString(), "8");
        Assertions.assertEquals(rsa.getE().toString(), "3");
        Assertions.assertEquals(rsa.getD().toString(), "3");

        BigInteger m = new BigInteger("4");
        BigInteger c = rsa.encrypt(m);
        BigInteger d = rsa.decrypt(c);
        Assertions.assertEquals(m, d);
    }

    @Test
    public void changeToBig() {
        Assertions.assertEquals(ToTab.generateBigInteger(new int[] {1, 2, 3}).toString(), "66051");

    }

    @Test
    public void changeToBits() {
        BigInteger m = new BigInteger("66051");
        int exp = 3;

        byte[] tab = ToTab.getByteTabBigInteger(m, exp);

        Assertions.assertArrayEquals(new byte[] {0,0,0,0,0,0,0,1,0,0,0,0,0,0,1,0,0,0,0,0,0,0,1,1}, tab);
    }
}
