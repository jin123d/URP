package com.jin123d.app;

import android.support.annotation.LayoutRes;
import android.support.v7.app.AppCompatActivity;

/**
 * Created by jin123d on 11/19 0019.
 **/

public abstract class BaseActivity extends AppCompatActivity {
    protected abstract void initView();

    protected abstract void initData();

    @Override
    public void setContentView(@LayoutRes int layoutResID) {
        super.setContentView(layoutResID);
        initView();
        initData();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}
