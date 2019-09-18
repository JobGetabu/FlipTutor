package cs.dal.krush;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import cs.dal.krush.seeders.DatabaseSeeder;

public class MainActivity extends AppCompatActivity {

    public static final String PREFS_NAME = "MyPrefs" ;
    SharedPreferences sharedPreferences;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // check to see if user has opened the app before
        sharedPreferences = getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
        if(!sharedPreferences.contains("first_login")){
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putBoolean("first_login", false);
            editor.commit();

            //seed data
            DatabaseSeeder seederInitial = new DatabaseSeeder(getApplicationContext());
            seederInitial.insertData();
            seederInitial.displayData();
        }

        //fetch UI elements:
        final Button signup_home_button = (Button) findViewById(R.id.signup_button);
        final Button login_home_button = (Button) findViewById(R.id.login_button);
        final TextView krush_logo_textView = (TextView) findViewById(R.id.krushLogo);
        final TextView krush_slogan_textView = (TextView) findViewById(R.id.krushSlogan);

        //fetch custom app font
        Typeface typeFace = Typeface.createFromAsset(getAssets(),"fonts/FredokaOne-Regular.ttf");

        //set logo font style
        krush_logo_textView.setTypeface(typeFace);
        krush_slogan_textView.setTypeface(typeFace);

        //setup OnClickListeners:
        signup_home_button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent i = new Intent(MainActivity.this, RegistrationActivity.class);
                startActivity(i);
            }
        });
        login_home_button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent i = new Intent(MainActivity.this, LoginMainActivity.class);
                startActivity(i);
            }
        });
    }
}
