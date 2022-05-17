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
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.ResourceBundle;
import java.util.concurrent.ExecutionException;

public class ReadingSceneController implements Initializable {
    private int pageNum = 1;
    private BackgroundImage backgroundImage;
    private Background background;
    private List<Paragraph> paragraphs = Arrays.asList(
            new Paragraph("Установка библиотеки JavaFX",0),
            new Paragraph("Написание первого JavaFX приложения",3),
            new Paragraph("Обучение работе с FXML файлами",9),
            new Paragraph("Элементы интерфейса JavaFX.Часть 1",19),
            new Paragraph("Элементы интерфейса JavaFX.Часть 1",28),
            new Paragraph("Применение CSS в JavaFX",36),
            new Paragraph("Интеграция JavaFX и Spring",45)
    );
    @FXML
    MenuButton paragraphsMenu;
    @FXML
    ScrollPane scrollPane;
    @FXML
    TextField pageNumberTextField;
    @FXML
    AnchorPane anchorPane;
    List<Image> pages;
    public void nextButtonOn(ActionEvent e){
        pageNum++;
        if (pageNum<= 1 || pageNum >= 53){
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
            pageNumberTextField.setText(String.valueOf(pageNum-1));
        }
    }
    public void backButtonOn(ActionEvent e ){
        pageNum--;
        if (pageNum <= 1 || pageNum>= 53){
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
            pageNumberTextField.setText(String.valueOf(pageNum-1));
        }
    }
    public void pageNumberInput(KeyEvent e){
        if (e.getCode().equals(KeyCode.ENTER)){
            pageNum = Integer.parseInt(pageNumberTextField.getText());
            if (pageNum <= 1 || pageNum >= 53){
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
                pageNumberTextField.setText(String.valueOf(pageNum-1));
            }
        }
    }
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        PDFtoJPGConverter jpgConverterTask = new PDFtoJPGConverter("src/main/resources/com/example/renata/kursach_medvedus.pdf");
        new Thread(jpgConverterTask).start();
        try {
            pages = jpgConverterTask.get();
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }
        paragraphs.forEach(paragraph -> {
            MenuItem item = new MenuItem(paragraph.getName());
            item.setOnAction(event -> {
                backgroundImage = new BackgroundImage(pages.get(paragraph.getPage_num()),
                        BackgroundRepeat.NO_REPEAT,
                        BackgroundRepeat.NO_REPEAT,
                        BackgroundPosition.DEFAULT,
                        BackgroundSize.DEFAULT);
                background = new Background(backgroundImage);
                anchorPane.setBackground(background);
                scrollPane.setVvalue(scrollPane.getVmin());
                pageNumberTextField.setText(String.valueOf(paragraph.getPage_num()+1));
            });
            paragraphsMenu.getItems().add(item);
        });
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
    private static class Paragraph{
        private String name;
        private int page_num;

        public Paragraph(String name, int page_num) {
            this.name = name;
            this.page_num = page_num;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public int getPage_num() {
            return page_num;
        }

        public void setPage_num(int page_num) {
            this.page_num = page_num;
        }
    }
}
