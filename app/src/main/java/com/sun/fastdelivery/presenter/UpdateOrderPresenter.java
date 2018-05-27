package com.sun.fastdelivery.presenter;

import android.util.Log;

import com.sun.fastdelivery.bean.Order;
import com.sun.fastdelivery.bean.RiderUser;
import com.sun.fastdelivery.model.OrderModel;
import com.sun.fastdelivery.model.callback.OnModelCallback;
import com.sun.fastdelivery.view.base.IUpdateOrderView;

/**
 * Created by sunxuedian on 2018/5/27.
 */

public class UpdateOrderPresenter extends BasePresenter<IUpdateOrderView> {

    /**
     * 更新订单
     */
    public void updateOrder(){
        if (!isViewAttached()){
            Log.e("UpdateOrderPresenter", "the view is not attached!");
            return;
        }

        RiderUser riderUser = getView().getRiderUser();
        if (riderUser == null){
            getView().showError("登录过期！");
            return;
        }

        Order order = getView().getOrder();
        int arrivePlace = order.getOrderShippingInfo().getArrivePlace();
        order.getOrderShippingInfo().setArrivePlace(arrivePlace + 1);
        getView().showLoading();
        OrderModel.getInstance().updateOrderShipping(riderUser, order, new OnModelCallback<String>() {
            @Override
            public void onSuccess(String data) {
                getView().stopLoading();
                getView().onUpdateOrder();
            }

            @Override
            public void onFailure(String code, String msg) {
                getView().stopLoading();
                getView().showError(msg);
            }
        });
    }
}
