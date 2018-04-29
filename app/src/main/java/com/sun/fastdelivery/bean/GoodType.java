package com.sun.fastdelivery.bean;

import java.io.Serializable;

/**
 * Created by sunxuedian on 2018/4/29.
 */

public enum  GoodType implements Serializable{

    CAKE(0,"蛋糕"), FLOWER(1, "鲜花"), MEALS(2, "餐品"), FRUIT(3, "生鲜果蔬"),
    FILE(4, "文件"), IT(5, "电子产品"), CLOTHE(6, "服饰"), OTHER(7, "其他");

    //成员信息
    public String text;
    public int type;

    GoodType(int i, String t){
        text = t;
        type = i;
    }

    public static String getName(int type){
        for (GoodType goodType: GoodType.values()){
            if (goodType.type == type){
                return goodType.text;
            }
        }
        return OTHER.text;
    }
}
