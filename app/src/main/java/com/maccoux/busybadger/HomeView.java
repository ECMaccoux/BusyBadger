package com.maccoux.busybadger;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.tabs.TabLayout;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.viewpager.widget.ViewPager;
import androidx.appcompat.app.AppCompatActivity;

import android.preference.PreferenceManager;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.TextView;

import com.maccoux.busybadger.Setup.Setup1_Welcome;
import com.maccoux.busybadger.UIMain.CalendarFragment;
import com.maccoux.busybadger.UIMain.SectionsPagerAdapter;

import java.util.Date;

public class HomeView extends AppCompatActivity implements CalendarFragment.CalendarDataListener {

    SectionsPagerAdapter sectionsPagerAdapter;

    FloatingActionButton fab_main, fab1_assignment, fab2_event;
    Animation fab_open, fab_close, fab_clock, fab_anticlock;
    TextView textview_assignment, textview_event;
    boolean isOpen = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        SharedPreferences prefs = this.getSharedPreferences("com.maccoux.busybadger", Context.MODE_PRIVATE);
        if(!prefs.getBoolean("firstRun", false)) {
            Intent intent = new Intent(getApplicationContext(), Setup1_Welcome.class);
            startActivity(intent);
        }

        super.onCreate(savedInstanceState);
        setContentView(R.layout.homeview_main);

        sectionsPagerAdapter = new SectionsPagerAdapter(this, getSupportFragmentManager());
        ViewPager viewPager = findViewById(R.id.view_pager);
        viewPager.setAdapter(sectionsPagerAdapter);
        viewPager.setCurrentItem(1);

        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                // Here's your instance
                Fragment fragment = sectionsPagerAdapter.getRegisteredFragment(position);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

        TabLayout tabs = findViewById(R.id.tabs);
        tabs.setupWithViewPager(viewPager);

        fab_main = (FloatingActionButton)findViewById(R.id.addEventScreenButton);
        fab1_assignment = (FloatingActionButton)findViewById(R.id.fabAddAssignment);
        fab2_event = (FloatingActionButton)findViewById(R.id.fabAddEvent);
        fab_close = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.fab_close);
        fab_open = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.fab_open);
        fab_clock = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.fab_rotate_clock);
        fab_anticlock = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.fab_rotate_anticlock);

        textview_assignment = (TextView)findViewById(R.id.textview_assignment);
        textview_event = (TextView)findViewById(R.id.textview_event);


        fab_main.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(isOpen) {
                    textview_assignment.setVisibility(View.INVISIBLE);
                    textview_event.setVisibility(View.INVISIBLE);
                    fab2_event.startAnimation(fab_close);
                    fab1_assignment.startAnimation(fab_close);
                    fab_main.startAnimation(fab_anticlock);
                    fab2_event.setClickable(false);
                    fab1_assignment.setClickable(false);
                    isOpen = false;
                } else {
                    textview_assignment.setVisibility(View.VISIBLE);
                    textview_event.setVisibility(View.VISIBLE);
                    fab2_event.startAnimation(fab_open);
                    fab1_assignment.startAnimation(fab_open);
                    fab_main.startAnimation(fab_clock);
                    fab2_event.setClickable(true);
                    fab1_assignment.setClickable(true);
                    isOpen = true;
                }
            }
        });

        fab1_assignment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), AddEvent.class);
                intent.putExtra("type", 1);
                startActivity(intent);
            }
        });

        fab2_event.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), AddEvent.class);
                intent.putExtra("type", 0);
                startActivity(intent);
            }
        });
    }
    /** This is the onCreateOptionsMenu function to inflate the settings menu
     *
     * @param menu
     * @return inflated menu
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        Intent intent;

        switch(item.getItemId()) {
            case R.id.TestButton:
                intent = new Intent(this, TestScreen.class);
                startActivity(intent);
                return true;
            case R.id.SettingsButton:
                intent = new Intent(this, Settings.class);
                startActivity(intent);
                break;
            default: return super.onOptionsItemSelected(item);
        }
        return true;
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        recreate();
    }

    @Override
    public void onDataReceived(Date selectedDate) {
        sectionsPagerAdapter.onDataReceived(selectedDate);
    }

}
