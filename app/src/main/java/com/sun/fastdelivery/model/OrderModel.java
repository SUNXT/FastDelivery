package com.sun.fastdelivery.model;

import android.util.Log;

import com.sun.fastdelivery.bean.CreateOrderDto;
import com.sun.fastdelivery.bean.Order;
import com.sun.fastdelivery.bean.ResponseBean;
import com.sun.fastdelivery.bean.User;
import com.sun.fastdelivery.model.callback.OnModelCallback;
import com.sun.fastdelivery.utils.JsonUtils;
import com.sun.fastdelivery.utils.OkHttpUtils;
import com.sun.fastdelivery.utils.UrlParamsUtils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * Created by sunxuedian on 2018/5/2.
 */

public class OrderModel {

    private String TAG = "OrderModel";

    private static OrderModel mInstance;

    private OrderModel(){}

    public static OrderModel getInstance(){
        if (mInstance == null){
            synchronized (OrderModel.class){
                if (mInstance == null){
                    mInstance = new OrderModel();
                }
            }
        }
        return mInstance;
    }

    /**
     * 创建订单
     * @param orderDto
     * @param callback
     */
    public void createOrder(CreateOrderDto orderDto, final OnModelCallback<Long> callback){
        OkHttpUtils.executeRequest(UrlParamsUtils.URL_CREATE_ORDER, JsonUtils.toJson(orderDto), callback, new OkHttpUtils.OnSuccessCallBack() {
            @Override
            public void onSuccess(ResponseBean responseBean) {
                if (ResponseBean.isSuccessCode(responseBean.getCode())){
                    callback.onSuccess(responseBean.getContent().optLong("orderId"));
                }else {
                    callback.onFailure(responseBean.getCode(), responseBean.getMessage());
                }
            }
        });
    }

    /**
     * 支付订单
     * @param user
     * @param orderId
     * @param money
     * @param payWay
     * @param callback
     */
    public void payOrder(User user, Long orderId, double money, String payWay, final OnModelCallback<String> callback){
        Map<String, Object> params = new HashMap<>();
        Map<String, String> token = new HashMap<>();
        token.put("tokenValue", user.getAllocatedToken());
        token.put("userPhone", user.getUserPhone());
        params.put("token", token);
        params.put("userId", user.getUserId());
        params.put("orderId", orderId);
        params.put("payAmount", money);
        params.put("payWay", payWay);
        OkHttpUtils.executeRequest(UrlParamsUtils.URL_PAY_ORDER, params, callback, new OkHttpUtils.OnSuccessCallBack() {
            @Override
            public void onSuccess(ResponseBean responseBean) {
                if (ResponseBean.isSuccessCode(responseBean.getCode())){
                    callback.onSuccess("支付成功！");
                }else {
                    callback.onFailure(responseBean.getCode(), responseBean.getMessage());
                }
            }
        });
    }

    /**
     * 取消订单
     * @param user
     * @param orderId
     * @param callback
     */
    public void cancelOrder(User user, Long orderId, final OnModelCallback callback){
        Map<String, Object> params = new HashMap<>();
        Map<String, String> token = new HashMap<>();
        token.put("tokenValue", user.getAllocatedToken());
        token.put("userPhone", user.getUserPhone());
        params.put("token", token);
        params.put("orderId", orderId);
        params.put("userId", user.getUserId());
        Log.e(TAG, params.toString());
        OkHttpUtils.executeRequest(UrlParamsUtils.URL_CANCEL_ORDER, params, callback, new OkHttpUtils.OnSuccessCallBack() {
            @Override
            public void onSuccess(ResponseBean responseBean) {
                if (ResponseBean.isSuccessCode(responseBean.getCode())){
                    callback.onSuccess("取消成功！");
                }else {
                    callback.onFailure(responseBean.getCode(), responseBean.getMessage());
                }
            }
        });
    }

    /**
     * 获取所有订单列表
     * @param user
     * @param callback
     */
    public void getAllOrders(User user, final OnModelCallback<List<Order>> callback){
        Map<String, Object> params = new HashMap<>();
        Map<String, String> token = new HashMap<>();
        token.put("tokenValue", user.getAllocatedToken());
        token.put("userPhone", user.getUserPhone());
        params.put("token", token);
        OkHttpUtils.executeRequest(UrlParamsUtils.URL_QUERY_ORDER, params, callback, new OkHttpUtils.OnSuccessCallBack() {
            @Override
            public void onSuccess(ResponseBean responseBean) {
                if (ResponseBean.isSuccessCode(responseBean.getCode())){
                    callback.onSuccess(JsonUtils.getListByJSONArray(Order.class, responseBean.getContent().optJSONArray("list")));
                }else {
                    callback.onFailure(responseBean.getCode(), responseBean.getMessage());
                }
            }
        });
    }

}
