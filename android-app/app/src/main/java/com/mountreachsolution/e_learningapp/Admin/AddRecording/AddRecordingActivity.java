package com.mountreachsolution.e_learningapp.Admin.AddRecording;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputEditText;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import com.mountreachsolution.e_learningapp.Admin.HomeAdminActivity;
import com.mountreachsolution.e_learningapp.Comman.Urls;
import com.mountreachsolution.e_learningapp.R;

import org.json.JSONException;
import org.json.JSONObject;

import cz.msebera.android.httpclient.Header;

public class AddRecordingActivity extends AppCompatActivity {

    TextInputEditText tie_recording_title,tie_recording_description,tie_recording_url;

    Button btn_add_recording;
    ProgressDialog progressDialog;

    SharedPreferences preferences;
    SharedPreferences.Editor editor;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_recording);
        preferences= PreferenceManager.getDefaultSharedPreferences(AddRecordingActivity.this);
        editor=preferences.edit();

        tie_recording_title = findViewById(R.id.tie_add_recording_recording_title);
        tie_recording_description = findViewById(R.id.tie_add_recording_recording_description);
        tie_recording_url = findViewById(R.id.tie_add_recording_recording_url);
        btn_add_recording = findViewById(R.id.btn_add_recording);

        btn_add_recording.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (tie_recording_title.getText().toString().isEmpty())
                {
                    tie_recording_title.setError("Enter Your Recording Title");
                }
                else if (tie_recording_description.getText().toString().isEmpty())
                {
                    tie_recording_description.setError("Enter Your Recording Description");
                }
                else
                {
                    progressDialog = new ProgressDialog(AddRecordingActivity.this);
                    progressDialog.setTitle("Adding Recording");
                    progressDialog.setCancelable(true);
                    progressDialog.setCanceledOnTouchOutside(true);
                    progressDialog.setMessage("Please Wait...");
                    progressDialog.show();
                    addRecording();
                }

            }
        });
    }

    private void addRecording() {

        AsyncHttpClient client = new AsyncHttpClient();
        RequestParams params = new RequestParams();
        params.put("class_id",preferences.getString("class_id",""));
        params.put("title",tie_recording_title.getText().toString());
        params.put("description",tie_recording_description.getText().toString());
        params.put("url",tie_recording_url.getText().toString());

        client.post(Urls.urlAddRecording,params,new JsonHttpResponseHandler() {
            @Override
            public void onStart() {
                super.onStart();
            }

            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                progressDialog.dismiss();
                try {
                    String aa = response.getString("success");
                    if (aa.equals("1"))
                    {
                        Toast.makeText(AddRecordingActivity.this, "Recording Added Successfully", Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(AddRecordingActivity.this, HomeAdminActivity.class));
                        finish();
                    }
                    else
                    {
                        Toast.makeText(AddRecordingActivity.this, "Already Added", Toast.LENGTH_SHORT).show();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                Toast.makeText(AddRecordingActivity.this, "Could not Connect", Toast.LENGTH_SHORT).show();
            }
        });

    }
}