package com.maccoux.busybadger.Room;

import android.location.Location;

import androidx.room.*;

import java.util.Calendar;

@Entity(tableName = "event")
public class Event {

    // TODO: Add repeat options, progress bar, location functionality

    @PrimaryKey(autoGenerate = true)
    public int eID;

    @ColumnInfo(name = "name")
    private String name;

    @ColumnInfo(name = "description")
    private String description;

    @ColumnInfo(name = "datetime")
    public Calendar c;

//    @ColumnInfo(name = "location")
//    private Location location;

    public Event() {

    }

    public Event(String name, String description, Calendar c) {
        this.name = name;
        this.description = description;
        this.c = c;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Calendar getCalendar() { return c; }

    public void setCalendar(Calendar c) { this.c = c; }

//    public Location getLocation() { return location; }

//    public void setLocation(Location location) { this.location = location; }
}
