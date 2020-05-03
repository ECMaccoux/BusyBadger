package com.maccoux.busybadger;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import androidx.appcompat.widget.Toolbar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.maccoux.busybadger.Room.AppDatabase;
import com.maccoux.busybadger.Setup.Setup1_Welcome;
import com.maccoux.busybadger.Setup.Setup3_Class;
import com.maccoux.busybadger.Setup.Setup4_Finish;

public class Settings extends AppCompatActivity {

    static AppDatabase db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.settings);
        Toolbar toolbar = (Toolbar) findViewById(R.id.settingsToolbar);
        setSupportActionBar(toolbar);

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        db = AppDatabase.getAppDatabase(this);
    }

    public void onEditListButton(View v) {
        Intent intent = new Intent(getApplicationContext(), Setup3_Class.class);
        startActivity(intent);
    }

    public void onResetButton(View v) {
        //Toast.makeText(this, "Reset button clicked!", Toast.LENGTH_SHORT).show();

        new AlertDialog.Builder(this, R.style.MyDialogTheme)
                .setTitle("Reset BusyBadger")
                .setMessage("Do you really want to reset BusyBadger?  Deleted data cannot be recovered.")
                .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        db.eventDao().deleteAll();
                        db.classDao().deleteAll();

                        SharedPreferences prefs = Settings.this.getSharedPreferences("com.maccoux.busybadger", Context.MODE_PRIVATE);
                        SharedPreferences.Editor editor = prefs.edit();
                        editor.putBoolean("firstRun", false);
                        editor.commit();

                        Intent intent = new Intent(getApplicationContext(), Setup1_Welcome.class);
                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                        startActivity(intent);
                    }
                })
                .setNegativeButton(android.R.string.no, null).show();
    }
}
