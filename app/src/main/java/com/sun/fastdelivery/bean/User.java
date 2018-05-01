package com.sun.fastdelivery.bean;

/**
 * Created by sunxuedian on 2018/4/30.
 */

public class User {

    private Long userId;
    private String userPhone;
    private String allocatedToken;

    public User(Long userId, String userPhone, String allocatedToken) {
        this.userId = userId;
        this.userPhone = userPhone;
        this.allocatedToken = allocatedToken;
    }

    public User() {
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getUserPhone() {
        return userPhone;
    }

    public void setUserPhone(String userPhone) {
        this.userPhone = userPhone;
    }

    public String getAllocatedToken() {
        return allocatedToken;
    }

    public void setAllocatedToken(String allocatedToken) {
        this.allocatedToken = allocatedToken;
    }
}
