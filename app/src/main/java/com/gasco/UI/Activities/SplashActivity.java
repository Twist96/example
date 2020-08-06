package com.gasco.UI.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;

import com.gasco.Models.User;
import com.gasco.R;
import com.gasco.utils.Constants;
import com.gasco.utils.HFunc;
import com.gasco.utils.SharedData;
import com.google.gson.Gson;

import java.util.Date;

public class SplashActivity extends AppCompatActivity {

    private static final int SPLASH_SCREEN_TIME = 2500;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setTheme(R.style.AppTheme);
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_splash);

        //getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);


        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                startActivity(getNextScreenIntent());
                finish();
            }
        }, SPLASH_SCREEN_TIME);

    }

    private Intent getNextScreenIntent(){

        SharedPreferences sharedPreferences = getSharedPreferences(Constants.PREFERENCE_NAME, 0);
        String userString = sharedPreferences.getString(Constants.USER, "");

        //todo: before publication, user should always login
        if (userString.isEmpty()){
            return new Intent(this, RegistrationActivity.class);
        }

        SharedData.USER = (new Gson()).fromJson(userString, User.class);
        SharedData.USER_TOKEN = sharedPreferences.getString(Constants.USER_TOKEN, "");
        SharedData.LAST_LOGIN_TIME = new Date(sharedPreferences.getLong(Constants.LAST_LOGIN_TIME, 0));
        return new Intent(this, DashboardActivity.class);
    }
}
