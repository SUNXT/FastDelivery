package com.sun.fastdelivery.presenter;

import android.util.Log;

import com.sun.fastdelivery.bean.Order;
import com.sun.fastdelivery.bean.RiderUser;
import com.sun.fastdelivery.model.OrderModel;
import com.sun.fastdelivery.model.callback.OnModelCallback;
import com.sun.fastdelivery.view.base.ITakeOrderView;

/**
 * Created by sunxuedian on 2018/5/27.
 */

public class TakeOrderPresenter extends BasePresenter<ITakeOrderView>{

    private static final String TAG = "TakeOrderPresenter";

    public void takeOrder(){
        if (!isViewAttached()){
            Log.d(TAG, "the view is no attached!");
            return;
        }

        Order order = getView().getOrder();
        if (order == null){
            getView().onFailure("订单错误！");
            return;
        }

        RiderUser riderUser = getView().getRiderUser();
        if (riderUser == null){
            getView().onFailure("用户登录信息已过期！");
            return;
        }

        getView().showLoading();
        OrderModel.getInstance().takeOrder(riderUser, order.getOrderId(), new OnModelCallback<String>() {
            @Override
            public void onSuccess(String data) {
                getView().stopLoading();
                getView().onTakeOrderSuccess();
            }

            @Override
            public void onFailure(String code, String msg) {
                getView().stopLoading();
                getView().onFailure(msg);
            }
        });
    }

}
