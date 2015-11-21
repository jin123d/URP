package com.jin123d.models;

/**
 * Created by jin123d on 2015/11/7.
 */
public class PgInfoModels {
    private String wjbm; //问卷编码 wjbm	0000000083
    private String bpr; //被评人 bpr	104226
    private String kcm; //课程名
    private String pgnr;

    public String getWjbm() {
        return wjbm;
    }

    public void setWjbm(String wjbm) {
        this.wjbm = wjbm;
    }

    public String getBpr() {
        return bpr;
    }

    public void setBpr(String bpr) {
        this.bpr = bpr;
    }

    public String getKcm() {
        return kcm;
    }

    public void setKcm(String kcm) {
        this.kcm = kcm;
    }

    public String getPgnr() {
        return pgnr;
    }

    public void setPgnr(String pgnr) {
        this.pgnr = pgnr;
    }

    public PgInfoModels(String wjbm, String bpr, String kcm, String pgnr) {
        this.wjbm = wjbm;
        this.bpr = bpr;
        this.kcm = kcm;
        this.pgnr = pgnr;
    }
}
