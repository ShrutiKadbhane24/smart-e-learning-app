package com.mountreachsolution.e_learningapp.HomeWork;

import android.app.Activity;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.mountreachsolution.e_learningapp.R;

import java.util.List;

public class HomeworkAdapter extends BaseAdapter {

    List<POJOHomework> list;
    AppCompatActivity activity;
    TextView tv_no_records;
    SharedPreferences preferences;
    SharedPreferences.Editor editor;

    public HomeworkAdapter(List<POJOHomework> list, AppCompatActivity activity) {
        this.list = list;
        this.activity = activity;

        preferences = PreferenceManager.getDefaultSharedPreferences(activity);
        editor = preferences.edit();


    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View v, ViewGroup parent) {

        final ViewHolder holder;
        LayoutInflater inflater = (LayoutInflater) activity.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);

        if (v == null)
        {

            holder = new ViewHolder();
            v = inflater.inflate(R.layout.lv_homework, null);


            holder.homework_date = (TextView) v.findViewById(R.id.tv_homework_date);
            holder.homework_title = (TextView) v.findViewById(R.id.tv_homework_title);
            holder.homework_description = (TextView) v.findViewById(R.id.tv_homework_description);


            v.setTag(holder);
        }
        else
        {
            holder = (ViewHolder) v.getTag();
        }

        final POJOHomework obj = list.get(position);
        holder.homework_date.setText(obj.getHowework_date());
        holder.homework_title.setText(obj.getHomework_title());
        holder.homework_description.setText(obj.getHomework_description());


        return v;
    }

    class ViewHolder
    {
        TextView homework_date,homework_title,homework_description;
    }
}
