package com.example.competenciesForStudents;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import java.io.IOException;

/**
 * Класс для запуска программы.
 * @author Щербак Анастасия Романовна
 * @version 0.6
 */

public class StartProgram extends Application
{
        @Override
        public void start(Stage stage) throws IOException
        {
            FXMLLoader fxmlLoader = new FXMLLoader(StartProgram.class.getResource("hello-view.fxml"));
            Scene scene = new Scene(fxmlLoader.load(), 600, 460);
            stage.setTitle("База данных");
            stage.setScene(scene);
            stage.show();
        }
    public static void main(String[] args)
    {
        launch();
    }
}