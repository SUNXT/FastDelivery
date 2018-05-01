package com.sun.fastdelivery.model.callback;

/**
 * Created by SUN on 2018/4/27.
 */

public interface OnModelCallback<T> {
    String OK_HTTP_ERROR = "-1";
    void onSuccess(T data);
    void onFailure(String code, String msg);
}
