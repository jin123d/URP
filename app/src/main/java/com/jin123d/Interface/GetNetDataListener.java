package com.jin123d.Interface;

/**
 * Created by jin123d on 2015/11/18.
 */
public interface GetNetDataListener {
    //成功获取到数据
    void getDataSuccess(String Data);
    //获取数据失败
    void getDataFail();
    //Session到期
    void getDataSession();
}
