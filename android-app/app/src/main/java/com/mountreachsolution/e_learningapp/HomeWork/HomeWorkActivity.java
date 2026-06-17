package com.mountreachsolution.e_learningapp.HomeWork;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;

import android.content.Intent;
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
import com.mountreachsolution.e_learningapp.HomeActivity.ClassInDetailsActivity;
import com.mountreachsolution.e_learningapp.HomeActivity.HomeActivity;
import com.mountreachsolution.e_learningapp.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import cz.msebera.android.httpclient.Header;

public class HomeWorkActivity extends AppCompatActivity {

    List<POJOHomework> list;

    SearchView sv_homework;
    ListView lv_homework;
    TextView tv_no_records;
    ProgressBar progressBar;
    HomeworkAdapter adapter;
    SharedPreferences preferences;
    SharedPreferences.Editor editor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_work);

        preferences= PreferenceManager.getDefaultSharedPreferences(HomeWorkActivity.this);
        editor=preferences.edit();

        setTitle(""+preferences.getString("class_name","")+" Home Work");
        preferences= PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        editor=preferences.edit();

        list = new ArrayList<POJOHomework>();
        sv_homework = (SearchView) findViewById(R.id.sv_homework);
        lv_homework = (ListView) findViewById(R.id.lv_my_homework);
        tv_no_records = (TextView) findViewById(R.id.tv_no_records);
        progressBar = (ProgressBar) findViewById(R.id.progressBar);

        sv_homework.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                searchHomework(query);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String query) {
                searchHomework(query);
                return false;
            }
        });

        getmyhomework();
    }

    private void searchHomework(String query) {
        List<POJOHomework> temppojoclass = new ArrayList<>();
        temppojoclass.clear();

        for(POJOHomework pojo :list)
        {
            if (pojo.getHowework_date().toUpperCase().contains(query.toUpperCase()) ||
                    pojo.getHomework_title().toUpperCase().contains(query.toUpperCase()) ||
                    pojo.getHomework_description().toUpperCase().contains(query.toUpperCase()))
            {
                temppojoclass.add(pojo);
            }

            adapter = new HomeworkAdapter(temppojoclass, HomeWorkActivity.this);
            lv_homework.setAdapter(adapter);

        }
    }

    public void getmyhomework()
    {
        AsyncHttpClient client = new AsyncHttpClient();
        RequestParams params = new RequestParams();

        params.put("class_id",preferences.getString("class_id",""));
        client.post(Urls.urlGetHomework, params, new JsonHttpResponseHandler(){
            public void onStart()
            {
                progressBar.setVisibility(View.VISIBLE);
                super.onStart();
            }

            public void onSuccess(int statusCode, Header[] headers, JSONObject response)
            {
                try {

                    progressBar.setVisibility(View.GONE);
                    JSONArray jarry = response.getJSONArray("getMyHomework");

                    for (int i = 0; i < jarry.length(); i++){
                        JSONObject jsonObject = jarry.getJSONObject(i);
                        String howework_date = jsonObject.getString("howework_date");
                        String howework_title = jsonObject.getString("howework_title");
                        String howework_description = jsonObject.getString("howework_description");

                        list.add(new POJOHomework(howework_date,howework_title,howework_description));
                    }

                    adapter = new HomeworkAdapter(list, HomeWorkActivity.this);
                    lv_homework.setAdapter(adapter);

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            public void onFailure(int statusCode, Header[] headers, String res, Throwable t)
            {
                Toast.makeText(HomeWorkActivity.this, "could not connect", Toast.LENGTH_LONG).show();

            }

        });
    }

    @Override
    public void onBackPressed() {
//        super.onBackPressed();

        startActivity(new Intent(HomeWorkActivity.this, ClassInDetailsActivity.class));
        editor.putString("class_id",preferences.getString("class_id","")).commit();
        editor.putString("class_name",preferences.getString("class_name","")).commit();
        editor.putString("subject_name",preferences.getString("subject_name","")).commit();
        finish();
    }
}