package com.example.graphic;

import java.util.List;

public interface InterDB
{
    List<StrDB> getAllStrDB();
   StrDB getStrDBById(int id);
    void addStrDB(StrDB strDB);
    void updateStrDB(StrDB strDB);

    void deleteStrDB(int id);
}
