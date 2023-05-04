module com.example.graphic {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;


    opens com.example.graphic to javafx.fxml;
    exports com.example.graphic;
}