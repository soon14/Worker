package com.xsd.jx.utils;

import com.alibaba.fastjson.JSON;
import com.xsd.jx.bean.BaseStrResponse;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

/**
 * Date: 2020/1/4
 * author: SmallCake
 */
public class AppJsonUtil {

    /**
     * 返回默认实体类
     */
    public static BaseStrResponse getResultInfo(String result){
        return JSON.parseObject(result, BaseStrResponse.class);
    }

    /**
     * 返回实体类
     */
    public static  <T> T getObject(String result, Class<T> clazz){
        return JSON.parseObject(getResultInfo(result).getData(),clazz);
    }

    /**
     * 返回数组实体类
     */
    public static  <T> List<T> getArrayList(String result, Class<T> clazz){
        return JSON.parseArray(getResultInfo(result).getData(),clazz);
    }
    /**
     * 返回数组实体类
     */
    public static  <T> List<T> getArrayList(String result, String key, Class<T> clazz){
        try {
            JSONObject jsonObject=new JSONObject(getResultInfo(result).getData());

            return JSON.parseArray(jsonObject.getString(key), clazz);
        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        }
    }
    /**
     * 返回String
     */
    public static String getString(String result, String key){
        try {
            JSONObject jsonObject=new JSONObject(getResultInfo(result).getData());
            return jsonObject.getString(key);
        } catch (JSONException e) {
            e.printStackTrace();
            return "";
        }
    }

}
