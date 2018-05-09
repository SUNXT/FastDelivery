package com.sun.fastdelivery.bean;

/**
 * 创建订单时的传输类
 * Created by sunxuedian on 2018/5/9.
 */

public class CreateOrderDto {

    private long orderId;
    
    private UserToken token;

    //下单用户id
    private Long userId;

    //寄件人姓名
    private String posterName;

    //寄件人手机
    private String posterPhone;

    //收件人姓名
    private String receiverName;

    //收件人手机
    private String receiverPhone;

    //自动计价
    private double orderPrice;

    //物品重量
    private Integer goodWeight;

    //物品种类
    private Integer goodType;

    //备注
    private String remark;

    //寄件地址
    private String departure;

    //收件地址
    private String destination;

    //配送工具
    private String distributionUtil;

    public long getOrderId() {
        return orderId;
    }

    public void setOrderId(long orderId) {
        this.orderId = orderId;
    }

    public UserToken getToken() {
        return token;
    }

    public void setToken(UserToken token) {
        this.token = token;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
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

    public Integer getGoodWeight() {
        return goodWeight;
    }

    public void setGoodWeight(Integer goodWeight) {
        this.goodWeight = goodWeight;
    }

    public Integer getGoodType() {
        return goodType;
    }

    public void setGoodType(Integer goodType) {
        this.goodType = goodType;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getDeparture() {
        return departure;
    }

    public void setDeparture(String departure) {
        this.departure = departure;
    }

    public String getDestination() {
        return destination;
    }

    public void setDestination(String destination) {
        this.destination = destination;
    }

    public String getDistributionUtil() {
        return distributionUtil;
    }

    public void setDistributionUtil(String distributionUtil) {
        this.distributionUtil = distributionUtil;
    }

}
