package com.jin123d.urp;

import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import com.jin123d.app.BaseActivity;
import com.jin123d.util.ErrorCode;
import com.jin123d.util.HttpUtil;
import com.jin123d.util.okgo.JsoupCallBack;

import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import okhttp3.Response;

public class XfjdActivity extends BaseActivity {
    private TextView tv_1;
    private TextView tv_2;
    private TextView tv_3;
    private TextView tv_4;
    private TextView tv_5;
    private TextView tv_6;
    private TextView tv_7;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_xfjd);
    }

    @Override
    protected void initView() {
        tv_1 = (TextView) findViewById(R.id.tv_1);
        tv_2 = (TextView) findViewById(R.id.tv_2);
        tv_3 = (TextView) findViewById(R.id.tv_3);
        tv_4 = (TextView) findViewById(R.id.tv_4);
        tv_5 = (TextView) findViewById(R.id.tv_5);
        tv_6 = (TextView) findViewById(R.id.tv_6);
        tv_7 = (TextView) findViewById(R.id.tv_7);
    }

    @Override
    protected void initData() {
        progressDialogShow();
        getInfo();
    }

    public void getInfo() {
        HttpUtil.getXfjd(this, new JsoupCallBack() {
            @Override
            public void onSuccess(Document document) {
                progressDialogDismiss();
                Elements es = document.select("thead").select("tr");
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

            @Override
            public void onError(ErrorCode errorCode, Response response) {
                super.onError(errorCode, response);
                progressDialogDismiss();
            }
        });
    }

}
