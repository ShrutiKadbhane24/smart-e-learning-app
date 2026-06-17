package com.mountreachsolution.e_learningapp;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputEditText;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import com.mountreachsolution.e_learningapp.Comman.Urls;

import org.json.JSONException;
import org.json.JSONObject;

import cz.msebera.android.httpclient.Header;

public class UpdateProfileActivity extends AppCompatActivity {

    TextInputEditText tie_name,tie_mobile_no,tie_email_id,tie_address,tie_username;

    Button btn_update_profile;
    ProgressDialog progressDialog;


    SharedPreferences preferences;
    SharedPreferences.Editor editor;

    String name,address,mobile_no,email_id,username,profile_pic;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_profile);

        setTitle("Update Profile Here");

        preferences = PreferenceManager.getDefaultSharedPreferences(UpdateProfileActivity.this);
        editor = preferences.edit();


        tie_name = findViewById(R.id.tie_update_profile_name);
        tie_mobile_no = findViewById(R.id.tie_update_profile_mobile_no);
        tie_email_id = findViewById(R.id.tie_update_profile_email_id);
        tie_address = findViewById(R.id.tie_update_profile_address);
        tie_username = findViewById(R.id.tie_update_profile_username);
        btn_update_profile = findViewById(R.id.btn_update_profile_update);
        progressDialog = new ProgressDialog(this);

        Intent intent = getIntent();
        name = intent.getStringExtra("name");
        address = intent.getStringExtra("address");
        mobile_no = intent.getStringExtra("mobile_no");
        email_id = intent.getStringExtra("email_id");
        username = intent.getStringExtra("username");

        tie_name.setText(name);
        tie_address.setText(address);
        tie_mobile_no.setText(mobile_no);
        tie_email_id.setText(email_id);
        tie_username.setText(username);

        btn_update_profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (TextUtils.isEmpty(tie_name.getText().toString()))
                {
                    tie_name.setError("Please Enter Your Name");
                }
                else if (TextUtils.isEmpty(tie_mobile_no.getText().toString()))
                {
                    tie_mobile_no.setError("Please Enter Your Mobile Number");
                }
                else if (tie_mobile_no.getText().toString().length()!=10)
                {
                    tie_mobile_no.setError("Enter 10 Digit Mobile Number");
                }
                else if (TextUtils.isEmpty(tie_email_id.getText().toString()))
                {
                    tie_email_id.setError("Please Enter Your Email Id");
                }
                else if (!tie_email_id.getText().toString().contains(".com") || !tie_email_id.getText().toString().contains("@"))
                {
                    tie_mobile_no.setError("Please Enter Valid Email Id");
                }
                else if (TextUtils.isEmpty(tie_address.getText().toString()))
                {
                    tie_address.setError("Please Enter Your Address");
                }
                else if (TextUtils.isEmpty(tie_username.getText().toString()))
                {
                    tie_username.setError("Please Enter Your Username");
                }
                else
                {
                    progressDialog = new ProgressDialog(UpdateProfileActivity.this);
                    progressDialog.setTitle("Updating Profile User");
                    progressDialog.setCancelable(false);
                    progressDialog.setCanceledOnTouchOutside(false);
                    progressDialog.setMessage("Please Wait...");
                    progressDialog.show();
                    update_profileUser();
                }
            }
        });
    }

    private void update_profileUser() {

        AsyncHttpClient client = new AsyncHttpClient();
        RequestParams params = new RequestParams();
        params.put("user_id", preferences.getString("id",""));
        params.put("name", tie_name.getText().toString());
        params.put("mobile_no", tie_mobile_no.getText().toString());
        params.put("email_id", tie_email_id.getText().toString());
        params.put("address", tie_address.getText().toString());
        params.put("username", tie_username.getText().toString());

        client.post(Urls.update_profileUserURL, params, new JsonHttpResponseHandler() {

            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                try {

                    String aa = response.getString("success");
                    if (aa.equals("1")) {
                        Toast.makeText(UpdateProfileActivity.this, "Update Profile Successfully", Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(UpdateProfileActivity.this, LoginActivity.class));
                        finish();
                    } else {
                        Toast.makeText(UpdateProfileActivity.this, "Already Update Profiled", Toast.LENGTH_SHORT).show();
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }

            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                progressDialog.dismiss();
                Toast.makeText(UpdateProfileActivity.this, "Could Not Connect", Toast.LENGTH_SHORT).show();
            }
        });
    }
}