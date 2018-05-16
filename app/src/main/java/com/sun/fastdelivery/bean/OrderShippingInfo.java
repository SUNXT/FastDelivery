package com.sun.fastdelivery.bean;

import java.io.Serializable;

/**
 * 订单的派送情况，及派送到哪个地点了的一些信息
 * Created by sunxuedian on 2018/5/16.
 */

public class OrderShippingInfo implements Serializable{

    private long orderShippingId;
    private String departure;//寄件地点
    private String destination;//目的地
    private String distributionUtil;//工具
    private long orderId;//订单id
    private long riderId;//骑手id
    private int arrivePlace;//到达的地方

    public long getOrderShippingId() {
        return orderShippingId;
    }

    public void setOrderShippingId(long orderShippingId) {
        this.orderShippingId = orderShippingId;
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

    public long getOrderId() {
        return orderId;
    }

    public void setOrderId(long orderId) {
        this.orderId = orderId;
    }

    public long getRiderId() {
        return riderId;
    }

    public void setRiderId(long riderId) {
        this.riderId = riderId;
    }

    public int getArrivePlace() {
        return arrivePlace;
    }

    public void setArrivePlace(int arrivePlace) {
        this.arrivePlace = arrivePlace;
    }
}
