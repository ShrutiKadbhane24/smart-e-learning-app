package com.mountreachsolution.e_learningapp.Admin;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.preference.PreferenceManager;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import com.mountreachsolution.e_learningapp.Admin.AddClass.AddClassActivity;
import com.mountreachsolution.e_learningapp.Comman.Urls;
import com.mountreachsolution.e_learningapp.HomeActivity.AllClassesAdapter;
import com.mountreachsolution.e_learningapp.HomeActivity.POJOAllClasses;
import com.mountreachsolution.e_learningapp.LoginActivity;
import com.mountreachsolution.e_learningapp.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import cz.msebera.android.httpclient.Header;

public class HomeAdminActivity extends AppCompatActivity {
    List<POJOAllClasses> list;
    ListView lv_view_all_class;
    TextView tv_no_records;
    ProgressBar pBar;
    AllClassesAdminAdapter adapter;
    SharedPreferences preferences;
    SharedPreferences.Editor editor;

    FloatingActionButton fab;

    boolean doubletap = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_admin);

        setTitle("Admin Dashboard");

        preferences= PreferenceManager.getDefaultSharedPreferences(HomeAdminActivity.this);
        editor=preferences.edit();

        list = new ArrayList<POJOAllClasses>();
        lv_view_all_class = (ListView) findViewById(R.id.lv_view_all_class);
        tv_no_records = (TextView) findViewById(R.id.tv_no_records);
        pBar = (ProgressBar) findViewById(R.id.progress);
        tv_no_records = (TextView) findViewById(R.id.tv_no_records);

        fab = findViewById(R.id.fab1);

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent1 = new Intent(HomeAdminActivity.this, AddClassActivity.class);
                startActivity(intent1);
            }
        });

        viewAllClass();
    }

    public void viewAllClass()
    {

        AsyncHttpClient client = new AsyncHttpClient();
        RequestParams params = new RequestParams();

        client.post(Urls.urlViewAllClasses, params, new JsonHttpResponseHandler(){

            public void onStart()
            {
                pBar.setVisibility(View.VISIBLE);
                super.onStart();
            }

            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                try {
                    pBar.setVisibility(View.GONE);

                    JSONArray jarry = response.getJSONArray("getAllClass");
                    if (jarry.isNull(0))
                    {
                        tv_no_records.setVisibility(View.VISIBLE);
                        tv_no_records.setText("No Class Available Yet");
                    }
                    for (int i = 0 ; i < jarry.length();i++)
                    {
                        JSONObject jsonObject = jarry.getJSONObject(i);
                        String id = jsonObject.getString("id");
                        String class_name = jsonObject.getString("class_name");
                        String subject_name = jsonObject.getString("subject_name");

                        list.add(new POJOAllClasses(id,class_name,subject_name));
                    }

                    adapter = new AllClassesAdminAdapter(list, (AppCompatActivity) HomeAdminActivity.this);
                    lv_view_all_class.setAdapter(adapter);

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            public void onFailure(int statusCode, Header[] headers, String res, Throwable t)
            {
                Toast.makeText(HomeAdminActivity.this, "could not connect", Toast.LENGTH_LONG).show();

            }

        });
    }


    public void onBackPressed() {
        logout();
//        if (doubletap){
//            super.onBackPressed();
//        }
//        else {
//            Toast.makeText(this, "Press back again to exit", Toast.LENGTH_SHORT).show();
//
//            doubletap = true;
//            Handler handler = new Handler();
//            handler.postDelayed(new Runnable() {
//                @Override
//                public void run() {
//                    doubletap = false;
//                }
//            }, 1000);
//        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.home_menu,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.home_menu_qr_code_scanner)
        {
            logout();
            return true;
        }
        return true;
    }


    private void logout() {
        AlertDialog.Builder ad = new AlertDialog.Builder(HomeAdminActivity.this);
        ad.setTitle("E-Learning App");
        ad.setMessage("Are you sure you want to logout");
        ad.setPositiveButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });
        ad.setNegativeButton("Logout", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Intent intent = new Intent(HomeAdminActivity.this, LoginActivity.class);
                editor.putBoolean("isAdminLogin",false).commit();
                startActivity(intent);
                finish();
            }
        });

        AlertDialog alertDialog = ad.create();
        alertDialog.setCanceledOnTouchOutside(true);
        alertDialog.show();
    }
}