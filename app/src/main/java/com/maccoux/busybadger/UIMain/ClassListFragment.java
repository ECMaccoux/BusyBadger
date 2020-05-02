package com.maccoux.busybadger.UIMain;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.maccoux.busybadger.R;
import com.maccoux.busybadger.Room.AppDatabase;
import com.maccoux.busybadger.Room.Class;
import com.maccoux.busybadger.Room.Event;

import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class ClassListFragment extends Fragment {

    View view;
    Date currentDate;
    static AppDatabase db;
    List<Event> events;

    FragmentManager manager;

    public static ClassListFragment newInstance() {
        ClassListFragment fragment = new ClassListFragment();
        return fragment;
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_class_list, container, false);

        /*ImageButton leftArrow = (ImageButton)view.findViewById(R.id.buttonLeftArrow);
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
        });*/

        return view;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        db = AppDatabase.getAppDatabase(getContext());

        addCards();
    }

    /*public void leftButtonClicked() {
        currentDate.setDate(currentDate.getDate() - 1);
        removeCards();
        addCards(currentDate);
    }*/

    /*
    public void rightButtonClicked() {
        currentDate.setDate(currentDate.getDate() + 1);
        removeCards();
        addCards(currentDate);
    }*/

    public void addCards() {

        List<Class> classes = db.classDao().getAll();

        TextView dayOff = (TextView)view.findViewById(R.id.textDayOff);
        if(events.size() != 0) {
            dayOff.setAlpha(0.0f);
        }
        else {
            dayOff.setAlpha(0.5f);
        }

        //TextView dateTitle = (TextView)view.findViewById(R.id.textCurrentDate);
        //Calendar c = Calendar.getInstance();
        //c.setTime(date);
        //String newText = c.getDisplayName(Calendar.MONTH, Calendar.LONG, Locale.getDefault()) + " " + c.get(Calendar.DAY_OF_MONTH)+ ", " + c.get(Calendar.YEAR);
        //dateTitle.setText(newText);

        manager = getChildFragmentManager();
        FragmentTransaction ft = manager.beginTransaction();

        for (Class current : classes) {
            EventCardFragment tempFrag = (EventCardFragment)manager.findFragmentByTag(Integer.toString(current.cID));
            if(tempFrag != null) {
                continue;
            }

            EventCardFragment fragment = EventCardFragment.newInstance();
            Bundle bundle = new Bundle();
            bundle.putInt("cid", current.cID);
            fragment.setArguments(bundle);
            ft.add(R.id.eventScrollLayout, fragment, Integer.toString(current.cID));
        }

        ft.commit();
    }

    public void removeCards() {
        for(Fragment fragment : manager.getFragments()) {
            if(fragment != null) {
                manager.beginTransaction().remove(fragment).commit();
            }
        }
    }

}


