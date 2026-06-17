package com.mountreachsolution.e_learningapp.Admin.AddHomeWork;

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

public class AddHomeWorkActivity extends AppCompatActivity {

    TextInputEditText tie_homework_title,tie_homework_description;

    Button btn_add_homework;
    ProgressDialog progressDialog;

    SharedPreferences preferences;
    SharedPreferences.Editor editor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_home_work);

        preferences= PreferenceManager.getDefaultSharedPreferences(AddHomeWorkActivity.this);
        editor=preferences.edit();

        tie_homework_title = findViewById(R.id.tie_add_homework_homework_title);
        tie_homework_description = findViewById(R.id.tie_add_homework_homework_description);
        btn_add_homework = findViewById(R.id.btn_add_homework);

        btn_add_homework.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (tie_homework_title.getText().toString().isEmpty())
                {
                    tie_homework_title.setError("Enter Your Homework title");
                }
                else if (tie_homework_description.getText().toString().isEmpty())
                {
                    tie_homework_description.setError("Enter Your Homework Description");
                }
                else
                {
                    progressDialog = new ProgressDialog(AddHomeWorkActivity.this);
                    progressDialog.setTitle("Adding Homework");
                    progressDialog.setCancelable(true);
                    progressDialog.setCanceledOnTouchOutside(true);
                    progressDialog.setMessage("Please Wait...");
                    progressDialog.show();
                    addHomework();
                }

            }
        });
    }

    private void addHomework() {

        AsyncHttpClient client = new AsyncHttpClient();
        RequestParams params = new RequestParams();
        params.put("class_id",preferences.getString("class_id",""));
        params.put("homework_title",tie_homework_title.getText().toString());
        params.put("homework_description",tie_homework_description.getText().toString());

        client.post(Urls.urlAddHomework,params,new JsonHttpResponseHandler() {
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
                        Toast.makeText(AddHomeWorkActivity.this, "Homework Added Successfully", Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(AddHomeWorkActivity.this, HomeAdminActivity.class));
                        finish();
                    }
                    else
                    {
                        Toast.makeText(AddHomeWorkActivity.this, "Already Added", Toast.LENGTH_SHORT).show();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                Toast.makeText(AddHomeWorkActivity.this, "Could not Connect", Toast.LENGTH_SHORT).show();
            }
        });

        
    }
}