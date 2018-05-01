package com.sun.fastdelivery.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.text.TextUtils;
import android.util.Log;

/**
 * Created by sunxuedian on 2018/3/23.
 */

public class SharedPreferencesUtils {
    
    private static final String SP_NAME = "AGP_SP";

    /**
     * 保存String
     * @param context
     * @param key
     * @param value
     * @return
     */
    public static boolean saveString(Context context, String key, String value){
        return saveObject(context, key, value);
    }

    /**
     * 保存Boolean
     * @param context
     * @param key
     * @param value
     * @return
     */
    public static boolean saveBoolean(Context context, String key, boolean value){
        return saveObject(context, key, value);
    }

    /**
     * 保存整型数
     * @param context
     * @param key
     * @param value
     * @return
     */
    public static boolean saveInt(Context context, String key, int value){
        return saveObject(context, key, value);
    }

    /**
     * 保存浮点数
     * @param context
     * @param key
     * @param value
     * @return
     */
    public static boolean saveFloat(Context context, String key, float value){
        return saveObject(context, key, value);
    }

    /**
     * 保存Long
     * @param context
     * @param key
     * @param value
     * @return
     */
    public static boolean saveLong(Context context, String key, long value){
        return saveObject(context, key, value);
    }

    /**
     * 保存对象
     * @param context
     * @param key
     * @param value
     * @return
     */
    private static boolean saveObject(Context context, String key, Object value){
        if (context == null){
            Log.e("SpUtils","the context can't be null!");
            return false;
        }

        if (TextUtils.isEmpty(key)){
            Log.e("SpUtils","the key can't be null or empty!");
            return false;
        }

        SharedPreferences sharedPreferences = context.getSharedPreferences(SP_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();

        if (value instanceof String){
            editor.putString(key, (String) value);
        }else if (value instanceof Boolean){
            editor.putBoolean(key, (Boolean) value);
        }else if (value instanceof Integer){
            editor.putInt(key, (Integer) value);
        }else if (value instanceof Float){
            editor.putFloat(key, (Float) value);
        }else if (value instanceof Long){
            editor.putLong(key, (Long) value);
        }else {
            Log.e("SpUtils","the value is null!");
            editor.apply();
            return false;
        }

        editor.apply();
        return true;
    }

    /**
     * 获取保存的String
     * @param context
     * @param key
     * @param defaultValue
     * @return
     */
    public static String readString(Context context, String key, String defaultValue){
        if (context == null){
            Log.e("SpUtils","the context can't be null!");
            return defaultValue;
        }

        if (TextUtils.isEmpty(key)){
            Log.e("SpUtils","the key can't be null or empty!");
            return defaultValue;
        }

        SharedPreferences sharedPreferences = context.getSharedPreferences(SP_NAME, Context.MODE_PRIVATE);
        return sharedPreferences.getString(key, defaultValue);
    }

    /**
     * 获取保存的Boolean
     * @param context
     * @param key
     * @param defaultValue
     * @return
     */
    public static boolean readBoolean(Context context, String key, boolean defaultValue){
        if (context == null){
            Log.e("SpUtils","the context can't be null!");
            return defaultValue;
        }

        if (TextUtils.isEmpty(key)){
            Log.e("SpUtils","the key can't be null or empty!");
            return defaultValue;
        }

        SharedPreferences sharedPreferences = context.getSharedPreferences(SP_NAME, Context.MODE_PRIVATE);
        return sharedPreferences.getBoolean(key, defaultValue);
    }

    /**
     * 获取保存的整型数
     * @param context
     * @param key
     * @param defaultValue
     * @return
     */
    public static int readInt(Context context, String key, int defaultValue){
        if (context == null){
            Log.e("SpUtils","the context can't be null!");
            return defaultValue;
        }

        if (TextUtils.isEmpty(key)){
            Log.e("SpUtils","the key can't be null or empty!");
            return defaultValue;
        }

        SharedPreferences sharedPreferences = context.getSharedPreferences(SP_NAME, Context.MODE_PRIVATE);
        return sharedPreferences.getInt(key, defaultValue);
    }

    /**
     * 获取保存的长整数
     * @param context
     * @param key
     * @param defaultValue
     * @return
     */
    public static long readLong(Context context, String key, long defaultValue){
        if (context == null){
            Log.e("SpUtils","the context can't be null!");
            return defaultValue;
        }

        if (TextUtils.isEmpty(key)){
            Log.e("SpUtils","the key can't be null or empty!");
            return defaultValue;
        }

        SharedPreferences sharedPreferences = context.getSharedPreferences(SP_NAME, Context.MODE_PRIVATE);
        return sharedPreferences.getLong(key, defaultValue);
    }

    /**
     * 获取保存的浮点数
     * @param context
     * @param key
     * @param defaultValue
     * @return
     */
    public static float readFloat(Context context, String key, float defaultValue){
        if (context == null){
            Log.e("SpUtils","the context can't be null!");
            return defaultValue;
        }

        if (TextUtils.isEmpty(key)){
            Log.e("SpUtils","the key can't be null or empty!");
            return defaultValue;
        }

        SharedPreferences sharedPreferences = context.getSharedPreferences(SP_NAME, Context.MODE_PRIVATE);
        return sharedPreferences.getFloat(key, defaultValue);
    }

}
