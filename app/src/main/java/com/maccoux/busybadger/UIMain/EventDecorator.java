package com.maccoux.busybadger.UIMain;

import android.app.Activity;
import android.content.Context;
import android.graphics.drawable.Drawable;

import androidx.core.content.ContextCompat;

import com.maccoux.busybadger.R;
import com.maccoux.busybadger.Room.Class;
import com.prolificinteractive.materialcalendarview.CalendarDay;
import com.prolificinteractive.materialcalendarview.DayViewDecorator;
import com.prolificinteractive.materialcalendarview.DayViewFacade;
import com.prolificinteractive.materialcalendarview.spans.DotSpan;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.HashSet;
import java.util.List;

public class EventDecorator implements DayViewDecorator {

    private final int color;
    private final HashSet<CalendarDay> dates;
    private final List<Class> classes;

    boolean monAdded = false;
    boolean tuesAdded = false;
    boolean wedAdded = false;
    boolean thursAdded = false;
    boolean friAdded = false;

    public EventDecorator(int color, List<Date> dates, List<Class> classes) {
        this.color = color;
        this.dates = new HashSet<>();
        this.classes = classes;

        for(Date d : dates) {
            this.dates.add(CalendarDay.from(d.getYear() + 1900, d.getMonth() + 1, d.getDate()));
        }

        for(Class c : classes) {
            if(c.getMonday() != null) {
                monAdded = true;
            }
            if(c.getTuesday() != null) {
                tuesAdded = true;
            }
            if(c.getWednesday() != null) {
                wedAdded = true;
            }
            if(c.getThursday() != null) {
                thursAdded = true;
            }
            if(c.getFriday() != null) {
                friAdded = true;
            }
        }
    }

    @Override
    public boolean shouldDecorate(CalendarDay day) {
        //return dates.contains(day);
        if(dates.contains(day)) {
            return true;
        }

        Date date = new Date(day.getYear() - 1900, day.getMonth() - 1, day.getDay());

        for(Class c : classes) {
            if(c.getBegin().compareTo(date) > 0) {
                return false;
            }

            if(c.getMonday() != null && date.getDay() == 1) {
                return true;
            }
            if(c.getTuesday() != null && date.getDay() == 2) {
                return true;
            }
            if(c.getWednesday() != null && date.getDay() == 3) {
                return true;
            }
            if(c.getThursday() != null && date.getDay() == 4) {
                return true;
            }
            if(c.getFriday() != null && date.getDay() == 5) {
                return true;
            }
        }

        /*if(monAdded && date.getDay() == 1) {
            return true;
        }
        if(tuesAdded && date.getDay() == 2) {
            return true;
        }
        if(wedAdded && date.getDay() == 3) {
            return true;
        }
        if(thursAdded && date.getDay() == 4) {
            return true;
        }
        if(friAdded && date.getDay() == 5) {
            return true;
        }*/

        return false;
    }

    @Override
    public void decorate(DayViewFacade view) {
        view.addSpan(new DotSpan(5, color));
    }

}
