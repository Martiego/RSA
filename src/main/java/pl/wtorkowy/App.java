package pl.wtorkowy;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import pl.wtorkowy.crypt.BlindSignature;

import java.math.BigInteger;

public class App extends Application {
    @Override
    public void start(Stage primaryStage) throws Exception {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/MainScreen.fxml"));
        StackPane stackPane = loader.load();
        Scene scene = new Scene(stackPane, 1000, 500);

        primaryStage.setResizable(false);
        primaryStage.setScene(scene);
        primaryStage.setTitle("RSA");
        primaryStage.show();
    }

    public static void main(String[] args) {
//        launch();
        BlindSignature blindSignature = new BlindSignature(new BigInteger("5"), new BigInteger("13"));
        BigInteger blindMessage = blindSignature.blindMessage(new BigInteger("25"));
        BigInteger cypherSign = blindSignature.cypherSign(blindMessage);
        BigInteger blindSignatureXD = blindSignature.getBlindSignature(cypherSign);
        BigInteger publicMessage = blindSignature.unblindSign(blindSignatureXD);
//        System.out.println(blindSignature.unblindSign(blindSignature.getBlindSignature(blindSignature.sign(blindSignature.blindMessage(new BigInteger("50"))))));
    }
}
