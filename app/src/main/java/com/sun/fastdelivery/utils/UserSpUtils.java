package com.sun.fastdelivery.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.text.TextUtils;
import android.util.Log;

import com.sun.fastdelivery.bean.User;

/**
 * Created by sunxuedian on 2018/4/30.
 */

public class UserSpUtils {

    public static final String TAG = "UserSpUtils";

    public static final String USER_PHONE = "phone";

    private static User mUser;
    private static boolean mNeedReadFromSp = true;

    /**
     * 获取用户信息
     * @param context
     * @return
     */
    public static User getUser(Context context){
        if (!mNeedReadFromSp){
            return mUser;
        }

        //为空的时候，从内存中读取
        String phone = SharedPreferencesUtils.readString(context, USER_PHONE, "");
        if (TextUtils.isEmpty(phone)){
            return null;
        }

        Long id = SharedPreferencesUtils.readLong(context, phone+"_id", 0);
        String token = SharedPreferencesUtils.readString(context, phone + "_token", "");
        mUser = new User(id, phone, token);//构造用户对象
        mNeedReadFromSp = false;//下次不需要从本地读取
        return mUser;
    }

    /**
     * 保存用户信息到本地
     * @param context
     * @param user
     */
    public static void saveUser(Context context, User user){
        if (user == null){
            Log.e(TAG, "the user is null, fail save");
            return;
        }

        if (TextUtils.isEmpty(user.getUserPhone())){
            Log.e(TAG, "the user phone is null, fail save");
        }

        SharedPreferencesUtils.saveString(context, USER_PHONE, user.getUserPhone());
        SharedPreferencesUtils.saveString(context, user.getUserPhone() + "_token", user.getAllocatedToken());
        SharedPreferencesUtils.saveLong(context, user.getUserPhone() + "_id", user.getUserId());
        mUser = new User(user.getUserId(), user.getUserPhone(), user.getAllocatedToken());//更新当前用户对象
    }

    /**
     * 判断用户是否处于登录状态
     * @param context
     * @return
     */
    public static boolean isUserLogin(Context context){
        User user = getUser(context);
        return user != null &&!TextUtils.isEmpty(user.getAllocatedToken());
    }

    /**
     * 注销操作，将用户的Token清空
     * @param context
     */
    public static void logout(Context context){
        User mUser = getUser(context);
        if (mUser != null){
            SharedPreferencesUtils.saveString(context, mUser.getUserPhone() + "_token", "");
            mUser.setAllocatedToken("");
        }
    }
}