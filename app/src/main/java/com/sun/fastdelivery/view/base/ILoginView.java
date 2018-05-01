package com.sun.fastdelivery.view.base;

import com.sun.fastdelivery.bean.User;

/**
 * Created by sunxuedian on 2018/4/30.
 */

public interface ILoginView extends ILoadingView{
    String getPhone();
    String getCode();
    void onSendCodeSuccess();
    void onLoginSuccess(User user);
    void onFailure(String msg);
}
