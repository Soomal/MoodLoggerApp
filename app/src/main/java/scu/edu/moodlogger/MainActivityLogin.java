package scu.edu.moodlogger;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;


public class MainActivityLogin extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_login);
    }


    public void register(View view) {
        Intent intent = new Intent(this, Register_user.class);
        startActivity(intent);
    }

    public void login(View view) {

        Intent intent = new Intent(this, Login_user.class);
        startActivity(intent);
    }
}
