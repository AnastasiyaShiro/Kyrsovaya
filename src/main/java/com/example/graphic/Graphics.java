package com.example.graphic;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Scene;
import javafx.scene.chart.*;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import java.util.ArrayList;

/**
 Класс для графиков.
 @author Щербак Анастасия Романовна
 @version 1.0
 */
public class Graphics
{
    private Scene scene;
    private Stage stage;
    public void onPieChart(ArrayList<String> columnValues, ArrayList<Integer> count)
    {
        ObservableList<PieChart.Data> pieChartData = FXCollections.observableArrayList();
        for (int i = 0; i < columnValues.size(); i++)
        {
            pieChartData.add(new PieChart.Data(columnValues.get(i), count.get(i)*100/29));
        }
        PieChart chart = new PieChart(pieChartData);
        Pane pane = new Pane(chart);
        scene = new Scene(pane);
        stage = new Stage();
        stage.setTitle("Диаграмма пирога");
        stage.setScene(scene);
        stage.show();
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
        Pane pane = new Pane(areaChart);
        scene = new Scene(pane);
        stage = new Stage();
        stage.setTitle("Диаграмма с областями");
        stage.setScene(scene);
        stage.show();
    }
}
