package com.jin123d.urp;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.jin123d.app.BaseActivity;
import com.jin123d.util.ErrorCode;
import com.jin123d.util.HttpUtil;
import com.jin123d.util.okgo.JsoupCallBack;
import com.lzy.okgo.callback.BitmapCallback;

import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import okhttp3.Call;
import okhttp3.Response;

public class XjxxActivity extends BaseActivity {
    private TextView tv_html;
    private ImageView img_zp;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_xjxx);
    }

    @Override
    protected void initView() {
        tv_html = (TextView) findViewById(R.id.tv_html);
        img_zp = (ImageView) findViewById(R.id.img_zp);
    }

    @Override
    protected void initData() {
        progressDialogShow();
        getInfo();
    }

    public void getInfo() {
        HttpUtil.getXjxx(this, new JsoupCallBack() {
            @Override
            public void onError(ErrorCode errorCode, Response response) {
                progressDialogDismiss();
            }

            @Override
            public void onSuccess(Document document) {
                progressDialogDismiss();
                Elements es = document.select("table[id=tblView]");
                if (es.size() > 0) {
                    Elements es_2 = es.get(0).select("tr");
                    for (int i = 0; i < es_2.size(); i++) {
                        Elements es_3 = es_2.get(i).select("td");
                        for (int j = 0; j < es_3.size(); j++) {
                            tv_html.setText(tv_html.getText().toString() + "\n" + es_3.get(j).text());
                        }
                    }
                }
            }
        });

        HttpUtil.getPic(this, new BitmapCallback() {
            @Override
            public void onSuccess(Bitmap bitmap, Call call, Response response) {
                img_zp.setImageBitmap(bitmap);
            }

            @Override
            public void onError(Call call, Response response, Exception e) {
                super.onError(call, response, e);
                progressDialogDismiss();
            }
        });
    }

}
