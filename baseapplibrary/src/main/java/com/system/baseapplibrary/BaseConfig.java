package com.system.baseapplibrary;

import android.content.Context;


public class BaseConfig {
    /**
     * token令牌
     */
    public static String TOKEN="token";
    /**
     * 自动登录标识
     */
    public static String ISLOGIN="isLogin";
    /**
     * 管理员标识
     */
    public static String ISADMIN="isAdmin";
    /**
     * 管理员标识
     */
    public static String USERINFO="userinfo";
    /**
     * 账号
     */
    public static String USER="user";
    /**
     * 密码
     */
    public static String PASSWORD="password";
    /**
     * 用户id
     */
    public static String USERID="userid";
    /**
     * 管理员标识
     */
    public static String TECENTTOKEN="tecentsign";

    public static void setToken(Context context, String token) {
        SPUtils.put(context,TOKEN,token);
    }

    public static String getToken(Context context){
        return (String)  SPUtils.get(context, TOKEN, "");
    }

}
