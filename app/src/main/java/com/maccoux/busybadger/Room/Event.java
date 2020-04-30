package com.maccoux.busybadger.Room;

import android.location.Location;

import androidx.room.*;
import java.util.Date;

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
    private Date dateTime;

//    @ColumnInfo(name = "location")
//    private Location location;

    public Event() {

    }

    public Event(String name, String description, Date dateTime) {
        this.name = name;
        this.description = description;
        this.dateTime = dateTime;
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

    public Date getDateTime() {
        return dateTime;
    }

    public void setDateTime(Date dateTime) {
        this.dateTime = dateTime;
    }

//    public Location getLocation() { return location; }

//    public void setLocation(Location location) { this.location = location; }
}
