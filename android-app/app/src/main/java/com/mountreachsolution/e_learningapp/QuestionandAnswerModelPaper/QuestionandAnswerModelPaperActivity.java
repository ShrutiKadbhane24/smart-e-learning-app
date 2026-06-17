package com.mountreachsolution.e_learningapp.QuestionandAnswerModelPaper;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import com.mountreachsolution.e_learningapp.Comman.Urls;
import com.mountreachsolution.e_learningapp.HomeActivity.HomeActivity;
import com.mountreachsolution.e_learningapp.LoginActivity;
import com.mountreachsolution.e_learningapp.NoticeBoard.NoticeBoardActivity;
import com.mountreachsolution.e_learningapp.NoticeBoard.NoticeBoardAdapter;
import com.mountreachsolution.e_learningapp.NoticeBoard.PojoNoticeBoard;
import com.mountreachsolution.e_learningapp.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import cz.msebera.android.httpclient.Header;

public class QuestionandAnswerModelPaperActivity extends AppCompatActivity {

    Spinner spinner_branch, spinner_semester, spinner_exam;
    AppCompatButton btn_search_question_and_answer_paper;
    SharedPreferences preferences;
    SharedPreferences.Editor editor;

    ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_questionand_answer_model_paper);

        preferences = PreferenceManager.getDefaultSharedPreferences(QuestionandAnswerModelPaperActivity.this);
        editor = preferences.edit();

        setTitle("" + preferences.getString("class_name", "") + " Q and A Paper");

        spinner_branch = findViewById(R.id.spinner_branch);
        spinner_semester = findViewById(R.id.spinner_semester);
        spinner_exam = findViewById(R.id.spinner_exam);
        btn_search_question_and_answer_paper = findViewById(R.id.btn_question_and_answer_paper_search);

        btn_search_question_and_answer_paper.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (spinner_branch.getSelectedItem().toString().equals("Select Your Branch")) {
                    ((TextView) spinner_branch.getSelectedView()).setError("Please Select Your Branch");
                } else if (spinner_semester.getSelectedItem().toString().equals("Select Your Sem")) {
                    ((TextView) spinner_branch.getSelectedView()).setError("Please Select Your Sem");
                } else if (spinner_exam.getSelectedItem().toString().equals("Select Your Exam")) {
                    ((TextView) spinner_exam.getSelectedView()).setError("Please Select Your Exam");
                } else {
                    Intent intent = new Intent(QuestionandAnswerModelPaperActivity.this, SearchQuestionandAnswerPaperActivity.class);
                    editor.putString("branch",spinner_branch.getSelectedItem().toString()).commit();
                    editor.putString("semester",spinner_semester.getSelectedItem().toString()).commit();
                    editor.putString("exam",spinner_exam.getSelectedItem().toString()).commit();
                    startActivity(intent);
                }

            }
        });
    }
}