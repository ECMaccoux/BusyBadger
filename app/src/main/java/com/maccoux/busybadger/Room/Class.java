package com.maccoux.busybadger.Room;

import androidx.room.*;

import java.util.Calendar;
import java.util.Date;

@Entity(tableName = "class")
public class Class {

    @PrimaryKey(autoGenerate = true)
    public int cID;

    @ColumnInfo(name = "name")
    private String name;

    @ColumnInfo(name="color")
    private String color;

    @ColumnInfo(name = "monday")
    private Date monday;

    @ColumnInfo(name = "tuesday")
    private Date tuesday;

    @ColumnInfo(name = "wednesday")
    private Date wednesday;

    @ColumnInfo(name = "thursday")
    private Date thursday;

    @ColumnInfo(name = "friday")
    private Date friday;

    @ColumnInfo(name = "begin")
    private Date begin;

    @ColumnInfo(name = "notifyID")
    private int notificationID;


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public Date getMonday() {
        return monday;
    }

    public void setMonday(Date monday) {
        this.monday = monday;
    }

    public Date getTuesday() {
        return tuesday;
    }

    public void setTuesday(Date tuesday) {
        this.tuesday = tuesday;
    }

    public Date getWednesday() {
        return wednesday;
    }

    public void setWednesday(Date wednesday) {
        this.wednesday = wednesday;
    }

    public Date getThursday() {
        return thursday;
    }

    public void setThursday(Date thursday) {
        this.thursday = thursday;
    }

    public Date getFriday() {
        return friday;
    }

    public void setFriday(Date friday) {
        this.friday = friday;
    }

    public Date getBegin() { return begin; }

    public void setBegin(Date begin) { this.begin = begin; }
    public void setNotificationID(int notificationID) {
        this.notificationID = notificationID;
    }
    public int getNotificationID() {
        return notificationID;
    }
}
