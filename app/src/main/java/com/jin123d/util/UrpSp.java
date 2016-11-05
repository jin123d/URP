package com.jin123d.util;

import android.content.Context;
import android.text.TextUtils;

/**
 * Created by jin123d on 2015/9/16.
 **/
public class UrpSp {
    private static final String COOKIE = "cookie";
    private static final String Zjh = "zjh";
    private static final String Mm = "mm";
    private static final String REMEMBER_MM = "RememberMm";
    private static final String AUTO = "Auto";

    private static String spCookie = "";

    public static void InitSp(Context context) {
        SpCache.init(context);
    }


    public static String getCookie() {
        if (TextUtils.isEmpty(spCookie)) {
            spCookie = SpCache.getString(COOKIE, spCookie);
        }
        return spCookie;
    }

    public static void setCookie(String cookie) {
        SpCache.putString(COOKIE, cookie);
        spCookie = cookie;
    }


    public static String getZjh() {
        return SpCache.getString(Zjh, "");
    }


    public static void setZjh(String zjh) {

        SpCache.putString(Zjh, zjh);
    }


    public static String getMm() {
        return SpCache.getString(Mm, "");
    }

    public static void setMm(String mm) {
        SpCache.putString(Mm, mm);
    }


    public static void setRememberMm(boolean rememberMm) {
        SpCache.putBoolean(REMEMBER_MM, rememberMm);
    }


    public static boolean getRememberMM() {
        return SpCache.getBoolean(REMEMBER_MM, false);
    }


    public static void setAuto(boolean auto) {
        SpCache.putBoolean(AUTO, auto);
    }

    public static boolean getAuto() {
        return SpCache.getBoolean(AUTO, false);
    }

}
