package com.gasco.UI.Activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;

import com.gasco.Adapters.RosterRecyclerAdapter;
import com.gasco.R;

public class WorkRosterActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_work_roster);

        RecyclerView recyclerView = findViewById(R.id.recycler_view);
        RosterRecyclerAdapter adapter = new RosterRecyclerAdapter();
        recyclerView.setAdapter(adapter);
        GridLayoutManager linearLayoutManager = new GridLayoutManager(getApplicationContext(), 1);
        recyclerView.setLayoutManager(linearLayoutManager);
    }

    public void DismissActivity(View view) {
        finish();
    }
}
