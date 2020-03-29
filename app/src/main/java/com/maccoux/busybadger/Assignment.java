package com.maccoux.busybadger;

import androidx.room.*;
import java.util.Date;

@Entity
public class Assignment {

    // TODO: Add repeat options, progress bar, class assignment functionality

    @PrimaryKey
    public int aID;

    @ColumnInfo(name = "name")
    private String name;

    @ColumnInfo(name = "description")
    private String description;

    @ColumnInfo(name = "dueDate")
    private Date dueDate;

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

    public Date getDueDate() {
        return dueDate;
    }

    public void setDueDate(Date dueDate) {
        this.dueDate = dueDate;
    }

    public int getProgress() { return progress; }

    public void setProgress(int progress) { this.progress = progress; }
}
