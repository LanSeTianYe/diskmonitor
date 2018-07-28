package com.sun.xiaotian.diskmonitor.cache;


import com.github.benmanes.caffeine.cache.Cache;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

/**
 * 一个类对应一个实体缓存
 */
@Component
public class OneClassOneCache {


    private final Cache<String, Object> cache;

    public OneClassOneCache(@Qualifier("cache") Cache<String, Object> cache) {
        this.cache = cache;
    }

    public <T> void add(T cacheValue) {
        cache.put(cacheValue.getClass().getName(), cacheValue);
    }

    public <T> T get(Class<T> type) {
        return (T) cache.getIfPresent(type.getName());
    }

}
