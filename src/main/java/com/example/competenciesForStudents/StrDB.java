package com.example.competenciesForStudents;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

/**
 * Класс для создания одной строки в базе данных.
 * @author Щербак Анастасия Романовна
 * @version 0.6
 */

public class StrDB
{
    private SimpleIntegerProperty id;
    private SimpleStringProperty discipline;
    private SimpleStringProperty competence;
    private SimpleStringProperty indicator;

    public StrDB(int id, String discipline, String competence, String indicator)
    {
        this.id = new SimpleIntegerProperty(id);
        this.discipline = new SimpleStringProperty(discipline);
        this.competence = new SimpleStringProperty(competence);
        this.indicator = new SimpleStringProperty(indicator);
    }

    public int getId() {
        return id.get();
    }
    public String getDiscipline() {
        return discipline.get();
    }
    public void setDiscipline(String discipline) {
        this.discipline.set(discipline);
    }
    public String getCompetence() {
        return competence.get();
    }
    public void setCompetence(String competence) {
        this.competence.set(competence);
    }
    public String getIndicator() {
        return indicator.get();
    }
    public void setIndicator(String indicator) {
        this.indicator.set(indicator);
    }
}
