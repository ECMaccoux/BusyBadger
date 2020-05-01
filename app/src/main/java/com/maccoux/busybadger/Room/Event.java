package com.maccoux.busybadger.Room;

import android.location.Location;

import androidx.room.*;

import com.google.android.gms.maps.model.LatLng;

import java.util.Calendar;
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
    private Date date;

    @ColumnInfo(name = "latitude")
    private double latitude;

    @ColumnInfo(name = "longitude")
    private double longitude;

    public Event() {

    }

    public Event(String name, String description, Calendar c, LatLng location) {
        this.name = name;
        this.description = description;
        this.date = c.getTime();
        this.latitude = location.latitude;
        this.longitude = location.longitude;
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

    public void setLocation(LatLng location) {
        this.latitude = location.latitude;
        this.longitude = location.longitude;
    }
}
