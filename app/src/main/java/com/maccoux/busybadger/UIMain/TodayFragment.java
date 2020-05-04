package com.maccoux.busybadger.UIMain;

import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.maccoux.busybadger.AddEvent;
import com.maccoux.busybadger.R;
import com.maccoux.busybadger.Room.AppDatabase;
import com.maccoux.busybadger.Room.Class;
import com.maccoux.busybadger.Room.Event;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class TodayFragment extends Fragment implements CalendarFragment.CalendarDataListener {

    View view;
    Date currentDate;
    static AppDatabase db;
    List<Event> events;
    List<Class> classes;

    FragmentManager manager;

    public static TodayFragment newInstance() {
        TodayFragment fragment = new TodayFragment();
        return fragment;
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_today, container, false);

        manager = getChildFragmentManager();

        ImageButton leftArrow = (ImageButton)view.findViewById(R.id.buttonLeftArrow);
        leftArrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                leftButtonClicked();
            }
        });

        ImageButton rightArrow = (ImageButton)view.findViewById(R.id.buttonRightArrow);
        rightArrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                rightButtonClicked();
            }
        });

        return view;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        db = AppDatabase.getAppDatabase(getContext());
        currentDate = new Date();

        removeCards();
        addCards(currentDate);
    }

    public void leftButtonClicked() {
        currentDate.setDate(currentDate.getDate() - 1);
        removeCards();
        addCards(currentDate);
    }

    public void rightButtonClicked() {
        currentDate.setDate(currentDate.getDate() + 1);
        removeCards();
        addCards(currentDate);
    }

    public void addCards(Date date) {
        Date from = new Date();
        Date to = new Date();

        from.setTime(date.getTime());
        to.setTime(date.getTime());
        from.setHours(0);
        from.setMinutes(0);
        from.setSeconds(0);
        to.setHours(23);
        to.setMinutes(59);
        to.setSeconds(59);

        events = db.eventDao().getAllDateRange(from, to);
        classes = db.classDao().getAll();

        ArrayList<Class> classesToday = new ArrayList<Class>();
        for(Class cl : classes) {
            if(cl.getBegin().compareTo(date) > 0) {
                continue;
            }

            if(cl.getMonday() != null && date.getDay() == 1) {
                classesToday.add(cl);
            }
            else if(cl.getTuesday() != null && date.getDay() == 2) {
                classesToday.add(cl);
            }
            else if(cl.getWednesday() != null && date.getDay() == 3) {
                classesToday.add(cl);
            }
            else if(cl.getThursday() != null && date.getDay() == 4) {
                classesToday.add(cl);
            }
            else if(cl.getFriday() != null && date.getDay() == 5) {
                classesToday.add(cl);
            }
        }

        TextView dayOff = (TextView)view.findViewById(R.id.textDayOff);
        if(events.size() != 0 || classesToday.size() != 0) {
            dayOff.setAlpha(0.0f);
        }
        else {
            dayOff.setAlpha(0.5f);
        }

        TextView dateTitle = (TextView)view.findViewById(R.id.textCurrentDate);
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        String newText = c.getDisplayName(Calendar.MONTH, Calendar.LONG, Locale.getDefault()) + " " + c.get(Calendar.DAY_OF_MONTH)+ ", " + c.get(Calendar.YEAR);
        dateTitle.setText(newText);

        ArrayList<Object> combinedList = new ArrayList<>();
        int curEvent = 0;
        int curClass = 0;
        while(curEvent < events.size() || curClass < classesToday.size()) {
            if (curClass == classesToday.size()) {
                combinedList.add(events.get(curEvent));
                curEvent++;
                continue;
            }
            else if (curEvent == events.size()) {
                combinedList.add(classesToday.get(curClass));
                curClass++;
                continue;
            }

            Event e = events.get(curEvent);
            Class cl = classesToday.get(curClass);

            if(cl.getMonday() != null && date.getDay() == 1) {
                if(e.getDate().getHours() < cl.getMonday().getHours()) {
                    combinedList.add(e);
                    curEvent++;
                    continue;
                } else if (e.getDate().getHours() > cl.getMonday().getHours()) {
                    combinedList.add(cl);
                    curClass++;
                    continue;
                } else if (e.getDate().getMinutes() >= cl.getMonday().getMinutes()) {
                    combinedList.add(e);
                    curEvent++;
                    continue;
                } else {
                    combinedList.add(cl);
                    curClass++;
                    continue;
                }
            }
            else if(cl.getTuesday() != null && date.getDay() == 2) {
                if(e.getDate().getHours() < cl.getTuesday().getHours()) {
                    combinedList.add(e);
                    curEvent++;
                    continue;
                } else if (e.getDate().getHours() > cl.getTuesday().getHours()) {
                    combinedList.add(cl);
                    curClass++;
                    continue;
                } else if (e.getDate().getMinutes() >= cl.getTuesday().getMinutes()) {
                    combinedList.add(e);
                    curEvent++;
                    continue;
                } else {
                    combinedList.add(cl);
                    curClass++;
                    continue;
                }
            }
            else if(cl.getWednesday() != null && date.getDay() == 3) {
                if(e.getDate().getHours() < cl.getWednesday().getHours()) {
                    combinedList.add(e);
                    curEvent++;
                    continue;
                } else if (e.getDate().getHours() > cl.getWednesday().getHours()) {
                    combinedList.add(cl);
                    curClass++;
                    continue;
                } else if (e.getDate().getMinutes() >= cl.getWednesday().getMinutes()) {
                    combinedList.add(e);
                    curEvent++;
                    continue;
                } else {
                    combinedList.add(cl);
                    curClass++;
                    continue;
                }
            }
            else if(cl.getThursday() != null && date.getDay() == 4) {
                if(e.getDate().getHours() < cl.getThursday().getHours()) {
                    combinedList.add(e);
                    curEvent++;
                    continue;
                } else if (e.getDate().getHours() > cl.getThursday().getHours()) {
                    combinedList.add(cl);
                    curClass++;
                    continue;
                } else if (e.getDate().getMinutes() >= cl.getThursday().getMinutes()) {
                    combinedList.add(e);
                    curEvent++;
                    continue;
                } else {
                    combinedList.add(cl);
                    curClass++;
                    continue;
                }
            }
            else if(cl.getFriday() != null && date.getDay() == 5) {
                if(e.getDate().getHours() < cl.getFriday().getHours()) {
                    combinedList.add(e);
                    curEvent++;
                    continue;
                } else if (e.getDate().getHours() > cl.getFriday().getHours()) {
                    combinedList.add(cl);
                    curClass++;
                    continue;
                } else if (e.getDate().getMinutes() >= cl.getFriday().getMinutes()) {
                    combinedList.add(e);
                    curEvent++;
                    continue;
                } else {
                    combinedList.add(cl);
                    curClass++;
                    continue;
                }
            }
        }

        FragmentTransaction ft = manager.beginTransaction();

        /*for (Event event : events) {
            EventCardFragment tempFrag = (EventCardFragment)manager.findFragmentByTag(Integer.toString(event.eID));
            if(tempFrag != null) {
                continue;
            }

            EventCardFragment fragment = EventCardFragment.newInstance();
            Bundle bundle = new Bundle();
            bundle.putInt("eid", event.eID);
            fragment.setArguments(bundle);
            ft.add(R.id.eventScrollLayout, fragment, Integer.toString(event.eID));
        }*/

        for(Object item : combinedList) {
            if(item.getClass() == Event.class) {
                Event event = (Event)item;
                EventCardFragment tempFrag = (EventCardFragment)manager.findFragmentByTag("event" + Integer.toString(event.eID));
                if(tempFrag != null) {
                    continue;
                }

                EventCardFragment fragment = EventCardFragment.newInstance();
                Bundle bundle = new Bundle();
                bundle.putInt("eid", event.eID);
                fragment.setArguments(bundle);
                ft.add(R.id.eventScrollLayout, fragment, "event" + Integer.toString(event.eID));
            }
            else if(item.getClass() == Class.class) {
                Class cl = (Class)item;
                ClassCardTodayFragment tempFrag = (ClassCardTodayFragment)manager.findFragmentByTag("class" + Integer.toString(cl.cID));
                if(tempFrag != null) {
                    continue;
                }

                ClassCardTodayFragment fragment = ClassCardTodayFragment.newInstance();
                Bundle bundle = new Bundle();
                bundle.putInt("cid", cl.cID);
                bundle.putLong("curDate", date.getTime());
                fragment.setArguments(bundle);
                ft.add(R.id.eventScrollLayout, fragment, "class" + Integer.toString(cl.cID));
            }
        }

        ft.commit();
    }

    public void removeCards() {
        for(Fragment fragment : manager.getFragments()) {
            if(fragment != null) {
                manager.beginTransaction().remove(fragment).commit();
                manager.popBackStackImmediate();
            }
        }
    }

    @Override
    public void onDataReceived(Date selectedDate) {
        if(selectedDate != null) {
            currentDate.setTime(selectedDate.getTime());
            currentDate.setMonth(currentDate.getMonth() - 1);
            currentDate.setYear(currentDate.getYear() - 1900);

            removeCards();
            addCards(currentDate);
        }
    }

}


