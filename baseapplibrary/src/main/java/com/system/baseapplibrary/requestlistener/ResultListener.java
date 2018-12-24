package com.system.baseapplibrary.requestlistener;

/**
 * 用于网络接口回调
 * Created by zbmobi on 15/11/12.
 */
public interface ResultListener<T> {
    public void onStart();
    public void onEnd();
    public void onSuccess(T data);
    public void onFailure(String message);
}
