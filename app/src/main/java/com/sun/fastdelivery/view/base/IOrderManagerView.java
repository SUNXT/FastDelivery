package com.sun.fastdelivery.view.base;

import com.sun.fastdelivery.bean.Order;
import com.sun.fastdelivery.bean.RiderUser;
import com.sun.fastdelivery.bean.User;

import java.util.List;

/**
 * Created by sunxuedian on 2018/5/16.
 */

public interface IOrderManagerView extends ILoadingView {
    User getUser();
    void showOrders(List<Order> orders);
    void showError(String msg);
}
