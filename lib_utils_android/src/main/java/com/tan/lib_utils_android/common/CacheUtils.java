package com.tan.lib_utils_android.common;

import android.content.Context;
import android.content.SharedPreferences;
import android.provider.SyncStateContract;

import com.tan.lib_utils_android.apache.StringUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CacheUtils {

    private static String CACHE_ID = "cacheId";

    /**
     * 根据缓存的Key获取缓存的数据 String
     * @param context 上下文
     * @param cacheKey 缓存的Key
     * @return
     */
    public static String getCache(Context context, String cacheKey) {
        return context.getSharedPreferences(CACHE_ID, Context.MODE_PRIVATE).getString(cacheKey, "");
    }

    /**
     * 缓存数据 String
     * @param context 上下文
     * @param cacheKey 缓存的Key
     * @param cacheValue 缓存的Value
     * @return
     */
    public static boolean setCache(Context context, String cacheKey, String cacheValue) {
        if (StringUtils.isNotBlank(cacheValue)) {
            SharedPreferences.Editor editor = context.getSharedPreferences(CACHE_ID, Context.MODE_PRIVATE).edit();
            editor.putString(cacheKey, cacheValue);
            editor.apply();
            return true;
        }
        return false;
    }

    /**
     *  缓存数据 Object
     * @param context
     * @param cacheKey
     * @param cacheObject
     * @return
     */
    public static boolean setCache(Context context, String cacheKey, Object cacheObject) {
        return setCache(context, cacheKey, JacksonUtils.objectToJson(cacheObject));
    }

    /**
     * 清除cacheKey 对应的缓存数据
     * @param context
     * @param cacheKey
     */
    public static void clearCache(Context context, String cacheKey) {
        SharedPreferences.Editor editor = context.getSharedPreferences(CACHE_ID, Context.MODE_PRIVATE).edit();
        editor.remove(cacheKey);
        editor.apply();
    }

    /**
     * 缓存数据 Map<String, Object>
     * @param context
     * @param key
     * @param cacheMap
     * @return
     */
    public static boolean setCacheMap(Context context, String key, Map<String, Object> cacheMap) {
        return setCache(context, key, cacheMap);
    }

    /**
     * 根据缓存的Key获取缓存的数据 Map<String, Object>
     * @param context
     * @param key
     * @return
     */
    public static Map<String, Object> getCacheMap(Context context, String key) {
        Map<String, Object> cacheMap = null;
        String tokenString = getCache(context, key);
        if (StringUtils.isNotBlank(tokenString)) {
            cacheMap = JacksonUtils.jsonToMap(tokenString);
        }
        if (cacheMap == null) {
            cacheMap = new HashMap<>();
        }
        return cacheMap;
    }

    /**
     * 缓存数据 List<Map<String, Object>>
     * @param context
     * @param key
     * @param cacheList
     * @return
     */
    public static boolean setCacheList(Context context, String key, List<Map<String, Object>> cacheList) {

        return setCache(context, key, cacheList);
    }

    /**
     * 根据缓存的Key获取缓存的数据 List<Map<String, Object>>
     * @param context
     * @param key
     * @return
     */
    public static List<Map<String, Object>> getCacheList(Context context,String key) {
        List<Map<String, Object>> cacheList = null;
        String tokenString = getCache(context, key);
        if (StringUtils.isNotBlank(tokenString)) {
            cacheList = JacksonUtils.jsonToList(tokenString);
        }
        if (cacheList == null) {
            cacheList = new ArrayList<>();
        }
        return cacheList;
    }
}
