package com.gasco.UI.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;
import android.widget.TextView;

import com.gasco.Adapters.MenuDashboardAdapter;
import com.gasco.Models.Menu;
import com.gasco.R;
import com.gasco.utils.SharedData;

import java.util.PriorityQueue;

public class DashboardActivity extends BaseActivity {

    private String welcomeNote = "Welcome, %s";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_dashboard);

        initMenuView();
        TextView welcomeNoteView = findViewById(R.id.welcome_note);
        welcomeNoteView.setText(String.format(welcomeNote, SharedData.USER.getFirstName()));
    }

    private void initMenuView(){
        GridView gridView = findViewById(R.id.gridView);
        MenuDashboardAdapter adapter = new MenuDashboardAdapter(this, Menu.getDashboardMenu());
        ViewGroup.LayoutParams layoutParams = gridView.getLayoutParams();
        gridView.setAdapter(adapter);
        gridView.setLayoutParams(layoutParams);
    }

    private void bindSessionService(){

    }
}
