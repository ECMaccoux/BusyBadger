package com.maccoux.busybadger;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.maccoux.busybadger.Room.AppDatabase;
import com.maccoux.busybadger.Room.Class;
import com.maccoux.busybadger.Room.Event;
import com.maccoux.busybadger.UIMain.ClassCardFragment;
import com.maccoux.busybadger.UIMain.EventCardFragment;

import java.util.Date;
import java.util.List;

public class PickClass extends AppCompatActivity {

    static AppDatabase db;
    List<Class> classes;
    FragmentManager manager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_classpicker);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbarClassPicker); // get the reference of Toolbar
        setSupportActionBar(toolbar); // Setting/replace toolbar as the ActionBar

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) { finish(); }
        });

        db = AppDatabase.getAppDatabase(this);

        addCards();
    }

    public void addCards() {
        classes = db.classDao().getAll();

        TextView noClasses = (TextView)findViewById(R.id.textNoClasses);
        if(classes.size() != 0) {
            noClasses.setAlpha(0.0f);
        }
        else {
            noClasses.setAlpha(0.5f);
        }

        //TextView dateTitle = (TextView)view.findViewById(R.id.textCurrentDate);
        //Calendar c = Calendar.getInstance();
        //c.setTime(date);
        //String newText = c.getDisplayName(Calendar.MONTH, Calendar.LONG, Locale.getDefault()) + " " + c.get(Calendar.DAY_OF_MONTH)+ ", " + c.get(Calendar.YEAR);
        //dateTitle.setText(newText);

        manager = getSupportFragmentManager();
        FragmentTransaction ft = manager.beginTransaction();

        for (Class current : classes) {
            ClassCardFragment tempFrag = (ClassCardFragment)manager.findFragmentByTag(Integer.toString(current.cID));
            if(tempFrag != null) {
                continue;
            }

            ClassCardFragment fragment = ClassCardFragment.newInstance();
            Bundle bundle = new Bundle();
            bundle.putInt("cid", current.cID);
            fragment.setArguments(bundle);
            ft.add(R.id.pickClassScrollLayout, fragment, Integer.toString(current.cID));
        }

        ft.commit();
    }

}
