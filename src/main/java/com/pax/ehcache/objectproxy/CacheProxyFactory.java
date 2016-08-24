package com.pax.ehcache.objectproxy;

import com.pax.ehcache.objectproxy.exception.Error;
import com.pax.ehcache.objectproxy.exception.ObjectProxyException;
import net.sf.ehcache.Cache;
import net.sf.ehcache.CacheManager;
import net.sf.ehcache.config.CacheConfiguration;

import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class CacheProxyFactory {

    private CacheManager cacheManager;
    private Cache defaultCache;
    private List<ProxyConfig> proxyConfigs;


    public CacheProxyFactory(CacheManager cacheManager, CacheConfiguration defaultCacheConfig) throws ObjectProxyException {
        if (defaultCacheConfig == null) {
            throw new ObjectProxyException(Error.NO_DEFAULT_CACHE);
        }

        this.cacheManager = cacheManager;
        this.defaultCache = new Cache(defaultCacheConfig);
        this.cacheManager.addCache(this.defaultCache);
    }

    /**
     * Create a caching proxy for all methods, of all declared interfaces on the target.
     */
    public final <T> T create(Object target, ProxyConfig... proxyConfigs) {
        CacheInvocationHandler handler = new CacheInvocationHandler(target, getMethodCacheMap(proxyConfigs));
        return (T) Proxy.newProxyInstance(ClassLoader.getSystemClassLoader(), getUniqueInterfaces(proxyConfigs), handler);
    }

    /**
     * Convenience method.
     * Create a caching proxy with the provided cache configuration which will proxy all
     * methods on all declared interfaces of the target.
     */
    public final <T> T create(Object target, CacheConfiguration cacheConfig) {
        ProxyConfig[] proxyConfigs = new ProxyConfig[] { new ProxyConfig().target(target).proxyAllInterfaces().cacheConfiguration(cacheConfig) };
        CacheInvocationHandler handler = new CacheInvocationHandler(target, getMethodCacheMap(proxyConfigs));
        return (T) Proxy.newProxyInstance(ClassLoader.getSystemClassLoader(), getUniqueInterfaces(proxyConfigs), handler);
    }

    private Class<?>[] getUniqueInterfaces(ProxyConfig... proxyConfigs) {
        Set<Class<?>> proxyConfigSet = new HashSet<>();
        for (ProxyConfig proxyConfig : proxyConfigs) {
            proxyConfig.getInterfaces().stream().filter(clazz -> !proxyConfigSet.contains(clazz)).forEach(proxyConfigSet::add);
        }

        int index = 0;
        Class<?>[] classes = new Class<?>[proxyConfigSet.size()];
        for (Class<?> clazz : proxyConfigSet) {
            classes[index] = clazz;
            index++;
        }

        return classes;
    }

    private MethodCacheMap getMethodCacheMap(ProxyConfig[] proxyConfigs) {
        MethodCacheMap methodCacheMap = new MethodCacheMap();

        for (ProxyConfig proxyConfig : proxyConfigs) {

            Cache cache = (proxyConfig.getCacheConfig() != null)
                    ? new Cache(proxyConfig.getCacheConfig())
                    : this.defaultCache;

            if (cacheManager.getCache(cache.getName()) == null) {
                cacheManager.addCache(cache);
            }

            // Technically if you're trying to proxy all interfaces, there `should` be only one ProxyConfig
            if (proxyConfig.isProxyAllInterfaces()) {
                // Grab every method, from every interface
                for (Class<?> clazz : proxyConfig.getInterfaces()) {
                    for (Method method : clazz.getMethods()) {
                        if (!methodCacheMap.containsKey(method)) {
                            methodCacheMap.put(method, cache);
                        }
                    }
                }
            } else {
                // Grab only the specified methods
                for (Method method : proxyConfig.getMethods()) {
                    methodCacheMap.put(method, cache);
                }
            }
        }

        return methodCacheMap;
    }

}
