package com.gasco.Adapters;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.gasco.Models.Menu;
import com.gasco.R;

import java.util.List;

public class MenuDashboardAdapter extends BaseAdapter implements View.OnClickListener {

    Menu[] menu;
    Context context;
    private Activity activity;

    public MenuDashboardAdapter(Activity activity, Menu[] menu) {
        this.menu = menu;
        this.context = activity.getApplicationContext();
        this.activity = activity;
    }

    @Override
    public int getCount() {
        return menu.length;
    }

    @Override
    public Object getItem(int position) {
        return menu[position];
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Menu menuItem = menu[position];

        if (convertView == null){
            final LayoutInflater layoutInflater = LayoutInflater.from(context);
            convertView = layoutInflater.inflate(R.layout.dashboard_menu_item, null);
            convertView.setTag(menuItem.getDestination());
        }

        ImageView imageView = convertView.findViewById(R.id.image);
        imageView.setImageResource(menuItem.getImage());
        TextView textView = convertView.findViewById(R.id.text);
        textView.setText(menuItem.getName());
        convertView.setOnClickListener(this);

        return convertView;
    }

    @Override
    public void onClick(View v) {
        Class<?> destination = (Class<?>) v.getTag();
        Intent intent = new Intent(context, destination);
        activity.startActivity(intent);
    }
}
