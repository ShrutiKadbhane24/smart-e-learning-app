package com.mountreachsolution.e_learningapp.Admin;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ActivityOptions;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputEditText;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import com.mountreachsolution.e_learningapp.Comman.Urls;
import com.mountreachsolution.e_learningapp.HomeActivity.HomeActivity;
import com.mountreachsolution.e_learningapp.LoginActivity;
import com.mountreachsolution.e_learningapp.R;

import org.json.JSONException;
import org.json.JSONObject;

import cz.msebera.android.httpclient.Header;

public class LoginAdminActivity extends AppCompatActivity {

    private static int SPLASH_SCREEN = 0000;
    TextInputEditText tie_login_username, tie_login_password;
    ImageView img_login_logo;
    ;
    CheckBox cb_show_hide;
    Button btn_login_login;
    ActivityOptions options;
    ProgressDialog progressDialog;

    SharedPreferences preferences;
    SharedPreferences.Editor editor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_admin);

        setTitle("E-Learning Admin App");
        preferences = PreferenceManager.getDefaultSharedPreferences(LoginAdminActivity.this);
        editor = preferences.edit();

        if (preferences.getBoolean("isAdminLogin", false)) {
            Intent intent = new Intent(LoginAdminActivity.this, HomeAdminActivity.class);
            startActivity(intent);
        }

        img_login_logo = findViewById(R.id.img_login_logo);
        tie_login_username = findViewById(R.id.tie_login_username);
        tie_login_password = findViewById(R.id.tie_login_password);
        cb_show_hide = findViewById(R.id.cb_login_show_hide);
        btn_login_login = findViewById(R.id.btn_login_login);

        cb_show_hide.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    tie_login_password.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                } else {
                    tie_login_password.setTransformationMethod(PasswordTransformationMethod.getInstance());
                }
            }
        });

        btn_login_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                progressDialog = new ProgressDialog(LoginAdminActivity.this);
                progressDialog.setTitle("Login User");
                progressDialog.setCancelable(false);
                progressDialog.setCanceledOnTouchOutside(false);
                progressDialog.setMessage("Please Wait...");
                progressDialog.show();
                loginAdmin();
            }
        });

    }

    private void loginAdmin() {

        AsyncHttpClient client = new AsyncHttpClient();
        RequestParams params = new RequestParams();
        params.put("username", tie_login_username.getText().toString());
        params.put("password", tie_login_password.getText().toString());

//        params.put("password",Password.getText().toString());

        client.post(Urls.loginAdminURL, params, new JsonHttpResponseHandler() {

            public void onStart() {
                // Pbar.setVisibility(View.VISIBLE);
                super.onStart();
            }

            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {

                progressDialog.dismiss();

                try {

                    //    Pbar.setVisibility(View.GONE);
                    editor.putString("username", tie_login_username.getText().toString()).commit();
                    editor.putString("password", tie_login_password.getText().toString()).commit();
//                    editor.putString("password",Password.getText().toString()).commit();

                    String status = response.getString("success");
                    String userrole = response.getString("userrole");

                    if (status.equals("1") && userrole.equals("admin")) {
                        startActivity(new Intent(LoginAdminActivity.this, HomeAdminActivity.class));
                        editor.putBoolean("isAdminLogin",true).commit();
                        finish();
                    } else {
                        Toast.makeText(LoginAdminActivity.this, "Incorrect Username or Password", Toast.LENGTH_SHORT).show();
                    }


                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                Log.d("Error", "" + errorResponse);

//   Pbar.setVisibility(View.GONE);
                progressDialog.dismiss();

                Toast.makeText(LoginAdminActivity.this, "Could Not Connect", Toast.LENGTH_SHORT);

            }

        });
    }
}