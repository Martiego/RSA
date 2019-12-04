package pl.wtorkowy.crypt;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.math.BigInteger;

public class EuklidesTest {
    @Test
    @DisplayName("Is relative prime")
    public void isRelativePrimeAndReverse() {
        BigInteger a = new BigInteger("120");
        BigInteger b = new BigInteger("53");
        BigInteger c = new BigInteger("77");
        BigInteger d = new BigInteger("19");

        Assertions.assertTrue(Euklides.isRelativelyPrime(a, b));
        Assertions.assertFalse(Euklides.isRelativelyPrime(120, 60));

        Assertions.assertEquals(Euklides.extentAlgorithmEuclidean(a, b), d);
        Assertions.assertEquals(Euklides.extentAlgorithmEuclidean(b, a), c);
    }
}
