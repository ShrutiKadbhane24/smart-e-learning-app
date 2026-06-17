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
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.Volley;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import com.mountreachsolution.e_learningapp.Comman.Urls;
import com.mountreachsolution.e_learningapp.Comman.VolleyMultipartRequest;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import cz.msebera.android.httpclient.Header;

public class MyProfileActivity extends AppCompatActivity {

    ImageView img_my_profile;
    TextView name,mobile_no,email_id,age,address,username;
    Button btn_my_profile_change_profile_photo,btn_update_profile;
    ProgressBar progress;

    SharedPreferences preferences;
    SharedPreferences.Editor editor;

    private Bitmap bitmap;
    private Uri filePath;
    private int PICK_IMAGE_REQUEST = 1;
    ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_profile);

        preferences = PreferenceManager.getDefaultSharedPreferences(MyProfileActivity.this);
        editor = preferences.edit();

        progress = findViewById(R.id.progress);
        img_my_profile = findViewById(R.id.img_my_profile);
        name = findViewById(R.id.txt_profile_name);
        mobile_no = findViewById(R.id.txt_profile_mobile_no);
        email_id = findViewById(R.id.txt_profile_email_id);
        age =findViewById(R.id.txt_profile_age);
        address = findViewById(R.id.txt_profile_address);
        username= findViewById(R.id.txt_profile_username);
        btn_my_profile_change_profile_photo= (Button) findViewById(R.id.btn_my_profile_change_profile_photo);
        btn_update_profile= (Button) findViewById(R.id.btn_my_profile_update_profile);

        progress.setVisibility(View.VISIBLE);
        getMyProfileDetails();

        btn_my_profile_change_profile_photo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                progressDialog = new ProgressDialog(MyProfileActivity.this);
                progressDialog.setTitle("My Profile Update");
                progressDialog.setCancelable(true);
                progressDialog.setCanceledOnTouchOutside(true);
                progressDialog.setMessage("Please Wait...");
                progressDialog.show();
                showFileChooser();
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
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null &&
                data.getData() != null) {
            filePath = data.getData();
            try {
                bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), filePath);
                img_my_profile.setImageBitmap(bitmap);
                uploadBitmap(bitmap, Integer.parseInt(preferences.getString("id","")));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private void uploadBitmap(final Bitmap bitmap, final int lastinsertedid) {
        //getting the tag from the edittext
        //our custom volley request
        VolleyMultipartRequest volleyMultipartRequest = new VolleyMultipartRequest(Request.Method.POST,
                Urls.urlAddProfileImage,
                new Response.Listener<NetworkResponse>() {
                    @Override
                    public void onResponse(NetworkResponse response) {
                        Intent i = new Intent(MyProfileActivity.this, LoginActivity.class);
                        startActivity(i);
                        MyProfileActivity.this.finish();
                        Toast.makeText(MyProfileActivity.this, "Profile Change Successfully Done", Toast.LENGTH_SHORT).show();
                        progressDialog.dismiss();
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(MyProfileActivity.this, error.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("id", "" + lastinsertedid);
                return params;
            }

            /*
             * Here we are passing image by renaming it with a unique name
             * */
            @Override
            protected Map<String, DataPart> getByteData() {
                Map<String, DataPart> params = new HashMap<>();
                long imagename = System.currentTimeMillis();
                params.put("pic", new DataPart(imagename + ".png", getFileDataFromDrawable1(bitmap)));
                return params;

            }
        };

        //adding the request to volley
        Volley.newRequestQueue(MyProfileActivity.this).add(volleyMultipartRequest);
    }

    public byte[] getFileDataFromDrawable1(Bitmap bitmap) {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 80, byteArrayOutputStream);
        return byteArrayOutputStream.toByteArray();
    }


    private void getMyProfileDetails() {

        AsyncHttpClient client = new AsyncHttpClient();
        RequestParams params = new RequestParams();
        params.put("user_id", preferences.getString("id", null));
        client.post(Urls.getMyProfileURL, params, new JsonHttpResponseHandler() {
                    @Override
                    public void onSuccess(int statusCode, cz.msebera.android.httpclient.Header[] headers, JSONObject response) {
                        try {
                            progress.setVisibility(View.GONE);
                            JSONArray jsonArray = response.getJSONArray("getMyDetails");

                            for (int i = 0; i < jsonArray.length(); i++) {
                                JSONObject jsonObject = jsonArray.getJSONObject(i);
                                String id = jsonObject.getString("id");
                                String strname = jsonObject.getString("name");
                                String strage = jsonObject.getString("age");
                                String strmobile_no = jsonObject.getString("mobile_no");
                                String stremail_id = jsonObject.getString("email_id");
                                String straddress = jsonObject.getString("address");
                                String strusername = jsonObject.getString("username");
                                String strProfilePic = jsonObject.getString("profile_pic");

                                name.setText(strname);
                                mobile_no.setText(strmobile_no);
                                email_id.setText(stremail_id);
                                age.setText(strage);
                                address.setText(straddress);
                                username.setText(strusername);

                                Picasso.get().load(Urls.OnlineImageAddress+strProfilePic).
                                        error(R.drawable.image_not_available1).into(img_my_profile);

                                btn_update_profile.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        Intent intent = new Intent(MyProfileActivity.this, UpdateProfileActivity.class);
                                        intent.putExtra("name",strname);
                                        intent.putExtra("age",strage);
                                        intent.putExtra("mobile_no",strmobile_no);
                                        intent.putExtra("email_id",stremail_id);
                                        intent.putExtra("address",straddress);
                                        intent.putExtra("username",strusername);
                                        intent.putExtra("profile_pic",strProfilePic);
                                        startActivity(intent);
                                    }
                                });

                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                    @Override
                    public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                        Toast.makeText(MyProfileActivity.this, "Could Not Connect", Toast.LENGTH_SHORT).show();
                    }
                }

        );
    }
}