package com.jin123d.urp;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.baidu.mobstat.StatService;
import com.jin123d.Interface.GetNetData;
import com.jin123d.util.Sp;
import com.jin123d.util.netUtil;
import com.jin123d.util.urlUtil;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

public class XfjdActivity extends AppCompatActivity implements GetNetData {
    private String cookie;
    private String tv;
    private ProgressDialog progressDialog;
    private TextView tv_1;
    private TextView tv_2;
    private TextView tv_3;
    private TextView tv_4;
    private TextView tv_5;
    private TextView tv_6;
    private TextView tv_7;
    private Toolbar toolbar;

    Handler handler = new Handler() {
        public void handleMessage(Message msg) {

            switch (msg.what) {
                case urlUtil.DATA_FAIL:
                    progressDialog.dismiss();

                    Toast.makeText(XfjdActivity.this, getString(R.string.getDataTimeOut), Toast.LENGTH_SHORT).show();
                    finish();
                    break;
                case urlUtil.DATA_SUCCESS:
                    if (tv == null) {
                        System.out.println("空");
                    } else {
                        progressDialog.dismiss();
                        Document doc = Jsoup.parse(tv);
                        if (getString(R.string.webTitle).equals(doc.title())) {
                            Toast.makeText(XfjdActivity.this, getString(R.string.loginFail),
                                    Toast.LENGTH_SHORT).show();
                            finish();
                        } else {
                            Elements es = doc.select("thead").select("tr");
                            if (es.size() > 0) {
                                es = es.get(1).select("td");
                                if (es.size() > 6) {
                                    tv_1.setText(es.get(0).text());
                                    tv_2.setText(es.get(1).text());
                                    tv_3.setText(es.get(2).text());
                                    tv_4.setText(es.get(3).text());
                                    tv_5.setText(es.get(4).text());
                                    tv_6.setText(es.get(5).text());
                                    tv_7.setText(es.get(6).text());
                                } else {
                                    Toast.makeText(XfjdActivity.this, getString(R.string.error), Toast.LENGTH_SHORT).show();
                                    finish();
                                }
                            } else {
                                Toast.makeText(XfjdActivity.this, getString(R.string.error), Toast.LENGTH_SHORT).show();
                                finish();
                            }
                        }

                    }
                    break;
                case urlUtil.SESSION:
                    Toast.makeText(XfjdActivity.this, getString(R.string.loginFail), Toast.LENGTH_SHORT).show();
                    break;
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_xfjd);

        Sp sp = new Sp(XfjdActivity.this);
        // cookie = intent.getStringExtra("cookie");
        cookie = sp.getCookie();
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
        progressDialog = new ProgressDialog(XfjdActivity.this);
        progressDialog.setMessage(getString(R.string.getData));
        progressDialog.show();
        tv_1 = (TextView) findViewById(R.id.tv_1);
        tv_2 = (TextView) findViewById(R.id.tv_2);
        tv_3 = (TextView) findViewById(R.id.tv_3);
        tv_4 = (TextView) findViewById(R.id.tv_4);
        tv_5 = (TextView) findViewById(R.id.tv_5);
        tv_6 = (TextView) findViewById(R.id.tv_6);
        tv_7 = (TextView) findViewById(R.id.tv_7);
    }

    public void getInfo() {
        new Thread() {
            public void run() {
                netUtil.getPostData(urlUtil.URL + urlUtil.URL_XFDJ, cookie, XfjdActivity.this);
            }
        }.start();
    }

    @Override
    protected void onResume() {
        super.onResume();
        StatService.onResume(XfjdActivity.this);
    }

    @Override
    protected void onPause() {
        super.onPause();
        StatService.onPause(XfjdActivity.this);
    }

    @Override
    public void getDataSuccess(String Data) {
        tv = Data;
        handler.sendEmptyMessage(urlUtil.DATA_SUCCESS);
    }

    @Override
    public void getDataFail() {
        handler.sendEmptyMessage(urlUtil.DATA_FAIL);
    }

    @Override
    public void getDataSession() {
        handler.sendEmptyMessage(urlUtil.SESSION);
    }
}
