package com.maccoux.busybadger.Room;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

@Dao
public interface EventDao {
    @Query("SELECT * FROM event ORDER BY datetime(datetime)")
    List<Event> getAll();

    @Query("SELECT * FROM event WHERE eid LIKE :eventID")
    Event loadById(int eventID);

    @Query("SELECT * FROM event WHERE eid IN (:eventIDs)")
    List<Event> loadAllByIds(int[] eventIDs);

    @Query("SELECT * FROM event WHERE date(datetime) = date(:date)")
    List<Event> getAllOnDate(Calendar date);

    @Query("SELECT * FROM event WHERE date(datetime) BETWEEN date(:from) AND date(:to)")
    List<Event> getAllDateRange(Calendar from, Calendar to);

    @Query("SELECT datetime FROM event ORDER BY datetime(datetime)")
    List<Date> getAllDates();

    @Insert
    void insert(Event event);

    @Delete
    void delete(Event event);
}
