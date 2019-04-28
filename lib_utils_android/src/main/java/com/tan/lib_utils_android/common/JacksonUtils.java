package com.tan.lib_utils_android.common;

import android.util.Log;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.util.List;
import java.util.Map;

public class JacksonUtils {

    public static ObjectMapper newInstance() {
        return new ObjectMapper();
    }

    public static String objectToJson(Object object) {
        String value = "";
        try {
            value = (new ObjectMapper()).writeValueAsString(object);
        } catch (JsonProcessingException e) {
            Log.e(JacksonUtils.class.getSimpleName(), "objectToJson error", e);
        }
        return value;
    }

    public static Map<String, Object> jsonToMap(String json) {
        return jsonToObject(json, new TypeReference<Map<String, Object>>() {
        });
    }

    public static List<Map<String, Object>> jsonToList(String json) {
        return jsonToObject(json, new TypeReference<List<Map<String, Object>>>() {
        });
    }

    public static <T> T jsonToObject(String json, TypeReference<T> typeReference) {
        T jsonObject = null;
        try {
            jsonObject = (new ObjectMapper()).readValue(json, typeReference);
        } catch (IOException e) {
            Log.e(JacksonUtils.class.getSimpleName(), "jsonToMap error", e);
        }
        return jsonObject;
    }
}
