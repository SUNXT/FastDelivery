package com.sun.fastdelivery.bean;

import android.text.TextUtils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;

/**
 * Created by SUN on 2017/5/8.
 */
public class ResponseBean implements Serializable {

    public static final String CODE_SUCCESS = "0";

    private String code;
    private String message;
    private Object content;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public JSONObject getContent() {
        try {
            JSONObject jsonObject = new JSONObject(content.toString());
            return jsonObject;
        } catch (Exception e) {
            e.printStackTrace();
            return new JSONObject();
        }
    }

    public JSONArray getJsonArray(){
        try {
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("list", content.toString());
            return jsonObject.getJSONArray("list");
        } catch (JSONException e) {
            e.printStackTrace();
            return new JSONArray();
        }
    }

    public void setContent(Object content) {
        this.content = content;
    }

    public static boolean isSuccessCode(String code){
        return TextUtils.equals(CODE_SUCCESS, code);
    }

    @Override
    public String toString() {
        String con = "";
        if (getContent() != null){
            con = getContent().toString();
        }
        return "code: " + code + " message: " + message + " content: " + con;
    }
}
