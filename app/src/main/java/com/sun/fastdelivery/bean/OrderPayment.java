package com.sun.fastdelivery.bean;

/**
 * 支付订单的信息
 * Created by sunxuedian on 2018/5/16.
 */

public class OrderPayment {

    private long orderPaymentId;
    private long orderId;//订单id
    private String payWay;//支付方式
    private double payPrice;//支付的价格
    private String createTime;//创建的时间


    public long getOrderPaymentId() {
        return orderPaymentId;
    }

    public void setOrderPaymentId(long orderPaymentId) {
        this.orderPaymentId = orderPaymentId;
    }

    public long getOrderId() {
        return orderId;
    }

    public void setOrderId(long orderId) {
        this.orderId = orderId;
    }

    public String getPayWay() {
        return payWay;
    }

    public void setPayWay(String payWay) {
        this.payWay = payWay;
    }

    public double getPayPrice() {
        return payPrice;
    }

    public void setPayPrice(double payPrice) {
        this.payPrice = payPrice;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

}
