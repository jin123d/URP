package com.jin123d.util;

import android.content.Context;
import android.text.TextUtils;

import com.jin123d.Interface.UrpUserListener;

import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

/**
 * Created by jin123d on 11/5 0005.
 **/

public class UrpGetData {

    public static void getUerInfo(Context context, String htmlStr, final UrpUserListener.GetInfoListener getInfoListener) {
        String cookie = UrpSp.getCookie();
        JsoupUtil.isLogin(context, htmlStr, new UrpUserListener.UserStateListener() {
            @Override
            public void loginSuccess(Document doc) {
                String str = "";
                Elements es = doc.select("table[id=tblView]");
                if (es.size() > 0) {
                    Elements es_2 = es.get(0).select("tr");
                    for (int i = 0; i < es_2.size(); i++) {
                        Elements es_3 = es_2.get(i).select("td");
                        for (int j = 0; j < es_3.size(); j++) {
                            str = TextUtils.concat(str, "\n", es_3.get(j).text()).toString();
                            getInfoListener.getUserInfoSuccess(str);
                        }
                    }
                }
            }

            @Override
            public void loginFailed(String errorMsg) {
                getInfoListener.loginFailed(errorMsg);
            }
        });
    }

    public static void login(){

    }
}
