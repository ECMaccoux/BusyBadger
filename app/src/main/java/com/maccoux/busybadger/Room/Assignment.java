package com.maccoux.busybadger.Room;

import androidx.room.*;

import java.util.Calendar;

@Entity(tableName = "assignment")
public class Assignment {

    // TODO: Add repeat options, progress bar, class assignment functionality

    @PrimaryKey
    public int aID;

    @ColumnInfo(name = "class")
    private int classID;

    @ColumnInfo(name = "name")
    private String name;

    @ColumnInfo(name = "description")
    private String description;

    @ColumnInfo(name = "dueDate")
    private Calendar dueDate;

    @ColumnInfo(name = "progress")
    private int progress;

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

    public Calendar getDueDate() {
        return dueDate;
    }

    public void setDueDate(Calendar dueDate) {
        this.dueDate = dueDate;
    }

    public int getProgress() { return progress; }

    public void setProgress(int progress) { this.progress = progress; }

    public void setClassID(int id) {
        this.classID = id;
    }

    public int getClassID() {
        return classID;
    }
}
