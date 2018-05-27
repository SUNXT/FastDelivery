package com.sun.fastdelivery.model;

import com.sun.fastdelivery.R;
import com.sun.fastdelivery.bean.ResponseBean;
import com.sun.fastdelivery.bean.RiderUser;
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

    public static final int TYPE_USER = 0;
    public static final int TYPE_RIDER_USER = 1;

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
     * 用户、骑手登录
     * @param phone
     * @param userType
     * @param onModelCallback
     */
    public void login(final String phone, int userType, final OnModelCallback<User> onModelCallback){
        Map<String, Object> params = new HashMap<>();
        params.put("userPhone", phone);
        if (userType == TYPE_USER){
            params.put("userRole", "用户");
        }else {
            params.put("userRole", "骑手");
        }
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

    /**
     * 用户请求网络进行登录操作
     * @param phone
     * @param onModelCallback
     */
    public void userLogin(final String phone, final OnModelCallback<User> onModelCallback){
        login(phone, TYPE_USER, onModelCallback);
    }

    /**
     * 骑手登录操作
     * @param phone
     * @param onModelCallback
     */
    public void riderUserLogin(final String phone, final OnModelCallback<User> onModelCallback){
        login(phone, TYPE_RIDER_USER, onModelCallback);
    }

    /**
     * 骑手注册
     * @param phone
     * @param onModelCallback
     */
    public void riderUserRegister(String phone, final OnModelCallback<String> onModelCallback){
        Map<String, Object> params = new HashMap<>();
        params.put("userPhone", phone);
        OkHttpUtils.executeRequest(UrlParamsUtils.URL_RIDER_USER_REGISTER, params, onModelCallback, new OkHttpUtils.OnSuccessCallBack() {
            @Override
            public void onSuccess(ResponseBean responseBean) {
                if (ResponseBean.isSuccessCode(responseBean.getCode())){
                    onModelCallback.onSuccess("注册成功");
                }else {
                    onModelCallback.onFailure(responseBean.getCode(), responseBean.getMessage());
                }
            }
        });
    }

    /**
     * 查询骑手的信息
     * @param user
     * @param onModelCallback
     */
    public void queryRiderUserInfo(RiderUser user, final OnModelCallback<RiderUser> onModelCallback){
        Map<String, Object> params = new HashMap<>();
        params.put("token", user.getToken());
        params.put("userPhone", user.getUserPhone());
        params.put("userId", user.getUserId());
        params.put("userRole", "骑手");
        OkHttpUtils.executeRequest(UrlParamsUtils.URL_QUERY_RIDER_USER_INFO, params, onModelCallback, new OkHttpUtils.OnSuccessCallBack() {
            @Override
            public void onSuccess(ResponseBean responseBean) {
                if (ResponseBean.isSuccessCode(responseBean.getCode())){
                    onModelCallback.onSuccess(JsonUtils.fromJson(RiderUser.class, responseBean.getContent()));
                }else {
                    onModelCallback.onFailure(responseBean.getCode(), responseBean.getMessage());
                }
            }
        });
    }

}
