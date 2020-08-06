package com.gasco.UI.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.gasco.R;
import com.gasco.utils.Constants;
import com.gasco.utils.SharedData;

public class ProfileActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_profile);

        TextView fullNameView = findViewById(R.id.full_name);
        fullNameView.setText(SharedData.USER.getFullName());
        TextView roleNameView = findViewById(R.id.role_name);
        roleNameView.setText(SharedData.USER.getRoleName());

    }

    public void EditProfile(View view) {
        Intent intent = new Intent(this, ProfileEditActivity.class);
        startActivity(intent);
    }

    public void UpdatePassword(View view) {
        Intent intent = new Intent(this, PasswordActivity.class);
        startActivity(intent);
    }

    public void LogOut(View view) {
//        SharedPreferences  sharedPreferences = getSharedPreferences(Constants.PREFERENCE_NAME, 0);
//        sharedPreferences.edit().remove(Constants.USER).apply();
//        sharedPreferences.edit().remove(Constants.USER_TOKEN).apply();

        SharedPreferences sharedPreferences = getSharedPreferences(Constants.PREFERENCE_NAME, 0);
        sharedPreferences.edit().clear().apply();
        SharedData.CLEAR_DATA();
        Intent intent = new Intent(this, RegistrationActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
        this.finish();
    }

    public void DismissActivity(View view) {
        finish();
    }
}
