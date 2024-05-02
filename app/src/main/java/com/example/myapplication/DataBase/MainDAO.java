package com.example.myapplication.DataBase;

import static androidx.room.OnConflictStrategy.REPLACE;
import java.util.List;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import com.example.myapplication.models.Notes;

@Dao
public interface MainDAO {

    @Insert(onConflict = REPLACE)
    void insert(Notes notes);
    @Query("SELECT * FROM notes ORDER BY ID DESC")
    List<Notes> getALL();

    @Query("update notes set title =:title, notes =:notes where id= :id")
    void update(int id,String title,String notes);
    @Delete
    void delete(Notes notes);
}

