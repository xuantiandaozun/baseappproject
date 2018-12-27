package com.system.baseapplibrary.base;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.LayoutInflater;

import com.alibaba.android.arouter.launcher.ARouter;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.system.baseapplibrary.BaseConfig;
import com.system.baseapplibrary.bean.EventCallBackBean;
import com.system.baseapplibrary.utils.AppManager;
import com.system.baseapplibrary.utils.SPUtils;


import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import butterknife.ButterKnife;
import me.yokeyword.fragmentation.SupportActivity;
import me.yokeyword.fragmentation.anim.DefaultHorizontalAnimator;
import me.yokeyword.fragmentation.anim.FragmentAnimator;

/**
 * 创建人： zhoudingwen
 * 创建时间：2018/4/2
 */

public abstract class MVPBaseActivity <P extends MVPBasePresenter> extends SupportActivity implements MVPBaseView{
    protected P mPresenter;
    public String mtoken;
    public String mUser;
    public String mUserId;
    private List<MVPBasePresenter> buildPresenter;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        init(savedInstanceState);
        setTheme(getThemeResId());
        setContentView(LayoutInflater.from(this).inflate(
                getLayoutResId(), null));
        //初始化数据
        ButterKnife.bind(this);
        //Arouter注入
        ARouter.getInstance().inject(this);
        //Mvp
        mPresenter = createPresenter();
        //填充View
        if (mPresenter != null) {
            mPresenter.attachView(this);
            buildPresenter=new ArrayList<>();

        }
        initInfo();
        initDatas();
        AppManager.addActivity(this);
    }

    /**
     * 初始化基本参数
     */
    private void initInfo() {
        String token = BaseConfig.getToken(getThisContext());
        if(!TextUtils.isEmpty(token)){
            mtoken=token;
        }
        String user = (String) SPUtils.get(getThisContext(), BaseConfig.USER, "");
        if(!TextUtils.isEmpty(user)){
            mUser=user;
        }
        String userid = (String) SPUtils.get(getThisContext(), BaseConfig.USERID, "");
        if(!TextUtils.isEmpty(userid)){
            mUserId=userid;
        }
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
                    refreshData(next,object);
                }
                break;
            case EventCallBackBean.CLOSE:
                pop();
                break;
            case EventCallBackBean.WHITEDATA:
                while (iterator.hasNext()) {
                    String next = iterator.next();
                    Object object = eventData.get(next);
                    withData(next,object);
                }
                break;
            default:
                break;
        }
    }
    /**
     * 建造presenter对象
     * @param presenter
     * @param <b>
     */
    private <b extends MVPBasePresenter> void buildPresenter(b presenter){
        presenter.attachView(this);
        buildPresenter.addAll((Collection<? extends MVPBasePresenter>) presenter);
    }

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
     * 创建presenter
     * @return
     */
    protected abstract P createPresenter();
    /**
     * 设置主题
     *
     * @return
     */
    protected abstract int getThemeResId();

    /**
     * 返回布局资源ID
     *
     * @return
     */
    protected abstract int getLayoutResId();

    /**
     * 初始化
     */
    protected abstract void init(Bundle savedInstanceState);

    /**
     * 实现功能，填充数据
     */
    protected abstract void initDatas();

    /**
     * 获取当前Context
     * @return
     */
    public Context getThisContext(){
        return this;
    }

    @Override
    public FragmentAnimator onCreateFragmentAnimator() {
        return new DefaultHorizontalAnimator();
    }

    /**
     * 处理传值
     *
     * @return
     */
    public HashMap<String, Object> getFilter() {
        if (getIntent() != null && getIntent().getSerializableExtra("filter") != null) {
            HashMap<String, Object> filter = (HashMap<String, Object>) getIntent().getSerializableExtra("filter");
            return filter;
        }
        return null;
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
    /**
     * 跳转到指定页面
     *
     * @param cls
     */
    public void goToActivity(Class<?> cls) {
        goToActivity(cls, null);
    }

    /**
     * 跳转到指定页面
     *
     * @param cls
     * @param filter
     */
    public void goToActivity(Class<?> cls, HashMap<String, Object> filter) {
        Intent intent = new Intent();
        intent.setClass(this, cls);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        if (filter != null) {
            intent.putExtra("filter", filter);
        }
        startActivity(intent);
    }
    /**
     * 关闭Activity
     */
    public void CloseActivity() {
        finish();

    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mPresenter != null) {
            mPresenter.detachView();
            if(buildPresenter!=null&&buildPresenter.size()!=0){
                for (int i=0;i<buildPresenter.size();i++){
                    MVPBasePresenter mvpBasePresenter = buildPresenter.get(i);
                    mvpBasePresenter.detachView();
                }
                buildPresenter=null;
            }
        }

    }


}
