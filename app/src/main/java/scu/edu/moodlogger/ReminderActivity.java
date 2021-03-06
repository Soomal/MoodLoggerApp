package scu.edu.moodlogger;

import android.app.Activity;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TimePicker;
import android.widget.Toast;

import java.util.Calendar;

/**
 * @author Soomal Choudhary
 * This class provides functionality to set the reminder.
 */

public class ReminderActivity extends Activity {

    Calendar calendar;
    TimePicker timePicker;
    Button buttonSet;
    CheckBox checkBox;
    PendingIntent pendingNotification;
    AlarmManager alarmManager;
    Boolean  isActive = false;
    public static final String PREFS_NAME = "MyPrefsFile";


    /**
     * Called when the activity is first created.
     */

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reminder);

        // Restore preferences
        SharedPreferences settings = getSharedPreferences(PREFS_NAME, 0);
        isActive = settings.getBoolean("isAlarmActive", false);

        timePicker = (TimePicker) findViewById(R.id.timePicker);
        buttonSet = (Button) findViewById(R.id.button_setAlarm);
        checkBox = (CheckBox) findViewById(R.id.checkBox);

        calendar = Calendar.getInstance();
        Intent notificationReceiver = new Intent(ReminderActivity.this, AlarmBroadcastReceiver.class);
        pendingNotification = PendingIntent.getBroadcast(ReminderActivity.this,
                6, notificationReceiver, 0);
        alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);


        if(isActive){
            checkBox.setChecked(true);
        }else{
            checkBox.setChecked(false);

        }

        checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(!checkBox.isChecked()){
                    alarmManager.cancel(pendingNotification);

                    isActive = false;

                }
            }
        });
        // It will set alarm to trigger notification everyday on user's set time.
        buttonSet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (checkBox.isChecked()) {
                    calendar.set(Calendar.HOUR_OF_DAY, timePicker.getCurrentHour());
                    calendar.set(Calendar.MINUTE, timePicker.getCurrentMinute());

                    alarmManager.setInexactRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(),
                            AlarmManager.INTERVAL_DAY, pendingNotification);
                    isActive = true;

                    Toast toast = Toast.makeText(getApplicationContext(), "Reminder Set", Toast.LENGTH_SHORT);
                    toast.show();
                } else {
                    alarmManager.cancel(pendingNotification);
                }
            }
        });

    }


    @Override
    protected void onStop(){
        super.onStop();

        // We need an Editor object to make preference changes.
        // All objects are from android.context.Context
        SharedPreferences settings = getSharedPreferences(PREFS_NAME, 0);
        SharedPreferences.Editor editor = settings.edit();
        editor.putBoolean("isAlarmActive", isActive);

        // Commit the edits!
        editor.commit();
    }

}
