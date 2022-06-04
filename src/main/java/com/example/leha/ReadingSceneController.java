package com.example.leha;

import com.example.leha.Model.PDFtoJPGConverter;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.event.ActionEvent;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.*;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import java.util.concurrent.ExecutionException;

public class ReadingSceneController implements Initializable {
    private int pageNum = 1;
    private BackgroundImage backgroundImage;
    private Background background;
    private FileChooser fileChooser = new FileChooser();
    private Stage stage;
    @FXML
    ScrollPane scrollPane;
    @FXML
    TextField pageNumberTextField;
    @FXML
    AnchorPane anchorPane;
    List<Image> pages;
    public void nextButtonOn(ActionEvent e){
        pageNum++;
        if (pageNum< 1 || pageNum >= pages.size()){
            pageNum--;
            Alert alert = new Alert(javafx.scene.control.Alert.AlertType.ERROR);
            alert.setTitle("Ошибка");
            alert.setHeaderText("Ошибка ввода страницы");
            alert.setContentText("Введите страницу от 1 до " + pages.size());
            if (alert.showAndWait().get() == ButtonType.OK){
                alert.close();
            }
        }
        else {
            backgroundImage = new BackgroundImage(pages.get(pageNum-1),
                    BackgroundRepeat.NO_REPEAT,
                    BackgroundRepeat.NO_REPEAT,
                    BackgroundPosition.DEFAULT,
                    BackgroundSize.DEFAULT);
            background = new Background(backgroundImage);
            anchorPane.setBackground(background);
            scrollPane.setVvalue(scrollPane.getVmin());
            pageNumberTextField.setText(String.valueOf(pageNum));
        }
    }
    public void backButtonOn(ActionEvent e ){
        pageNum--;
        if (pageNum < 1 || pageNum>=pages.size()){
            pageNum++;
            Alert alert = new Alert(javafx.scene.control.Alert.AlertType.ERROR);
            alert.setTitle("Ошибка");
            alert.setHeaderText("Ошибка ввода страницы");
            alert.setContentText("Введите страницу от 1 до " + pages.size());
            if (alert.showAndWait().get() == ButtonType.OK){
                alert.close();
            }
        }
        else {
            backgroundImage = new BackgroundImage(pages.get(pageNum-1),
                    BackgroundRepeat.NO_REPEAT,
                    BackgroundRepeat.NO_REPEAT,
                    BackgroundPosition.DEFAULT,
                    BackgroundSize.DEFAULT);
            background = new Background(backgroundImage);
            anchorPane.setBackground(background);
            scrollPane.setVvalue(scrollPane.getVmin());
            pageNumberTextField.setText(String.valueOf(pageNum));
        }
    }
    public void pageNumberInput(KeyEvent e){
        if (e.getCode().equals(KeyCode.ENTER)){
            pageNum = Integer.parseInt(pageNumberTextField.getText());
            if (pageNum < 1 || pageNum >= pages.size()){
                Alert alert = new Alert(javafx.scene.control.Alert.AlertType.ERROR);
                alert.setTitle("Ошибка");
                alert.setHeaderText("Ошибка ввода страницы");
                alert.setContentText("Введите страницу от 1 до " + pages.size());
                if (alert.showAndWait().get() == ButtonType.OK){
                    alert.close();
                }
            }
            else {
                backgroundImage = new BackgroundImage(pages.get(pageNum-1),
                        BackgroundRepeat.NO_REPEAT,
                        BackgroundRepeat.NO_REPEAT,
                        BackgroundPosition.DEFAULT,
                        BackgroundSize.DEFAULT);
                background = new Background(backgroundImage);
                anchorPane.setBackground(background);
                scrollPane.setVvalue(scrollPane.getVmin());
                pageNumberTextField.setText(String.valueOf(pageNum));
            }
        }
    }
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        fileChooser.setTitle("Выберите pdf файл");
        fileChooser.setInitialDirectory(new File("C:\\Users\\user\\Downloads"));
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("All Files","*.*"),
                new FileChooser.ExtensionFilter("PDF","*.pdf"));
        String filePath = fileChooser.showOpenDialog(stage).getAbsolutePath();
        PDFtoJPGConverter pdFtoJPGConverter = new PDFtoJPGConverter(filePath);
        new Thread(pdFtoJPGConverter).start();
        try {
            pages = pdFtoJPGConverter.get();
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }
        backgroundImage = new BackgroundImage(pages.get(0),
                BackgroundRepeat.NO_REPEAT,
                BackgroundRepeat.NO_REPEAT,
                BackgroundPosition.DEFAULT,
                BackgroundSize.DEFAULT);
        background = new Background(backgroundImage);
        anchorPane.setBackground(background);
        scrollPane.setVvalue(scrollPane.getVmin());
        pageNumberTextField.setText(String.valueOf(pageNum));
    }
}
