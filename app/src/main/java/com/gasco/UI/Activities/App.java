package com.gasco.UI.Activities;

import android.app.Application;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.CountDownTimer;
import android.util.Log;
import android.util.TimeUtils;
import android.widget.Toast;

import com.gasco.Models.SessionListener;
import com.gasco.utils.Constants;
import com.gasco.utils.SharedData;

import java.util.Date;
import java.util.concurrent.TimeUnit;

public class App extends Application {
    private static CountDownTimer countDownTimer;
    private static SessionListener listener;
    private static long timeLeft;

    private static final String TAG = "App";

    @Override
    public void onCreate() {
        super.onCreate();
    }

    private static void start(){
        timeLeft = getTimeLeft(SharedData.LAST_LOGIN_TIME);
        //Toast.makeText(getApplicationContext(), "Time left" + (timeLeft / (60 * 1000)), Toast.LENGTH_LONG).show();
        countDownTimer = new CountDownTimer(timeLeft, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                Log.d(TAG, "onTick: " + millisUntilFinished);
            }

            @Override
            public void onFinish() {
                //navigate to registration screen and clear data
                Log.d(TAG, "onFinish: Time Up");
                listener.onSessionTimeOut();
            }
        };
    }

    private static long getTimeLeft(Date loginTime){
        Date expectedEndTime = new Date(loginTime.getTime() + TimeUnit.MINUTES.toMillis(58));
        Date currentDate = new Date();
        long x = expectedEndTime.getTime() - currentDate.getTime();
        return x;
    }

    public static void startSession(){
        start();
        countDownTimer.start();
    }

    public static void restartSession(){
        if (countDownTimer != null){
            countDownTimer.cancel();
            countDownTimer.start();
        }
    }

    public static void registerListener(SessionListener listener){
        App.listener = listener;
    }
}
