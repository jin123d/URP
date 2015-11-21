package com.jin123d.urp;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.jin123d.models.TzggModels;
import com.jin123d.util.urlUtil;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


public class TzggActivity extends AppCompatActivity{
    private ProgressDialog progressDialog;
    private Toolbar toolbar;
    private ListView listView;
    private List<String> lists;
    private ArrayAdapter arrayAdapter;
    private List<TzggModels> list_tz;
    Handler handler = new Handler() {
        public void handleMessage(Message msg) {
            arrayAdapter.notifyDataSetChanged();
            progressDialog.dismiss();
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tzgg);
        initView();
        getInfo();
    }

    private void initView() {
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        progressDialog = new ProgressDialog(TzggActivity.this);
        progressDialog.setMessage(getString(R.string.getData));
        progressDialog.show();
        listView = (ListView) findViewById(R.id.lv_news);
        lists = new ArrayList<>();
        list_tz = new ArrayList<>();
        arrayAdapter = new ArrayAdapter(TzggActivity.this, android.R.layout.simple_list_item_1, lists);
        listView.setAdapter(arrayAdapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent();
                intent.putExtra("URL", list_tz.get(position).getUrl());
                intent.setClass(TzggActivity.this, NewsActivity.class);
                startActivity(intent);
            }
        });
    }

    public void getInfo() {
        new Thread() {
            public void run() {
                try {
                    Document document = Jsoup.connect(urlUtil.URL_JWC + urlUtil.URL_TZGG).timeout(5000).get();
                    Elements es = document.select("[target=V2]");
                    for (int i = 0; i < es.size(); i++) {
                        String linkHref = es.get(i).attr("href");
                        String title = es.get(i).text();
                        lists.add(title);
                        TzggModels tzggModels = new TzggModels(title, linkHref);
                        list_tz.add(tzggModels);
                    }
                    handler.sendEmptyMessage(1);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }.start();
    }
}
