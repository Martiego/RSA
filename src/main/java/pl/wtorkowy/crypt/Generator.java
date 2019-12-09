package pl.wtorkowy.crypt;

import java.math.BigInteger;
import java.util.Random;

public class Generator {
    private static Random random = new Random();

    private static String[] numbers = { "11",  "4",  "3", "22", "16",  "6", "38", "44", "29", "10",
                                         "1", "12", "20", "14", "15", "50", "17", "18", "19", "13",
                                        "21",  "2", "23", "24", "25", "26",  "7", "28",  "9", "30",
                                        "31", "32", "33", "34", "35", "36", "41", "27", "39", "37",
                                        "40", "42", "43",  "8", "45", "46", "47", "48", "49",  "5"};
    private static String[] primeNumbers = {  "1", "21",  "3",  "7", "51", "13",  "9",  "5", "47",
                                             "15", "31", "33", "47", "49", "51", "23", "99", "16",
                                             "27", "61", "41", "43", "29", "25", "17", "71", "73"};

    public static BigInteger generateLessThan(BigInteger x) {
        BigInteger tmp = x.divide(new BigInteger("3"));
        while(!Euklides.isRelativelyPrime(x, tmp)) {
            tmp = tmp.add(BigInteger.ONE);
        }

        return tmp;
    }

    public static BigInteger generatePrimeNumber(int bits) {
        BigInteger tmp = new BigInteger("2");
        tmp = tmp.pow(bits);
        tmp = tmp.subtract(BigInteger.ONE);

        while (!tmp.isProbablePrime(20)) {
            tmp = tmp.add(new BigInteger(primeNumbers[random.nextInt(primeNumbers.length)]));
        }

        return tmp;
    }
}
