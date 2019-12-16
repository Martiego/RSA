package pl.wtorkowy;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import pl.wtorkowy.crypt.RSA;

import java.math.BigInteger;

public class AppTest {
    @Test
    public void rsa() {
        RSA rsa = new RSA(new BigInteger("3"), new BigInteger("5"));
        Assertions.assertEquals(rsa.getN().toString(), "15");
        Assertions.assertEquals(rsa.getEulerFunction().toString(), "8");
        Assertions.assertEquals(rsa.getE(), true);
        Assertions.assertEquals(rsa.getD().toString(), "3");

        BigInteger m = new BigInteger("4");
        BigInteger c = rsa.encrypt(m);
        BigInteger d = rsa.decrypt(c);
        Assertions.assertEquals(m, d);
    }
}
