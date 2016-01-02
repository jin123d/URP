package com.jin123d.urp;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.baidu.mobstat.StatService;
import com.jin123d.Interface.GetNetData;
import com.jin123d.Interface.GetZpInterface;
import com.jin123d.util.Sp;
import com.jin123d.util.netUtil;
import com.jin123d.util.urlUtil;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

public class XjxxActivity extends AppCompatActivity implements GetNetData, GetZpInterface {
    private TextView tv_html;
    private String cookie;
    private String tv;
    private ProgressDialog progressDialog;
    private Bitmap bitmap;
    private ImageView img_zp;
    private byte[] bytes;
    private Toolbar toolbar;

    Handler handler = new Handler() {
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case urlUtil.DATA_SUCCESS:
                    if (tv == null) {
                        System.out.println("空");
                    } else {
                        progressDialog.dismiss();
                        Document doc = Jsoup.parse(tv);
                        if (getString(R.string.webTitle).equals(doc.title())) {
                            Toast.makeText(XjxxActivity.this, getString(R.string.loginFail),
                                    Toast.LENGTH_SHORT).show();
                            finish();
                        } else {
                            Elements es = doc.select("table[id=tblView]");
                            if (es.size() > 0) {
                                Elements es_2 = es.get(0).select("tr");
                                for (int i = 0; i < es_2.size(); i++) {
                                    Elements es_3 = es_2.get(i).select("td");
                                    for (int j = 0; j < es_3.size(); j++) {
                                        tv_html.setText(tv_html.getText().toString() + "\n" + es_3.get(j).text());
                                    }
                                }
                            } else {
                                Toast.makeText(XjxxActivity.this, getString(R.string.loginFail), Toast.LENGTH_SHORT).show();
                                finish();
                            }
                        }
                    }
                    break;
                case urlUtil.DATA_FAIL:
                    progressDialog.dismiss();
                    Toast.makeText(XjxxActivity.this, getString(R.string.getDataTimeOut), Toast.LENGTH_SHORT).show();
                    finish();
                    break;
                case urlUtil.YZM_SUCCESS:
                    bitmap = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
                    img_zp.setImageBitmap(bitmap);
                    break;
                case urlUtil.SESSION:
                    Toast.makeText(XjxxActivity.this, getString(R.string.loginFail), Toast.LENGTH_SHORT).show();
                    break;
            }

        }
    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_xjxx);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        tv_html = (TextView) findViewById(R.id.tv_html);
        img_zp = (ImageView) findViewById(R.id.img_zp);
        Sp sp = new Sp(XjxxActivity.this);
        cookie = sp.getCookie();
        getInfo();
        progressDialog = new ProgressDialog(XjxxActivity.this);
        progressDialog.setMessage(getString(R.string.getData));
        progressDialog.setCanceledOnTouchOutside(false);
        progressDialog.setCancelable(false);
        progressDialog.show();
    }

    public void getInfo() {
        new Thread() {
            public void run() {
                netUtil.getPostData(urlUtil.URL + urlUtil.URL_XJXX, cookie, XjxxActivity.this);
                netUtil.getZp(urlUtil.URL + urlUtil.URL_ZP, cookie, XjxxActivity.this);
            }
        }.start();
    }


    @Override
    protected void onResume() {
        super.onResume();
        StatService.onResume(XjxxActivity.this);
    }

    @Override
    protected void onPause() {
        super.onPause();
        StatService.onPause(XjxxActivity.this);
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

    @Override
    public void getZpSuccess(byte[] bytes) {
        this.bytes = bytes;
        handler.sendEmptyMessage(urlUtil.YZM_SUCCESS);
    }

    @Override
    public void getZpFail() {
        handler.sendEmptyMessage(urlUtil.YZM_FAIL);
    }

    @Override
    public void getZpSession() {
        handler.sendEmptyMessage(urlUtil.SESSION);
    }
}
