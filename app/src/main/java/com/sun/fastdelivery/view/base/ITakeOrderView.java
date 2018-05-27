package com.sun.fastdelivery.view.base;

import com.sun.fastdelivery.bean.Order;
import com.sun.fastdelivery.bean.RiderUser;

/**
 * Created by sunxuedian on 2018/5/27.
 */

public interface ITakeOrderView extends ILoadingView{
    RiderUser getRiderUser();
    Order getOrder();
    void onTakeOrderSuccess();
    void onFailure(String msg);
}
