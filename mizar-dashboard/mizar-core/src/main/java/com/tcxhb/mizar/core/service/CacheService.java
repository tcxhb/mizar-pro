package com.tcxhb.mizar.core.service;

/**
 * @Description:
 * @Auther: tcxhb
 * @Date: 2023/4/3
 */
public interface CacheService {
    /**
     *
     * @param key
     * @param data
     * @param expireTime 秒
     * @return
     */
    public boolean put(String key, Object data,int expireTime);

    /**
     * 获取数据
     * @param key
     * @param <T>
     * @return
     */
    public <T> T get(String key);

    /**
     *
     * @param key
     * @return
     */
    public boolean clean(String key);
}
