package com.sasucen.softinput;

import android.app.Application;

/**
 * 创建日期：2018/12/20 0020on 上午 10:29
 * 描述：
 * @author Vincent QQ：3332168769
 * 备注：
 */
public class App extends Application {
    public static App instance;

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
    }
}
