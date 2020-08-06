package com.gasco.UI.Activities;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.PersistableBundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.gasco.Models.SessionListener;
import com.gasco.utils.Constants;
import com.gasco.utils.SharedData;

public class BaseActivity extends AppCompatActivity implements SessionListener {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        App.registerListener(this);
        App.startSession();
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onSessionTimeOut() {
        SharedPreferences sharedPreferences = getSharedPreferences(Constants.PREFERENCE_NAME, 0);
        sharedPreferences.edit().clear().apply();
        SharedData.CLEAR_DATA();
        Intent intent = new Intent(this, RegistrationActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
        this.finish();
    }

}
