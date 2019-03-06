package com.system.baseapplibrary.requestlistener;


import android.content.Intent;

import com.google.gson.Gson;
import com.google.gson.JsonParseException;
import com.google.gson.JsonSyntaxException;
import com.jakewharton.retrofit2.adapter.rxjava2.HttpException;
import com.orhanobut.logger.Logger;
import com.system.baseapplibrary.BaseMainApp;
import com.system.baseapplibrary.utils.ToastUtil;


import org.json.JSONException;

import java.io.IOException;
import java.net.ConnectException;
import java.net.SocketTimeoutException;

import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;

/**
 * 创建人： zhoudingwen
 * 创建时间：2018/5/3
 * @author 18081
 */

public  class OnRequestCallback<T> implements Observer<T> {
    private ResultListener<T> listener;

    public OnRequestCallback(ResultListener listener) {
        this.listener = listener;
    }

    @Override
    public void onSubscribe(Disposable d) {
        listener.onStart();
    }

    @Override
    public void onNext(T t) {
        String s = new Gson().toJson(t);
        if(s.contains("登录过期")){
           // ToastUtil.showSnack("登录过期");
            Intent intent = new Intent("com.system.main");
            intent.putExtra("name","logout");
           // ToastUtil.showSnack("登录过期");
            BaseMainApp.getContext().sendBroadcast(intent);
        }else {
            listener.onSuccess(t);
        }

    }

    @Override
    public void onError(Throwable e) {
        if (e instanceof HttpException) {
            HttpException httpException = (HttpException) e;
            int code = httpException.code();
            try {
                String string = httpException.response().errorBody().string();
                Logger.e("异常数据: " ,string);
            } catch (IOException e1) {
                e1.printStackTrace();
            }
            if (code == 500 || code == 404) {
                listener.onFailure("服务器出错");
            }else {
                listener.onFailure("网络异常");
            }
        }else if(e instanceof JsonSyntaxException |e instanceof JsonParseException|e instanceof JSONException){
            listener.onFailure("json解析异常");
        } else if (e instanceof ConnectException) {
            listener.onFailure("网络断开,请打开网络!");
        } else if (e instanceof SocketTimeoutException) {
            listener.onFailure("网络连接超时!!");
        } else {
            listener.onFailure("发生未知错误" + e.getMessage());
        }
    }

    @Override
    public void onComplete() {
        listener.onEnd();
    }
}
