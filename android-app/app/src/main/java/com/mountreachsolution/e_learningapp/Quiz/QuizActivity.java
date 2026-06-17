package com.mountreachsolution.e_learningapp.Quiz;

import androidx.appcompat.app.AppCompatActivity;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import com.mountreachsolution.e_learningapp.Comman.Urls;
import com.mountreachsolution.e_learningapp.HomeActivity.HomeActivity;
import com.mountreachsolution.e_learningapp.NoticeBoard.NoticeBoardActivity;
import com.mountreachsolution.e_learningapp.NoticeBoard.NoticeBoardAdapter;
import com.mountreachsolution.e_learningapp.NoticeBoard.PojoNoticeBoard;
import com.mountreachsolution.e_learningapp.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import cz.msebera.android.httpclient.Header;

public class QuizActivity extends AppCompatActivity {
//
//    List<PojoGetQuiz> list;
//    ListView lv_get_quiz;
//    TextView tv_no_records;
    ProgressBar pBar;
    Button btn_quiz_submit;
    RadioGroup rgquestion1,rgquestion2,rgquestion3,rgquestion4,rgquestion5;
    RadioButton rbquestion1,rbquestion2,rbquestion3,rbquestion4,rbquestion5;
//    QuizAdapter adapter;
    SharedPreferences preferences;
    SharedPreferences.Editor editor;

    ArrayList<String> question = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz);

        preferences= PreferenceManager.getDefaultSharedPreferences(QuizActivity.this);
        editor=preferences.edit();

        setTitle(""+preferences.getString("class_name","")+" Quiz");
        rgquestion1 = findViewById(R.id.lv_rg_quiz_11);
        rgquestion2 = findViewById(R.id.lv_rg_quiz_21);
        rgquestion3 = findViewById(R.id.lv_rg_quiz_31);
        rgquestion4 = findViewById(R.id.lv_rg_quiz_41);
        rgquestion5 = findViewById(R.id.lv_rg_quiz_51);
        btn_quiz_submit = findViewById(R.id.btn_quiz_submit);

        // on below line we are adding check change listener for our radio group.
        rgquestion1.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {

                // on below line we are getting radio button from our group.
                rbquestion1 = findViewById(checkedId);

            }
        });

        // on below line we are adding check change listener for our radio group.
        rgquestion2.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {

                // on below line we are getting radio button from our group.
                rbquestion2 = findViewById(checkedId);

            }
        });

        // on below line we are adding check change listener for our radio group.
        rgquestion3.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {

                // on below line we are getting radio button from our group.
                rbquestion3 = findViewById(checkedId);

            }
        });

        // on below line we are adding check change listener for our radio group.
        rgquestion4.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {

                // on below line we are getting radio button from our group.
                rbquestion4 = findViewById(checkedId);

            }
        });

        // on below line we are adding check change listener for our radio group.
        rgquestion5.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {

                // on below line we are getting radio button from our group.
                rbquestion5 = findViewById(checkedId);

            }
        });

        btn_quiz_submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(QuizActivity.this, "Question 1 Ans is c) Dennis Ritchie \nQuestion 2 Ans is d) int $main; \n Question 3 Ans is a) LowerCase letters \n Question 4 Ans is  a) volatile \n Question 5 Ans is b) 0 or 1", Toast.LENGTH_SHORT).show();
            }
        });
//
//        list = new ArrayList<PojoGetQuiz>();
//        lv_get_quiz = (ListView) findViewById(R.id.lv_quiz);
//        tv_no_records = (TextView) findViewById(R.id.tv_no_records);
//        pBar = (ProgressBar) findViewById(R.id.progressBar);
//        tv_no_records = (TextView) findViewById(R.id.tv_no_records);

//        getQuiz();
    }

//    private void getQuiz() {
//        AsyncHttpClient client = new AsyncHttpClient();
//        RequestParams params = new RequestParams();
//
//        client.post(Urls.urlGetQuiz, params, new JsonHttpResponseHandler(){
//
//            public void onStart()
//            {
//                pBar.setVisibility(View.VISIBLE);
//                super.onStart();
//            }
//
//            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
//                try {
//                    pBar.setVisibility(View.GONE);
//
//                    JSONArray jarry = response.getJSONArray("getQuiz");
//                    if (jarry.isNull(0))
//                    {
//                        tv_no_records.setVisibility(View.VISIBLE);
//                        tv_no_records.setText("No Quiz Available Yet");
//                    }
//                    for (int i = 0 ; i < jarry.length();i++)
//                    {
//                        JSONObject jsonObject = jarry.getJSONObject(i);
//                        String id = jsonObject.getString("id");
//                        String questions = jsonObject.getString("question");
//                        question.add(questions);
//                        String option1 = jsonObject.getString("option1");
//                        String option2 = jsonObject.getString("option2");
//                        String option3 = jsonObject.getString("option3");
//                        String option4 = jsonObject.getString("option4");
//                        String correct_answer = jsonObject.getString("correct_answer");
//
//                        list.add(new PojoGetQuiz(id,questions,option1,option2,option3,option4,correct_answer
//                                ));
//                    }
//                    adapter = new QuizAdapter(list, (AppCompatActivity) QuizActivity.this,question);
//                    lv_get_quiz.setAdapter(adapter);
//                } catch (JSONException e) {
//                    e.printStackTrace();
//                }
//            }
//
//            public void onFailure(int statusCode, Header[] headers, String res, Throwable t)
//            {
//                Toast.makeText(QuizActivity.this, "could not connect", Toast.LENGTH_LONG).show();
//
//            }
//        });
//    }
}