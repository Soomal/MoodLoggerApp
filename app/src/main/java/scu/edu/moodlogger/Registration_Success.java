package scu.edu.moodlogger;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

/**
 * @author Abhishek Brijepatil
 *         This class provides functionality to go to login
 *         once registration is successful.
 */

public class Registration_Success extends Activity {

    /**
     * Called when the activity is first created.
     */

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.registration_success);
    }


    /**
     * redirect to login screen
     */
    public void redirect_to_login(View view) {
        Intent intent = new Intent(this, Login_user.class);
        startActivity(intent);
    }
}
