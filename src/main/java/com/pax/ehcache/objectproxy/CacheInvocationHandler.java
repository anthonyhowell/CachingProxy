package com.pax.ehcache.objectproxy;

import com.sun.deploy.util.StringUtils;
import net.sf.ehcache.Element;
import net.sf.ehcache.config.CacheConfiguration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class CacheInvocationHandler implements InvocationHandler {

    private static final Logger LOG = LoggerFactory.getLogger(CacheConfiguration.class.getName());

    private Object target;
    private MethodCacheMap methodCacheMap;


    public CacheInvocationHandler(Object target, MethodCacheMap methodCacheMap) {
        this.target = target;
        this.methodCacheMap = methodCacheMap;
    }

    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        final String cacheKey = generateCacheName(target, method, args);

        if (!shouldCache(method)) {
            return getTargetResult(method, args);
        }

        Object cachedResult = getCachedResult(method, cacheKey);
        if (cachedResult != null) {
            LOG.debug("Found cache!");
            return cachedResult;
        }

        Object targetResult =  getTargetResult(method, args);
        cacheTargetResult(method, targetResult, cacheKey);

        return targetResult;
    }

    private final boolean shouldCache(Method method) {
        return methodCacheMap.containsKey(method);
    }

    private final Object getCachedResult(Method method, String cacheKey) {
        debug(Arrays.asList(cacheKey));

        Element cachedResult = methodCacheMap.get(method).get(cacheKey);
        return (cachedResult != null) ? cachedResult.getObjectValue() : null;
    }

    private final Object getTargetResult(Method method, Object[] args) throws Throwable {
        debug(Arrays.asList(method, args));

        return method.invoke(target, args);
    }

    private final void cacheTargetResult(Method method, Object targetResult, String cacheKey) {
        debug(Arrays.asList(targetResult, cacheKey));

        if (targetResultIsValid(targetResult)) {
            methodCacheMap.get(method).put(new Element(cacheKey, targetResult));
        }
    }

    private final String generateCacheName(Object target, Method method, Object[] args) {
        List<String> bits = new ArrayList<>();

        bits.add(method.getDeclaringClass().getSimpleName());
        bits.add(method.getName());

        if (args != null) {
            for (Object arg : args) {
                bits.add(arg.toString());
            }
        }

        return StringUtils.join(bits, ":");
    }

    private final boolean targetResultIsValid(Object result) {
        if (result == null) return false;
        return true;
    }

    private void debug(List<Object> args) {
        StackTraceElement[] stack = Thread.currentThread().getStackTrace();
        LOG.debug("{} : {} : {}", this.getClass().getSimpleName(), stack[2].getMethodName(), args);
    }

}
