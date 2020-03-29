package com.maccoux.busybadger;

import androidx.room.Database;
import androidx.room.RoomDatabase;

@Database(entities = {Event.class, Assignment.class, Class.class}, version = 1)
public abstract class AppDatabase extends RoomDatabase {
    public abstract EventDao eventDao();
    public abstract AssignmentDao assignmentDao();
    public abstract ClassDao classDao();
}
