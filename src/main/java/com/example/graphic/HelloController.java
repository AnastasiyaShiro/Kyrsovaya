package com.example.graphic;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.Initializable;
import javafx.scene.chart.XYChart;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;

import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class HelloController implements Initializable
{
    public TableView tbl;
    public TextField fieldDisc;
    public TextField fieldCom;
    public TextField fieldInd;
    ArrayList<String> columnValues;
    ArrayList<Integer> count;
    ArrayList<Integer> counterInd;
    InterDB interDB;
    private ObservableList<StrDB> fxlist;// cпециальный cпиcок для работы GUI
    TableColumn col0;
    TableColumn col1;
    TableColumn col2;
    TableColumn col3;
    StrDB strDBAdd;
    Graphics gr;
    XYChart.Series<String, Number> series;
    private String[] competencies=new String[] {"УК1","УК2","УК3","УК4","УК5","УК6","ОПК1","ОПК2","ОПК3","ПК1","ПК2","ПК3"};


    private void updateTable()
    {
        fxlist= FXCollections.observableList(interDB.getAllStrDB());
        tbl.setItems(fxlist);
    }

    private void updateSorts()
    {
        StrDB strDB=fxlist.get(tbl.getSelectionModel().getSelectedIndex());
        interDB.updateStrDB(strDB);
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

    public void onGraphics(ActionEvent actionEvent) throws SQLException {
        columnValues=interDB.arrDisc();
        count=interDB.arrCount();
        gr=new Graphics();
        gr.printPieChart(columnValues,count);

        ObservableList<XYChart.Series<String, Number>> seriesList = FXCollections.observableArrayList();
        for (int i=0;i<columnValues.size();i++)
        {
            counterInd=interDB.countIndicator(columnValues.get(i),competencies);
            series=gr.onAreaChart(columnValues.get(i),counterInd,competencies);
            seriesList.add(series);
        }
        gr.printAreaChart(seriesList);
    }
}

