package com.jin123d.models;

/**
 * Created by jin123d on 2015/9/14.
 */
public class CjModels {
    private String kcm;
    private String xf;
    private String cj;

    public String getKcm() {
        return kcm;
    }

    public void setKcm(String kcm) {
        this.kcm = kcm;
    }

    public String getXf() {
        return xf;
    }

    public void setXf(String xf) {
        this.xf = xf;
    }

    public String getCj() {
        return cj;
    }

    public void setCj(String cj) {
        this.cj = cj;
    }

    public CjModels(String kcm, String xf, String cj) {
        this.kcm = kcm;
        this.xf = xf;
        this.cj = cj;
    }
}
