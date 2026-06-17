package com.mountreachsolution.e_learningapp;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.Volley;
import com.google.android.material.textfield.TextInputEditText;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import com.mountreachsolution.e_learningapp.Comman.Urls;
import com.mountreachsolution.e_learningapp.Comman.VolleyMultipartRequest;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import cz.msebera.android.httpclient.Header;

public class RegistrationActivity extends AppCompatActivity {

    TextInputEditText tie_name,tie_mobile_no,tie_email_id,tie_age,tie_address, tie_username,tie_password;

    
    Button btn_select_student_image,btn_register;
    ProgressDialog progressDialog;

    private int PICK_IMAGE_REQUEST = 1;
    private Bitmap bitmap;
    ImageView img_student_image;
    private Uri filePath;


    SharedPreferences preferences;
    SharedPreferences.Editor editor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);
        setTitle("Register");
        preferences = PreferenceManager.getDefaultSharedPreferences(this);
        editor = preferences.edit();

        tie_name = findViewById(R.id.tie_register_name);
        tie_mobile_no = findViewById(R.id.tie_register_mobile_no);
        tie_email_id = findViewById(R.id.tie_register_email_id);
        tie_address = findViewById(R.id.tie_register_address);
        tie_age = findViewById(R.id.tie_register_age);
        tie_username = findViewById(R.id.tie_register_username);
        tie_password = findViewById(R.id.tie_register_password);
        btn_select_student_image = findViewById(R.id.btn_choose_Student_image);
        btn_register = findViewById(R.id.btn_register_register);
        img_student_image = findViewById(R.id.img_Student_image);

        btn_select_student_image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showFileChooser();
            }
        });

        btn_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(tie_name.getText().toString().isEmpty())
                {
                    tie_name.setError("Please Enter Your Name");
                }
                else if(tie_mobile_no.getText().toString().isEmpty())
                {
                    tie_mobile_no.setError("Please Enter Your Mobile Number");
                }
                else if (tie_email_id.getText().toString().isEmpty())
                {
                    tie_email_id.setError("Please Enter Your Mail Id");
                }
                else if (tie_address.getText().toString().isEmpty())
                {
                    tie_address.setError("Enter Your Address");
                }
                else if (tie_age.getText().toString().isEmpty())
                {
                    tie_age.setError("Enter Your Age");
                }
                else if (tie_username.getText().toString().isEmpty())
                {
                    tie_username.setError("Enter Your Username");
                }
                else if (tie_password.getText().toString().isEmpty())
                {
                    tie_password.setError("Enter Your Password");
                }
                else
                {
                    progressDialog = new ProgressDialog(RegistrationActivity.this);
                    progressDialog.setTitle("Registering student");
                    progressDialog.setCancelable(true);
                    progressDialog.setCanceledOnTouchOutside(true);
                    progressDialog.setMessage("Please Wait...");
                    progressDialog.show();
                    addstudent();
                }
            }
        });
    }

    private void showFileChooser() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE_REQUEST);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null && data.getData() != null) {
            filePath = data.getData();
            try {
                bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), filePath);
                img_student_image.setImageBitmap(bitmap);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private void addstudent() {

        AsyncHttpClient client = new AsyncHttpClient();
        RequestParams params = new RequestParams();
        params.put("name",tie_name.getText().toString());
        params.put("mobile_no",tie_mobile_no.getText().toString());
        params.put("email_id",tie_email_id.getText().toString());
        params.put("address",tie_address.getText().toString());
        params.put("age",tie_age.getText().toString());
        params.put("username",tie_username.getText().toString());
        params.put("password",tie_password.getText().toString());

        client.post(Urls.urlRegisterStudent,params,new JsonHttpResponseHandler() {
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
                        uploadBitmap(bitmap,response.getInt("lastinsertedid"));
//                        Toast.makeText(RegistrationActivity.this, "Registration Succesfully Done", Toast.LENGTH_SHORT).show();
//                        startActivity(new Intent(RegistrationActivity.this,LoginActivity.class));
//                        finish();
                    }
                    else
                    {
                        Toast.makeText(RegistrationActivity.this, "Already Register", Toast.LENGTH_SHORT).show();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                Toast.makeText(RegistrationActivity.this, "Could not Connect", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void uploadBitmap(final Bitmap bitmap,final int lastinsertedid) {
        //getting the tag from the edittext
        //our custom volley request
        VolleyMultipartRequest volleyMultipartRequest = new VolleyMultipartRequest(Request.Method.POST,
                Urls.url_addStudentimage,
                new Response.Listener<NetworkResponse>() {
                    @Override
                    public void onResponse(NetworkResponse response) {
                        try {
                            JSONObject obj = new JSONObject(new String(response.data));
                            Toast.makeText(RegistrationActivity.this,"Registration Successful",Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(RegistrationActivity.this, LoginActivity.class));
                            finish();
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(getApplicationContext(), error.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("tags",""+ lastinsertedid);
                return params;
            }

            /*
             * Here we are passing image by renaming it with a unique name
             * */
            @Override
            protected Map<String, DataPart> getByteData() {
                Map<String, DataPart> params = new HashMap<>();
                long imagename = System.currentTimeMillis();
                params.put("pic", new DataPart(imagename + ".png", getFileDataFromDrawable(bitmap)));
                return params;
            }
        };

        //adding the request to volley
        Volley.newRequestQueue(this).add(volleyMultipartRequest);

    }

    public byte[] getFileDataFromDrawable(Bitmap bitmap) {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 80, byteArrayOutputStream);
        return byteArrayOutputStream.toByteArray();
    }
    

    @Override
    public void onBackPressed() {
        startActivity(new Intent(RegistrationActivity.this,LoginActivity.class));
        finish();
    }
}