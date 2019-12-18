package pl.wtorkowy.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import pl.wtorkowy.cast.ToTab;
import pl.wtorkowy.crypt.BlindSignature;
import pl.wtorkowy.crypt.Generator;
import pl.wtorkowy.crypt.RSA;

import java.awt.*;
import java.awt.datatransfer.StringSelection;
import java.io.*;
import java.math.BigInteger;
import java.util.Scanner;

public class CryptoController {

    @FXML
    private Label n;
    @FXML
    private Label d;
    @FXML
    private Label e;

    @FXML
    private TextField p;
    @FXML
    private TextField q;

    @FXML
    private Label path;
    @FXML
    private TextArea text;
    @FXML
    private Label cipherText;
    @FXML
    private TextField times;


    @FXML
    private Label publicKey;
    @FXML
    private TextField nameFile;

    @FXML
    private Label pFile;
    @FXML
    private Label qFile;
    @FXML
    private Label eFile;
    @FXML
    private Label dFile;
    @FXML
    private Label kFile;
    @FXML
    private Label nFile;

    @FXML
    private ProgressBar progressBar;
    private double progress = 0;
    private double tmpProgress;
    private RSA rsa;
    private BlindSignature blindSignature;
    private BigInteger pRSA;
    private BigInteger qRSA;

    @FXML
    private File file;
    @FXML
    private Stage stage;

    @FXML
    public void openFile() {
        FileChooser fileChooser = new FileChooser();

        fileChooser.setTitle("Otworz Plik do zaszyfrowania");

        file = fileChooser.showOpenDialog(stage);

        if (file != null) {
            path.setText(file.getAbsolutePath());
        }
    }

    @FXML
    public void encrypt() {
        BigInteger p = new BigInteger(this.p.getText());
        BigInteger q = new BigInteger(this.q.getText());
        if(p.isProbablePrime(10) && q.isProbablePrime(10)) {
            rsa = new RSA(p, q);
            n.setText(rsa.getN().toString());
            e.setText(rsa.getE().toString());
            d.setText(rsa.getD().toString());
//            int times = rsa.getN().toString().length()/3;
//            int len = text.getText().length();
            int[] tmpInt = ToTab.toIntTab(text.getText().toCharArray());
            String s = "";
            for (int i : tmpInt) {
                s += rsa.encrypt(new BigInteger(Integer.toString(i))).toString() + " ";
            }
            cipherText.setText(s);
        }
        else
            cipherText.setText("p and q aren't prime");
    }

    @FXML
    public void decrypt() {
        String s = "";
        String tmp = cipherText.getText();
        int j = 0;

        for (int i = 0; i < tmp.length(); i++) {
            if(tmp.charAt(i) == ' ') {
                s += (char)rsa.decrypt(new BigInteger(tmp.substring(j, i))).intValue();
                j = i+1;
            }
        }

        cipherText.setText(s);
    }


    @FXML
    public void copy() {
        Toolkit.getDefaultToolkit().getSystemClipboard().setContents(new StringSelection(cipherText.getText()), null);
    }

    @FXML
    public void generate() {
        int t = Integer.parseInt(times.getText());
        while(t%8 != 0)
            t--;

        pRSA = Generator.generatePrimeNumber(t);
        qRSA = Generator.generatePrimeNumber(t-1);

        rsa = new RSA(pRSA, qRSA);

        pFile.setText(pRSA.toString());
        qFile.setText(qRSA.toString());
        eFile.setText(rsa.getE().toString());
        dFile.setText(rsa.getD().toString());
        kFile.setText("{k}");
        nFile.setText(rsa.getN().toString());
    }

    @FXML
    public void blindFile() {
        blindSignature = new BlindSignature(pRSA, qRSA);
        kFile.setText(blindSignature.getK().toString());

        if(file != null) {
            Thread thread = new Thread(new BlindFile());
            thread.start();
        }
    }

    @FXML
    public void signFile() {
        if(file != null) {
            Thread thread = new Thread(new SignFile());
            thread.start();
        }
    }

    @FXML
    public void checkSign() {
        if(file != null) {
            Thread thread = new Thread(new CheckSign());
            thread.start();
        }
    }

    public class CheckSign implements Runnable {

        @Override
        public void run() {
            try {
                progressBar.setProgress(progress);

                int tmp = blindSignature.getN().bitLength();

                while (tmp%8 != 0)
                    tmp--;

                tmp /= 8;

                String name = ToTab.replace(file.getAbsolutePath(), File.separatorChar, nameFile.getText());

                File newFile = new File(name);
                FileWriter test = new FileWriter(name + "TEST");

                Scanner in = new Scanner(file);

                FileOutputStream fileOutputStream = new FileOutputStream(newFile);
                byte[] bigInByte = new byte[tmp];
                byte[] tmpByte = new byte[tmp*8];
                int tmpi = 0;

                BigInteger i = new BigInteger("1");


                while(in.hasNext()) {
                    progressBar.setProgress(progress += tmpProgress);
                    tmpByte = ToTab.getByteTabBigInteger(blindSignature.unblindSign(new BigInteger(in.next())), tmp);

                    for (int j = 0; j < tmpByte.length/8; j++) {
                        tmpi = ToTab.toInt(ToTab.cutTab(tmpByte, j*8, 8));
                        fileOutputStream.write(tmpi);
                        test.write(tmpi + "\n");
                    }

//                    for (byte c: bigInByte
//                         ) {
//                        test.write(unsigned(c) + " TMP: " + tmp + " Len: " + bigInByte.length + "\n");
//                        fileOutputStream.write(unsigned(c));
//                    }
//                    fileOutputStream.write(bigInByte);
                }

                fileOutputStream.close();
                test.close();
                progress = 0;

            } catch(IOException ex) {
                ex.printStackTrace();
            }
        }
    }

    private int unsigned(byte b) {
        return b & 0xFF;
    }

    public class BlindFile implements Runnable {

        @Override
        public void run() {

            try {
                progressBar.setProgress(progress);
                FileInputStream fileInputStream = new FileInputStream(file);

                int tmp = blindSignature.getN().bitLength();

                while (tmp%8 != 0)
                    tmp--;

                tmp /= 8;

                String name = ToTab.replace(file.getAbsolutePath(), File.separatorChar, nameFile.getText());

                FileWriter newFile = new FileWriter(name);
                FileWriter test = new FileWriter(name + "TEST");


                long fileLen = file.length();
                long rest = fileLen%tmp;

                tmpProgress = 1.0/(fileLen/tmp);

                int[] tabWithBytes = new int[tmp];

                for (int i = 0; i < fileLen/tmp; i++) {
                    progressBar.setProgress(progress += tmpProgress);
                    for (int j = 0; j < tmp; j++) {
                        tabWithBytes[j] = fileInputStream.read();
                        test.write(tabWithBytes[j] + "\n");
                    }
                    newFile.write(blindSignature.blindMessage(ToTab.generateBigInteger(tabWithBytes)).toString() + "\n");
                }

                if(rest != 0) {
                    for (int i = 0; i < rest; i++) {
                        tabWithBytes[i] = fileInputStream.read();
                    }
                    for (int i = (int)rest; i < tmp; i++) {
                        tabWithBytes[i] = 0;
                    }
                    newFile.write(blindSignature.blindMessage(ToTab.generateBigInteger(tabWithBytes)).toString() + "\n");
                }

                newFile.close();
                test.close();
                fileInputStream.close();

                progress = 0;

            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
    }

    public class SignFile implements Runnable {

        @Override
        public void run() {
            try {
                progressBar.setProgress(progress);

                String name = ToTab.replace(file.getAbsolutePath(), File.separatorChar, nameFile.getText());

                FileWriter newFile = new FileWriter(name);

                Scanner in = new Scanner(file);

                while(in.hasNext()) {
                    progressBar.setProgress(progress += tmpProgress);
                    newFile.write(blindSignature.getBlindSignature(blindSignature.cypherSign(new BigInteger(in.next()))).toString() + "\n");
                }

                newFile.close();
                progress = 0;

            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
    }
}

