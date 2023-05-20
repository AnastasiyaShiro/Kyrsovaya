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
import javax.print.*;
import javax.print.attribute.HashPrintRequestAttributeSet;
import javax.print.attribute.PrintRequestAttributeSet;
import javax.print.attribute.standard.Copies;
import javax.print.attribute.standard.MediaSizeName;
import java.io.*;
import java.util.ArrayList;

/**
 * Класс для графиков: их создание и печать.
 * @author Щербак Анастасия Романовна
 * @version 0.4
 */
public class Charts
{
    private Scene scene;
    private Stage stage;
    Pane pane;

    public void showChart(Chart chart, String name,String fname, int x)
    {
        pane = new Pane(chart);
        scene = new Scene(pane,520,450);
        stage = new Stage();
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

        // Создаем параметры снимка
        SnapshotParameters snapshotParameters = new SnapshotParameters();
        snapshotParameters.setDepthBuffer(true);//Эта строчка кода указывает на то, что параметры снимка (snapshot)
// объекта будут содержать буфер глубины (depth buffer). Глубинный буфер используется для определения того, какие пиксели
// находятся ближе к камере, а какие дальше, что позволяет правильно отображать 3D-модели на 2D-экране.
        // Создаем снимок диаграммы
        WritableImage image = chart.snapshot(snapshotParameters, null);
        // Сохраняем снимок в файл
        File file = new File(fname);
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
        PrintService[] services = PrintServiceLookup.lookupPrintServices(null, null);//для получения
// доступных принтеров на компьютере. Метод lookupPrintServices принимает на вход два параметра: DocFlavor и attributes.
// null означает, что мы запрашиваем все доступные принтеры без фильтрации по типу и атрибутам

/*        DocPrintJob printer = null;
        for (PrintService service : services)
        {
            if (service.getName().equals("Samsung ML-1520 (USB001)"))
            {
                printer = service.createPrintJob();
                break;
            }
        }
        DocPrintJob job = printer;*/

        PrintService printer = services[0];
        DocPrintJob job = printer.createPrintJob();//создает объект типа DocPrintJob, который представляет задание на
//печать документа на конкретном принтере. Метод createPrintJob() вызывается на объекте типа PrintService, который
// представляет конкретный принтер, выбранный пользователем или установленный по умолчанию.
        if (printer != null)
        {
            PrintRequestAttributeSet printRequestAttributeSet = new HashPrintRequestAttributeSet();
//создает объект типа PrintRequestAttributeSet, который используется для задания дополнительных параметров печати.
//Эти параметры могут включать ориентацию страницы, количество копий, степень сжатия и др.
            printRequestAttributeSet.add(new Copies(1));
            printRequestAttributeSet.add(MediaSizeName.ISO_A4);
            InputStream inputStream = null;  //для чтения изображения, и чтобы избежать ошибки компиляции, необходимо
//инициализировать переменную inputStream значением null
            try
            {
                inputStream = new FileInputStream(name);
            }
            catch (FileNotFoundException e)
            {
                throw new RuntimeException(e);
            }
            Doc doc = new SimpleDoc(inputStream, DocFlavor.INPUT_STREAM.AUTOSENSE, null);//документ на печать.
//DocFlavor.INPUT_STREAM.AUTOSENSE - формат документа (тут используется автоматическое определение) null - объект
// PrintRequestAttributeSet, содержащий доп. атрибуты для печати.
            try
            {
                job.print(doc, printRequestAttributeSet);
            }
            catch (PrintException e)
            {
                throw new RuntimeException(e);
            }
        }
        else
        {
            System.out.println("Принтер отсутствует.");
        }
    }
}
