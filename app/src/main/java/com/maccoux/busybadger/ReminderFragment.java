package com.maccoux.busybadger;

import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;

import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.DialogFragment;


public class ReminderFragment extends DialogFragment {
    public boolean[] checkOptions;
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity(),android.R.style.Theme_Material_Dialog);
        DialogInterface.OnClickListener listener = new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    CheckBox check15 = getView().findViewById(R.id.checkBox15min);
                    CheckBox check1hour = getView().findViewById(R.id.checkBox1hour);
                    CheckBox check1day = getView().findViewById(R.id.checkBox1day);
                    CheckBox check1week = getView().findViewById(R.id.checkBox1week);
                    boolean[] checkOptions2 = {check15.isChecked(),check1hour.isChecked(),check1day.isChecked(),check1week.isChecked()};
                    checkOptions = checkOptions2;
                }
        };
        DialogInterface.OnClickListener listener2 = new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dismiss();
            }
        };
        builder.setPositiveButton("Confirm",
               listener);
        builder.setNegativeButton("Cancel",
              listener2);
        builder.setTitle("Remind Me");
        builder.setView(R.layout.reminder_fragment);

        return builder.create();

    }
    public boolean[] onConfirm(View v) {
        return checkOptions;
    }
}
