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
}
