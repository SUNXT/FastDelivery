package com.sun.fastdelivery.model;

import com.sun.fastdelivery.bean.ResponseBean;
import com.sun.fastdelivery.bean.User;
import com.sun.fastdelivery.model.callback.OnModelCallback;
import com.sun.fastdelivery.utils.JsonUtils;
import com.sun.fastdelivery.utils.OkHttpUtils;
import com.sun.fastdelivery.utils.UrlParamsUtils;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by sunxuedian on 2018/4/30.
 */

public class UserModel {
    private static UserModel mInstance;
    private UserModel(){}

    public static UserModel getInstance(){
        if (mInstance == null){
            synchronized (UserModel.class){
                if (mInstance == null){
                    mInstance = new UserModel();
                }
            }
        }
        return mInstance;
    }

    /**
     * 用户请求网络进行登录操作
     * @param phone
     * @param onModelCallback
     */
    public void userLogin(final String phone, final OnModelCallback<User> onModelCallback){
        Map<String, Object> params = new HashMap<>();
        params.put("userPhone", phone);
        params.put("userRole", "用户");
        OkHttpUtils.executeRequest(UrlParamsUtils.URL_LOGIN, params, onModelCallback, new OkHttpUtils.OnSuccessCallBack() {
            @Override
            public void onSuccess(ResponseBean responseBean) {
                if (ResponseBean.isSuccessCode(responseBean.getCode())){
                    //将返回的数据进行转换，转换为user类
                    User user = JsonUtils.fromJson(User.class, responseBean.getContent());
                    user.setUserPhone(phone);
                    onModelCallback.onSuccess(user);
                }else {
                    onModelCallback.onFailure(responseBean.getCode(), responseBean.getMessage());
                }
            }
        });
    }
}
