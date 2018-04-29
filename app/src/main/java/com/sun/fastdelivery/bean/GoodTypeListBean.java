package com.sun.fastdelivery.bean;

/**
 * Created by sunxuedian on 2018/4/29.
 */

public class GoodTypeListBean {

    private GoodType type;
    private int picRes;

    public boolean isSelected() {
        return selected;
    }

    public void setSelected(boolean selected) {
        this.selected = selected;
    }

    private boolean selected = false;

    public GoodType getType() {
        return type;
    }

    public void setType(GoodType type) {
        this.type = type;
    }

    public int getPicRes() {
        return picRes;
    }

    public void setPicRes(int picRes) {
        this.picRes = picRes;
    }


}
