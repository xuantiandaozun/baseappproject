package com.system.baseapplibrary.utils;

import android.content.Context;
import android.widget.Toast;

import com.system.baseapplibrary.BaseMainApp;

/**
 * Created by 18081 on 2018/5/26.
 */

public class ToastUtil {
    /**
     * string  类型的msg
     */
    private static Context mContext = BaseMainApp.getContext();

    public static void showSnack(String content) {
        Toast.makeText(mContext, content, Toast.LENGTH_SHORT).show();
    }

    public static void showSnackLong(String content) {
        Toast.makeText(mContext, content, Toast.LENGTH_LONG).show();
    }

}
