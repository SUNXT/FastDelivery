package com.sun.fastdelivery.presenter;

import android.app.Activity;
import android.text.TextUtils;
import android.util.Log;

import com.sun.fastdelivery.bean.User;
import com.sun.fastdelivery.model.UserModel;
import com.sun.fastdelivery.model.callback.OnModelCallback;
import com.sun.fastdelivery.utils.MyTextUtils;
import com.sun.fastdelivery.view.base.ILoginView;

import org.json.JSONException;
import org.json.JSONObject;

import cn.smssdk.EventHandler;
import cn.smssdk.SMSSDK;


/**
 * Created by sunxuedian on 2018/4/30.
 */

public class LoginPresenter extends BasePresenter<ILoginView> {

    private String TAG = getClass().getSimpleName();
    private EventHandler mEventHandler;
    private String mPhone;
    private boolean mCanSendCode = true;
    private int mUserType;

    public LoginPresenter(final Activity context, int userType){
        mUserType = userType;
        //处理SMSSDK 短信验证码的回调
        mEventHandler = new EventHandler(){
            @Override
            public void afterEvent(final int event, final int result, final Object data) {
                context.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {

                        if (!isViewAttached()){
                            Log.e(TAG, "the View is not attached！");
                            return;
                        }

                        mCanSendCode = true;//可以再次发送短信
                        if (result == SMSSDK.RESULT_COMPLETE) {
                            //回调完成
                            if (event == SMSSDK.EVENT_SUBMIT_VERIFICATION_CODE) {
                                //提交验证码成功
                                loginToService();
                            }else if (event == SMSSDK.EVENT_GET_VERIFICATION_CODE){
                                //获取验证码成功
                                getView().onSendCodeSuccess();
                            }else if (event ==SMSSDK.EVENT_GET_SUPPORTED_COUNTRIES){
                                //返回支持发送验证码的国家列表
                            }
                        }else{
                            ((Throwable)data).printStackTrace();
                            try {
                                JSONObject object = new JSONObject(((Throwable)data).getMessage());
                                getView().onFailure(object.optString("detail"));
                            } catch (JSONException e) {
                                e.printStackTrace();
                                //调用View处理验证失败的信息
                                getView().onFailure(((Throwable)data).getMessage());
                            }
                        }
                    }
                });
            }
        };

        SMSSDK.registerEventHandler(mEventHandler);

    }

    /**
     * 发送验证码
     */
    public void sendCode(){
        if (!isViewAttached()){
            Log.e(TAG, "the is not attached");
            return;
        }

        if (!mCanSendCode){
            getView().onFailure("短信正在发送中...");
            return;
        }

        String phone = getView().getPhone();

        if (TextUtils.isEmpty(phone)){
            getView().onFailure("手机号码不能为空！");
            return;
        }

        if (!MyTextUtils.isPhoneNumLegitimate(phone)){
            getView().onFailure("手机号码不合法！");
            return;
        }

        mCanSendCode = false;//在未完成发送验证码的情况下，不能再次发送验证码
        SMSSDK.getVerificationCode("86", phone);//请求SDK发送验证码

    }

    /**
     * 提供外部调用的登录操作
     */
    public void onLogin(){
        if (!isViewAttached()){
            Log.e(TAG, "the is not attached");
            return;
        }

        String phone = getView().getPhone();
        if (TextUtils.isEmpty(phone)){
            getView().onFailure("手机号码不能为空！");
            return;
        }

        String code = getView().getCode();
        if (TextUtils.isEmpty(code)){
            getView().onFailure("验证码不能为空！");
            return;
        }

        mPhone = phone;
        //调用SMSSDK验证验证码
        SMSSDK.submitVerificationCode("86", phone, code);
    }

    /**
     * 请求服务器进行
     */
    private void loginToService(){
        getView().showLoading();

        UserModel.getInstance().login(mPhone, mUserType, new OnModelCallback<User>() {

            @Override
            public void onSuccess(User data) {
                getView().stopLoading();
                getView().onLoginSuccess(data);
            }

            @Override
            public void onFailure(String code, String msg) {
                getView().stopLoading();
                getView().onFailure(msg);
            }
        });
    }
}
