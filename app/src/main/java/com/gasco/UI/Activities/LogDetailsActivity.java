package com.gasco.UI.Activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.gasco.Adapters.DispenseLogDetailRecyclerViewAdapter;
import com.gasco.Enums.AlertType;
import com.gasco.Models.DispenseLog;
import com.gasco.Models.DispenseLog2;
import com.gasco.R;
import com.gasco.UI.Dialogs.PopUpAlertDialog;
import com.gasco.UI.Dialogs.ProcessDialog;
import com.gasco.services.API;
import com.gasco.services.gescoServices.DispenseLogService;
import com.gasco.utils.HFunc;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class LogDetailsActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    ProcessDialog processDialog;
    PopUpAlertDialog popUpAlertDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log_details);

        recyclerView = findViewById(R.id.recycler_view);
        popUpAlertDialog = new PopUpAlertDialog(this);
        processDialog = new ProcessDialog(this);
        processDialog.setMessage("Fetching details");
        processDialog.show();
        String dispenseString = getIntent().getStringExtra("data");
        Gson gson = new Gson();
        DispenseLog2 dispenseLog = gson.fromJson(dispenseString, DispenseLog2.class);

        //todo: populate dispense log

        API.DISPENSE_LOG_SERVICE.GetDispenseLog(dispenseLog.getId(), new DispenseLogService.LogCallBack() {
            @Override
            public void onSuccess(final DispenseLog dispenseLog) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        mSuccess(dispenseLog);
                        RecyclerView recyclerView = findViewById(R.id.recycler_view);
                        DispenseLogDetailRecyclerViewAdapter adapter = new DispenseLogDetailRecyclerViewAdapter(changeLogDetailToMap2(dispenseLog));
                        LinearLayoutManager layoutManager = new LinearLayoutManager(LogDetailsActivity.this);
                        recyclerView.setAdapter(adapter);
                        recyclerView.setLayoutManager(layoutManager);
                        processDialog.dismiss();
                    }
                });
            }

            @Override
            public void onFail(String errorMessage) {
                mFail(errorMessage);
            }
        });

        Intent intent = new Intent();
        intent.putExtra("refreshTable", true);
        setResult(3, intent);
    }

    public void mSuccess(final DispenseLog dispenseLog){
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
//                processDialog.dismiss();
//                DispenseLogDetailRecyclerViewAdapter adapter = new DispenseLogDetailRecyclerViewAdapter(dispenseLog);
//                recyclerView.setAdapter(adapter);
//                recyclerView.setLayoutManager(new LinearLayoutManager(LogDetailsActivity.this));
            }
        });
    }

    public void mFail(final String errorMessage){
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                processDialog.dismiss();
                popUpAlertDialog.setValues("Error", errorMessage, AlertType.error);

            }
        });
    }

    public void DismissActivity(View view) {
        finish();

    }

    public class DispenseLogProperty{
        private String key;
        private String value;

        public DispenseLogProperty(String key, String value) {
            this.key = key;
            this.value = value;
        }

        public String getKey() {
            return key;
        }

        public void setKey(String key) {
            this.key = key;
        }

        public String getValue() {
            return value;
        }

        public void setValue(String value) {
            this.value = value;
        }
    }

    public DispenseLogProperty[] changeLogDetailToMap(final DispenseLog dispenseLog){

//        final DispenseLogProperty[] data = {
//                new DispenseLogProperty("Data of Entry", "HFunc.DateToString(dispenseLog.getDispenseDate())"),
//                new DispenseLogProperty("Skid Number", dispenseLog.getSkidNo()),
//                new DispenseLogProperty("Initial Dispenser Reading", dispenseLog.getInitialDspReading()),
//                new DispenseLogProperty("Final Dispenser Reading", dispenseLog.getFinDspReading()),
//                new DispenseLogProperty("Volume (Kg)", dispenseLog.getVolKG()),
//                new DispenseLogProperty("Volume (scm)", dispenseLog.getVolSCM()),
//                new DispenseLogProperty("StartTime", dispenseLog.getStartTime()),
//                new DispenseLogProperty("Stop Time", dispenseLog.getStopTime()),
//                new DispenseLogProperty("Total time", dispenseLog.getTotalTime()),
//                new DispenseLogProperty("No. of Cylinder", "null"),
//                new DispenseLogProperty("Gas Inlet Pressure", dispenseLog.getGasInletPressure()),
//                new DispenseLogProperty("Dispenser Number", "null"),
//                new DispenseLogProperty("Pressure Bar", dispenseLog.getPressure()),
//                new DispenseLogProperty("Residual Pressure", dispenseLog.getResidualPressure()),
//                new DispenseLogProperty("Temperature (°C)", dispenseLog.getTemperature()),
//                new DispenseLogProperty("Compressor Used", "null"),
//                new DispenseLogProperty("Destination", "null")
//            };
        DispenseLogProperty[] i = {};
        return i;
    }

    public DispenseLogProperty[] changeLogDetailToMap2(final DispenseLog dispenseLog){

        final DispenseLogProperty[] data = {
                new DispenseLogProperty("Data of Entry", HFunc.DateToString(dispenseLog.getLogDate())),
                new DispenseLogProperty("Skid Number", dispenseLog.getSkid().getSkidNo()),
                new DispenseLogProperty("Initial Dispenser Reading", Float.toString(dispenseLog.getInitialDispenserReading())),
                new DispenseLogProperty("Final Dispenser Reading", Float.toString(dispenseLog.getFinalDispenserReading())),
                new DispenseLogProperty("Volume (Kg)", Float.toString(dispenseLog.getVolKG())),
                new DispenseLogProperty("Volume (scm)", Float.toString(dispenseLog.getVolSCM())),
                new DispenseLogProperty("StartTime", dispenseLog.getStartTime()),
                new DispenseLogProperty("Stop Time", dispenseLog.getStopTime()),
                new DispenseLogProperty("Total time", Float.toString(dispenseLog.getTotalTime())),
                new DispenseLogProperty("No. of Cylinder", Float.toString(dispenseLog.getSkid().getCylinders())),
                //new DispenseLogProperty("Gas Inlet Pressure", ),
                //new DispenseLogProperty("Dispenser Number", "null"),
                //new DispenseLogProperty("Pressure Bar", dispenseLog.getPressure()),
                new DispenseLogProperty("Residual Pressure", Float.toString(dispenseLog.getResidualPressure())),
                new DispenseLogProperty("Final Pressure", Float.toString(dispenseLog.getFinalPressure())),
                new DispenseLogProperty("Temperature (°C)", Float.toString(dispenseLog.getTemperature())),
                //new DispenseLogProperty("Compressor Used", "null"),
                //new DispenseLogProperty("Destination", "null"),
                new DispenseLogProperty("Created by", dispenseLog.getCreatedByUser())
            };
        return data;
    }

}
