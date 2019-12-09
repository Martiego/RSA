package pl.wtorkowy.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import pl.wtorkowy.cast.ToTab;
import pl.wtorkowy.crypt.RSA;

import java.awt.*;
import java.awt.datatransfer.StringSelection;
import java.io.*;
import java.math.BigInteger;
import java.util.Arrays;
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
    private Label publicKeyFile;
    @FXML
    private Label privateKeyFile;
    @FXML
    private Label nFile;
    @FXML
    private Label mFile;
    @FXML
    private TextField nameFile;

    @FXML
    private ProgressBar progressBar;
    private double progress = 0;
    private double tmpProgress;
    private RSA rsa;

    @FXML
    private File file;
    @FXML
    private Stage stage;
    @FXML
    private int[] cipherTextTab;

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
        String d;
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
    public void encryptFile() {
        if (file != null) {
            Thread thread = new Thread(new EncryptFile());
            thread.start();
        }
    }

    @FXML
    public void decryptFile() {
        if(file != null) {
            Thread thread = new Thread(new DecryptFile());
            thread.start();
        }
    }

    @FXML
    public void copy() {
        Toolkit.getDefaultToolkit().getSystemClipboard().setContents(new StringSelection(cipherText.getText()), null);
    }

    @FXML
    public void generate() {

    }

    public class EncryptFile implements Runnable {

        @Override
        public void run() {

        }
    }

    public class DecryptFile implements Runnable {

        @Override
        public void run() {

        }
    }
}

