package com.mountreachsolution.e_learningapp.Recording;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.preference.PreferenceManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.mountreachsolution.e_learningapp.HomeWork.POJOHomework;
import com.mountreachsolution.e_learningapp.R;

import java.util.List;

public class RecordingAdapter extends BaseAdapter {

    List<POJORecording> list;
    AppCompatActivity activity;
    TextView tv_no_records;
    SharedPreferences preferences;
    SharedPreferences.Editor editor;

    public RecordingAdapter(List<POJORecording> list, AppCompatActivity activity) {
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
            v = inflater.inflate(R.layout.lv_recording, null);


            holder.recording_date = (TextView) v.findViewById(R.id.tv_recording_date);
            holder.recording_title = (TextView) v.findViewById(R.id.tv_recording_title);
            holder.recording_description = (TextView) v.findViewById(R.id.tv_recording_description);
            holder.recording_url = (TextView) v.findViewById(R.id.tv_recording_url);

            holder.recording_url.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.youtube.com/watch?v=bzSTpdcs-EI"));
                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    intent.setPackage("com.google.android.youtube");
                    activity.startActivity(intent);
                }
            });

            v.setTag(holder);
        }
        else
        {
            holder = (ViewHolder) v.getTag();
        }

        final POJORecording obj = list.get(position);
        holder.recording_date.setText(obj.getRecording_date());
        holder.recording_title.setText(obj.getRecording_title());
        holder.recording_description.setText(obj.getRecording_description());
        holder.recording_url.setText(obj.getRecording_url());

        return v;
    }

    class ViewHolder
    {
        TextView recording_date,recording_title,recording_description,recording_url;
    }
}
