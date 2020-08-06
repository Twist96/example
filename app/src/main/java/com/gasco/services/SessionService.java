package com.gasco.services;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.CountDownTimer;
import android.os.IBinder;
import android.widget.Toast;

import androidx.annotation.Nullable;

public class SessionService extends Service {

    public static CountDownTimer timer;
    public Context context;

    @Override
    public void onCreate() {
        super.onCreate();

        context = getApplicationContext();
        timer = new CountDownTimer(58 * 60 * 1000, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {

            }

            @Override
            public void onFinish() {
                //navigate to registration screen
                Toast.makeText(context, "Finished Logged OUT", Toast.LENGTH_LONG).show();
            }
        };
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
}
