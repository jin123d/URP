package com.jin123d.urp;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;

import com.jin123d.Interface.GetCodeListener;
import com.jin123d.Interface.UrpUserListener;
import com.jin123d.app.BaseActivity;
import com.jin123d.util.ErrorCode;
import com.jin123d.util.HttpUtil;
import com.jin123d.util.JsoupUtil;
import com.jin123d.util.UrpSpUtil;
import com.jin123d.util.okgo.JsoupCallBack;

import org.jsoup.nodes.Document;

import okhttp3.Call;
import okhttp3.Response;


public class LoginActivity extends BaseActivity implements View.OnClickListener {
    private EditText et_user, et_pwd, et_yzm;
    private ImageView img_yzm;
    private ProgressBar pgb_yzm;
    private String zjh;
    private String mm;
    private CheckBox chb_mm;
    private CheckBox chk_auto;
    private LinearLayout lin_main;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
    }

    @Override
    protected void initView() {
        chb_mm = (CheckBox) findViewById(R.id.chk_mm);
        chk_auto = (CheckBox) findViewById(R.id.chk_auto);
        et_user = (EditText) findViewById(R.id.et_user);
        et_pwd = (EditText) findViewById(R.id.et_pwd);
        et_yzm = (EditText) findViewById(R.id.et_yzm);
        pgb_yzm = (ProgressBar) findViewById(R.id.pgb_yzm);
        Button btn_login = (Button) findViewById(R.id.btn_login);
        img_yzm = (ImageView) findViewById(R.id.img_yzm);
        lin_main = (LinearLayout) findViewById(R.id.lin_main);
        pgb_yzm.setVisibility(View.GONE);
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
        if (UrpSpUtil.getRememberMM()) {
            et_user.setText(UrpSpUtil.getZjh());
            et_pwd.setText(UrpSpUtil.getMm());
            chb_mm.setChecked(true);
        }

    }

    @Override
    protected void initData() {
        if (UrpSpUtil.getAuto()) {
            Intent intent = new Intent();
            intent.setClass(getApplicationContext(), MainActivity.class);
            startActivity(intent);
            finish();
        } else {
            getCode();
        }
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.img_yzm:
                getCode();
                break;
            case R.id.btn_login:
                if (TextUtils.isEmpty(et_yzm.getText().toString())) {
                    Snackbar.make(lin_main, R.string.codeIsNull, Snackbar.LENGTH_SHORT)
                            .show();
                } else {
                    progressDialog.setMessage(getString(R.string.logining));
                    progressDialog.show();
                    login();
                }
                break;
        }
    }

    private void getCode() {
        pgb_yzm.setVisibility(View.VISIBLE);
        et_yzm.setFocusable(true);
        et_yzm.setText(null);
        HttpUtil.getCode(this, new GetCodeListener() {
            @Override
            public void getSuccess(Bitmap bitmap) {
                img_yzm.setImageBitmap(bitmap);
                pgb_yzm.setVisibility(View.INVISIBLE);
            }

            @Override
            public void getError(String errorMsg) {
                Snackbar.make(lin_main, R.string.getYzmFail, Snackbar.LENGTH_SHORT).show();
                pgb_yzm.setVisibility(View.INVISIBLE);
            }
        });
    }

    private void login() {
        zjh = et_user.getText().toString();
        mm = et_pwd.getText().toString();
        HttpUtil.login(this, zjh, mm, et_yzm.getText().toString(), new JsoupCallBack() {

            @Override
            public void onSuccess(Document document, Call call, Response response) {
                progressDialogDismiss();
                JsoupUtil.isLogin(document, new UrpUserListener.UserStateListener() {
                    @Override
                    public void loginSuccess() {
                        if (chb_mm.isChecked()) {
                            UrpSpUtil.setZjh(zjh);
                            UrpSpUtil.setMm(mm);
                            UrpSpUtil.setRememberMm(true);
                        } else {
                            UrpSpUtil.setZjh(null);
                            UrpSpUtil.setMm(null);
                        }
                        if (chk_auto.isChecked()) {
                            UrpSpUtil.setAuto(true);
                        }
                        // UrpSp.setCookie(cookie);
                        startActivity(new Intent(LoginActivity.this, MainActivity.class));
                        finish();
                    }

                    @Override
                    public void loginFailed(String errorMsg) {
                        progressDialogDismiss();
                        Snackbar.make(lin_main, errorMsg, Snackbar.LENGTH_SHORT).show();
                        getCode();
                    }
                });
            }

            @Override
            protected void onSuccess(Document document) {
                //登录时无用
            }

            @Override
            public void onError(ErrorCode errorCode, Response response) {
                super.onError(errorCode, response);
                progressDialogDismiss();
            }
        });

    }

}
