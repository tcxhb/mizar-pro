package com.tcxhb.mizar.common.utils;

import lombok.Data;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Function;

/**
 * @Description:
 * @Auther: tcxhb
 * @Date: 2022/11/2
 */
public class LocalCache<T> {
    final private static Long TEN_MINITE = 5 * 1000 * 60L;
    private Long expireTime;

    public LocalCache() {
        this.expireTime = TEN_MINITE;
    }

    public LocalCache(Long expireTime) {
        this.expireTime = expireTime;
    }

    private Map<String, DataObject<T>> cacheMap = new ConcurrentHashMap<String, DataObject<T>>();


    public void put(String k, T object) {
        put(k, object, this.expireTime);
    }

    /**
     * @param k
     * @param object
     * @param expireTime
     */
    public void put(String k, T object, long expireTime) {
        String fullKey = getPrefixKey(k);
        Long now = System.currentTimeMillis();
        DataObject obj = new DataObject(object, now + expireTime);
        cacheMap.put(fullKey, obj);
    }

    //获取数据
    public T get(String key) {
        String fullKey = getPrefixKey(key);
        DataObject<T> dataObj = cacheMap.get(fullKey);
        if (dataObj == null) {
            return null;
        }
        //是否失效了
        if (isExpire(dataObj)) {
            return null;
        }
        return dataObj.getObject();
    }


    public T get(String key, Function<String, T> function) {
        //从缓存里面获取
        T value = get(key);
        if (value != null) {
            return value;
        }
        //请求接口
        value = function.apply(key);
        if (value != null) {
            //放入缓存
            put(key, value);
        }
        return value;
    }


    public boolean clean(String key) {
        String fullKey = getPrefixKey(key);
        cacheMap.remove(fullKey);
        return true;
    }

    public String getPrefixKey(String key) {
        return key;
    }

    private Boolean isExpire(DataObject dataObject) {
        Long current = System.currentTimeMillis();
        if (dataObject.getExpTime() >= current) {
            return false;
        }
        return true;
    }

    @Data
    public static class DataObject<T> {
        private Long expTime;
        private T object;

        public DataObject(T object, Long expTime) {
            this.expTime = expTime;
            this.object = object;
        }
    }
}
