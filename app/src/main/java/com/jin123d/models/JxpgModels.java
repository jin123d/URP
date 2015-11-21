package com.jin123d.models;

/**
 * Created by jin123d on 2015/11/7.
 */
public class JxpgModels {
    private String wjmc; //问卷名称
    private String bprm; //被评人名
    private String pgnr; //评估内容
    private String sfpg; //是否评估


    public String getWjmc() {
        return wjmc;
    }

    public void setWjmc(String wjmc) {
        this.wjmc = wjmc;
    }

    public String getBprm() {
        return bprm;
    }

    public void setBprm(String bprm) {
        this.bprm = bprm;
    }

    public String getPgnr() {
        return pgnr;
    }

    public void setPgnr(String pgnr) {
        this.pgnr = pgnr;
    }

    public String getSfpg() {
        return sfpg;
    }

    public void setSfpg(String sfpg) {
        this.sfpg = sfpg;
    }


    /**
     * @param wjmc 问卷名称
     * @param pgnr 评估内容 这里为课程名
     * @param bprm 被评人名
     * @param sfpg 是否评价
     */
    public JxpgModels(String wjmc, String pgnr, String bprm, String sfpg) {
        this.pgnr = pgnr;
        this.wjmc = wjmc;
        this.bprm = bprm;
        this.sfpg = sfpg;
    }

}
