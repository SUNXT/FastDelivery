package com.sun.fastdelivery.bean;

/**
 * 用于网络通信中的token
 * Created by sunxuedian on 2018/5/9.
 */

public class UserToken {

    public String getTokenValue() {
        return tokenValue;
    }

    public void setTokenValue(String tokenValue) {
        this.tokenValue = tokenValue;
    }

    public String getUserPhone() {
        return userPhone;
    }

    public void setUserPhone(String userPhone) {
        this.userPhone = userPhone;
    }

    private String tokenValue;
    private String userPhone;
}
