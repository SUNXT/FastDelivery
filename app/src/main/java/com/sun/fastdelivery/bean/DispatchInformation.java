package com.sun.fastdelivery.bean;

import android.os.Parcel;
import android.os.Parcelable;
import android.text.TextUtils;

import com.amap.api.maps2d.model.LatLng;

/**
 * 派送信息
 * Created by sunxuedian on 2018/4/29.
 */

public class DispatchInformation implements Parcelable{
    public static final String TAG = "DispatchInformation";

    public DispatchInformation() {
    }

    protected DispatchInformation(Parcel in) {
        location = in.readString();
        latLng = in.readParcelable(LatLng.class.getClassLoader());
        address = in.readString();
        userName = in.readString();
        userPhone = in.readString();
    }

    public static final Creator<DispatchInformation> CREATOR = new Creator<DispatchInformation>() {
        @Override
        public DispatchInformation createFromParcel(Parcel in) {
            return new DispatchInformation(in);
        }

        @Override
        public DispatchInformation[] newArray(int size) {
            return new DispatchInformation[size];
        }
    };

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public LatLng getLatLng() {
        return latLng;
    }

    public void setLatLng(LatLng latLng) {
        this.latLng = latLng;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserPhone() {
        return userPhone;
    }

    public void setUserPhone(String userPhone) {
        this.userPhone = userPhone;
    }

    private String location;
    private LatLng latLng;
    private String address;
    private String userName;
    private String userPhone;

    //判断是否所有属性不为空
    public boolean isAllNotNull(){
        return !(TextUtils.isEmpty(location)||TextUtils.isEmpty(address)||
                TextUtils.isEmpty(userName) || TextUtils.isEmpty(userPhone)
                || latLng == null);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(location);
        dest.writeParcelable(latLng, flags);
        dest.writeString(address);
        dest.writeString(userName);
        dest.writeString(userPhone);
    }
}
