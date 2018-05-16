package com.sun.fastdelivery.view.base;

import com.sun.fastdelivery.bean.Order;
import com.sun.fastdelivery.bean.User;

/**
 * Created by sunxuedian on 2018/5/17.
 */

public interface IPayOrderView extends ILoadingView {
    User getUser();
    Order getOrder();
    String getPayWay();
    void onPayOrderSuccess();
    void onCancelOrderSuccess();
    void showError(String msg);
}
