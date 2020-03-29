package com.maccoux.busybadger;

import androidx.room.*;
import java.util.Date;

@Entity
public class Event {

    // TODO: Add repeat options, progress bar, location functionality

    @PrimaryKey
    public int eID;

    @ColumnInfo(name = "name")
    private String name;

    @ColumnInfo(name = "description")
    private String description;

    @ColumnInfo(name = "datetime")
    private Date dateTime;

}
