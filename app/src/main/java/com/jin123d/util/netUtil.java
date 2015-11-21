package com.jin123d.util;

import android.util.Log;

import com.jin123d.Interface.GetNetData;
import com.jin123d.Interface.GetZpInterface;

import java.util.List;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;


public class netUtil {
    public static void doPost(String url, String cookie, List<NameValuePair> nameValuePair, final GetNetData getNetData) {
        HttpPost http = new HttpPost(url);
        Log.d("URL", url);
        if (cookie != null) {
            http.addHeader("Cookie", "JSESSIONID=" + cookie);
        }
        try {
            DefaultHttpClient dhc = new DefaultHttpClient();
            http.setEntity(new UrlEncodedFormEntity(nameValuePair, "GBK"));
            HttpParams param = new BasicHttpParams();
            HttpConnectionParams.setConnectionTimeout(param, 5000); //设置连接超时
            HttpConnectionParams.setSoTimeout(param, 10000); //设置请求超时
            dhc.setParams(param);
            HttpResponse res = dhc.execute(http);
            switch (res.getStatusLine().getStatusCode()) {
                case 200:
                    HttpEntity entity = res.getEntity();
                    getNetData.getDataSuccess(EntityUtils.toString(entity, HTTP.UTF_8));
                    break;
                case 302:
                    getNetData.getDataSession();
                    break;
            }
        } catch (Exception e) {
            getNetData.getDataFail();
        }
    }


    public static void getPostData(String url, String cookie, final GetNetData getData) {
        HttpPost http = new HttpPost(url);
        if (cookie != null) {
            http.addHeader("Cookie", "JSESSIONID=" + cookie);
        }
        try {
            DefaultHttpClient dhc = new DefaultHttpClient();
            HttpParams param = new BasicHttpParams();
            HttpConnectionParams.setConnectionTimeout(param, 5000); //设置连接超时
            HttpConnectionParams.setSoTimeout(param, 10000); //设置请求超时
            dhc.setParams(param);
            HttpResponse res = dhc.execute(http);
            switch (res.getStatusLine().getStatusCode()) {
                case 200:
                    HttpEntity entity = res.getEntity();
                    getData.getDataSuccess(EntityUtils.toString(entity, HTTP.UTF_8));
                    break;
                case 302:
                    getData.getDataSession();
                    break;
            }
        } catch (Exception e) {
            getData.getDataFail();
        }
    }


    public static void getZp(String url, String cookie, final GetZpInterface getZpInterface) {
        HttpPost http = new HttpPost(url);
        if (cookie != null) {
            http.addHeader("Cookie", "JSESSIONID=" + cookie);
        }
        try {
            DefaultHttpClient dhc = new DefaultHttpClient();
            HttpParams param = new BasicHttpParams();
            HttpConnectionParams.setConnectionTimeout(param, 5000); //设置连接超时
            HttpConnectionParams.setSoTimeout(param, 10000); //设置请求超时
            dhc.setParams(param);
            HttpResponse res = dhc.execute(http);

            switch (res.getStatusLine().getStatusCode()) {
                case 200:
                    HttpEntity entity = res.getEntity();
                    byte[] bytes = EntityUtils.toByteArray(entity);
                    getZpInterface.getZpSuccess(bytes);
                    break;
                case 302:
                    getZpInterface.getZpSession();
                    break;
            }
        } catch (Exception e) {
            getZpInterface.getZpFail();
        }
    }
}
