package com.maccoux.busybadger.Room;

import androidx.room.*;

import java.util.Calendar;

@Entity(tableName = "class")
public class Class {

    @PrimaryKey
    public int cID;

    @ColumnInfo(name = "name")
    private String name;

    @ColumnInfo(name="color")
    private String color;

    @ColumnInfo(name = "monday")
    private Calendar monday;

    @ColumnInfo(name = "tuesday")
    private Calendar tuesday;

    @ColumnInfo(name = "wednesday")
    private Calendar wednesday;

    @ColumnInfo(name = "thursday")
    private Calendar thursday;

    @ColumnInfo(name = "friday")
    private Calendar friday;

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

    public Calendar getMonday() {
        return monday;
    }

    public void setMonday(Calendar monday) {
        this.monday = monday;
    }

    public Calendar getTuesday() {
        return tuesday;
    }

    public void setTuesday(Calendar tuesday) {
        this.tuesday = tuesday;
    }

    public Calendar getWednesday() {
        return wednesday;
    }

    public void setWednesday(Calendar wednesday) {
        this.wednesday = wednesday;
    }

    public Calendar getThursday() {
        return thursday;
    }

    public void setThursday(Calendar thursday) {
        this.thursday = thursday;
    }

    public Calendar getFriday() {
        return friday;
    }

    public void setFriday(Calendar friday) {
        this.friday = friday;
    }
}
