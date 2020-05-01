package com.maccoux.busybadger.Room;

import androidx.room.TypeConverter;

import java.util.Calendar;
import java.util.Date;

public class Converters {
    @TypeConverter
    public static Calendar timestampToCal(Long value) {
        Calendar c = Calendar.getInstance();
        c.setTimeInMillis(value);
        return c;
    }

    @TypeConverter
    public static Long calToTimestamp(Calendar c) {
        if(c == null) {
            return null;
        }
        return c.getTimeInMillis();
    }

    @TypeConverter
    public static Date fromTimestamp(Long value) {
        return value == null ? null : new Date(value);
    }
    @TypeConverter
    public static Long dateToTimestamp(Date date) {
        return date == null ? null : date.getTime();
    }
}
