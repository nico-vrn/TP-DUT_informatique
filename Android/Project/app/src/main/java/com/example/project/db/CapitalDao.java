package com.example.project.db;

import androidx.room.Dao;
import androidx.room.Query;

import java.util.List;
@Dao
public interface CapitalDao {

    @Query("SELECT * FROM capital")
    List<Capital> getAll();

}
