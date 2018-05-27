package com.sun.fastdelivery.view.base;

import com.sun.fastdelivery.bean.RiderUser;

/**
 * Created by sunxuedian on 2018/5/27.
 */

public interface IRiderUserInfoView extends ILoadingView{
    RiderUser getRiderUser();
    void showRiderUser(RiderUser user);
    void showError(String msg);
}
