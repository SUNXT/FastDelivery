package com.sun.fastdelivery.utils;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

/**
 * Created by sunxuedian on 2018/1/4.
 */

public class ToastUtils {

    protected static Context mContext;

    public static void init(Context context){
        mContext = context;
    }

    public static void showToast(String msg){
        if (mContext == null){
            Log.e("ToastUtils", "you have to init this context");
            return;
        }

        Toast.makeText(mContext, msg, Toast.LENGTH_SHORT).show();
    }
}
