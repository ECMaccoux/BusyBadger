package com.maccoux.busybadger;

import androidx.room.*;

import java.util.Date;

@Entity
public class Class {

    @PrimaryKey
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
}
