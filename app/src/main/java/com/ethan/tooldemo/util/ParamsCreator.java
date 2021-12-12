package com.ethan.tooldemo.util;

import android.util.Log;

import com.ethan.tooldemo.bean.BaseParams;

import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ParamsCreator {
    private static String TAG = "ParamsCreator";

    public static String getObjectSign(BaseParams params) {
        Class<?> clazz = params.getClass();
        Field[] fields = clazz.getDeclaredFields();
        List<String> keys = new ArrayList<>();
        for (Field field : fields) {
            if (field == null) continue;
            keys.add(field.getName());
        }
        Log.d(TAG, "keys = " + keys.toString());
        Collections.sort(keys);
        Log.d(TAG, "keys sorted = " + keys.toString());

        Field[] teamFields = new Field[fields.length];

        for (int i = 0; i < keys.size(); i++) {
            String key = keys.get(i);
            for (Field field : fields) {
                if (field == null) continue;
                Log.d(TAG, "key = " + key + " - field.getName = " + field.getName());
                if (field.getName().equals(key)){
                    teamFields[i] = field;
                    break;
                }
            }
        }

        StringBuilder buffer = new StringBuilder();
        for (int i = 0; i < teamFields.length; i++) {
            Field field = fields[i];
            if (i != 0) buffer.append("&");
            try {
                field.setAccessible(true);
                buffer.append(field.getName()).append("=").append(field.get(params));
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }
        buffer.append("&ParamsSign");
        String sign = buffer.toString();
        Log.d(TAG, "buffer = " + sign);
        return sign;
    }

    public static String getObjectParams(BaseParams params) {
        Class<?> clazz = params.getClass();
        Field[] fields = clazz.getDeclaredFields();
        JSONObject paramsObject = new JSONObject();
        for (Field field : fields) {
            field.setAccessible(true);
            try {
                Log.d(TAG, "key = " + field.getName() + " - value = " + field.get(params));
                paramsObject.put(field.getName(), field.get(params));
            } catch (IllegalAccessException | JSONException e) {
                e.printStackTrace();
            }
        }
        String paramStr = paramsObject.toString();
        Log.d(TAG, "paramStr = " + paramStr);
        return paramStr;
    }
}
