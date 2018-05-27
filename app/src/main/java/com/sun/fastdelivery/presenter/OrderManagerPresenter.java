package com.sun.fastdelivery.presenter;

import android.util.Log;

import com.sun.fastdelivery.bean.Order;
import com.sun.fastdelivery.bean.RiderUser;
import com.sun.fastdelivery.bean.User;
import com.sun.fastdelivery.model.OrderModel;
import com.sun.fastdelivery.model.callback.OnModelCallback;
import com.sun.fastdelivery.view.base.IOrderManagerView;

import java.util.List;

/**
 * Created by sunxuedian on 2018/5/16.
 */

public class OrderManagerPresenter extends BasePresenter<IOrderManagerView> {

    private String TAG = "OrderManagerPresenter";

    public void getOrdersByUser(){
        if (!isViewAttached()){
            Log.e(TAG, "the view is not attached!");
            return;
        }

        getView().showLoading();
        OrderModel.getInstance().getAllOrdersByUser(getView().getUser(), new OnModelCallback<List<Order>>() {
            @Override
            public void onSuccess(List<Order> data) {
                getView().stopLoading();
                getView().showOrders(data);
            }

            @Override
            public void onFailure(String code, String msg) {
                getView().stopLoading();
                getView().showError(msg);
            }
        });
    }

    /**
     * 获取所有可以接单的订单
     */
    public void getAllOrdersCanTake(){
        if (!isViewAttached()){
            Log.e(TAG, "the view is not attached!");
            return;
        }

        getView().showLoading();
        OrderModel.getInstance().getAllOrdersCanTake(getView().getUser(), new OnModelCallback<List<Order>>() {
            @Override
            public void onSuccess(List<Order> data) {
                getView().stopLoading();
                getView().showOrders(data);
            }

            @Override
            public void onFailure(String code, String msg) {
                getView().stopLoading();
                getView().showError(msg);
            }
        });
    }

    /**
     * 获取骑手接的订单
     */
    public void getAllOrdersByRider(){
        if (!isViewAttached()){
            Log.e(TAG, "the view is not attached!");
            return;
        }

        User user = getView().getUser();
        if (user == null){
            getView().showError("登录过期！");
            return;
        }

        RiderUser riderUser = new RiderUser(user.getUserId(), user.getUserPhone(), user.getAllocatedToken());

        getView().showLoading();
        OrderModel.getInstance().getAllOrdersByRiderUser(riderUser, new OnModelCallback<List<Order>>() {
            @Override
            public void onSuccess(List<Order> data) {
                getView().stopLoading();
                getView().showOrders(data);
            }

            @Override
            public void onFailure(String code, String msg) {
                getView().stopLoading();
                getView().showError(msg);
            }
        });
    }
}
