package com.maccoux.busybadger;

import android.content.Intent;
import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.tabs.TabLayout;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.viewpager.widget.ViewPager;
import androidx.appcompat.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;

import com.maccoux.busybadger.UIMain.CalendarFragment;
import com.maccoux.busybadger.UIMain.SectionsPagerAdapter;

import java.util.Date;

public class HomeView extends AppCompatActivity implements CalendarFragment.CalendarDataListener {

    SectionsPagerAdapter sectionsPagerAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
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

        FloatingActionButton addEventButton = (FloatingActionButton)findViewById(R.id.addEventScreenButton);
        addEventButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
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
