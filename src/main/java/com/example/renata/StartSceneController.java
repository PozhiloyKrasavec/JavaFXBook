package com.example.renata;

import javafx.application.Preloader;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;

public class StartSceneController {
    private Stage stage;
    private Scene scene;
    private Parent root;

    @FXML
    private Button exitButton;
    @FXML
    private AnchorPane startScenePane;
    //move to next scene
    public void startButtonOn(ActionEvent e) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("reading-scene.fxml"));
        Parent root = loader.load();
        stage = (Stage) ((Node)e.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }
    //exit from app
    public void exitButtonOn(ActionEvent e){

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Выход");
        alert.setHeaderText("Вы выходите");
        alert.setContentText("Вы точно хотите выйти?");

        if (alert.showAndWait().get() == ButtonType.OK) {
            stage = (Stage) startScenePane.getScene().getWindow();
            stage.close();
        }
    }
}