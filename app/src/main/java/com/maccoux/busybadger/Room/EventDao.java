package com.maccoux.busybadger.Room;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

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
    List<Event> getAllOnDate(Date date);

    @Query("SELECT * FROM event WHERE datetime BETWEEN :from AND :to ORDER BY datetime ASC")
    List<Event> getAllDateRange(Date from, Date to);

    @Query("SELECT * FROM event WHERE datetime >= :from ORDER BY datetime ASC")
    List<Event> getAllFromDate(Date from);

    @Query("SELECT datetime FROM event ORDER BY datetime ASC")
    List<Date> getAllDates();

    @Query("SELECT * FROM event WHERE notifyID LIKE :notificationID")
    Event loadByNotID(int notificationID);

    @Insert
    void insert(Event event);

    @Delete
    void delete(Event event);

    @Query("DELETE FROM event")
    void deleteAll();

    @Update
    void update(Event event);
}
