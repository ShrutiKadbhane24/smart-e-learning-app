package com.mountreachsolution.e_learningapp.Recording;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;

import android.annotation.SuppressLint;
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
import com.mountreachsolution.e_learningapp.HomeWork.HomeWorkActivity;
import com.mountreachsolution.e_learningapp.HomeWork.HomeworkAdapter;
import com.mountreachsolution.e_learningapp.HomeWork.POJOHomework;
import com.mountreachsolution.e_learningapp.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import cz.msebera.android.httpclient.Header;

public class RecordingActivity extends AppCompatActivity {

    List<POJORecording> pojoRecordings;

    SearchView sv_recording;
    ListView lv_recording;
    TextView tv_no_records;
    ProgressBar progressBar;
    RecordingAdapter adapter;
    
    SharedPreferences preferences;
    SharedPreferences.Editor editor;
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recording);

        preferences= PreferenceManager.getDefaultSharedPreferences(RecordingActivity.this);
        editor=preferences.edit();

        setTitle(""+preferences.getString("class_name","")+" Recording");
        pojoRecordings = new ArrayList<POJORecording>();
        sv_recording = (SearchView) findViewById(R.id.sv_recording);
        lv_recording = (ListView) findViewById(R.id.lv_my_recording);
        tv_no_records = (TextView) findViewById(R.id.tv_no_records);
        progressBar = (ProgressBar) findViewById(R.id.progressBar);

        sv_recording.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                searchRecording(query);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String query) {
                searchRecording(query);
                return false;
            }
        });

        getRecording();
    }

    private void searchRecording(String query) {
        List<POJORecording> temppojoclass = new ArrayList<>();
        temppojoclass.clear();

        for(POJORecording pojo :pojoRecordings)
        {
            if (pojo.getRecording_date().toUpperCase().contains(query.toUpperCase()) ||
                    pojo.getRecording_title().toUpperCase().contains(query.toUpperCase()) ||
                    pojo.getRecording_description().toUpperCase().contains(query.toUpperCase()))
            {
                temppojoclass.add(pojo);
            }

            adapter = new RecordingAdapter(temppojoclass, RecordingActivity.this);
            lv_recording.setAdapter(adapter);

        }
    }

    public void getRecording()
    {
        AsyncHttpClient client = new AsyncHttpClient();
        RequestParams params = new RequestParams();

        params.put("class_id",preferences.getString("class_id",""));
        client.post(Urls.urlGetRecording, params, new JsonHttpResponseHandler(){
            public void onStart()
            {
                progressBar.setVisibility(View.VISIBLE);
                super.onStart();
            }

            public void onSuccess(int statusCode, Header[] headers, JSONObject response)
            {
                try {

                    progressBar.setVisibility(View.GONE);
                    JSONArray jarry = response.getJSONArray("getMyRecording");

                    for (int i = 0; i < jarry.length(); i++){
                        JSONObject jsonObject = jarry.getJSONObject(i);
                        String recording_date = jsonObject.getString("recording_date");
                        String recording_title = jsonObject.getString("recording_title");
                        String recording_description = jsonObject.getString("recording_description");
                        String recording_url = jsonObject.getString("recording_url");

                        pojoRecordings.add(new POJORecording(recording_date,recording_title,
                                recording_description,recording_url));
                    }

                    adapter = new RecordingAdapter(pojoRecordings, RecordingActivity.this);
                    lv_recording.setAdapter(adapter);

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            public void onFailure(int statusCode, Header[] headers, String res, Throwable t)
            {
                Toast.makeText(RecordingActivity.this, "could not connect", Toast.LENGTH_LONG).show();

            }

        });
    }

    @Override
    public void onBackPressed() {
//        super.onBackPressed();

        startActivity(new Intent(RecordingActivity.this, ClassInDetailsActivity.class));
        editor.putString("class_id",preferences.getString("class_id","")).commit();
        editor.putString("class_name",preferences.getString("class_name","")).commit();
        editor.putString("subject_name",preferences.getString("subject_name","")).commit();
        finish();
    }
}