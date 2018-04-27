package com.sun.fastdelivery.bean;

/**
 * 高德地图中的城市类
 * Created by SUN on 2018/4/27.
 */

public class City {
//     {"adcode":"654300","name":"阿勒泰地区","spell":"Altay","lng":88.13963,"lat":47.848393,"areaCode":"0906"}
    private String adcode;//城市编号
    private String name;//城市名
    private String spell;//
    private double lng;//经度
    private double lat;//纬度

    @Override
    public String toString() {
        return "City{" +
                "adcode='" + adcode + '\'' +
                ", name='" + name + '\'' +
                ", spell='" + spell + '\'' +
                ", lng=" + lng +
                ", lat=" + lat +
                ", areaCode='" + areaCode + '\'' +
                '}';
    }

    private String areaCode;//地区编码

    public String getAdcode() {
        return adcode;
    }

    public void setAdcode(String adcode) {
        this.adcode = adcode;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSpell() {
        return spell;
    }

    public void setSpell(String spell) {
        this.spell = spell;
    }

    public double getLng() {
        return lng;
    }

    public void setLng(double lng) {
        this.lng = lng;
    }

    public double getLat() {
        return lat;
    }

    public void setLat(double lat) {
        this.lat = lat;
    }

    public String getAreaCode() {
        return areaCode;
    }

    public void setAreaCode(String areaCode) {
        this.areaCode = areaCode;
    }
}
