package com.maccoux.busybadger.Room;

import android.location.Location;

import androidx.room.*;

import com.google.android.gms.maps.model.LatLng;

import java.util.Calendar;
import java.util.Date;

@Entity(tableName = "event")
public class Event {

    @PrimaryKey(autoGenerate = true)
    public int eID;

    @ColumnInfo(name = "name")
    private String name;

    @ColumnInfo(name = "description")
    private String description;

    @ColumnInfo(name = "datetime")
    private Date date;

    @ColumnInfo(name = "latitude")
    private double latitude;

    @ColumnInfo(name = "longitude")
    private double longitude;

    @ColumnInfo(name = "notifyID")
    private int notificationID;

    @ColumnInfo(name = "eventType")
    private int eventType;

    @ColumnInfo(name = "classID")
    private int classID;

    @ColumnInfo(name = "progress")
    private int progress;

    public Event() {

    }

    public Event(String name, String description, Calendar c, LatLng location, Class aClass) {
        this.name = name;
        this.description = description;
        this.date = c.getTime();
        this.latitude = location.latitude;
        this.longitude = location.longitude;
        this.classID = classID;
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

    public Date getDate() { return date; }

    public void setDate(Date date) { this.date = date; }

    public LatLng getLocation() { return new LatLng(latitude, longitude); }

    public double getLatitude() { return latitude; }

    public double getLongitude() { return longitude; }

    public void setLatitude(double latitude) { this.latitude = latitude; }

    public void setLongitude(double longitude) { this.longitude = longitude; }

    public void setClassID(int id) {
        this.classID = id;
    }

    public int getClassID() {
        return classID;
    }

    public void setLocation(LatLng location) {
        this.latitude = location.latitude;
        this.longitude = location.longitude;
    }
    public void setNotificationID(int notificationID) {
        this.notificationID = notificationID;
    }

    public int getNotificationID() {
        return notificationID;
    }
    public int getEventType() {
        return eventType;
    }
    public void setEventType(int eventType) {
        //0 = event, 1 = assignment
        this.eventType = eventType;
    }

    public int getProgress() { return progress; }
    public void setProgress(int progress) { this.progress = progress; }
}
