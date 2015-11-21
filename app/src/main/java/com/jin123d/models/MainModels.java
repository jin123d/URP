package com.jin123d.models;

import android.widget.ImageView;
import android.widget.TextView;

/**
 * Created by jin123d on 2015/9/20.
 */
public class MainModels {
    private String  img;
    private String  name;

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public MainModels(String img, String name) {
        this.img = img;
        this.name = name;
    }
}
