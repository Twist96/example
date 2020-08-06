package com.gasco.UI.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Base64;
import android.util.Log;
import android.view.View;

import com.gasco.Enums.AlertType;
import com.gasco.Models.User;
import com.gasco.R;
import com.gasco.UI.Dialogs.PopUpAlertDialog;
import com.gasco.UI.Dialogs.ProcessDialog;
import com.gasco.services.API;
import com.gasco.services.gescoServices.UserService;
import com.gasco.utils.Constants;
import com.gasco.utils.HFunc;
import com.gasco.utils.SharedData;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.google.gson.Gson;

import java.util.Date;

public class RegistrationActivity extends AppCompatActivity {

    private static final String TAG = "RegistrationActivity";
    private TextInputLayout usernameLayout, passwordLayout;
    private TextInputEditText usernameView, passwordView;
    private MaterialButton loginBtn;
    private SharedPreferences sharedPreferences;
    ProcessDialog processDialog;
    PopUpAlertDialog alertDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);

        usernameLayout = findViewById(R.id.outlinedTextField_username);
        passwordLayout = findViewById(R.id.outlinedTextField_password);
        usernameView = findViewById(R.id.username);
        passwordView = findViewById(R.id.password);
        loginBtn = findViewById(R.id.login_btn);

        sharedPreferences = getSharedPreferences(Constants.PREFERENCE_NAME, 0);
        processDialog = new ProcessDialog(this, android.R.style.Theme_Translucent_NoTitleBar_Fullscreen);
        alertDialog = new PopUpAlertDialog(this, android.R.style.Theme_Translucent_NoTitleBar_Fullscreen);
        disableSendButton();

        usernameView.addTextChangedListener(usernameTextWatcher);
        passwordView.addTextChangedListener(passwordTextWatcher);
    }

    public void login(View view) {
        final String username = usernameView.getText().toString();
        final String password = passwordView.getText().toString();
        processDialog.show();
        processDialog.setMessage("");
        API.USER_SERVICE.LoginUser(username, password, new UserService.UserCallBack() {
            @Override
            public void onSuccess(final User user, final String authorization) {

                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        saveData(user, authorization);
                        processDialog.dismiss();
                        App.restartSession();
                        Intent intent = new Intent(getApplicationContext(), DashboardActivity.class);
                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                        startActivity(intent);
                    }
                });

            }

            @Override
            public void onFail(final String errorMessage) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        processDialog.dismiss();
                        alertDialog.setValues("Login Failed", errorMessage, AlertType.error);
                        alertDialog.show();
                    }
                });
            }
        });
    }

    private void disableSendButton(){
        loginBtn.setEnabled(false);
        loginBtn.setAlpha(0.65f);
     }
     private void enableSendButton(){
        loginBtn.setEnabled(true);
        loginBtn.setAlpha(1f);
    }

    private void saveData(User user, String authorization){
        SharedData.USER = user;
        Gson gson = new Gson();
        String userString = gson.toJson(user);
        SharedData.USER_TOKEN = authorization;
        SharedData.LAST_LOGIN_TIME = new Date();
        sharedPreferences.edit().putLong(Constants.LAST_LOGIN_TIME, SharedData.LAST_LOGIN_TIME.getTime()).apply();
        sharedPreferences.edit().putString(Constants.USER, userString).apply();
        sharedPreferences.edit().putString(Constants.USER_TOKEN, authorization).apply();
    }

    TextWatcher usernameTextWatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            String username = usernameView.getText().toString();
            String password = passwordView.getText().toString();
            if (username.isEmpty()){
                usernameLayout.setErrorEnabled(true);
                usernameLayout.setError("Please enter email");
                disableSendButton();
                return;
            }else {
                usernameLayout.setErrorEnabled(false);
                if (!password.isEmpty()){
                    enableSendButton();
                }
            }
        }

        @Override
        public void afterTextChanged(Editable s) {

        }
    };

    TextWatcher passwordTextWatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {

        }

        @Override
        public void afterTextChanged(Editable s) {
            String username = usernameView.getText().toString();
            String password = passwordView.getText().toString();

            if (password.isEmpty()){
                passwordLayout.setErrorEnabled(true);
                passwordLayout.setError("Please enter password");
                disableSendButton();
                return;
            }else {
                passwordLayout.setErrorEnabled(false);
                if (!username.isEmpty()){
                    enableSendButton();
                }
            }
        }
    };

    @Override
    protected void onDestroy() {
        super.onDestroy();
        alertDialog.dismiss();
        processDialog.dismiss();
    }
}
