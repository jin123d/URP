package com.jin123d.util;

import android.graphics.Bitmap;
import android.text.TextUtils;

import com.jin123d.Interface.GetCodeListener;
import com.jin123d.models.PgInfoModels;
import com.jin123d.util.okgo.JsoupCallBack;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.BitmapCallback;
import com.lzy.okgo.model.HttpHeaders;
import com.lzy.okgo.model.HttpParams;

import org.jsoup.nodes.Document;

import okhttp3.Call;
import okhttp3.Response;


public class HttpUtil {


    /**
     * 获取验证码
     *
     * @param tag             context
     * @param getCodeListener 回调
     */
    public static void getCode(Object tag, final GetCodeListener getCodeListener) {
        OkGo.get(TextUtils.concat(UrpUrl.URL, UrpUrl.URL_YZM).toString())
                .tag(tag)
                .execute(new BitmapCallback() {
                    @Override
                    public void onSuccess(Bitmap bitmap, Call call, Response response) {
                        getCodeListener.getSuccess(bitmap);
                        // bitmap 即为返回的图片数据
                        UrpSpUtil.setCookie(UrlUtil.getCookie(response.header("Set-Cookie")));
                    }

                    @Override
                    public void onError(Call call, Response response, Exception e) {
                        super.onError(call, response, e);
                        getCodeListener.getError(e.getMessage());
                    }
                });
    }

    /**
     * 登录
     */
    public static void login(Object tag, String zjh, String mm, String v_yzm, JsoupCallBack callback) {
        HttpParams params = new HttpParams();
        params.put("zjh", zjh);
        params.put("mm", mm);
        params.put("v_yzm", v_yzm);
        OkGo.post(UrlUtil.getUrl(UrpUrl.URL_LOGIN))
                .params(params)
                .tag(tag)//
                .execute(callback);
    }


    /**
     * 学籍信息
     *
     * @param tag           tag
     * @param jsoupCallBack 回调
     */
    public static void getXjxx(Object tag, JsoupCallBack jsoupCallBack) {
        OkGo.get(UrlUtil.getUrl(UrpUrl.URL_XJXX)).tag(tag).execute(jsoupCallBack);
    }

    /**
     * 获取照片
     *
     * @param tag            tag
     * @param bitmapCallback 回调
     */
    public static void getPic(Object tag, BitmapCallback bitmapCallback) {
        OkGo.get(UrlUtil.getUrl(UrpUrl.URL_ZP)).tag(tag).execute(bitmapCallback);
    }

    /**
     * 自主实践
     *
     * @param tag           tag
     * @param jsoupCallBack 回调
     */
    public static void getZjsj(Object tag, JsoupCallBack jsoupCallBack) {
        OkGo.get(UrlUtil.getUrl(UrpUrl.URL_ZJSJ)).tag(tag).execute(jsoupCallBack);
    }

    /**
     * 学分绩点
     *
     * @param tag           tag
     * @param jsoupCallBack 回调
     */
    public static void getXfjd(Object tag, JsoupCallBack jsoupCallBack) {
        OkGo.get(UrlUtil.getUrl(UrpUrl.URL_XFDJ)).tag(tag).execute(jsoupCallBack);
    }


    /**
     * 获取评估列表
     *
     * @param tag           tag
     * @param jsoupCallBack 回调
     */
    public static void getJxpgList(Object tag, JsoupCallBack jsoupCallBack) {
        OkGo.get(UrlUtil.getUrl(UrpUrl.URL_JXPG_LIST)).tag(tag).execute(jsoupCallBack);
    }

    /**
     * 教学评估
     *
     * @param tag           tag
     * @param pgInfoModels  评估科目
     * @param isZq          是否是中期评教
     * @param jsoupCallBack 回调
     */
    public static void pg_1(final Object tag, final PgInfoModels pgInfoModels, final boolean isZq, final JsoupCallBack jsoupCallBack) {
        HttpParams httpParams = new HttpParams();
        httpParams.put("wjbm", pgInfoModels.getWjbm());
        httpParams.put("bpr", pgInfoModels.getBpr());
        httpParams.put("pgnr", pgInfoModels.getPgnr());
        httpParams.put("oper", "wjShow");
        OkGo.post(UrlUtil.getUrl(UrpUrl.URL_PG)).params(httpParams).tag(tag).execute(new JsoupCallBack() {
            @Override
            public void onSuccess(Document document, Call call, Response response) {
                pg_2(tag, pgInfoModels, isZq, jsoupCallBack);
            }
        });
    }

    /**
     * @param tag          tag
     * @param pgInfoModels 评估科目
     * @param isZq         是否是中期评教
     */
    private static void pg_2(Object tag, PgInfoModels pgInfoModels, boolean isZq, JsoupCallBack jsoupCallBack) {
        HttpParams httpParams = new HttpParams();
        httpParams.put("wjbm", pgInfoModels.getWjbm());
        httpParams.put("bpr", pgInfoModels.getBpr());
        httpParams.put("pgnr", pgInfoModels.getPgnr());
        if (isZq) {
            httpParams.put("0000000045", "10_1");
        } else {
            for (int i = 24; i <= 33; i++) {
                httpParams.put("00" + i, "100_1");
            }
        }
        httpParams.put("zgpj", "good");
        OkGo.post(UrlUtil.getUrl(UrpUrl.URL_JXPG)).params(httpParams).tag(tag).execute(jsoupCallBack);
    }


    /**
     * 获取不及格成绩列表
     *
     * @param tag           tag
     * @param jsoupCallBack 回调
     */
    public static void getBJgList(Object tag, JsoupCallBack jsoupCallBack) {
        OkGo.get(UrlUtil.getUrl(UrpUrl.URL_BJG)).tag(tag).execute(jsoupCallBack);
    }

    /**
     * 获取全部成绩列表
     *
     * @param tag           tag
     * @param jsoupCallBack 回调
     */
    public static void getAllCjList(Object tag, JsoupCallBack jsoupCallBack) {
        OkGo.get(UrlUtil.getUrl(UrpUrl.URL_QB)).tag(tag).execute(jsoupCallBack);
    }


    public static HttpHeaders getHeader() {
        HttpHeaders headers = new HttpHeaders();
        headers.put("Cookie", "JSESSIONID=" + UrpSpUtil.getCookie());
        return headers;
    }
}
