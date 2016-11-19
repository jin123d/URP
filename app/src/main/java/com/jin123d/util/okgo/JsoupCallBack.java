package com.jin123d.util.okgo;

import com.jin123d.util.ErrorCode;
import com.jin123d.util.HttpUtil;
import com.jin123d.util.JsoupUtil;
import com.lzy.okgo.callback.AbsCallback;
import com.lzy.okgo.request.BaseRequest;

import org.jsoup.nodes.Document;

import java.net.ConnectException;

import okhttp3.Call;
import okhttp3.Response;

/**
 * Created by hekr_jds on 11/15 0015.
 **/

public abstract class JsoupCallBack extends AbsCallback<Document> {


    /**
     * 成功获取数据
     *
     * @param document document
     */
    public void onSuccess(Document document) {

    }


    public void onError(ErrorCode errorCode) {

    }


    @Override
    public void onBefore(BaseRequest request) {
        super.onBefore(request);
        request.headers(HttpUtil.getHeader());
    }

    @Override
    public Document convertSuccess(Response response) throws Exception {
        Document document = JsoupConvert.create().convertSuccess(response);
        response.close();
        return document;
    }


    @Override
    public void onSuccess(final Document document, Call call, Response response) {
        if (JsoupUtil.checkCookieExpire(document)) {
            onError(ErrorCode.CookieExpire);
        } else {
            onSuccess(document);
        }
    }

    @Override
    public void onError(Call call, Response response, Exception e) {
        super.onError(call, response, e);
        if (e instanceof ConnectException) {
            onError(ErrorCode.TimeOUT);
        } else {
            onError(ErrorCode.UnKnowError);
        }
    }
}
