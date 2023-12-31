package com.tcxhb.mizar.core.service.impl;

import com.tcxhb.mizar.core.service.CacheService;
import com.tcxhb.mizar.common.utils.LocalCache;
import org.springframework.stereotype.Component;

/**
 * @Description:
 * @Auther: tcxhb
 * @Date: 2023/4/3
 */
@Component
public class CacheServiceImpl implements CacheService {
    LocalCache cache = new LocalCache();

    @Override
    public boolean put(String key, Object data, int expireTime) {
        cache.put(key, data,expireTime * 1000);
        return true;
    }

    @Override
    public <T> T get(String key) {
        return (T) cache.get(key);
    }

    @Override
    public boolean clean(String key) {
        cache.clean(key);
        return true;
    }
}
