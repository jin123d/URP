package com.jin123d.util;

import android.text.TextUtils;
import android.util.Log;

import com.jin123d.Interface.UrpUserListener;
import com.jin123d.models.CjModels;
import com.jin123d.models.ZjsjModels;

import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by jin123d on 11/3 0003.
 **/

public class JsoupUtil {


    public static void isLogin(Document document, final UrpUserListener.UserStateListener isLoginListener) {
        String title = document.title();
        if (TextUtils.equals("学分制综合教务", title)) {
            //登录成功
            isLoginListener.loginSuccess();
        } else if (TextUtils.equals("URP 综合教务系统 - 登录", title)) {
            String error = document.select("[class=errorTop]").text();
            //登录失败
            isLoginListener.loginFailed(error);
        } else {
            isLoginListener.loginFailed("未知错误");
            Log.e("login", "未知错误");
        }
    }


    /**
     * 检查cookie是否过期
     *
     * @param document document
     * @return 过期true
     */
    public static boolean checkCookieExpire(Document document) {
        return TextUtils.equals("URP 综合教务系统 - 登录", document.title());
    }


    /**
     * 获取自主实践信息
     *
     * @param document document
     * @return 自主实践list
     */
    public static List<ZjsjModels> getZjsj(Document document) {
        List<ZjsjModels> list = new ArrayList<>();
        Elements e2 = document.select("table");
        if (e2.size() > 0) {
            Elements es = e2.get(4).select("tr");
            if (es.size() > 1) {
                for (int i = 1; i < es.size(); i++) {
                    Elements e = es.get(i).select("td");
                    if (e.size() > 4) {
                        String mc = e.get(2).text();
                        String bz = e.get(3).text();
                        String xf = e.get(4).text();
                        ZjsjModels ZjsjModels = new ZjsjModels(mc, bz, xf);
                        list.add(ZjsjModels);
                    }
                }
            }
        }
        return list;
    }

    public static List<CjModels> getAllCj(Document document) {
        List<CjModels> list = new ArrayList<>();
        Elements es = document.select("[class=titleTop2]");
        if (es.size() != 0) {
            for (int i = 0; i < es.size(); i++) {
                Elements es_2 = es.get(i).select("tr").select("td").select("table").get(0).select("tr");
                for (int j = 0; j < es_2.size(); j++) {
                    Elements es_3 = es_2.get(j).select("td");
                    if (es_3.size() > 0) {
                        String ckm = es_3.get(2).text();
                        String xf = es_3.get(4).text();
                        String cj = es_3.get(6).text();
                        CjModels cjModels = new CjModels(ckm, xf, cj);
                        list.add(cjModels);
                    }
                }
            }
        }
        return list;
    }
}
