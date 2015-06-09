package scu.edu.moodlogger;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;


/**
 * @author Abhishek Brijepatil
 */

public class Login_Success extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_success);
        final Context context = getApplicationContext();
    }
}
