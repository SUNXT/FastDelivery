package com.sun.fastdelivery.utils;

/**
 * Created by sunxuedian on 2018/4/30.
 */

public class UrlParamsUtils {

    private static String IP = "http://47.106.117.228:8090";//lc 服务器
//    private static String IP = "http://10.242.66.133:8090";//lc 本地

    public static String URL_LOGIN = IP + "/user/login";//登录接口
    public static String URL_QUERY_ORDER = IP + "/order/queryOrder";//获取所有订单列表
    public static String URL_CREATE_ORDER = IP + "/order/createOrder";//创建订单
    public static String URL_PAY_ORDER = IP + "/order/payOrder";//支付订单
    public static String URL_CANCEL_ORDER = IP + "/order/cancelOrder";//取消订单

}
