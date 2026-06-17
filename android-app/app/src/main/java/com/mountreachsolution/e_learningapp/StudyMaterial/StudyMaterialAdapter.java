package com.mountreachsolution.e_learningapp.StudyMaterial;

import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.content.SharedPreferences;
import android.media.Image;
import android.net.Uri;
import android.preference.PreferenceManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.mountreachsolution.e_learningapp.Comman.Urls;
import com.mountreachsolution.e_learningapp.R;

import java.util.List;

public class StudyMaterialAdapter extends BaseAdapter {

    List<PojoMyStudyMaterial> list;
    AppCompatActivity activity;
    TextView tv_no_records;
    SharedPreferences preferences;
    SharedPreferences.Editor editor;

    public StudyMaterialAdapter(List<PojoMyStudyMaterial> list, AppCompatActivity activity, TextView tv_no_records) {
        this.list = list;
        this.activity = activity;
        this.tv_no_records = tv_no_records;

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
            v = inflater.inflate(R.layout.lv_study_material, null);

            holder.study_material_title = (TextView) v.findViewById(R.id.lv_tv_study_material_title);
            holder.study_material_description = (TextView)v.findViewById(R.id.lv_tv_study_material_description);
            holder.iv_study_material_pdf = (ImageView) v.findViewById(R.id.iv_study_material_pdf);
            holder.study_material_date = (TextView)v.findViewById(R.id.lv_tv_study_material_date);

            v.setTag(holder);
        }
        else
        {
            holder = (ViewHolder) v.getTag();
        }

        final PojoMyStudyMaterial obj = list.get(position);
        holder.study_material_title.setText(obj.getTitle());
        holder.study_material_description.setText(obj.getDescription());
        holder.study_material_date.setText(obj.getDate());

        holder.iv_study_material_pdf.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String doc1= Urls.OnlineDocAddress+obj.getViewdoc();

                Intent intent=new Intent(Intent.ACTION_VIEW);
                intent.setDataAndType(Uri.parse(doc1), "application/pdf");
                // intent.setDataAndType(uri, "application/pdf");
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

                try
                {
                    activity.startActivity(intent);
                }
                catch(ActivityNotFoundException e)
                {
                    Toast.makeText(activity, "No Application available to view document", Toast.LENGTH_LONG).show();
                }


            }
//                Intent intent = new Intent(ViewGovtScemeDetailsActivity.this, ViewDocumentActivity.class);
//                intent.putExtra("file",getIntent().getStringExtra("name"));
//                startActivity(intent);
        });

        return v;
    }

    class ViewHolder
    {
        TextView study_material_title,study_material_description,study_material_date;
        ImageView iv_study_material_pdf;
    }
}
