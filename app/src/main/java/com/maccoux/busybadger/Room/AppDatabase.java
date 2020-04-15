package com.maccoux.busybadger.Room;

import androidx.room.Database;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;

@Database(entities = {Event.class, Assignment.class, Class.class}, version = 1)
@TypeConverters(Converters.class)
public abstract class AppDatabase extends RoomDatabase {
    public abstract EventDao eventDao();
    public abstract AssignmentDao assignmentDao();
    public abstract ClassDao classDao();
}
