package com.sun.fastdelivery.presenter;

import android.util.Log;

import com.sun.fastdelivery.bean.Order;
import com.sun.fastdelivery.bean.User;
import com.sun.fastdelivery.model.OrderModel;
import com.sun.fastdelivery.model.callback.OnModelCallback;
import com.sun.fastdelivery.view.base.IPayOrderView;

/**
 * Created by sunxuedian on 2018/5/17.
 */

public class PayOrderPresenter extends BasePresenter<IPayOrderView> {
    String TAG = getClass().getSimpleName();

    public void payOrder(){
        if (!isViewAttached()){
            Log.e(TAG, "the view is not attached!");
            return;
        }

        User user = getView().getUser();
        Order order = getView().getOrder();
        String payWay = getView().getPayWay();

        getView().showLoading();
        OrderModel.getInstance().payOrder(user, order.getOrderId(), order.getOrderPrice(), payWay, new OnModelCallback<String>() {
            @Override
            public void onSuccess(String data) {
                getView().stopLoading();
                getView().onPayOrderSuccess();
            }

            @Override
            public void onFailure(String code, String msg) {
                getView().stopLoading();
                getView().showError(msg);
            }
        });
    }

    public void cancelOrder(){
        if (!isViewAttached()){
            Log.e(TAG, "the view is not attached!");
            return;
        }

        User user = getView().getUser();
        Order order = getView().getOrder();
        getView().showLoading();
        OrderModel.getInstance().cancelOrder(user, order.getOrderId(), new OnModelCallback() {
            @Override
            public void onSuccess(Object data) {
                getView().stopLoading();
                getView().onCancelOrderSuccess();
            }

            @Override
            public void onFailure(String code, String msg) {
                getView().stopLoading();
                getView().showError(msg);
            }
        });
    }
}
