package com.sun.fastdelivery.utils;

import android.util.Log;
import android.webkit.JavascriptInterface;

import com.sun.fastdelivery.bean.City;

/**
 * 处理webView js 选中城市的回调
 */
public class ChooseCityToJs extends Object {

    public String TAG = getClass().getCanonicalName();

    OnChooseListener onChooseListener;

    public void setOnChooseListener(OnChooseListener onChooseListener) {
        this.onChooseListener = onChooseListener;
    }

    @JavascriptInterface
    public void chooseCity(String json){

        Log.d(TAG, "js返回的数据： " + json);
        if (onChooseListener == null){
            Log.e(TAG, "onChooseListener is null!");
            return;
        }

        City city = JsonUtils.fromJson(City.class, json);
        onChooseListener.onChoose(city);
    }

    public interface OnChooseListener{
        void onChoose(City city);//选中回调
    }
}