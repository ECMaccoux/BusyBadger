package com.maccoux.busybadger;

import androidx.room.Dao;
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
}
