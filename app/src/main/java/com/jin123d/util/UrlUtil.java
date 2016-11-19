package com.jin123d.util;

import android.text.TextUtils;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by hekr_jds on 11/15 0015.
 **/

public class UrlUtil {

    public static String getCookie(String response) {
        if (response != null && response.length() > 0) {
            if (response.contains(";")) {
                String[] cookies = response.split(";");
                if (cookies.length > 0 && cookies[0].contains("=")) {
                    response = cookies[0];
                }
            }
            String[] ids = response.split("=");
            if (ids.length > 0) {
                return ids[1];
            }
        }
        return null;
    }


    /**
     * 获取访问的url
     *
     * @param baseUrl   baseUrl
     * @param urlParams 参数
     * @return url
     */
    public static String getUrl(String baseUrl, HashMap<String, Object> urlParams) {
        // 添加url参数
        if (urlParams != null && urlParams.size() > 0) {
            StringBuilder sb = null;
            for (Map.Entry<String, Object> it : urlParams.entrySet()) {
                String key = it.getKey();
                Object value = it.getValue();
                //key与value均不能为空
                if (!TextUtils.isEmpty(key) && value != null) {
                    if (sb == null) {
                        sb = new StringBuilder();
                        sb.append("?");
                    } else {
                        sb.append("&");
                    }
                    sb.append(key).append("=").append(value);
                }
            }
            if (sb != null) {
                baseUrl = TextUtils.concat(baseUrl, sb).toString();
            }
        }
        return baseUrl;
    }


    /**
     * 合并网址
     *
     * @param url 后半截网址
     * @return 完整网址
     */
    public static String getUrl(String url) {
        return TextUtils.concat(UrpUrl.URL, url).toString();
    }
}
