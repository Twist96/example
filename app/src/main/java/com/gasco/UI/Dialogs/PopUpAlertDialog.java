package com.gasco.UI.Dialogs;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.gasco.Enums.AlertType;
import com.gasco.R;
import com.google.android.material.button.MaterialButton;

public class PopUpAlertDialog extends Dialog {

    private String title, message;
    private AlertType alertType;
    private int waitTime = 10000;

    public PopUpAlertDialog(@NonNull Context context) {
        super(context, android.R.style.Theme_Translucent_NoTitleBar_Fullscreen);
    }

    public PopUpAlertDialog(@NonNull Context context, int themeResId) {
        super(context, themeResId);
    }

    protected PopUpAlertDialog(@NonNull Context context, boolean cancelable, @Nullable OnCancelListener cancelListener) {
        super(context, cancelable, cancelListener);
    }

    public void setValues(String title, String message, AlertType alertType){
        this.title = title;
        this.message = message;
        this.alertType = alertType;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.dialog_pop_up_alert);

        TextView titleView = findViewById(R.id.title);
        TextView messageView = findViewById(R.id.message);
        ImageView imageView = findViewById(R.id.img);
        MaterialButton backBtn = findViewById(R.id.back_btn);

        titleView.setText(title);
        messageView.setText(message);
        switch (alertType){

            case success:
                titleView.setTextColor(Color.parseColor("#111111"));
                imageView.setImageResource(R.drawable.success_bubble);
                break;
            case warning:
                titleView.setTextColor(Color.parseColor("#FFC850"));
                imageView.setImageResource(R.drawable.success_bubble);
                break;
            case error:
                titleView.setTextColor(Color.parseColor("#F14336"));
                imageView.setImageResource(R.drawable.success_bubble);
                break;
        }
        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                dismiss();
            }
        }, waitTime);
    }

}
