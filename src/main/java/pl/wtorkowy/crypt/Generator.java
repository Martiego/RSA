package pl.wtorkowy.crypt;

import java.math.BigInteger;
import java.util.Random;

public class Generator {
    private Random random = new Random();

    private String[] numbers = {        "11",  "4",  "3", "22", "16",  "6", "38", "44", "29", "10",
                                         "1", "12", "20", "14", "15", "50", "17", "18", "19", "13",
                                        "21",  "2", "23", "24", "25", "26",  "7", "28",  "9", "30",
                                        "31", "32", "33", "34", "35", "36", "41", "27", "39", "37",
                                        "40", "42", "43",  "8", "45", "46", "47", "48", "49",  "5"};

    public static BigInteger generateLessThan(BigInteger x) {
        BigInteger tmp = x.divide(new BigInteger("3"));
        while(!Euklides.isRelativelyPrime(x, tmp)) {
            tmp = tmp.add(BigInteger.ONE);
        }

        return tmp;
    }
}
