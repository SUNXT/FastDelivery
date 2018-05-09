package com.sun.fastdelivery.presenter;

import android.text.TextUtils;
import android.util.Log;

import com.sun.fastdelivery.bean.CreateOrderDto;
import com.sun.fastdelivery.bean.DispatchInformation;
import com.sun.fastdelivery.bean.GoodType;
import com.sun.fastdelivery.bean.User;
import com.sun.fastdelivery.bean.UserToken;
import com.sun.fastdelivery.model.OrderModel;
import com.sun.fastdelivery.model.callback.OnModelCallback;
import com.sun.fastdelivery.utils.JsonUtils;
import com.sun.fastdelivery.view.base.ICreateOrderView;

import static android.icu.lang.UCharacter.GraphemeClusterBreak.T;

/**
 * Created by sunxuedian on 2018/5/9.
 */

public class CreateOrderPresenter extends BasePresenter<ICreateOrderView> {

    String TAG = getClass().getSimpleName();

    public void createOrder(){
        if (!isViewAttached()){
            Log.d(TAG, "the view is no attached!");
            return;
        }

        User user = getView().getUser();
        if (user == null){
            getView().onFailure("用户登录信息过去！");
            return;
        }

        UserToken token = new UserToken();
        token.setUserPhone(user.getUserPhone());
        token.setTokenValue(user.getAllocatedToken());

        DispatchInformation sendInfo = getView().getSendInfo();
        if (sendInfo == null || !sendInfo.isAllNotNull()){
            getView().onFailure("寄件信息不完整，请补全！");
            return;
        }

        DispatchInformation receivedInfo = getView().getReceivedInfo();
        if (receivedInfo == null || !receivedInfo.isAllNotNull()){
            getView().onFailure("收件信息不完整，请补全！");
            return;
        }

        GoodType goodType = getView().getGoodType();
        if (goodType == null){
            getView().onFailure("请选择物品类型！");
            return;
        }

        String remark = getView().getExtraInfo();
        if (TextUtils.isEmpty(remark)){
            getView().onFailure("请补充您的订单信息及要求！");
            return;
        }

        double orderMoney = getView().getOrderMoney();
        if (orderMoney == 0){
            getView().onFailure("配送费有误！");
            return;
        }
        int goodWeight = getView().getGoodWeight();
        String util = getView().getDistributionUtil();

        //设置订单信息
        final CreateOrderDto orderDto = new CreateOrderDto();
        orderDto.setUserId(user.getUserId());//用户id
        orderDto.setToken(token);//设置Token
        orderDto.setPosterName(sendInfo.getUserName());//寄件人信息
        orderDto.setPosterPhone(sendInfo.getUserPhone());
        orderDto.setDeparture(sendInfo.getLocation() + " " + sendInfo.getAddress());
        orderDto.setReceiverName(receivedInfo.getUserName());//收件人信息
        orderDto.setReceiverPhone(receivedInfo.getUserPhone());
        orderDto.setDestination(receivedInfo.getLocation() + " " + receivedInfo.getAddress());
        orderDto.setGoodType(goodType.type);//物品类型
        orderDto.setRemark(remark);//备注
        orderDto.setGoodWeight(goodWeight);//重量
        orderDto.setDistributionUtil(util);//配送工具
        orderDto.setOrderPrice(orderMoney);//订单总额
        Log.d(TAG, JsonUtils.toJson(orderDto));

        getView().showLoading();
        OrderModel.getInstance().createOrder(orderDto, new OnModelCallback<Long>() {
            @Override
            public void onSuccess(Long data) {
                getView().stopLoading();
                orderDto.setOrderId(data);
                getView().onCreateOrderSuccess(orderDto);
            }

            @Override
            public void onFailure(String code, String msg) {
                getView().stopLoading();
                getView().onFailure(msg);
            }
        });
    }
}
