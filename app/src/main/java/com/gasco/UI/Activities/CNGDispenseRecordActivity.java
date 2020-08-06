package com.gasco.UI.Activities;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.gasco.Adapters.CNGRecordsRecyclerAdapter;
import com.gasco.Enums.AlertType;
import com.gasco.Models.DispenseLog;
import com.gasco.Models.DispenseLog2;
import com.gasco.Models.Skid;
import com.gasco.R;
import com.gasco.UI.Dialogs.PopUpAlertDialog;
import com.gasco.UI.Dialogs.ProcessDialog;
import com.gasco.services.API;
import com.gasco.services.gescoServices.DispenseLogService;
import com.google.gson.Gson;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class CNGDispenseRecordActivity extends AppCompatActivity {

    private CNGRecordsRecyclerAdapter adapter;
    private LinearLayoutManager layoutManager;
    private ProcessDialog processDialog;
    private PopUpAlertDialog alertDialog;
    int page = 0;
    private boolean refreshTable;
    Gson gson;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_c_g_n_dispens_record);
        processDialog = new ProcessDialog(this);
        processDialog.setMessage("Fetching logs . .");
        processDialog.show();
        alertDialog = new PopUpAlertDialog(this);
        gson = new Gson();

        initRecyclerView();
    }

    private void initRecyclerView(){
        RecyclerView recyclerView = findViewById(R.id.recycler_view);
        adapter = new CNGRecordsRecyclerAdapter(this, new ArrayList<DispenseLog2>());
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.addOnScrollListener(onScrollListener);
        updateDispenseLog(page);
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (refreshTable){
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    updateDispenseLog(page);
                    refreshTable = false;
                }
            });
        }
    }

    private void updateDispenseLog(int pageNumber){
        // Fix date
        Date demoDate = new Date();

        API.DISPENSE_LOG_SERVICE.GetDispenseLogs(10, pageNumber, demoDate,
                false, false, new DispenseLogService.LogsCallBack() {
            @Override
            public void onSuccess(final List<DispenseLog2> dispenseLog2s) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        processDialog.dismiss();
                        if (dispenseLog2s.isEmpty()){
                            return;
                        }
                        if (page == 0){
                            adapter.setData(dispenseLog2s);
                        }else{
                            adapter.updateData(dispenseLog2s);
                        }
                        page++;
                    }
                });
            }

            @Override
            public void onFail(final String errorMessage) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        processDialog.dismiss();
                        alertDialog.setValues("Network Error", errorMessage, AlertType.error);
                        alertDialog.show();

                    }
                });
            }
        });
    }

    public void AddDispenseLog(View view) {
        Intent intent = new Intent(this, CNGProcedureActivity.class);
        startActivityForResult(intent, 3);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 03){
            assert data != null;
            refreshTable = data.getBooleanExtra("refreshTable", false);
            if (refreshTable){
                page = 0;
            }
        }
    }

    RecyclerView.OnScrollListener onScrollListener = new RecyclerView.OnScrollListener() {
        @Override
        public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
            super.onScrollStateChanged(recyclerView, newState);

            if (!recyclerView.canScrollVertically(1) && newState==RecyclerView.SCROLL_STATE_IDLE) {
                //the bellow line was deleted because of no documentation
                //updateDispenseLog(page);
            }
        }
    };

    public void DismissActivity(View view) {
        finish();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        processDialog.dismiss();
        alertDialog.dismiss();
    }
}