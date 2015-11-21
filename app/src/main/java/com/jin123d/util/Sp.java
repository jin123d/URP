package com.jin123d.util;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by jin123d on 2015/9/16.
 */
public class Sp {
    static SharedPreferences spf;
    private Context c;
    private static final String SHARE_PREFERENCES = "set";
    private static final String COOKIE = "cookie";
    private static final String Zjh = "zjh";
    private static final String Mm = "mm";
    private static final String  REMEMBER_MM = "RememberMm";
    private static final String  AUTO = "Auto";

    public Sp(Context c) {
        super();
        this.c = c;
        spf = c.getSharedPreferences(SHARE_PREFERENCES, Context.MODE_PRIVATE);
    }

    public  String getCookie() {
        return spf.getString(COOKIE, null);
    }

    public  void setCookie(String cookie) {
        spf.edit().putString(COOKIE, cookie).commit();
    }


    public String getZjh(){
        return spf.getString(Zjh, null);
    }


    public void setZjh(String zjh){
        spf.edit().putString(Zjh, zjh).commit();
    }


    public  String getMm() {
        return spf.getString(Mm, null);
    }

    public void setMm(String mm){
        spf.edit().putString(Mm, mm).commit();
    }


    public void setRememberMm(boolean rememberMm){
        spf.edit().putBoolean(REMEMBER_MM, rememberMm).commit();
    }


    public boolean getRememberMM(){
        return spf.getBoolean(REMEMBER_MM,false);
    }


    public void setAuto(boolean auto){
        spf.edit().putBoolean(AUTO, auto).commit();
    }

    public boolean getAuto(){
        return spf.getBoolean(AUTO,false);
    }

}
