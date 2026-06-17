package com.mountreachsolution.e_learningapp.QuestionandAnswerModelPaper;

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
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.mountreachsolution.e_learningapp.Comman.Urls;
import com.mountreachsolution.e_learningapp.NoticeBoard.PojoNoticeBoard;
import com.mountreachsolution.e_learningapp.R;

import java.util.List;

public class QuestionandAnswerAdapter extends BaseAdapter {

    List<PojoQuestionandAnswerPaper> list;
    AppCompatActivity activity;
    TextView tv_no_records;
    SharedPreferences preferences;
    SharedPreferences.Editor editor;

    public QuestionandAnswerAdapter(List<PojoQuestionandAnswerPaper> list, AppCompatActivity activity, TextView tv_no_records) {
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
            v = inflater.inflate(R.layout.lv_question_and_answer_paper, null);
            holder.lv_tv_question_and_answer_paper_name = (TextView) v.findViewById(R.id.lv_tv_question_and_answer_paper_name);
            holder.lv_iv_question_and_answer_paper_doc = (ImageView) v.findViewById(R.id.lv_iv_question_and_answer_paper_doc);

            v.setTag(holder);
        }
        else
        {
            holder = (ViewHolder) v.getTag();
        }

        final PojoQuestionandAnswerPaper obj = list.get(position);
        holder.lv_tv_question_and_answer_paper_name.setText(obj.getTitle());

        holder.lv_iv_question_and_answer_paper_doc.setOnClickListener(new View.OnClickListener() {
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
        TextView lv_tv_question_and_answer_paper_name;
        ImageView lv_iv_question_and_answer_paper_doc;
    }
}
