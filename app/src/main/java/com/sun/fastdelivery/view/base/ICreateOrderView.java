package com.sun.fastdelivery.view.base;

import com.sun.fastdelivery.bean.CreateOrderDto;
import com.sun.fastdelivery.bean.DispatchInformation;
import com.sun.fastdelivery.bean.GoodType;
import com.sun.fastdelivery.bean.Order;
import com.sun.fastdelivery.bean.User;

/**
 * 创建订单View
 * Created by sunxuedian on 2018/5/9.
 */

public interface ICreateOrderView extends ILoadingView {
    DispatchInformation getSendInfo();//获取寄件信息
    DispatchInformation getReceivedInfo();//获取收件信息
    User getUser();//获取用户信息
    GoodType getGoodType();//获取物品类型
    String getExtraInfo();//获取备注信息
    int getGoodWeight();//获取物品重量
    String getDistributionUtil();//获取派送的工具
    double getOrderMoney();//获取订单的总额
    void onCreateOrderSuccess(CreateOrderDto order);//创建成功
    void onFailure(String msg);//失败
}
