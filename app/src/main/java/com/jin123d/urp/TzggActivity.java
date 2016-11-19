package com.jin123d.urp;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.jin123d.app.BaseActivity;
import com.jin123d.models.TzggModels;
import com.jin123d.util.UrpUrl;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


public class TzggActivity extends BaseActivity {
    private ProgressDialog progressDialog;
    private Toolbar toolbar;
    private ListView listView;
    private List<String> lists;
    private ArrayAdapter arrayAdapter;
    private List<TzggModels> list_tz;
    Handler handler = new Handler() {
        public void handleMessage(Message msg) {
            progressDialog.dismiss();
            switch (msg.what) {
                case 1:
                    arrayAdapter.notifyDataSetChanged();
                    break;
                case 2:
                    Snackbar.make(listView, "获取数据失败", Snackbar.LENGTH_SHORT).show();
                    break;
            }

        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tzgg);
    }

    @Override
    protected void initView() {
        progressDialog = new ProgressDialog(TzggActivity.this);
        progressDialog.setMessage(getString(R.string.getData));
        progressDialog.setCanceledOnTouchOutside(false);
        progressDialog.setCancelable(false);
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
        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {
                Toast.makeText(TzggActivity.this, list_tz.get(i).getUrl(), Toast.LENGTH_SHORT).show();
                return true;
            }
        });
    }

    @Override
    protected void initData() {
        getInfo();
    }

    public void getInfo() {
        new Thread() {
            public void run() {
                try {
                    String url = UrpUrl.URL_JWC + UrpUrl.URL_TZGG;
                    Document document = Jsoup.connect(url).timeout(5000).get();
                    Elements es = document.select("[style=height: 310px]").select("table[align=center]");
                    // Log.d("内容",es.toString());
                    for (int i = 0; i < es.size(); i++) {
                        Element element = es.get(i).select("tbody").select("tr").select("td").get(1).select("span").get(0);
                        Log.d("内容", element.toString());
                        String linkHref = element.select("a").attr("href");
                        Log.d("网址", linkHref);
                        String title = element.text();
                        lists.add(title);
                        TzggModels tzggModels = new TzggModels(title, linkHref);
                        list_tz.add(tzggModels);
                    }
                    handler.sendEmptyMessage(1);
                } catch (IOException e) {
                    handler.sendEmptyMessage(2);
                    e.printStackTrace();
                }
            }
        }.start();
    }
}
