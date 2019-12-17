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

    }

    @FXML
    public void checkSign() {

    }

    public class BlindFile implements Runnable {

        @Override
        public void run() {

            try {
                progressBar.setProgress(progress);
                FileInputStream fileInputStream = new FileInputStream(file);

                int tmp = blindSignature.getN().bitCount();

                while (tmp%8 != 0)
                    tmp--;

                tmp /= 8;

                String name = ToTab.replace(file.getAbsolutePath(), File.separatorChar, nameFile.getText());

                FileWriter newFile = new FileWriter(name);

                long fileLen = file.length();
                long juzChybaPietnastaZmiennaTmp = fileLen%tmp;

                tmpProgress = 1.0/(fileLen/tmp);

                int[] tabWithBytes = new int[tmp];

                for (int i = 0; i < fileLen/tmp; i++) {
                    progressBar.setProgress(progress += tmpProgress);
                    for (int j = 0; j < tmp; j++) {
                        tabWithBytes[j] = fileInputStream.read();
                    }
                    newFile.write(blindSignature.blindMessage(ToTab.generateBigInteger(tabWithBytes)).toString());
                }
                //TODO
                // Trzeba zająć się resztą bitów, tych pozostałych
                // Tam to jest ta następna zmienna
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
    }

    public class SignFile implements Runnable {

        @Override
        public void run() {

        }
    }
}

