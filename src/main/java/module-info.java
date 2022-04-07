module com.example.renata {
    requires javafx.controls;
    requires javafx.fxml;
    requires org.apache.pdfbox;
    requires java.desktop;


    opens com.example.renata to javafx.fxml;
    exports com.example.renata.Model;
    exports com.example.renata;
}