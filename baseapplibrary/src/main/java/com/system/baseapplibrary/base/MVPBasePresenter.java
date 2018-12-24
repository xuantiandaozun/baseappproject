package com.system.baseapplibrary.base;

import java.lang.ref.Reference;
import java.lang.ref.WeakReference;

/**
 * Created by zbmobi on 2017/2/28.
 * 通过弱引用和Activity以及Fragment的生命周期预防内存泄露的问题
 */

public abstract class MVPBasePresenter {

    /**
     * //View 接口类型的弱引用
     */
    protected Reference<MVPBaseView> mViewRef;

    public void attachView(MVPBaseView view) {
        mViewRef = new WeakReference<MVPBaseView>(view);
    }

    protected MVPBaseView getView() {
        return mViewRef.get();
    }

    public boolean isViewAttached() {
        return mViewRef != null && mViewRef.get() != null;
    }

    public void detachView() {
        if (mViewRef != null) {
            mViewRef.clear();
            mViewRef = null;
        }
    }

}