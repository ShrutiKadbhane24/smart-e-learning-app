package com.mountreachsolution.e_learningapp.NoticeBoard;

import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.preference.PreferenceManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.mountreachsolution.e_learningapp.Comman.Urls;
import com.mountreachsolution.e_learningapp.R;

import java.util.List;

public class NoticeBoardAdapter extends BaseAdapter {

    List<PojoNoticeBoard> list;
    AppCompatActivity activity;
    TextView tv_no_records;
    SharedPreferences preferences;
    SharedPreferences.Editor editor;

    public NoticeBoardAdapter(List<PojoNoticeBoard> list, AppCompatActivity activity, TextView tv_no_records) {
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
            v = inflater.inflate(R.layout.lv_notice_board, null);

            holder.notice_title = (TextView) v.findViewById(R.id.lv_tv_notice_board_notice_title);
            holder.notice_description = (TextView)v.findViewById(R.id.lv_tv_notice_board_notice_description);
            holder.lv_tv_notice_board_notice_pdf = (TextView)v.findViewById(R.id.lv_tv_notice_board_notice_pdf);

            v.setTag(holder);
        }
        else
        {
            holder = (ViewHolder) v.getTag();
        }

        final PojoNoticeBoard obj = list.get(position);
        holder.notice_title.setText(obj.getTitle());
        holder.notice_description.setText(obj.getDescription());

        holder.lv_tv_notice_board_notice_pdf.setOnClickListener(new View.OnClickListener() {
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
        TextView notice_title,notice_description,lv_tv_notice_board_notice_pdf;
    }
}
