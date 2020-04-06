package com.maccoux.busybadger;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        reqPermissions();
        createNotificationChannel();
    }

    /** This function asks for the permissions required
     *
     */
    public void reqPermissions() {
        int PERMISSION_ALL = 1;
        String[] PERMISSIONS = {Manifest.permission.SET_ALARM,Manifest.permission.ACCESS_FINE_LOCATION};
        if(!hasPermissions(this, PERMISSIONS)){
            ActivityCompat.requestPermissions(this, PERMISSIONS, PERMISSION_ALL);
        }

    }

    /** This function checks if the application has the needed permissions
     *
     * @param context
     * @param permissions string array of Manifest.permission.XXXXX
     * @return true/false if a needed permission is missing
     */
    public boolean hasPermissions(Context context, String[] permissions) {

        if (android.os.Build.VERSION.SDK_INT >= 23 && context != null && permissions != null) {
            for (String permission : permissions) {
                if (ActivityCompat.checkSelfPermission(context, permission) != PackageManager.PERMISSION_GRANTED) {
                    return false;
                }
            }
        }
        return true;
    }

    /** Taken from https://developer.android.com/training/notify-user/build-notification
     *
     */
    private void createNotificationChannel() {

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            CharSequence name = "BusyBadger";
            String description = "Notifications for Assignments or other School Events";
            int importance = NotificationManager.IMPORTANCE_DEFAULT;
            NotificationChannel channel = new NotificationChannel("1", name, importance);
            channel.setDescription(description);

            NotificationManager notificationManager = getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);
        }
    }

    /** This is a function that builds the notifications given a title and body, will be adding more advanced functions later
     *
     * @param title string of title
     * @param body string of body (what you want to say)
     * @return notification builder object
     */
  public NotificationCompat.Builder buildNotification(String title,String body) {
        String textTitle = title;
        String textContent = body;
      NotificationCompat.Builder builder = new NotificationCompat.Builder(this, "1")
              .setSmallIcon(R.drawable.ic_launcher_background)
              .setContentTitle(textTitle)
              .setContentText(textContent)
              .setPriority(NotificationCompat.PRIORITY_DEFAULT);
      return builder;
  }

    /** This is the Onclick function to test Notifications
     *
     * @param v
     */
  public void onTestNotify(View v) {
    NotificationCompat.Builder builder = buildNotification("Test","This is a test Notification");
      NotificationManagerCompat notificationManager = NotificationManagerCompat.from(this);
      int notificationId = 0;
      notificationManager.notify(notificationId, builder.build());
  }

    /** This is the Onclick function to go to the views' screens
     *
     * @param v
     */
    public void onViewButton(View v) {
        //Intent intent = new Intent(this, HomeView.class);
        //startActivity(intent);
    }

    public void onAddEventButton(View v) {
        Intent intent = new Intent(this, AddEvent.class);
        startActivity(intent);
    }

}
