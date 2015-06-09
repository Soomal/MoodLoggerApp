package scu.edu.moodlogger;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.media.Image;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Toast;

import scu.edu.moodlogger.graph.GraphViewFragmentActivity;

/**
 * @author Soomal Choudhary
 *         This class provides the main screen for the app. From here user can access
 *         all the functionalities of the app.
 */

public class MainActivity extends Activity {

    /**
     * Called when the activity is first created.
     */

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button moodBtn = (Button) findViewById(R.id.imageButton_logmood);
        Button calendarBtn = (Button) findViewById(R.id.imageButton_calendar);
        Button chartBtn = (Button) findViewById(R.id.imageButton_chart);
        Button reminderBtn = (Button) findViewById(R.id.imageButton_reminder);


        //Retrieve the user id when the login is successful
        //using shared preferences.

        SharedPreferences sp = getSharedPreferences("user_pref", Activity.MODE_PRIVATE);
        String userid = sp.getString("user_key", "");


        /**
         * Redirects to the mood logging screen
         */
        moodBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent moodIntent = new Intent(getApplicationContext(), MoodLoggingActivity.class);
                startActivity(moodIntent);

            }
        });


        /**
         * Redirects to the calendar screen
         */

        calendarBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent calIntent = new Intent(getApplicationContext(), CalendarActivity.class);
                startActivity(calIntent);

            }
        });

        /**
         * Redirects to the Graph and charts screen
         */

        chartBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

//                Intent chartIntent = new Intent(getApplicationContext(), ChartActivity.class);
                Intent chartIntent = new Intent(getApplicationContext(), GraphViewFragmentActivity.class);
                startActivity(chartIntent);

            }
        });

        /**
         * Redirects to the set reminder screen
         */
        reminderBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent reminderIntent = new Intent(getApplicationContext(), ReminderActivity.class);
                startActivity(reminderIntent);

            }
        });
    }


}
