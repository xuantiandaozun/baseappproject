package com.system.baseapplibrary.utils;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.List;
import java.util.Map;

/**
 * Gson解析封装
 */
public class GsonUtil {
    private static Gson gson = null;

    static {
        if (gson == null) {
            gson = new Gson();
        }
    }

    private GsonUtil() {
    }

    /**
     * 转成json
     *
     * @param object
     * @return
     */
    public static String GsonString(Object object) {
        String gsonString = null;
        if (gson != null) {
            gsonString = gson.toJson(object);
        }
        return gsonString;
    }

    /**
     * 转成bean
     *
     * @param gsonString
     * @param cls
     * @return
     */
    public static <T> T GsonToBean(String gsonString, Class<T> cls) {
        T t = null;
        if (gson != null) {
            t = gson.fromJson(gsonString, cls);
        }
        return t;
    }
    /**
     * 转成bean
     *
     * @param object
     * @param cls
     * @return
     */
    public static <T> T GsonToBean(Object object, Class<T> cls) {
        T t = null;
        if (gson != null) {
            t = gson.fromJson(GsonString(object), cls);
        }
        return t;
    }
    /**
     * 转成list
     *
     * @return
     */
    public <T> List<T> GsonToList(String json,Class clazz) {
        Type type = new ParameterizedTypeImpl(clazz);
        List<T> list =  new Gson().fromJson(json, type);
        return list;
    }
    /**
     * 转成list
     *
     * @return
     */
    public <T> List<T> GsonToList(Object object,Class clazz) {
        Type type = new ParameterizedTypeImpl(clazz);
        List<T> list =  new Gson().fromJson(GsonString(object), type);
        return list;
    }

    private  class ParameterizedTypeImpl implements ParameterizedType {
        Class clazz;

        public ParameterizedTypeImpl(Class clz) {
            clazz = clz;
        }

        @Override
        public Type[] getActualTypeArguments() {
            return new Type[]{clazz};
        }

        @Override
        public Type getRawType() {
            return List.class;
        }

        @Override
        public Type getOwnerType() {
            return null;
        }
    }

    /**
     * 转成list中有map的
     *
     * @param gsonString
     * @return
     */
    public static <T> List<Map<String, T>> GsonToListMaps(String gsonString) {
        List<Map<String, T>> list = null;
        if (gson != null) {
            list = gson.fromJson(gsonString, new TypeToken<List<Map<String, T>>>() {
            }.getType());
        }
        return list;
    }
    /**
     * 转成list中有map的
     *
     * @param object
     * @return
     */
    public static <T> List<Map<String, T>> GsonToListMaps(Object object) {
        List<Map<String, T>> list = null;
        if (gson != null) {
            list = gson.fromJson(GsonString(object), new TypeToken<List<Map<String, T>>>() {
            }.getType());
        }
        return list;
    }

    /**
     * 转成map的
     *
     * @param gsonString
     * @return
     */
    public static <T> Map<String, T> GsonToMaps(String gsonString) {
        Map<String, T> map = null;
        if (gson != null) {
            map = gson.fromJson(gsonString, new TypeToken<Map<String, T>>() {
            }.getType());
        }
        return map;
    }
    /**
     * 转成map的
     *
     * @param object
     * @return
     */
    public static <T> Map<String, T> GsonToMaps(Object object) {
        Map<String, T> map = null;
        if (gson != null) {
            map = gson.fromJson(GsonString(object), new TypeToken<Map<String, T>>() {
            }.getType());
        }
        return map;
    }

}
