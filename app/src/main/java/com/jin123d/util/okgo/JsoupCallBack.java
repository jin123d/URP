package com.jin123d.util.okgo;

import android.widget.Toast;

import com.jin123d.app.UrpApplication;
import com.jin123d.urp.R;
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
    protected abstract void onSuccess(Document document);


    /**
     * 获取数据失败，非抽象方法，可重写
     *
     * @param errorCode 错误码
     * @param response  返回数据
     */
    public void onError(ErrorCode errorCode, Response response) {

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
            //cookie过期
            onError(ErrorCode.CookieExpire, response);
            Toast.makeText(UrpApplication.getContext(), R.string.loginFail, Toast.LENGTH_SHORT).show();
        } else {
            //获取数据成功
            onSuccess(document);
        }
    }

    @Override
    public void onError(Call call, Response response, Exception e) {
        super.onError(call, response, e);
        e.printStackTrace();
        if (e instanceof ConnectException) {
            //获取数据超时
            onError(ErrorCode.TimeOUT, response);
            Toast.makeText(UrpApplication.getContext(), R.string.getDataTimeOut, Toast.LENGTH_SHORT).show();
        } else {
            //其他错误
            onError(ErrorCode.UnKnowError, response);
        }
    }
}
