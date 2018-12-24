package com.system.baseapplibrary.base;

import com.system.baseapplibrary.bean.EventCallBackBean;

/**
 * Created by zbmobi on 2016/12/28.
 */

public interface MVPBaseView {
    void showMessage(String message);
    void callBack(EventCallBackBean bean);
    void onFaild();

}
