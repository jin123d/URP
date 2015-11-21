package com.jin123d.urp;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;

import com.baidu.mobstat.StatService;
import com.jin123d.util.urlUtil;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.io.IOException;

public class NewsActivity extends AppCompatActivity{
    private String tv;
    private ProgressDialog progressDialog;
    private Toolbar toolbar;
    private TextView textView;
    String URL;

    Handler handler = new Handler() {
        public void handleMessage(Message msg) {
            progressDialog.dismiss();
            textView.setText(tv);
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news);
        Intent intent = getIntent();
        URL = intent.getStringExtra("URL");
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
        progressDialog = new ProgressDialog(NewsActivity.this);
        progressDialog.setMessage(getString(R.string.getData));
        progressDialog.show();
        textView = (TextView) findViewById(R.id.tv_html);

    }

    public void getInfo() {
        new Thread() {
            public void run() {
                try {
                    Document document = Jsoup.connect(urlUtil.URL_JWC + URL).timeout(5000).get();
                    Elements es = document.select("[id=ctl00_ContentPlaceHolder1_DIV1]");
                    tv = es.text();
                    handler.sendEmptyMessage(1);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }.start();
    }

    @Override
    protected void onResume() {
        super.onResume();
        StatService.onResume(NewsActivity.this);
    }

    @Override
    protected void onPause() {
        super.onPause();
        StatService.onPause(NewsActivity.this);
    }

}
