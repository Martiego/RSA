package pl.wtorkowy.cast;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;

public class ToTab {

    public static char[] toCharTab(String text) {
        char[] result = new char[text.length()];
        for (int i = 0; i < text.length(); i++) {
            result[i] = text.charAt(i);
        }

        return result;
    }

    public static char[] toCharTab(int[] block) {
        char[] result = new char[block.length];
        for (int i = 0; i < block.length; i++) {
            result[i] = (char) block[i];
        }
        return result;
    }

    public static int[] toIntTab(byte[] block) {
        int[] result = new int[block.length/8];
        for (int i = 0; i < block.length/8; i++) {
            result[i] = toInt(cutTab(block, i * 8, 8));
        }

        return result;
    }

    public static int[] toIntTab(char[] block) {
        int[] blockInt = new int[block.length];
        for (int i = 0; i < block.length; i++) { blockInt[i] = block[i]; }

        return blockInt;
    }

    public static byte[] toByteTab(int[] blockInt) {
        byte[] blockByte = new byte[blockInt.length*8];
        int x = 0;
        for (int i = 0; i < blockInt.length; i++) {
            for (int j = 7 + x; j >= x; j--) {
                blockByte[j] = (byte) (blockInt[i]%2);
                blockInt[i] = blockInt[i]/2;
            }
            x += 8;
        }
        return blockByte;
    }

    public static byte[] toByteTab(char[] block) {
        int[] tmp = toIntTab(block);

        return toByteTab(tmp);
    }

    public static byte[] toByteTab(byte number) {
        byte[] blockByte = new byte[4];
        byte tmp;

        for (int i = 0; i < 4; i++) {
            blockByte[i] = (byte) (number%2);
            number = (byte) (number/2);
        }

        for(int i = 0; i < 2; i++){
            tmp = blockByte[i];
            blockByte[i] = blockByte[3 - i];
            blockByte[3 - i] = tmp;
        }

        return blockByte;
    }

    public static int toInt(byte[] tab) {
        int result = 0;
        int temp = 1;

        for (int i = tab.length - 1; i >= 0; i--) {
            result += tab[i] * temp;
            temp *= 2;
        }
        return result;
    }

    public static byte[] cutTab(byte[] tab, int first, int count) {
        byte[] result = new byte[count];
        for (int i = 0; i < count; i++) {
            result[i] = tab[first++];
        }
        return result;
    }

    public static char[] cutTab(char[] tab, int first, int count) {
        char[] result = new char[count];
        for (int i = 0; i < count; i++) {
            result[i] = tab[first++];
        }
        return result;
    }

    public static String replace(String first, char separator, String name) {
        int i = first.length()-1;
        while(first.charAt(i) != separator) {
            i--;
        }

        first = first.substring(0, i+1);

        return first + name;
    }

    public static int[] toIntTab(String s) {
        List<Integer> list = new ArrayList<>();
        int tmp = 0;
        for (int i = 0; i < s.length(); i++) {
            if(s.charAt(i) == ',') {
                list.add(Integer.parseInt(s.substring(tmp, i)));
                tmp = i+1;
            }
        }
        list.add(Integer.parseInt(s.substring(tmp)));
        int[] result = new int[list.size()];
        for (int i = 0; i < list.size(); i++) {
            result[i] = list.get(i);
        }

        return result;
    }

    public static byte[] reverse(byte[] tab) {
        byte[] result = new byte[tab.length];

        for (int i = 0; i < tab.length; i++) {
            result[tab.length - 1 - i] = tab[i];
        }

        return result;
    }

    public static BigInteger generateBigInteger(int[] tab) {
        BigInteger result = new BigInteger(Integer.toString(tab[0]));
        BigInteger byteBig = new BigInteger("256");


        for (int i = 0; i < tab.length - 1; i++) {
            result = result.multiply(byteBig).add(new BigInteger(Integer.toString(tab[i+1])));
        }

        return result;
    }

    public static byte[] removeZero(byte[] b) {
        int tmp = 0;
        for (byte c: b) {
            if(c == 0)
                tmp++;
        }

        byte[] tmpByte = new byte[b.length-tmp];

        int j = 0;

        for (int i = 0; i < b.length; i++) {
            if(b[i] != 0) {
                tmpByte[j] = b[i];
                j++;
            }

        }
        return tmpByte;
    }

}
