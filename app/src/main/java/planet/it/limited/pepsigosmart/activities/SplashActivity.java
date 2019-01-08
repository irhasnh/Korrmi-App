package planet.it.limited.pepsigosmart.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;

import static planet.it.limited.pepsigosmart.utils.SaveValueSharedPreference.getBoleanValueSharedPreferences;


public class SplashActivity extends AppCompatActivity {
    boolean checkFirstTimeUser = false;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        checkFirstTimeUser = getBoleanValueSharedPreferences("check_first_time", SplashActivity.this);
        if (checkFirstTimeUser) {
            Intent intent = new Intent(SplashActivity.this, SelectPuposeAct.class);
            startActivity(intent);
            ActivityCompat.finishAffinity(SplashActivity.this);
        } else {

            Intent loginIntent = new Intent(SplashActivity.this, SignInAct.class);
            startActivity(loginIntent);
            ActivityCompat.finishAffinity(SplashActivity.this);
        }
        //to use phone state permission


    }


}
