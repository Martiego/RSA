package pl.wtorkowy;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

public class App extends Application {
    @Override
    public void start(Stage primaryStage) throws Exception {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/MainScreen.fxml"));
        StackPane stackPane = loader.load();
        Scene scene = new Scene(stackPane, 800, 500);

        primaryStage.setResizable(false);
        primaryStage.setTitle("RSA");
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}
