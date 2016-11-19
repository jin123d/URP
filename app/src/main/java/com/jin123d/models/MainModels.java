package com.jin123d.models;

/**
 * Created by jin123d on 2015/9/20.
 **/
public class MainModels {
    private int imgId;
    private String name;

    public int getImgId() {
        return imgId;
    }

    public void setImgId(int imgId) {
        this.imgId = imgId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public MainModels(int imgId, String name) {
        this.imgId = imgId;
        this.name = name;
    }
}
