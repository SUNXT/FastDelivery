package com.sun.fastdelivery.bean;

/**
 * 骑手类
 * Created by SUN on 2018/5/18.
 */

public class RiderUser extends User{
    private double userWalletAmount;//骑手的钱包

    public double getUserWalletAmount() {
        return userWalletAmount;
    }

    public void setUserWalletAmount(double userWalletAmount) {
        this.userWalletAmount = userWalletAmount;
    }
}
