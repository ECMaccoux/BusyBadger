package com.maccoux.busybadger;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import androidx.appcompat.widget.Toolbar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.util.Log;
import android.view.View;
import android.widget.Toast;

public class Settings extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.settings);
        Toolbar toolbar = (Toolbar) findViewById(R.id.settingsToolbar);
        setSupportActionBar(toolbar);

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent myIntent = new Intent(Settings.this, HomeView.class);
                myIntent.putExtra("settings", ""); //Optional parameters
                Settings.this.startActivity(myIntent);
            }
        });
    }

    public void onEditEventButton(View v) {
        String[] multiChoiceItems = getResources().getStringArray(R.array.eventListArray);
        final boolean[] checkedItems = {false, false};
        new AlertDialog.Builder(this, R.style.MyDialogTheme)
                .setTitle(R.string.EditEventList)
                .setMultiChoiceItems(multiChoiceItems, checkedItems, new DialogInterface.OnMultiChoiceClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int index, boolean isChecked) {
                        Log.d("MainActivity", "clicked item index is " + index);
                    }
                })
                .setPositiveButton("Ok", null)
                .setNegativeButton("Cancel", null)
                .show();
    }
    public void onEditListButton(View v) {
        String[] multiChoiceItems = getResources().getStringArray(R.array.classListArray);
        final boolean[] checkedItems = {false, false};
        new AlertDialog.Builder(this, R.style.MyDialogTheme)
                .setTitle(R.string.EditClassList)
                .setMultiChoiceItems(multiChoiceItems, checkedItems, new DialogInterface.OnMultiChoiceClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int index, boolean isChecked) {
                        Log.d("MainActivity", "clicked item index is " + index);
                    }
                })
                .setPositiveButton("Ok", null)
                .setNegativeButton("Cancel", null)
                .show();
    }

    public void onResetButton(View v) {
        Toast.makeText(this, "Reset button clicked!", Toast.LENGTH_SHORT).show();
    }
}
