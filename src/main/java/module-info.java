module com.example.graphic
{
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;
    requires java.desktop;
    requires javafx.swing;

    opens com.example.competenciesForStudents to javafx.fxml;
    exports com.example.competenciesForStudents;
}