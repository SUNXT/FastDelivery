package com.sun.fastdelivery.utils;

import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import com.sun.fastdelivery.bean.ResponseBean;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Json的工具类
 * Created by SUN on 2017/5/14.
 */
public class JsonUtils {

    public static <T>  T fromJson(Class<T> tClass, String jsonStr){
        Gson gson = new Gson();
        return gson.fromJson(jsonStr, tClass);
    }

    public static <T>  T fromJson(Class<T> tClass, JSONObject jsonStr){
        Gson gson = new Gson();
        return fromJson(tClass, jsonStr.toString());
    }

    /**
     *
     * @param clazz bean类的Class
     * @param array JSONArray
     * @param <T> 对应的bean类
     * @return
     */
    public static <T> List<T> getListByJSONArray(Class<T> clazz, JSONArray array){
        if (array == null){
            return null;
        }
        List<T> list = new ArrayList<>();
        //Json的解析类对象
        JsonParser parser = new JsonParser();
        //将JSON的String 转成一个JsonArray对象
        JsonArray jsonArray = parser.parse(array.toString()).getAsJsonArray();
        Gson gson = new Gson();
        for (JsonElement element: jsonArray){
            T item = gson.fromJson(element, clazz);
            list.add(item);
        }
        return list;
    }


    /**
     * 解析返回的数据，因为如果
     * @param str
     * @return
     */
    public static ResponseBean jsonStrToResponseBean(String str){
        Log.d("ResponseBean: ", str);
        Gson gson = new Gson();
        ResponseBean bean = gson.fromJson(str, ResponseBean.class);
        JSONObject jsonObject = null;
        try {
            jsonObject = new JSONObject(str);
            if (jsonObject.getJSONObject("content") != null){
                bean.setContent(jsonObject.getJSONObject("content").toString());
            }else {
                bean.setContent("");
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return bean;
    }
}
