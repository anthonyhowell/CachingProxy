package com.pax.ehcache.objectproxy;

import net.sf.ehcache.Cache;
import net.sf.ehcache.CacheManager;
import net.sf.ehcache.config.CacheConfiguration;

import java.util.concurrent.ConcurrentHashMap;

public class CacheStore {

    private CacheManager manager;
    private Cache defaultCache;
    private ConcurrentHashMap<String, Cache> store;



    public CacheStore(CacheManager cacheManager, CacheConfiguration defaultCacheConfig) {
        manager = cacheManager;
        store = new ConcurrentHashMap<>();

        defaultCache = new Cache(defaultCacheConfig);
        manager.addCache(defaultCache);
    }

    public void addIfNotExists(Cache cache) {
        if (has(cache.getCacheConfiguration())) {
            return;
        }
        manager.addCache(cache);
    }

    public void addIfNotExists(CacheConfiguration cacheConfig) {
        if (has(cacheConfig)) {
            return;
        }

        manager.addCache(new Cache(cacheConfig));
    }

    public boolean has(Class<?> clazz) {
        return store.contains(clazz);
    }

    public boolean has(CacheConfiguration cacheConfig) {
        return store.contains(cacheConfig.getName());
    }

    public Cache get(Class<?> clazz) {
        return store.get(clazz);
    }

    public Cache getDefaultCache() {
        return defaultCache;
    }

    public interface DefaultCache {}

}
