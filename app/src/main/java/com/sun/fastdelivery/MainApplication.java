package com.sun.fastdelivery;

import android.app.Application;

import com.sun.fastdelivery.utils.ToastUtils;

/**
 * Created by SUN on 2018/4/27.
 */

public class MainApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        ToastUtils.init(this);
    }
}
