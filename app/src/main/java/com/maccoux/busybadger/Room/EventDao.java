package com.maccoux.busybadger.Room;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

@Dao
public interface EventDao {
    @Query("SELECT * FROM event")
    List<Event> getAll();

    @Query("SELECT * FROM event WHERE eid LIKE :eventID")
    Event loadById(int eventID);

    @Query("SELECT * FROM event WHERE eid IN (:eventIDs)")
    List<Event> loadAllByIds(int[] eventIDs);

    @Insert
    void insert(Event event);

    @Delete
    void delete(Event event);
}
