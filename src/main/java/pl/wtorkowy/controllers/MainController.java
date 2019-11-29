package pl.wtorkowy.controllers;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;

import java.io.IOException;

public class MainController {

    @FXML
    private StackPane mainStackPane;

    @FXML
    public void initialize() throws IOException {
//        FXMLLoader loader = new FXMLLoader(this.getClass().getResource("/crypto.fxml"));
//        Pane pane = loader.load();
//        mainStackPane.getChildren().add(pane);
    }
}
