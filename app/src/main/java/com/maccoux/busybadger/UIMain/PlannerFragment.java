package com.maccoux.busybadger.UIMain;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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

import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class PlannerFragment extends Fragment {

    View view;
    static AppDatabase db;
    List<Event> events;
    FragmentManager manager;

    public static PlannerFragment newInstance() {
        PlannerFragment fragment = new PlannerFragment();
        return fragment;
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_planner, container, false);

        manager = getChildFragmentManager();

        return view;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        db = AppDatabase.getAppDatabase(getContext());
        removeCards();
        addCards();
    }

    public void addCards() {
        events = db.eventDao().getAllFromDate(new Date());

        TextView noEvents = (TextView)view.findViewById(R.id.textNoEvents);
        if(events.size() != 0) {
            noEvents.setAlpha(0.0f);
        }
        else {
            noEvents.setAlpha(0.5f);
        }

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

    public void removeCards() {
        for(Fragment fragment : manager.getFragments()) {
            if(fragment != null) {
                manager.beginTransaction().remove(fragment).commit();
                manager.popBackStackImmediate();
            }
        }
    }

}


