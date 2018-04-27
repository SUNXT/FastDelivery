package com.sun.fastdelivery.model.callback;

/**
 * Created by SUN on 2018/4/27.
 */

public interface OnModelCallback {
    int OK_HTTP_ERROR = 0;
    void onFailure(int code, String msg);
}
