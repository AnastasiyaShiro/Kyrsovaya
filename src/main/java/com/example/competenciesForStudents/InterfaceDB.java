package com.example.competenciesForStudents;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Класс для интерфейса базы данных.
 * @author Щербак Анастасия Романовна
 * @version 0.4
 */

public interface InterfaceDB
{
    List<StrDB> getAllStrDB();
    void addStrDB(StrDB strDB);
    void updateStrDB(StrDB strDB);
    ArrayList<String> arrDisc() throws SQLException;
    ArrayList<Integer> arrCount() throws SQLException;
    ArrayList<Integer> countIndicator(String disc, String[] competencies) throws SQLException;
    void deleteStrDB(int id);
}
