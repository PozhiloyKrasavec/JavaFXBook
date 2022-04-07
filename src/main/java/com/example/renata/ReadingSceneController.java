package com.example.renata;

import com.example.renata.Model.PDFtoJPGConverter;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.event.ActionEvent;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.*;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import java.util.concurrent.ExecutionException;

public class ReadingSceneController implements Initializable {
    private int pageNum = 0;
    private BackgroundImage backgroundImage;
    private Background background;
    @FXML
    ScrollPane scrollPane;
    @FXML
    TextField pageNumberTextField;
    @FXML
    AnchorPane anchorPane;
    List<Image> pages;
    public void nextButtonOn(ActionEvent e){
        pageNum++;
        if (pageNum <= 0 || pageNum >= 52){
            pageNum--;
            Alert alert = new Alert(javafx.scene.control.Alert.AlertType.ERROR);
            alert.setTitle("Ошибка");
            alert.setHeaderText("Ошибка ввода страницы");
            alert.setContentText("Введите страницу от 1 до 53");
            if (alert.showAndWait().get() == ButtonType.OK){
                alert.close();
            }
        }
        else {
            backgroundImage = new BackgroundImage(pages.get(pageNum),
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
        if (pageNum <= 0 || pageNum >= 52){
            pageNum++;
            Alert alert = new Alert(javafx.scene.control.Alert.AlertType.ERROR);
            alert.setTitle("Ошибка");
            alert.setHeaderText("Ошибка ввода страницы");
            alert.setContentText("Введите страницу от 1 до 53");
            if (alert.showAndWait().get() == ButtonType.OK){
                alert.close();
            }
        }
        else {
            backgroundImage = new BackgroundImage(pages.get(pageNum),
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
            if (pageNum-1 <= 0 || pageNum-1 >= 52){
                Alert alert = new Alert(javafx.scene.control.Alert.AlertType.ERROR);
                alert.setTitle("Ошибка");
                alert.setHeaderText("Ошибка ввода страницы");
                alert.setContentText("Введите страницу от 1 до 53");
                if (alert.showAndWait().get() == ButtonType.OK){
                    alert.close();
                }
            }
            else {
                backgroundImage = new BackgroundImage(pages.get(pageNum),
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
        PDFtoJPGConverter jpgConverterTask = new PDFtoJPGConverter("src/main/resources/kursach_medvedus.pdf");
        new Thread(jpgConverterTask).start();
        try {
            pages = jpgConverterTask.get();
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
        pageNumberTextField.setText(String.valueOf(pageNum));
    }
}
