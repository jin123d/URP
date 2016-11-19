package com.jin123d.util.okgo;

import com.lzy.okgo.convert.Converter;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import okhttp3.Response;

public class JsoupConvert implements Converter<Document> {

    public static JsoupConvert create() {
        return JsoupConvert.ConvertHolder.convert;
    }

    private static class ConvertHolder {
        private static JsoupConvert convert = new JsoupConvert();
    }

    @Override
    public Document convertSuccess(Response value) throws Exception {
        return Jsoup.parse(value.body().string());
    }
}