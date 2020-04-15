package com.maccoux.busybadger.Room;

import androidx.room.Dao;
import androidx.room.Query;

import java.util.List;

@Dao
public interface AssignmentDao {
    @Query("SELECT * FROM assignment")
    List<Assignment> getAll();

    @Query("SELECT * FROM assignment WHERE aid LIKE :assignmentID")
    Assignment loadById(int assignmentID);

    @Query("SELECT * FROM assignment WHERE aid IN (:assignmentIDs)")
    List<Assignment> loadAllByIds(int[] assignmentIDs);
}
