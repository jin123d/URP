package com.jin123d.app;

import android.app.Application;

import com.jin123d.util.UrpSpUtil;
import com.lzy.okgo.OkGo;

/**
 * Created by jin123d on 11/5 0005.
 **/

public class UrpApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        //必须调用初始化
        OkGo.init(this);
        //好处是全局参数统一,特定请求可以特别定制参数
        //以下都不是必须的，根据需要自行选择,一般来说只需要 debug,缓存相关,cookie相关的 就可以了
        OkGo.getInstance()
                //打开该调试开关,控制台会使用 红色error 级别打印log,并不是错误,是为了显眼,不需要就不要加入该行
                .debug("OkGo")
                //如果使用默认的 60秒,以下三行也不需要传
                .setConnectTimeout(10 * 1000)  //全局的连接超时时间
                .setReadTimeOut(10 * 1000)     //全局的读取超时时间
                .setWriteTimeOut(10 * 1000); //全局的写入超时时间

        UrpSpUtil.InitSp(getApplicationContext());
    }
}
