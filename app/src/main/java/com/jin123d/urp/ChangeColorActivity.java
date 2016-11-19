package com.jin123d.urp;

import android.os.Bundle;
import android.widget.Button;

import com.jin123d.app.BaseActivity;


public class ChangeColorActivity extends BaseActivity {

    Button btn_blue, btn_red;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_color);

    }

    @Override
    protected void initView() {
        btn_blue = (Button) findViewById(R.id.btn_blue);
        btn_red = (Button) findViewById(R.id.btn_red);
    }

    @Override
    protected void initData() {

    }
}
