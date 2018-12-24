package com.system.baseapplibrary;

import android.content.Context;
import android.support.multidex.MultiDex;
import android.support.multidex.MultiDexApplication;

import com.alibaba.android.arouter.launcher.ARouter;
import com.system.baseapplibrary.utils.SPUtils;

/**
 * 创建人： zhoudingwen
 * 创建时间：2018/4/2
 */

public class BaseMainApp extends MultiDexApplication {
    private static  BaseMainApp mainApp;
    @Override
    public void onCreate() {
        super.onCreate();
        mainApp=this;
    }

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        MultiDex.install(this);
    }
    public static Context getContext(){
        return mainApp;
    }
}
