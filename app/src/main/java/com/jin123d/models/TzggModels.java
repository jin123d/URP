package com.jin123d.models;

/**
 * Created by jin123d on 2015/9/22.
 */
public class TzggModels {
    private String title;
    private String url;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public TzggModels(String title, String url) {
        this.title = title;
        this.url = url;
    }
}
