package com.jin123d.Interface;

/**
 * Created by jin123d on 2015/11/18.
 */
public interface GetZpInterface {
    //获取照片成功
    void getZpSuccess(byte[] bytes);
    //获取照片失败
    void getZpFail();
    //获取失效
    void getZpSession();
}
