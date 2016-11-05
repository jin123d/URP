package com.jin123d.util;

import android.content.Context;
import android.text.TextUtils;
import android.util.Log;

import com.jin123d.Interface.UrpUserListener;
import com.jin123d.urp.R;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

/**
 * Created by jin123d on 11/3 0003.
 **/

public class JsoupUtil {


    public static void isLogin(Context context, String htmlStr, final UrpUserListener.UserStateListener isLoginListener) {
        if (TextUtils.isEmpty(htmlStr)) {
            isLoginListener.loginFailed("获取到的内容为空");
            Log.e("login", "获取到的内容为空");
        } else {
            Document doc = Jsoup.parse(htmlStr);
            String title = doc.title();
            if (TextUtils.equals(context.getString(R.string.title_success), title)) {
                //登录成功
                isLoginListener.loginSuccess(doc);
            } else if (TextUtils.equals(context.getText(R.string.webTitle), title)) {
                String error = doc.select("[class=errorTop]").text();
                //登录失败
                isLoginListener.loginFailed(error);
            } else {
                isLoginListener.loginFailed("未知错误");
                Log.e("login", "未知错误");
            }
        }
    }

}
