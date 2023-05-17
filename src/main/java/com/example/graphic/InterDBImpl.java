package com.example.graphic;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class InterDBImpl implements InterDB
{
    private Connection conn;
    private PreparedStatement ps;
    private ResultSet rs;
    private Statement stmt;

    public InterDBImpl()
    {
        try
        {
            Class.forName("org.h2.Driver");
            conn = DriverManager.getConnection("jdbc:h2:~/test", "11", "11");
            stmt = conn.createStatement();
            stmt.executeUpdate("CREATE TABLE IF NOT EXISTS strDB (" +
                    "id INTEGER PRIMARY KEY AUTO_INCREMENT," +
                    "discipline VARCHAR(255)," +
                    "competence VARCHAR(255)," +
                    "indicator VARCHAR(255))");
        }
        catch (SQLException e) { e.printStackTrace();  }
        catch (ClassNotFoundException e) { throw new RuntimeException(e);  }
    }

    @Override
    public List<StrDB> getAllStrDB()
    {
        List<StrDB> strDBs = new ArrayList<>();
        try
        {
            ps = conn.prepareStatement("SELECT * FROM strDB");
            rs = ps.executeQuery();
            while (rs.next()) {
                StrDB strDB = new StrDB(rs.getInt("id"),
                        rs.getString("discipline"),
                        rs.getString("competence"),
                        rs.getString("indicator"));
                strDBs.add(strDB);
            }
        }
        catch (SQLException e) { e.printStackTrace(); }
        return strDBs;
    }

    @Override
    public void addStrDB(StrDB strDB)
    {
        try
        {
            ps = conn.prepareStatement("INSERT INTO strDB (discipline, competence, indicator) VALUES (?, ?, ?)");
            ps.setString(1, strDB.getDiscipline());
            ps.setString(2, strDB.getCompetence());
            ps.setString(3, strDB.getIndicator());
            ps.executeUpdate();
        }
        catch (SQLException e)  { e.printStackTrace(); }
    }

    @Override
    public void updateStrDB(StrDB strDB)
    {
        try
        {
            ps = conn.prepareStatement("UPDATE strDB SET discipline=?, competence=?, indicator=? WHERE id = ?");
            ps.setString(1, strDB.getDiscipline());
            ps.setString(2, strDB.getCompetence());
            ps.setString(3, strDB.getIndicator());
            ps.setInt(4, strDB.getId());
            ps.executeUpdate();
        }
        catch (SQLException e) { e.printStackTrace(); }
    }

    @Override
    public void deleteStrDB(int id)
    {
        try
        {
            ps = conn.prepareStatement("DELETE FROM strDB WHERE id = ?");
            ps.setInt(1, id);
            ps.executeUpdate();
        }
        catch (SQLException e) { e.printStackTrace();   }
    }

    public ArrayList<String> arrDisc() throws SQLException {
        conn = DriverManager.getConnection("jdbc:h2:~/test", "11", "11");
        stmt = conn.createStatement();
        String query = "SELECT DISTINCT discipline FROM strDB ORDER BY discipline";
        rs = stmt.executeQuery(query);
        ArrayList arr = new ArrayList<>();
        String columnValue;
        while (rs.next())
        {
            columnValue = rs.getString("discipline");
            arr.add(columnValue);
        }
        return arr;
    }

    public ArrayList<Integer> arrCount() throws SQLException {
        conn = DriverManager.getConnection("jdbc:h2:~/test", "11", "11");
        ArrayList disciplineCount = new ArrayList<>();
        stmt = conn.createStatement();
        String query = "SELECT COUNT(discipline) AS num FROM strDB GROUP BY discipline ORDER BY discipline";
        rs = stmt.executeQuery(query);
        int count;
        while (rs.next())
        {
            count = rs.getInt("num");
            disciplineCount.add(count);
        }
        return disciplineCount;
    }

    public ArrayList<Integer> countIndicator(String disc, String[] competencies) throws SQLException
    {
        conn = DriverManager.getConnection("jdbc:h2:~/test", "11", "11");
        PreparedStatement stmt;
        String query;
        ArrayList indCount = new ArrayList<Integer>();
        for (int i=0;i<competencies.length;i++)
        {
            query = "SELECT competence, COUNT(*) AS count FROM strDB WHERE discipline = ? AND competence= ?  GROUP BY competence";
            stmt = conn.prepareStatement(query);
            stmt.setString(1, disc);
            stmt.setString(2, competencies[i]);
            rs = stmt.executeQuery();
            if (rs.next())
            {
                int count = rs.getInt("count");
                indCount.add(count);
            }
            else
            {
                indCount.add(0);
            }
        }
        return indCount;
    }
}
