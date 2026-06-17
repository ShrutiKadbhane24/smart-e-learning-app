package com.mountreachsolution.e_learningapp.NoticeBoard;

import androidx.appcompat.app.AppCompatActivity;

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
import com.mountreachsolution.e_learningapp.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import cz.msebera.android.httpclient.Header;

public class NoticeBoardActivity extends AppCompatActivity {

    List<PojoNoticeBoard> list;
    ListView lv_view_notice_board;
    TextView tv_no_records;
    ProgressBar pBar;
    NoticeBoardAdapter adapter;
    SharedPreferences preferences;
    SharedPreferences.Editor editor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notice_board);

        preferences= PreferenceManager.getDefaultSharedPreferences(NoticeBoardActivity.this);
        editor=preferences.edit();

        setTitle(""+preferences.getString("class_name","")+" Notice Board");
        list = new ArrayList<PojoNoticeBoard>();
        lv_view_notice_board = (ListView) findViewById(R.id.lv_notice_board);
        tv_no_records = (TextView) findViewById(R.id.tv_no_records);
        pBar = (ProgressBar) findViewById(R.id.progressBar);
        tv_no_records = (TextView) findViewById(R.id.tv_no_records);

        viewNoticeBoard();

    }
    public void viewNoticeBoard()
    {

        AsyncHttpClient client = new AsyncHttpClient();
        RequestParams params = new RequestParams();

        params.put("class_id",preferences.getString("class_id",""));
        client.post(Urls.urlViewNoticeBoard, params, new JsonHttpResponseHandler(){

            public void onStart()
            {
                pBar.setVisibility(View.VISIBLE);
                super.onStart();
            }

            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                try {
                    pBar.setVisibility(View.GONE);

                    JSONArray jarry = response.getJSONArray("getNoticeBoard");
                    if (jarry.isNull(0))
                    {
                        tv_no_records.setVisibility(View.VISIBLE);
                        tv_no_records.setText("No Notice Available Yet");
                    }
                    for (int i = 0 ; i < jarry.length();i++)
                    {
                        JSONObject jsonObject = jarry.getJSONObject(i);
                        String id = jsonObject.getString("id");
                        String title = jsonObject.getString("title");
                        String description = jsonObject.getString("description");
                        String doc = jsonObject.getString("doc");

                        list.add(new PojoNoticeBoard(title,description,doc));
                    }

                    adapter = new NoticeBoardAdapter(list, (AppCompatActivity) NoticeBoardActivity.this,tv_no_records);
                    lv_view_notice_board.setAdapter(adapter);

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            public void onFailure(int statusCode, Header[] headers, String res, Throwable t)
            {
                Toast.makeText(NoticeBoardActivity.this, "could not connect", Toast.LENGTH_LONG).show();

            }

        });

    }

}