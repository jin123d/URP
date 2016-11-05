package com.jin123d.models;

/**
 * Created by jin123d on 2015/9/14.
 **/
public class ZjsjModels {

    private String xh;
    private String xm;
    private String mc;
    private String bz;
    private String xf;

    public String getBz() {
        return bz;
    }

    public void setBz(String bz) {
        this.bz = bz;
    }

    public String getXh() {
        return xh;
    }

    public void setXh(String xh) {
        this.xh = xh;
    }

    public String getXm() {
        return xm;
    }

    public void setXm(String xm) {
        this.xm = xm;
    }

    public String getMc() {
        return mc;
    }

    public void setMc(String mc) {
        this.mc = mc;
    }

    public String getXf() {
        return xf;
    }

    public void setXf(String xf) {
        this.xf = xf;
    }

    public ZjsjModels(String xh, String xm, String mc, String bz, String xf) {
        this.xh = xh;
        this.xm = xm;
        this.mc = mc;
        this.bz = bz;
        this.xf = xf;
    }

    public ZjsjModels(String mc, String bz, String xf) {
        this.mc = mc;
        this.bz = bz;
        this.xf = xf;
    }
}
