package com.example.competenciesForStudents;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class StrDBTest
{
    int id;
    String discipline;
    String competence;
    String indicator;
    StrDB strDB;

    @BeforeEach
    void setUp()
    {
        id=1;
        discipline="Математика";
        competence="УК1";
        indicator="УК1-ИД2";
        strDB=new StrDB(id,discipline,competence,indicator);
    }

    @Test
    void getId()
    {
        Assertions.assertEquals(strDB.getId(),1);
    }

    @Test
    void getDiscipline()
    {
        Assertions.assertEquals(strDB.getDiscipline(),"Математика");
    }

    @Test
    void setDiscipline()
    {
        strDB.setDiscipline("Экономика");
        Assertions.assertEquals(strDB.getDiscipline(),"Экономика");
    }

    @Test
    void getCompetence()
    {
        Assertions.assertEquals(strDB.getCompetence(),"УК1");
    }

    @Test
    void setCompetence()
    {
        strDB.setCompetence("УК4");
        Assertions.assertEquals(strDB.getCompetence(),"УК4");
    }

    @Test
    void getIndicator()
    {
        Assertions.assertEquals(strDB.getIndicator(),"УК1-ИД2");
    }

    @Test
    void setIndicator()
    {
        strDB.setIndicator("УК1-ИД5");
        Assertions.assertEquals(strDB.getIndicator(),"УК1-ИД5");
    }
}