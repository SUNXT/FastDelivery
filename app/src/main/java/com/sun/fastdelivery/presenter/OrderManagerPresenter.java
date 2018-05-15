package com.sun.fastdelivery.presenter;

import android.util.Log;

import com.sun.fastdelivery.bean.Order;
import com.sun.fastdelivery.model.OrderModel;
import com.sun.fastdelivery.model.callback.OnModelCallback;
import com.sun.fastdelivery.view.base.IOrderManagerView;

import java.util.List;

/**
 * Created by sunxuedian on 2018/5/16.
 */

public class OrderManagerPresenter extends BasePresenter<IOrderManagerView> {

    private String TAG = "OrderManagerPresenter";

    public void getOrderList(){
        if (!isViewAttached()){
            Log.e(TAG, "the view is not attached!");
            return;
        }

        getView().showLoading();
        OrderModel.getInstance().getAllOrders(getView().getUser(), new OnModelCallback<List<Order>>() {
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
