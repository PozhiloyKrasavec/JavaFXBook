module com.example.leha {
    requires javafx.controls;
    requires javafx.fxml;
    requires org.apache.pdfbox;
    requires java.desktop;


    opens com.example.leha to javafx.fxml;
    exports com.example.leha.Model;
    exports com.example.leha;
}