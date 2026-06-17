package com.mountreachsolution.e_learningapp.HomeActivity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;

import com.mountreachsolution.e_learningapp.HomeWork.HomeWorkActivity;
import com.mountreachsolution.e_learningapp.NoticeBoard.NoticeBoardActivity;
import com.mountreachsolution.e_learningapp.QuestionandAnswerModelPaper.QuestionandAnswerModelPaperActivity;
import com.mountreachsolution.e_learningapp.Quiz.QuizActivity;
import com.mountreachsolution.e_learningapp.R;
import com.mountreachsolution.e_learningapp.Recording.RecordingActivity;
import com.mountreachsolution.e_learningapp.StudyMaterial.StudyMaterialActivity;

public class ClassInDetailsActivity extends AppCompatActivity {


    CardView cardView11, cardView22, cardView33, cardView44, cardView55, cardView66;
    SharedPreferences preferences;
    SharedPreferences.Editor editor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_class_in_details);

        preferences= PreferenceManager.getDefaultSharedPreferences(ClassInDetailsActivity.this);
        editor=preferences.edit();

        setTitle(""+preferences.getString("class_name",""));

        cardView11 = findViewById(R.id.cardview11);
        cardView22 = findViewById(R.id.cardview22);
        cardView33 = findViewById(R.id.cardview33);
        cardView44 = findViewById(R.id.cardview44);
        cardView55 = findViewById(R.id.cardview55);
        cardView66 = findViewById(R.id.cardview66);

        cardView11.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ClassInDetailsActivity.this, HomeWorkActivity.class);
                editor.putString("class_id",preferences.getString("class_id","")).commit();
                editor.putString("class_name",preferences.getString("class_name","")).commit();
                editor.putString("subject_name",preferences.getString("subject_name","")).commit();
                startActivity(intent);
            }
        });

        cardView22.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ClassInDetailsActivity.this, StudyMaterialActivity.class);
                editor.putString("class_id",preferences.getString("class_id","")).commit();
                editor.putString("class_name",preferences.getString("class_name","")).commit();
                editor.putString("subject_name",preferences.getString("subject_name","")).commit();
                startActivity(intent);
            }
        });

        cardView33.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ClassInDetailsActivity.this, RecordingActivity.class);
                editor.putString("class_id",preferences.getString("class_id","")).commit();
                editor.putString("class_name",preferences.getString("class_name","")).commit();
                editor.putString("subject_name",preferences.getString("subject_name","")).commit();
                startActivity(intent);
            }
        });

        cardView44.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ClassInDetailsActivity.this, NoticeBoardActivity.class);
                editor.putString("class_id",preferences.getString("class_id","")).commit();
                editor.putString("class_name",preferences.getString("class_name","")).commit();
                editor.putString("subject_name",preferences.getString("subject_name","")).commit();
                startActivity(intent);
            }
        });

        cardView55.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ClassInDetailsActivity.this, QuestionandAnswerModelPaperActivity.class);
                editor.putString("class_id",preferences.getString("class_id","")).commit();
                editor.putString("class_name",preferences.getString("class_name","")).commit();
                editor.putString("subject_name",preferences.getString("subject_name","")).commit();
                startActivity(intent);
            }
        });

        cardView66.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ClassInDetailsActivity.this, QuizActivity.class);
                editor.putString("class_id",preferences.getString("class_id","")).commit();
                editor.putString("class_name",preferences.getString("class_name","")).commit();
                editor.putString("subject_name",preferences.getString("subject_name","")).commit();
                startActivity(intent);
            }
        });
    }
}