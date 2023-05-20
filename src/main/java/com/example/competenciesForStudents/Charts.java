package com.example.competenciesForStudents;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.embed.swing.SwingFXUtils;
import javafx.scene.Scene;
import javafx.scene.SnapshotParameters;
import javafx.scene.chart.*;
import javafx.scene.control.Button;
import javafx.scene.image.WritableImage;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

/**
 * Класс для диаграмм: их создание и печать.
 * @author Щербак Анастасия Романовна
 * @version 0.5
 */
public class Charts
{
    public void showChart(Chart chart, String name,String fname, int x)
    {
        Pane pane = new Pane(chart);
        Scene scene = new Scene(pane,520,450);
        Stage stage = new Stage();
        stage.setTitle(name);
        stage.setScene(scene);
        stage.setX(x);
        stage.setY(100);
        Button button = new Button("Печать");
        button.setLayoutX(230);
        button.setLayoutY(405);
        button.setOnAction(event -> printChart(fname));
        pane.getChildren().add(button);
        stage.show();

        SnapshotParameters snapshotParameters = new SnapshotParameters(); // Создаем параметры снимка
        snapshotParameters.setDepthBuffer(true);//Эта строчка кода указывает на то, что параметры снимка (snapshot)
// объекта будут содержать буфер глубины (depth buffer). Глубинный буфер используется для определения того, какие пиксели
// находятся ближе к камере, а какие дальше, что позволяет правильно отображать 3D-модели на 2D-экране.
        WritableImage image = chart.snapshot(snapshotParameters, null); // Создаем снимок диаграммы
        File file = new File(fname);// Сохраняем снимок в файл
        try
        {
            ImageIO.write(SwingFXUtils.fromFXImage(image, null), "png", file);//сохранение
//изображения в файл. Он преобразует объект Image, который используется в JavaFX, в BufferedImage, который используется в
//стандартной библиотеке Java для работы с изображениями. Затем он сохраняет BufferedImage в файл формата PNG.
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
    }
    public void printPieChart(ArrayList<String> columnValues, ArrayList<Integer> count)
    {
        ObservableList<PieChart.Data> pieChartData = FXCollections.observableArrayList();
        for (int i = 0; i < columnValues.size(); i++)
        {
            pieChartData.add(new PieChart.Data(columnValues.get(i), count.get(i)*100/29));
        }
        PieChart chart = new PieChart(pieChartData);
        String name="Диаграмма пирога";
        showChart(chart, name,name+".png",100);
    }

    public XYChart.Series<String, Number> onAreaChart(String columnValues, ArrayList<Integer> counterInd, String[] competencies)
    {
        XYChart.Series<String, Number> series;
        series = new XYChart.Series<>();
        series.setName(columnValues);
        for (int j = 0; j < competencies.length; j++)
        {
            series.getData().add(new XYChart.Data<>(competencies[j], counterInd.get(j)));
        }
        return series;
    }

    public void printAreaChart(ObservableList<XYChart.Series<String, Number>> seriesList)
    {
        CategoryAxis xAxis = new CategoryAxis();
        xAxis.setLabel("Компетенции");
        NumberAxis yAxis = new NumberAxis(0, 7, 1);
        yAxis.setLabel("Количество компетенций");
        AreaChart<String, Number> areaChart=new AreaChart(xAxis, yAxis);
        areaChart.setData(seriesList);
        String name="Диаграмма с областями";
        showChart(areaChart, name,name+".png",800);
    }

    public void printChart(String name)
    {
        BufferedImage image = null;
        try {
            image = ImageIO.read(new File(name));
        } catch (IOException e) {
            e.printStackTrace();
        }
        PrintCharts pc=new PrintCharts(image);
        pc.printing(name);
    }
}
