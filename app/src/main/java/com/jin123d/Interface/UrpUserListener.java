package com.jin123d.Interface;

import com.jin123d.models.CjModels;
import com.jin123d.models.PgInfoModels;
import com.jin123d.models.ZjsjModels;

import java.util.List;

/**
 * Created by jin123d on 11/5 0005.
 **/

public class UrpUserListener {

    interface IsLoginListener {
        void loginFailed(String error);
    }

    public interface UserStateListener extends IsLoginListener {
        void loginSuccess();
    }


    /**
     * 获取用户信息回调/学分绩点
     */
    public interface GetInfoListener extends IsLoginListener {
        void getUserInfoSuccess(String str);
    }

    /**
     * 获取用户成绩回调
     */
    public interface GetGradeListener extends IsLoginListener {
        void getGradeSuccess(List<CjModels> lists);
    }

    /**
     * 获取用户自主实践回调
     */
    public interface GetZjsjListener extends IsLoginListener {
        void getZjsjSuccess(List<ZjsjModels> list);
    }

    public interface GetPgInfoListener extends IsLoginListener {
        void getPgInfoListener(List<PgInfoModels> list);
    }


}
