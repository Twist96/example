package com.gasco.Adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.gasco.Models.DispenseLog;
import com.gasco.R;
import com.gasco.UI.Activities.LogDetailsActivity;

import java.util.Map;

public class DispenseLogDetailRecyclerViewAdapter extends RecyclerView.Adapter<DispenseLogDetailRecyclerViewAdapter.ViewHolder> {

    LogDetailsActivity.DispenseLogProperty[] dispenseLogProperties;

    public DispenseLogDetailRecyclerViewAdapter(LogDetailsActivity.DispenseLogProperty[] dispenseLogProperties) {
        this.dispenseLogProperties = dispenseLogProperties;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.dispense_log_detail_item, parent, false);
        return new DispenseLogDetailRecyclerViewAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        if (isEven(position)){
            holder.bg.setBackgroundResource(R.color.lightBlueBg);
        }else {
            holder.bg.setBackgroundResource(R.color.thinBlueBg);
        }
        holder.title.setText(dispenseLogProperties[position].getKey());
        holder.value.setText(dispenseLogProperties[position].getValue());
    }

    @Override
    public int getItemCount() {
        return dispenseLogProperties.length;
    }

    class ViewHolder extends RecyclerView.ViewHolder{
        LinearLayout bg;
        TextView title, value;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            bg = itemView.findViewById(R.id.parent);
            title = itemView.findViewById(R.id.title);
            value = itemView.findViewById(R.id.value);
        }
    }

    private boolean isEven(int num){
        return num % 2 == 0;
    }
}
