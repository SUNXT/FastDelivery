package com.sun.fastdelivery.view.base;


import com.sun.fastdelivery.bean.RiderUser;
import com.sun.fastdelivery.bean.User;

/**
 * Created by sunxuedian on 2018/5/25.
 */

public interface IRiderRegisterView extends ILoadingView {
    String getPhone();
    String getCode();
    void onSendCodeSuccess();
    void onRegisterSuccess();
    void onLoginSuccess(RiderUser user);
    void onFailure(String msg);
}
