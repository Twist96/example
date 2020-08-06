package com.gasco.Adapters;

import android.app.Activity;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.gasco.Models.DispenseLog;
import com.gasco.Models.DispenseLog2;
import com.gasco.R;
import com.gasco.UI.Activities.LogDetailsActivity;
import com.gasco.services.API;
import com.gasco.services.gescoServices.DispenseLogService;
import com.gasco.utils.HFunc;
import com.google.gson.Gson;

import java.util.Date;
import java.util.List;

public class CNGRecordsRecyclerAdapter extends RecyclerView.Adapter<CNGRecordsRecyclerAdapter.ViewHolder> implements View.OnClickListener {

    Activity activity;
    List<DispenseLog2> dispenseLog2s;
    Gson gson;

    public CNGRecordsRecyclerAdapter(Activity activity, List<DispenseLog2> dispenseLog2s){
        this.activity = activity;
        this.dispenseLog2s = dispenseLog2s;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        gson = new Gson();
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.c_n_g_records_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, int position) {
        DispenseLog2 dispenseLog2 = dispenseLog2s.get(position);
        Date dn = dispenseLog2.getLogDate();
        String d = HFunc.DateToString(dispenseLog2.getLogDate());
        holder.skidNumberView.setText(dispenseLog2.getSkid().getSkidNo());
        holder.volumeKgView.setText(String.format("volume(kg):  %s", dispenseLog2.getVolKG()));
        holder.volumeScmView.setText(String.format("volume(scm):  %s", dispenseLog2.getVolSCM()));
        holder.dateView.setText(String.format("date:  %s", HFunc.DateToString(dispenseLog2.getLogDate()))); //dispenseLog2.getLogDate()));//
        //String dispenseLogString = gson.toJson(dispenseLog);
        holder.itemView.setTag(dispenseLog2);
        holder.itemView.setOnClickListener(CNGRecordsRecyclerAdapter.this);
    }

    public void updateData(List<DispenseLog2> dispenseLog2s){
        this.dispenseLog2s.addAll(dispenseLog2s);
       notifyDataSetChanged();
    }

    public void insertData(DispenseLog2 dispenseLog2, int at){
        this.dispenseLog2s.add(at, dispenseLog2);
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return dispenseLog2s.size();
    }

    @Override
    public void onClick(View v) {
        DispenseLog2 dispenseLog = (DispenseLog2) v.getTag();
        Intent intent = new Intent(activity, LogDetailsActivity.class);
        Gson gson = new Gson();
        String value = gson.toJson(dispenseLog);
        intent.putExtra("data", value);
        activity.startActivityForResult(intent, 3);
    }

    public void setData(List<DispenseLog2> dispenseLog2s) {
        this.dispenseLog2s = dispenseLog2s;
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        private TextView skidNumberView, volumeKgView, volumeScmView, dateView;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            skidNumberView = itemView.findViewById(R.id.skid_number);
            volumeKgView = itemView.findViewById(R.id.volume_kg);
            volumeScmView = itemView.findViewById(R.id.volume_scm);
            dateView = itemView.findViewById(R.id.date);
        }

    }
}
