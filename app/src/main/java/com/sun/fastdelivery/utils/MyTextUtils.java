package com.sun.fastdelivery.utils;

import java.security.MessageDigest;
import java.util.regex.Pattern;

/**
 * Created by SUN on 2017/5/7.
 */
public class MyTextUtils {


    /**
     * 判断是否为有效合理的手机号码
     * @param phoneNum
     * @return
     */
    public static boolean isPhoneNumLegitimate(String phoneNum){
//        String regex_1 = "^((13[0-9])|(15[^4,\\D])|(18[0,5-9]))\\d{8}$";//手机号码
        String regex = "^1(3[0-9]|4[57]|5[0-35-9]|8[0-9]|7[0678])\\d{8}$";//手机号码
        return Pattern.compile(regex).matcher(phoneNum).matches();
//        return true;
    }

    /**
     * 检验密码是否合法
     * @param password
     * @return
     */
    public static boolean isPasswordLegitimate(String password){
        return !(password.length() < 6 || password.length() > 20);
    }

    /**
     * 判断身份证格式是否合法
     * @param idCard
     * @return
     */
    public static boolean isIDCardLegitimate(String idCard){
        String regex_15 = "^[1-9]\\d{7}((0\\d)|(1[0-2]))(([0|1|2]\\d)|3[0-1])\\d{3}$";
        String regex_18 = "^[1-9]\\d{5}[1-9]\\d{3}((0\\d)|(1[0-2]))(([0|1|2]\\d)|3[0-1])\\d{3}([0-9]|X|x)$";
        return Pattern.compile(regex_15).matcher(idCard).matches() || Pattern.compile(regex_18).matcher(idCard).matches();
    }

    /**
     * md5加密
     * @param s
     * @return
     */
    public final static String MD5(String s) {
        char hexDigits[]={'0','1','2','3','4','5','6','7','8','9','A','B','C','D','E','F'};
        try {
            byte[] btInput = s.getBytes();
            // 获得MD5摘要算法的 MessageDigest 对象
            MessageDigest mdInst = MessageDigest.getInstance("MD5");
            // 使用指定的字节更新摘要
            mdInst.update(btInput);
            // 获得密文
            byte[] md = mdInst.digest();
            // 把密文转换成十六进制的字符串形式
            int j = md.length;
            char str[] = new char[j * 2];
            int k = 0;
            for (int i = 0; i < j; i++) {
                byte byte0 = md[i];
                str[k++] = hexDigits[byte0 >>> 4 & 0xf];
                str[k++] = hexDigits[byte0 & 0xf];
            }
            return new String(str);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

}
