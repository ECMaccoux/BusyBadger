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
}
