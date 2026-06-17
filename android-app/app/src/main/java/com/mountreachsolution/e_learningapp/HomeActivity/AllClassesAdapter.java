package com.mountreachsolution.e_learningapp.HomeActivity;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;

import androidx.cardview.widget.CardView;

import com.mountreachsolution.e_learningapp.R;

import java.util.List;

public class AllClassesAdapter extends BaseAdapter {

    List<POJOAllClasses> list;
    Activity activity;
    TextView tv_no_records;
    SharedPreferences preferences;
    SharedPreferences.Editor editor;

    public AllClassesAdapter(List<POJOAllClasses> list, Activity activity) {
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

        final AllClassesAdapter.ViewHolder holder;
        LayoutInflater inflater = (LayoutInflater) activity.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);

        if (v == null) {
            holder = new AllClassesAdapter.ViewHolder();
            v = inflater.inflate(R.layout.lv_all_class, null);

            holder.tv_all_class_class_name = (TextView) v.findViewById(R.id.tv_all_class_class_name);
            holder.tv_all_class_class_subject = (TextView) v.findViewById(R.id.tv_all_class_class_subject);
            holder.cardView_all_classes = (CardView) v.findViewById(R.id.cardview11);

            v.setTag(holder);
        } else {
            holder = (AllClassesAdapter.ViewHolder) v.getTag();
        }

        final POJOAllClasses obj = list.get(position);
        holder.tv_all_class_class_name.setText(obj.getClassname());
        holder.tv_all_class_class_subject.setText(obj.getSubjectname());

        holder.cardView_all_classes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(activity, ClassInDetailsActivity.class);
                editor.putString("class_id", obj.getId()).commit();
                editor.putString("class_name", obj.getClassname()).commit();
                editor.putString("subject_name", obj.getSubjectname()).commit();
                activity.startActivity(intent);
            }
        });

        return v;
    }
    class ViewHolder {
        TextView tv_all_class_class_name, tv_all_class_class_subject;
        CardView cardView_all_classes;
    }
}