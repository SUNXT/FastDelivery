package com.sun.fastdelivery.bean;

import java.math.BigDecimal;
import java.util.Date;

/**
 * Created by sunxuedian on 2018/5/2.
 */

public class Order {

    //业务订单编号
    private Long orderId;
    //用户id
    private Long userId;
    //骑手id
    private Long riderId;
    //寄件人信息
    private String posterName;
    private String posterPhone;
    //收件人信息
    private String receiverName;
    private String receiverPhone;
    //订单价格及重量
    private double orderPrice;
    private int goodWeight;
    //物品类型，具体对应看 GoodType
    private int goodType;
    //订单状态
    private Integer status;// --- -1/已取消 0/已创建  1/已支付  2/配送中  3/已完成
    //订单备注
    private String remark;
    //订单创建时间
    private String createTime;
    //支付信息
    private OrderPayment orderPayment;
    //派送情况信息
    private OrderShippingInfo orderShippingInfo;

    public Long getOrderId() {
        return orderId;
    }

    public void setOrderId(Long orderId) {
        this.orderId = orderId;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Long getRiderId() {
        return riderId;
    }

    public void setRiderId(Long riderId) {
        this.riderId = riderId;
    }

    public String getPosterName() {
        return posterName;
    }

    public void setPosterName(String posterName) {
        this.posterName = posterName;
    }

    public String getPosterPhone() {
        return posterPhone;
    }

    public void setPosterPhone(String posterPhone) {
        this.posterPhone = posterPhone;
    }

    public String getReceiverName() {
        return receiverName;
    }

    public void setReceiverName(String receiverName) {
        this.receiverName = receiverName;
    }

    public String getReceiverPhone() {
        return receiverPhone;
    }

    public void setReceiverPhone(String receiverPhone) {
        this.receiverPhone = receiverPhone;
    }

    public double getOrderPrice() {
        return orderPrice;
    }

    public void setOrderPrice(double orderPrice) {
        this.orderPrice = orderPrice;
    }

    public int getGoodWeight() {
        return goodWeight;
    }

    public void setGoodWeight(int goodWeight) {
        this.goodWeight = goodWeight;
    }

    public int getGoodType() {
        return goodType;
    }

    public void setGoodType(int goodType) {
        this.goodType = goodType;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public OrderPayment getOrderPayment() {
        return orderPayment;
    }

    public void setOrderPayment(OrderPayment orderPayment) {
        this.orderPayment = orderPayment;
    }

    public OrderShippingInfo getOrderShippingInfo() {
        return orderShippingInfo;
    }

    public void setOrderShippingInfo(OrderShippingInfo orderShippingInfo) {
        this.orderShippingInfo = orderShippingInfo;
    }

}
