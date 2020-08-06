package com.gasco.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CalendarView;
import android.widget.DatePicker;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.gasco.R;
import com.google.android.material.datepicker.MaterialDatePicker;

import java.util.Calendar;

public class RosterRecyclerAdapter extends RecyclerView.Adapter<RosterRecyclerAdapter.ViewHolder> {

    Context context;

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        context = parent.getContext();
        View view = LayoutInflater.from(context).inflate(R.layout.calender_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        DatePicker calendarView = holder.calendarView;

        Calendar calendar1 = Calendar.getInstance();
        calendar1.set(2017, 1, 1, 0, 0, 0);

        Calendar calendar2 = Calendar.getInstance();
        calendar2.set(2017, 2, 0, 0, 0, 0);

        calendarView.setMinDate(calendar1.getTimeInMillis());
        calendarView.setMaxDate(calendar2.getTimeInMillis());
        //calendarView.
        
    }

    @Override
    public int getItemCount() {
        return 5;
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        DatePicker calendarView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            calendarView = itemView.findViewById(R.id.calender_view);
        }
    }
}
