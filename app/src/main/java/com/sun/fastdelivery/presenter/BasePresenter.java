package com.sun.fastdelivery.presenter;

import java.lang.ref.Reference;
import java.lang.ref.WeakReference;

/**
 * 对P层的封装，主要对View进行统一管理；避免P层对View层的强应用引起的内存泄漏
 * Created by SUN on 2018/4/27.
 */

public abstract class BasePresenter<V> {

    protected Reference<V> mViewRef;//对View的引用

    /**
     * 绑定View
     * @param view
     */
    public void attachView(V view){
        mViewRef = new WeakReference<V>(view);
    }

    /**
     * 解绑View
     */
    public void detachView(){
        if (mViewRef != null){
            mViewRef.clear();
            mViewRef = null;
        }
    }

    /**
     * 获取绑定的View对象
     * @return
     */
    public V getView(){
        return mViewRef.get();
    }

    /**
     * 判断View是否已经绑定
     * @return
     */
    public boolean isViewAttached(){
        return mViewRef != null && mViewRef.get() != null;
    }
}
