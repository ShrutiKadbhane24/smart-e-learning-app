package com.mountreachsolution.e_learningapp.Admin.AddClass;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputEditText;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import com.mountreachsolution.e_learningapp.Admin.ClassInDetailsAdminActivity;
import com.mountreachsolution.e_learningapp.Admin.HomeAdminActivity;
import com.mountreachsolution.e_learningapp.Comman.Urls;
import com.mountreachsolution.e_learningapp.R;
import com.mountreachsolution.e_learningapp.RegistrationActivity;

import org.json.JSONException;
import org.json.JSONObject;

import cz.msebera.android.httpclient.Header;

public class AddClassActivity extends AppCompatActivity {

    TextInputEditText tie_class_name,tie_subject_name;

    Button btn_add_class;
    ProgressDialog progressDialog;

    SharedPreferences preferences;
    SharedPreferences.Editor editor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_class);

        preferences= PreferenceManager.getDefaultSharedPreferences(AddClassActivity.this);
        editor=preferences.edit();

        tie_class_name = findViewById(R.id.tie_add_class_class_name);
        tie_subject_name = findViewById(R.id.tie_add_class_subject_name);
        btn_add_class = findViewById(R.id.btn_add_class);

        btn_add_class.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (tie_class_name.getText().toString().isEmpty())
                {
                    tie_class_name.setError("Enter Your Class Name");
                }
                else if (tie_subject_name.getText().toString().isEmpty())
                {
                    tie_subject_name.setError("Enter Your Subject Name");
                }
                else
                {
                    progressDialog = new ProgressDialog(AddClassActivity.this);
                    progressDialog.setTitle("Adding Class");
                    progressDialog.setCancelable(true);
                    progressDialog.setCanceledOnTouchOutside(true);
                    progressDialog.setMessage("Please Wait...");
                    progressDialog.show();
                    addClass();
                }

            }
        });
    }

    private void addClass() {

        AsyncHttpClient client = new AsyncHttpClient();
        RequestParams params = new RequestParams();
        params.put("class_name",tie_class_name.getText().toString());
        params.put("subject_name",tie_subject_name.getText().toString());

        client.post(Urls.urlAddClass,params,new JsonHttpResponseHandler() {
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
                        Toast.makeText(AddClassActivity.this, "Class Added Successfully", Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(AddClassActivity.this, HomeAdminActivity.class));
                        finish();
                    }
                    else
                    {
                        Toast.makeText(AddClassActivity.this, "Already Added", Toast.LENGTH_SHORT).show();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                Toast.makeText(AddClassActivity.this, "Could not Connect", Toast.LENGTH_SHORT).show();
            }
        });

    }
}