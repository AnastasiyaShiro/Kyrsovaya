package com.example.graphic;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.chart.*;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import java.net.URL;
import java.sql.*;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class HelloController implements Initializable
{
    public TableView tbl;
    public TextField fieldDisc;
    public TextField fieldCom;
    public TextField fieldInd;
    public String[] competencies=new String[] {"УК1","УК2","УК3","УК4","УК5","УК6","ОПК1","ОПК2","ОПК3","ПК1","ПК2","ПК3"};
    ArrayList<String> columnValues;
    ArrayList<Integer> count;
    InterDB interDB;
    private ObservableList<StrDB> fxlist;// cпециальный cпиcок для работы GUI
    TableColumn col0;
    TableColumn col1;
    TableColumn col2;
    TableColumn col3;
    StrDB strDBAdd;
    Connection connection;
    Statement statement;
    String query;
    ResultSet resultSet;
    private void updateTable()
    {
        fxlist= FXCollections.observableList(interDB.getAllStrDB());
        tbl.setItems(fxlist);
    }

    private void updateSorts()
    {
        StrDB StrDB=fxlist.get(tbl.getSelectionModel().getSelectedIndex());
        interDB.updateStrDB(StrDB);
    }
    private void createtable()
    {
        col0 = new TableColumn("Номер");//отображаемый заголовок cтолбца
        col0.setMinWidth(15);//ширина
        col0.setCellValueFactory(new PropertyValueFactory<StrDB, Integer>("id"));
        col1 = new TableColumn("Дисциплина");//отображаемый заголовок cтолбца
        col1.setMinWidth(100);//ширина
        col1.setCellValueFactory(new PropertyValueFactory<StrDB, String>("discipline"));
        col2 = new TableColumn("Компетенция");//отображаемый заголовок cтолбца
        col2.setMinWidth(50);//ширина
        col2.setCellValueFactory(new PropertyValueFactory<StrDB, String>("competence"));
        col3 = new TableColumn("Индикатор");//отображаемый заголовок cтолбца
        col3.setMinWidth(50);//ширина
        col3.setCellValueFactory(new PropertyValueFactory<StrDB, String>("indicator"));
        col1.setCellFactory(TextFieldTableCell.forTableColumn());
        col1.setOnEditCommit(new EventHandler<TableColumn.CellEditEvent <StrDB, String>>()
        {
            @Override
            public void handle(TableColumn.CellEditEvent<StrDB, String> t)
            {
                ((StrDB) t.getTableView().getItems().get(t.getTablePosition().getRow())).setDiscipline(t.getNewValue());
                updateSorts();
            }
        });
        col2.setCellFactory(TextFieldTableCell.forTableColumn());
        col2.setOnEditCommit(new EventHandler<TableColumn.CellEditEvent <StrDB, String>>()
        {
            @Override
            public void handle(TableColumn.CellEditEvent<StrDB, String> t)
            {
                ((StrDB) t.getTableView().getItems().get(t.getTablePosition().getRow())).setCompetence(t.getNewValue());
                updateSorts();
            }
        });
        col3.setCellFactory(TextFieldTableCell.forTableColumn());
        col3.setOnEditCommit(new EventHandler<TableColumn.CellEditEvent <StrDB, String>>()
        {
            @Override
            public void handle(TableColumn.CellEditEvent<StrDB, String> t)
            {
                ((StrDB) t.getTableView().getItems().get(t.getTablePosition().getRow())).setIndicator(t.getNewValue());
                updateSorts();
            }
        });
        tbl.getColumns().addAll(col0, col1, col2,col3);// добавление cтолбцов
        tbl.setItems(fxlist);// загрузка cпиcка объектов StrDB из fx_ListStrDB-
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle)
    {
        interDB=new InterDBImpl();//направили в бд
        fxlist= FXCollections.observableList(interDB.getAllStrDB());
        createtable();
    }

    public void onSef(ActionEvent actionEvent)
    {
        strDBAdd=new StrDB(1,fieldDisc.getText(),fieldCom.getText(),fieldInd.getText());
        interDB.addStrDB(strDBAdd);
        updateTable();
    }

    public void onDel(ActionEvent actionEvent)
    {
        int index=tbl.getSelectionModel().getSelectedIndex();
        StrDB StrDB=fxlist.get(index);
        interDB.deleteStrDB(StrDB.getId());
        updateTable();
    }

    public ArrayList<String> arrDisc() throws SQLException
    {
        connection = DriverManager.getConnection("jdbc:h2:~/test", "11", "11");
        statement = connection.createStatement();
        query = "SELECT DISTINCT discipline FROM strDB ORDER BY discipline";
        resultSet = statement.executeQuery(query);
        ArrayList arr = new ArrayList<>();
        String columnValue;
        while (resultSet.next())
        {
            columnValue = resultSet.getString("discipline");
            arr.add(columnValue);
        }
        return arr;
    }

    public ArrayList<Integer> arrCount() throws SQLException
    {
        connection = DriverManager.getConnection("jdbc:h2:~/test", "11", "11");
        ArrayList disciplineCount = new ArrayList<>();
        statement = connection.createStatement();
        query = "SELECT COUNT(discipline) AS num FROM strDB GROUP BY discipline ORDER BY discipline";
        resultSet = statement.executeQuery(query);
        int count;
        while (resultSet.next())
        {
            count = resultSet.getInt("num");
            disciplineCount.add(count);
        }
        return disciplineCount;
    }

    public ArrayList<Integer> countIndicator(String disc) throws SQLException
    {
        connection = DriverManager.getConnection("jdbc:h2:~/test", "11", "11");
        PreparedStatement stmt;
        ArrayList indCount = new ArrayList<Integer>();
        for (int i=0;i<competencies.length;i++)
        {
            String query = "SELECT competence, COUNT(*) AS count FROM strDB WHERE discipline = ? AND competence= ?  GROUP BY competence";
            stmt = connection.prepareStatement(query);
            stmt.setString(1, disc);
            stmt.setString(2, competencies[i]);
            resultSet = stmt.executeQuery();
            if (resultSet.next())
            {
                int count = resultSet.getInt("count");
                indCount.add(count);
            }
            else
            {
                indCount.add(0);
            }
        }
        return indCount;
    }

    public void onPieChart(ActionEvent actionEvent) throws SQLException {
        columnValues=arrDisc();
        ObservableList<PieChart.Data> pieChartData = FXCollections.observableArrayList();
        count=arrCount();
        for (int i = 0; i < columnValues.size(); i++)
        {
            pieChartData.add(new PieChart.Data(columnValues.get(i), count.get(i)*100/29));
        }
        PieChart chart = new PieChart(pieChartData);
        Pane pane = new Pane(chart);
        Scene scene = new Scene(pane);
        Stage stage = new Stage();
        stage.setTitle("Диаграмма пирога");
        stage.setScene(scene);
        stage.show();
        onAreaChart();
    }

    public void onAreaChart() throws SQLException
    {
        //Defining the X axis
        CategoryAxis xAxis = new CategoryAxis();
        xAxis.setLabel("Компетенции");
        //Defining the y Axis
        NumberAxis yAxis = new NumberAxis(0, 7, 1);
        yAxis.setLabel("Количество компетенций");
        //Creating the Area chart
        AreaChart<String, Number> areaChart = new AreaChart(xAxis, yAxis);
        columnValues=arrDisc();
        ObservableList<XYChart.Series<String, Number>> seriesList = FXCollections.observableArrayList();
        ArrayList <Integer> counterInd;
        XYChart.Series<String, Number> series;
        for (int i = 0; i < columnValues.size(); i++)
        {
            counterInd=countIndicator(columnValues.get(i));
            series = new XYChart.Series<>();
            series.setName(columnValues.get(i));
            for (int j = 0; j < counterInd.size(); j++)
            {
                series.getData().add(new XYChart.Data<>(competencies[j], counterInd.get(j)));
            }
            seriesList.add(series);
        }
        areaChart.setData(seriesList);
        Pane pane = new Pane(areaChart);
        Scene scene = new Scene(pane);
        Stage stage = new Stage();
        stage.setTitle("Диаграмма с областями");
        stage.setScene(scene);
        stage.show();
    }
}

