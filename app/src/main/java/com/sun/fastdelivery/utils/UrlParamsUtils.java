package com.sun.fastdelivery.utils;

/**
 * Created by sunxuedian on 2018/4/30.
 */

public class UrlParamsUtils {

    private static String IP = "http://47.106.117.228:8090";//lc 服务器
//    private static String IP = "http://10.242.66.133:8090";//lc 本地

    public static String URL_LOGIN = IP + "/user/login";//登录接口
    public static String URL_QUERY_ORDER = IP + "/order/queryOrder";//获取所有订单列表

}
