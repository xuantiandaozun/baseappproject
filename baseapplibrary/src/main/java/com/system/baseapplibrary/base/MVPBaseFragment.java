package com.system.baseapplibrary.base;

import android.arch.lifecycle.Observer;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.alibaba.android.arouter.launcher.ARouter;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.system.baseapplibrary.BaseConfig;
import com.system.baseapplibrary.bean.EventCallBackBean;
import com.system.baseapplibrary.utils.LiveEventBus;
import com.system.baseapplibrary.utils.SPUtils;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import butterknife.ButterKnife;
import butterknife.Unbinder;
import me.yokeyword.fragmentation.SupportFragment;

/**
 * 创建人： zhoudingwen
 * 创建时间：2018/4/2
 */

public abstract class MVPBaseFragment<P extends MVPBasePresenter> extends SupportFragment implements MVPBaseView {
    /*分页相关*/
    /* 每页最大分页数量 */
    public int mPageSize = 20;
    public static final int mMaxPageSize = Integer.MAX_VALUE;
    public static final int mNormalPageSize = 20;
    /* 当前页码 */
    public int mPageIndex = 1;
    public View rootView;
    protected P mPresenter;
    private Unbinder unbinder;
    public String mtoken;
    public String mUser;
    public String mUserId;
    protected List<MVPBasePresenter> buildPresenter;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //Arouter注入
        ARouter.getInstance().inject(this);
        init();
        //MVP
        mPresenter = createPresenter();
        if (mPresenter != null) {
            mPresenter.attachView(this);
            buildPresenter = new ArrayList<>();
        }
        initInfo();
        LiveEventBus.get()
                .with("base_fragment", String.class)
                .observe(this, new Observer<String>() {
                    @Override
                    public void onChanged(@Nullable String s) {
                        switch (s) {
                            case "initInfo":
                                initInfo();
                                break;
                        }
                    }
                });
    }

    /**
     * 初始化基本参数
     */
    private void initInfo() {
        String token = BaseConfig.getToken(getContext());

        mtoken = token;

        String user = (String) SPUtils.get(getThisContext(), BaseConfig.USER, "");

        mUser = user;

        String userid = (String) SPUtils.get(getThisContext(), BaseConfig.USERID, "");

        mUserId = userid;

    }


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        rootView = inflater.inflate(getLayoutResId(), container, false);
        unbinder = ButterKnife.bind(this, rootView);
        return rootView;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initDatas();
    }

    @Override
    public void callBack(EventCallBackBean bean) {
        int eventNumber = bean.getEventNumber();
        HashMap<String, Object> eventData = bean.getEventData();
        Set<String> keySet = eventData.keySet();
        Iterator<String> iterator = keySet.iterator();
        switch (eventNumber) {
            case EventCallBackBean.REFRESH:
                while (iterator.hasNext()) {
                    String next = iterator.next();
                    Object object = eventData.get(next);
                    refreshData(next, object);
                }
                break;
            case EventCallBackBean.CLOSE:
                pop();
                break;
            case EventCallBackBean.WHITEDATA:
                while (iterator.hasNext()) {
                    String next = iterator.next();
                    Object object = eventData.get(next);
                    withData(next, object);
                }
                break;
            default:
                break;
        }
    }

    /**
     * 建造presenter对象
     *
     * @param presenter
     */
    protected void buildPresenter(MVPBasePresenter presenter) {
        presenter.attachView(this);
        buildPresenter.add(presenter);
    }

    /**
     * 创建Presenter
     *
     * @return
     */
    protected abstract P createPresenter();

    /**
     * 初始化
     */
    protected abstract void init();

    /**
     * 返回布局资源ID
     *
     * @return
     */
    protected abstract int getLayoutResId();

    /**
     * 刷新数据
     *
     * @return
     */
    protected abstract void refreshData(String key, Object object);

    /**
     * 加载数据
     *
     * @return
     */
    protected abstract void withData(String key, Object object);

    /**
     * 实现功能，填充数据
     */
    protected abstract void initDatas();

    /**
     * 获取context
     *
     * @return
     */
    public Context getThisContext() {
        return getActivity();
    }

    /**
     * 关闭Fragment
     */
    public void closeFragment() {
        Bundle bundle = new Bundle();
        setFragmentResult(RESULT_OK, bundle);
        pop();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
        if (mPresenter != null) {
            mPresenter.detachView();
            if (buildPresenter != null && buildPresenter.size() != 0) {
                for (int i = 0; i < buildPresenter.size(); i++) {
                    MVPBasePresenter mvpBasePresenter = buildPresenter.get(i);
                    mvpBasePresenter.detachView();
                }
                buildPresenter = null;
            }
        }
    }

    /**
     * 根据String获取参数
     *
     * @param params
     * @return
     */
    public HashMap<String, Object> getHashMapByParams(String params) {
        if (TextUtils.isEmpty(params)) {
            return new HashMap<>();
        }
        Type type = new TypeToken<HashMap<String, Object>>() {
        }.getType();
        HashMap<String, Object> filter = new Gson().fromJson(params, type);
        return filter;
    }
}
