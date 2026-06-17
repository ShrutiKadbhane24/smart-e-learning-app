

package com.mountreachsolution.e_learningapp.HomeActivity;

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

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import com.mountreachsolution.e_learningapp.Comman.Urls;
import com.mountreachsolution.e_learningapp.CompilerActivity;
import com.mountreachsolution.e_learningapp.LoginActivity;
import com.mountreachsolution.e_learningapp.MyProfileActivity;
import com.mountreachsolution.e_learningapp.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import cz.msebera.android.httpclient.Header;

public class HomeActivity extends AppCompatActivity {

    List<POJOAllClasses> list;
    ListView lv_view_all_class;
    TextView tv_no_records;
    ProgressBar pBar;
    AllClassesAdapter adapter;
    SharedPreferences preferences;
    SharedPreferences.Editor editor;

    boolean doubletap = false;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        setTitle("All Classes");
        preferences= PreferenceManager.getDefaultSharedPreferences(HomeActivity.this);
        editor=preferences.edit();

        list = new ArrayList<POJOAllClasses>();
        lv_view_all_class = (ListView) findViewById(R.id.lv_view_all_class);
        tv_no_records = (TextView) findViewById(R.id.tv_no_records);
        pBar = (ProgressBar) findViewById(R.id.progress);
        tv_no_records = (TextView) findViewById(R.id.tv_no_records);

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

                    adapter = new AllClassesAdapter(list, (AppCompatActivity) HomeActivity.this);
                    lv_view_all_class.setAdapter(adapter);

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            public void onFailure(int statusCode, Header[] headers, String res, Throwable t)
            {
                Toast.makeText(HomeActivity.this, "could not connect", Toast.LENGTH_LONG).show();

            }

        });
    }

    @Override
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
//                    editor.p
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
        if (item.getItemId() == R.id.home_menu_compiler)
        {
            startActivity(new Intent(HomeActivity.this, CompilerActivity.class));
            return true;
        }
        else if (item.getItemId() == R.id.home_menu_my_profile)
        {
            startActivity(new Intent(HomeActivity.this, MyProfileActivity.class));
            return true;
        }

        else if (item.getItemId() == R.id.home_menu_qr_code_scanner)
        {
            logout();
            return true;
        }
        return true;
    }


    private void logout() {
        AlertDialog.Builder ad = new AlertDialog.Builder(HomeActivity.this);
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
                Intent intent = new Intent(HomeActivity.this, LoginActivity.class);
                editor.putBoolean("isLogin",false).commit();
                startActivity(intent);
                finish();
            }
        });

        AlertDialog alertDialog = ad.create();
        alertDialog.setCanceledOnTouchOutside(true);
        alertDialog.show();
    }
}