package com.jin123d.urp;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.jin123d.Interface.GetNetData;
import com.jin123d.Interface.GetZpInterface;
import com.jin123d.util.Sp;
import com.jin123d.util.netUtil;
import com.jin123d.util.urlUtil;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.AbstractHttpClient;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;
import org.apache.http.util.EntityUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


public class LoginActivity extends AppCompatActivity implements View.OnClickListener, GetNetData {
    private EditText et_user, et_pwd, et_yzm;
    private ImageView img_yzm;
    private ProgressBar pgb_yzm;
    private Button btn_login;
    private HttpClient httpClient;
    private String cookie;
    private Bitmap bitmap;
    private String zjh;
    private String mm;
    private String tv;
    private ProgressDialog progressDialog;
    private ProgressBar progressBar;
    private Sp sp;
    private CheckBox chb_mm;
    private CheckBox chk_auto;
    private Toolbar toolbar;
    private LinearLayout lin_main;

    Handler handler = new Handler() {
        public void handleMessage(android.os.Message msg) {
            switch (msg.what) {
                case urlUtil.DATA_FAIL:
                    progressDialog.dismiss();
                    // shapeLoadingDialog.dismiss();
                    Snackbar.make(lin_main, getText(R.string.getDataTimeOut), Snackbar.LENGTH_SHORT)
                            .show();
                    break;
                case urlUtil.YZM_SUCCESS:
                    img_yzm.setImageBitmap(bitmap);
                    progressBar.setVisibility(View.INVISIBLE);
                    /*Toast.makeText(LoginActivity.this, cookie, Toast.LENGTH_SHORT)
                            .show();*/
                    break;
                case urlUtil.DATA_SUCCESS:
                    if (tv == null) {
                        Log.d("login","获取到的内容为空");
                    } else {
                        Document doc = Jsoup.parse(tv);
                        String title = doc.title();
                        progressDialog.dismiss();
                        //shapeLoadingDialog.dismiss();
                        if (getString(R.string.title_success).equals(title)) {
                            if (chb_mm.isChecked()) {
                                sp.setZjh(zjh);
                                sp.setMm(mm);
                                sp.setRememberMm(true);
                            } else {
                                sp.setZjh(null);
                                sp.setMm(null);
                            }
                            if (chk_auto.isChecked()) {
                                sp.setAuto(true);
                            }
                            sp.setCookie(cookie);
                            startActivity(new Intent(LoginActivity.this,MainActivity.class));
                            finish();
                        } else if (getText(R.string.webTitle).equals(title)) {
                            String error = doc.select("[class=errorTop]").text();
                            Snackbar.make(lin_main, error, Snackbar.LENGTH_SHORT)
                                    .show();
                            getCode();
                        }
                    }
                    break;
                case urlUtil.YZM_FAIL:
                    Snackbar.make(lin_main, getText(R.string.getYzmFail), Snackbar.LENGTH_SHORT)
                            .show();
                    progressBar.setVisibility(View.INVISIBLE);
                    break;
                case urlUtil.SESSION:
                    Toast.makeText(LoginActivity.this, getString(R.string.loginFail), Toast.LENGTH_SHORT).show();
                    break;
                default:
                    break;
            }
        }

        ;
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        //友盟自动更新
        setTitle(getString(R.string.login));
        sp = new Sp(LoginActivity.this);
        if (sp.getAuto()) {
            Intent intent = new Intent();
            intent.setClass(getApplicationContext(), MainActivity.class);
            startActivity(intent);
            finish();
        } else {
            httpClient = new DefaultHttpClient();
            initView();
            getCode();
        }
    }

    private void initView() {
        chb_mm = (CheckBox) findViewById(R.id.chk_mm);
        chk_auto = (CheckBox) findViewById(R.id.chk_auto);
        et_user = (EditText) findViewById(R.id.et_user);
        et_pwd = (EditText) findViewById(R.id.et_pwd);
        et_yzm = (EditText) findViewById(R.id.et_yzm);
        pgb_yzm = (ProgressBar) findViewById(R.id.pgb_yzm);
        btn_login = (Button) findViewById(R.id.btn_login);
        img_yzm = (ImageView) findViewById(R.id.img_yzm);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        lin_main = (LinearLayout) findViewById(R.id.lin_main);
        //--滑动视图开始
        // ObservableScrollView scrollView = (ObservableScrollView) findViewById(R.id.list);
        //  scrollView.setScrollViewCallbacks(this);
        //---滑动视图结束
        setSupportActionBar(toolbar);
        pgb_yzm.setVisibility(View.GONE);
        progressBar = (ProgressBar) findViewById(R.id.pgb_yzm);
        progressDialog = new ProgressDialog(LoginActivity.this);
        progressDialog.setMessage(getText(R.string.logining));
        progressDialog.setCancelable(false);
        progressDialog.setCanceledOnTouchOutside(false);
        //shapeLoadingDialog =new ShapeLoadingDialog(this);
        //shapeLoadingDialog.setLoadingText("加载中...");
        btn_login.setOnClickListener(this);
        img_yzm.setOnClickListener(this);
        chk_auto.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    chb_mm.setChecked(true);
                }
            }
        });
        //记住密码是否开启
        if (sp.getRememberMM()) {
            et_user.setText(sp.getZjh());
            et_pwd.setText(sp.getMm());
            chb_mm.setChecked(true);
        }

    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.img_yzm:
                getCode();
                break;
            case R.id.btn_login:
                if (et_yzm.getText().toString().length() == 0) {
                    Snackbar.make(lin_main, "验证码为空", Snackbar.LENGTH_SHORT)
                            .show();
                } else {
                    login();
                    progressDialog.show();
                    //shapeLoadingDialog.show();
                }
                break;
        }
    }

    private void getCode() {
        progressBar.setVisibility(View.VISIBLE);
        et_yzm.setFocusable(true);
        et_yzm.setText(null);
        new Thread() {
            public void run() {
                bitmap = getcode();
                if (bitmap == null) {
                    handler.sendEmptyMessage(urlUtil.YZM_FAIL);
                } else {
                    handler.sendEmptyMessage(urlUtil.YZM_SUCCESS);
                }
            }
        }.start();
    }

    private void login() {
        new Thread() {
            public void run() {
                zjh = et_user.getText().toString();
                mm = et_pwd.getText().toString();
                List<NameValuePair> params = new ArrayList<NameValuePair>();
                params.add(new BasicNameValuePair("zjh", zjh));
                params.add(new BasicNameValuePair("mm", mm));
                params.add(new BasicNameValuePair("v_yzm", et_yzm.getText()
                        .toString()));
                netUtil.doPost(
                        urlUtil.URL + urlUtil.URL_LOGIN, cookie, params, LoginActivity.this);
            }
        }.start();
    }


    //获取验证码
    public Bitmap getcode() {
        HttpPost httpPost = new HttpPost(
                urlUtil.URL + urlUtil.URL_YZM);
        HttpResponse httpResponse = null;
        try {
            HttpParams params = new BasicHttpParams();
            HttpConnectionParams.setConnectionTimeout(params, 5000); //设置连接超时
            HttpConnectionParams.setSoTimeout(params, 10000); //设置请求超时
            httpPost.setParams(params);
            httpResponse = httpClient.execute(httpPost);
            cookie = ((AbstractHttpClient) httpClient).getCookieStore()
                    .getCookies().get(0).getValue();
            byte[] bytes = EntityUtils.toByteArray(httpResponse.getEntity());
            bitmap = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
        } catch (ClientProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
            handler.sendEmptyMessage(urlUtil.YZM_FAIL);
        }
        return bitmap;
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_login, menu);
        return true;
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_settings:
                String str = urlUtil.URL;
                Snackbar.make(lin_main, str, Snackbar.LENGTH_SHORT)
                        .show();
                break;
            case R.id.action_about:
                startActivity(new Intent(this, WebActivity.class));
                break;
        }

        return true;
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
