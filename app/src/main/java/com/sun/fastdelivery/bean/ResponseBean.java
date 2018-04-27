package com.sun.fastdelivery.bean;

import org.json.JSONObject;

import java.io.Serializable;

/**
 * Created by SUN on 2017/5/8.
 */
public class ResponseBean implements Serializable {
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

    public void setContent(Object content) {
        this.content = content;
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
