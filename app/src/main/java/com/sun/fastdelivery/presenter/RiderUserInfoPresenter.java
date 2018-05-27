package com.sun.fastdelivery.presenter;

import android.util.Log;

import com.sun.fastdelivery.bean.RiderUser;
import com.sun.fastdelivery.model.UserModel;
import com.sun.fastdelivery.model.callback.OnModelCallback;
import com.sun.fastdelivery.view.base.IRiderUserInfoView;

/**
 * Created by sunxuedian on 2018/5/27.
 */

public class RiderUserInfoPresenter extends BasePresenter<IRiderUserInfoView> {

    /**
     * 获取骑手信息
     */
    public void getRiderUserInfo(){
        if (!isViewAttached()){
            Log.e("RiderUserInfoPresenter", "the view is not attached!");
            return;
        }

        RiderUser riderUser = getView().getRiderUser();
        if (riderUser == null){
            getView().showError("登录过期！");
            return;
        }

        getView().showLoading();
        UserModel.getInstance().queryRiderUserInfo(riderUser, new OnModelCallback<RiderUser>() {
            @Override
            public void onSuccess(RiderUser data) {
                getView().stopLoading();
                getView().showRiderUser(data);
            }

            @Override
            public void onFailure(String code, String msg) {
                getView().stopLoading();
                getView().showError(msg);
            }
        });
    }
}
