package com.maccoux.busybadger.Room;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

@Dao
public interface ClassDao {
    @Query("SELECT * FROM class")
    List<Class> getAll();

    @Query("SELECT * FROM class WHERE cid LIKE :classID")
    Class loadById(int classID);

    @Query("SELECT * FROM class WHERE cid IN (:classIDs)")
    List<Class> loadAllByIds(int[] classIDs);

    @Insert
    void insert(Class c);

    @Delete
    void delete(Class c);
}
