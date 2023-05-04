package com.example.graphic;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class InterDBImpl implements InterDB
{
    private Connection conn;

    public InterDBImpl()
    {
        try
        {
            Class.forName("org.h2.Driver");
            conn = DriverManager.getConnection("jdbc:h2:~/test", "11", "11");
            Statement stmt = conn.createStatement();
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
            PreparedStatement ps = conn.prepareStatement("SELECT * FROM strDB");
            ResultSet rs = ps.executeQuery();
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
    public StrDB getStrDBById(int id)
    {
        StrDB strDB = null;
        try
        {
            PreparedStatement ps = conn.prepareStatement("SELECT * FROM strDB WHERE id = ?");
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            if (rs.next())
            {
                strDB = new StrDB(rs.getInt("id"),
                        rs.getString("discipline"),
                        rs.getString("competence"),
                        rs.getString("indicator"));
            }
        }
        catch (SQLException e) { e.printStackTrace();  }
        return strDB;
    }

    @Override
    public void addStrDB(StrDB strDB)
    {
        try
        {
            PreparedStatement ps = conn.prepareStatement("INSERT INTO strDB (discipline, competence, indicator) VALUES (?, ?, ?)");
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
            PreparedStatement ps = conn.prepareStatement("UPDATE strDB SET discipline=?, competence=?, indicator=? WHERE id = ?");
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
            PreparedStatement ps = conn.prepareStatement("DELETE FROM strDB WHERE id = ?");
            ps.setInt(1, id);
            ps.executeUpdate();
        }
        catch (SQLException e) { e.printStackTrace();   }
    }
}
