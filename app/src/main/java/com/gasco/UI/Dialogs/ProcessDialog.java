package com.gasco.UI.Dialogs;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.gasco.R;

public class ProcessDialog extends Dialog {

    private String message = "";

    public ProcessDialog(@NonNull Context context) {
        super(context, android.R.style.Theme_Translucent_NoTitleBar_Fullscreen);
    }

    public ProcessDialog(@NonNull Context context, int themeResId) {
        super(context, themeResId);
    }

    protected ProcessDialog(@NonNull Context context, boolean cancelable, @Nullable OnCancelListener cancelListener) {
        super(context, cancelable, cancelListener);
    }

    public void setMessage(String message){
        this.message = message;
    }

    ProgressBar progressBar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.dialog_process);

        TextView messageView = findViewById(R.id.message);
        ProgressBar progressBar = findViewById(R.id.progress_bar);
        messageView.setText(message.isEmpty() ? "" : message);
        progressBar.animate();
    }

    @Override
    public void onDetachedFromWindow() {
        super.onDetachedFromWindow();
    }
}
