package com.maccoux.busybadger.UIMain;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.maccoux.busybadger.AddEvent;
import com.maccoux.busybadger.R;
import com.maccoux.busybadger.Room.AppDatabase;
import com.maccoux.busybadger.Room.Event;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class TodayFragment extends Fragment {

    View view;
    Date currentDate;
    static AppDatabase db;
    List<Event> events;

    FragmentManager manager;

    public static TodayFragment newInstance() {
        TodayFragment fragment = new TodayFragment();
        return fragment;
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_today, container, false);

        FloatingActionButton addEventButton = (FloatingActionButton)view.findViewById(R.id.addEventScreenButton);
        addEventButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), AddEvent.class);
                startActivity(intent);
            }
        });

        return view;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        db = AppDatabase.getAppDatabase(getContext());

        currentDate = new Date();

        Date from = new Date();
        Date to = new Date();

        from.setTime(currentDate.getTime());
        to.setTime(currentDate.getTime());
        from.setHours(0);
        from.setMinutes(0);
        from.setSeconds(0);
        to.setHours(23);
        to.setMinutes(59);
        to.setSeconds(59);

        events = db.eventDao().getAllDateRange(from, to);

        TextView dayOff = (TextView)view.findViewById(R.id.textDayOff);
        if(events.size() != 0) {
            dayOff.setAlpha(0.0f);
        }
        else {
            dayOff.setAlpha(0.5f);
        }

        TextView dateTitle = (TextView)view.findViewById(R.id.textCurrentDate);
        Calendar c = Calendar.getInstance();
        c.setTime(currentDate);
        String newText = c.getDisplayName(Calendar.MONTH, Calendar.LONG, Locale.getDefault()) + " " + c.get(Calendar.DAY_OF_MONTH)+ ", " + c.get(Calendar.YEAR);
        dateTitle.setText(newText);

        manager = getChildFragmentManager();
        FragmentTransaction ft = manager.beginTransaction();

        for (Event event : events) {
            EventCardFragment tempFrag = (EventCardFragment)manager.findFragmentByTag(Integer.toString(event.eID));
            if(tempFrag != null) {
                continue;
            }

            EventCardFragment fragment = EventCardFragment.newInstance();
            Bundle bundle = new Bundle();
            bundle.putInt("eid", event.eID);
            fragment.setArguments(bundle);
            ft.add(R.id.eventScrollLayout, fragment, Integer.toString(event.eID));
        }

        ft.commit();
    }

}


