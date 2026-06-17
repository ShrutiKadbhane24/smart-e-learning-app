package com.mountreachsolution.e_learningapp.QuestionandAnswerModelPaper;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;

import android.app.ProgressDialog;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import com.mountreachsolution.e_learningapp.Comman.Urls;
import com.mountreachsolution.e_learningapp.HomeWork.HomeWorkActivity;
import com.mountreachsolution.e_learningapp.HomeWork.HomeworkAdapter;
import com.mountreachsolution.e_learningapp.HomeWork.POJOHomework;
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

public class SearchQuestionandAnswerPaperActivity extends AppCompatActivity {

    List<PojoQuestionandAnswerPaper> list;
    ListView lv_question_and_answer_paper;
    SearchView sv_search_question_and_answer_paper;
    TextView tv_no_records;
    QuestionandAnswerAdapter adapter;
    SharedPreferences preferences;
    SharedPreferences.Editor editor;
    ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_questionand_answer_paper);

        preferences = PreferenceManager.getDefaultSharedPreferences(SearchQuestionandAnswerPaperActivity.this);
        editor = preferences.edit();

        setTitle("Question and Answer Paper");

        list = new ArrayList<PojoQuestionandAnswerPaper>();
        lv_question_and_answer_paper = (ListView) findViewById(R.id.lv_question_and_answer_paper);
        sv_search_question_and_answer_paper = (SearchView) findViewById(R.id.sv_search_question_and_answer_paper);
        tv_no_records = (TextView) findViewById(R.id.tv_no_records);
        lv_question_and_answer_paper = (ListView) findViewById(R.id.lv_question_and_answer_paper);
        tv_no_records = (TextView) findViewById(R.id.tv_no_records);

        progressDialog = new ProgressDialog(SearchQuestionandAnswerPaperActivity.this);
        progressDialog.setTitle("Searching Question and Answer Paper");
        progressDialog.setCancelable(true);
        progressDialog.setCanceledOnTouchOutside(true);
        progressDialog.setMessage("Please Wait...");
        progressDialog.show();

        sv_search_question_and_answer_paper.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                searchPaper(query);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String query) {
                searchPaper(query);
                return false;
            }
        });

        searchQuestionandAnswerPaper();
    }

    private void searchPaper(String query) {
        List<PojoQuestionandAnswerPaper> temppojoclass = new ArrayList<>();
        temppojoclass.clear();

        for(PojoQuestionandAnswerPaper pojo :list)
        {
            if (pojo.getTitle().toUpperCase().contains(query.toUpperCase()) ||
                    pojo.getViewdoc().toUpperCase().contains(query.toUpperCase()))
            {
                temppojoclass.add(pojo);
            }

            adapter = new QuestionandAnswerAdapter(temppojoclass, SearchQuestionandAnswerPaperActivity.this,tv_no_records);
            lv_question_and_answer_paper.setAdapter(adapter);

        }
    }


    public void searchQuestionandAnswerPaper()
    {

        AsyncHttpClient client = new AsyncHttpClient();
        RequestParams params = new RequestParams();

        params.put("branch",preferences.getString("branch",""));
        params.put("semester",preferences.getString("semester",""));
        params.put("exam",preferences.getString("exam",""));
        client.post(Urls.urlViewQuestionandAnswerPaper, params, new JsonHttpResponseHandler(){

            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                try {
                    progressDialog.dismiss();

                    JSONArray jarry = response.getJSONArray("getQuestionandAnswerPaper");
                    if (jarry.isNull(0))
                    {
                        tv_no_records.setVisibility(View.VISIBLE);
                        tv_no_records.setText("No Question and Answer Paper Available Yet");
                    }
                    for (int i = 0 ; i < jarry.length();i++)
                    {
                        JSONObject jsonObject = jarry.getJSONObject(i);
                        String id = jsonObject.getString("id");
                        String title = jsonObject.getString("title");
                        String doc = jsonObject.getString("doc");

                        list.add(new PojoQuestionandAnswerPaper(title,doc));
                    }

                    adapter = new QuestionandAnswerAdapter(list, (AppCompatActivity) SearchQuestionandAnswerPaperActivity.this,tv_no_records);
                    lv_question_and_answer_paper.setAdapter(adapter);

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            public void onFailure(int statusCode, Header[] headers, String res, Throwable t)
            {
                Toast.makeText(SearchQuestionandAnswerPaperActivity.this, "could not connect", Toast.LENGTH_LONG).show();

            }

        });

    }

}