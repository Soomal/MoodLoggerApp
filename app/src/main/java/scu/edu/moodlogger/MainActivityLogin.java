package scu.edu.moodlogger;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

/**
 * @author Abhishek Brijepatil
 *         This class provides login functionality to the app.
 */
public class MainActivityLogin extends Activity {


    /**
     * Called when the activity is first created.
     */

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_login);
    }


    /**
     * Redirects to the register screen
     */
    public void register(View view) {
        Intent intent = new Intent(this, Register_user.class);
        startActivity(intent);
    }

    /**
     * Redirects to the login screen
     */
    public void login(View view) {

        Intent intent = new Intent(this, Login_user.class);
        startActivity(intent);
    }
}
