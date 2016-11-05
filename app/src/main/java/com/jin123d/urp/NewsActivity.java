package com.jin123d.urp;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.jin123d.util.UrpUrl;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.io.IOException;

public class NewsActivity extends AppCompatActivity {
    private String tv;
    private ProgressDialog progressDialog;
    private Toolbar toolbar;
    private TextView textView;
    String URL;

    Handler handler = new Handler() {
        public void handleMessage(Message msg) {
            progressDialog.dismiss();
            if (msg.what == 1) {
                textView.setText(tv);
            } else {
                Snackbar.make(textView, "获取数据失败", Snackbar.LENGTH_SHORT).show();

            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news);
        Intent intent = getIntent();
        URL = intent.getStringExtra("URL");
        Log.d("url", URL);
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
        progressDialog.setCanceledOnTouchOutside(false);
        progressDialog.setCancelable(false);
        progressDialog.show();
        textView = (TextView) findViewById(R.id.tv_html);

    }

    public void getInfo() {
        new Thread() {
            public void run() {
                try {
                    Document document = Jsoup.connect(UrpUrl.URL_JWC + URL).timeout(5000).get();
                    Elements es = document.select("td[align=left]");
                    tv = es.text();

                    handler.sendEmptyMessage(1);
                } catch (IOException e) {
                    handler.sendEmptyMessage(2);
                    e.printStackTrace();
                }
            }
        }.start();
    }


}
