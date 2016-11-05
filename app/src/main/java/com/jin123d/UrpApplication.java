package com.jin123d;

import android.app.Application;

import com.jin123d.util.UrpSp;

/**
 * Created by jin123d on 11/5 0005.
 **/

public class UrpApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        UrpSp.InitSp(getApplicationContext());
    }
}
