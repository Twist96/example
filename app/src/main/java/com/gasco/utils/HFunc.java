package com.gasco.utils;

import android.content.ClipData;
import android.util.Log;
import android.widget.Toast;

import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.stream.Collector;
import java.util.stream.Collectors;

public class HFunc {

    public static String TAG = "HFunc";

    public static Date StringToDate(String date){
        Date demoDate = new Date();
        try {
            demoDate  = new SimpleDateFormat("yyyy-MM-dd").parse(date);
        }catch (Exception e){
            Log.d(TAG, e.getLocalizedMessage());
        }
        return demoDate;
    }

    public static Date longToDate(long date){
        return new Date(date);
    }

    public static String DateToString(Date date){
        SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy");
        return formatter.format(date);
    }



}
