package com.pax.ehcache.objectproxy;

import net.sf.ehcache.config.CacheConfiguration;

import java.lang.reflect.Method;
import java.util.HashSet;
import java.util.Set;

public class ProxyConfig {

    private Set<Class<?>> interfaces = new HashSet<>();
    private Set<Method> methods = new HashSet<>();
    private Object target;
    private CacheConfiguration cacheConfig;
    private boolean proxyAllInterfaces = false;


    public ProxyConfig ProxyConfig() {
        return this;
    }

    public ProxyConfig addInterface(Class<?> iface) {
        interfaces.add(iface);
        return this;
    }

    public boolean hasInterface(Class<?> c1) {
        for (Class<?> c2 : interfaces) {
            if (c1.equals(c2)) {
                return true;
            }
        }
        return false;
    }

    public ProxyConfig addMethod(Method method) {
        methods.add(method);
        return this;
    }

    public boolean hasMethod(Method method) {
        return methods.contains(method);
    }

    public ProxyConfig target(Object target) {
        this.target = target;
        return this;
    }

    public ProxyConfig cacheConfiguration(CacheConfiguration cacheConfig) {
        this.cacheConfig = cacheConfig;
        return this;
    }

    public ProxyConfig proxyAllInterfaces() {
        addAllTargetInterfaces();
        return this;
    }

    private void addAllTargetInterfaces() {
        this.interfaces = new HashSet<>();
        for (Class<?> clazz : getTarget().getClass().getInterfaces()) {
            interfaces.add(clazz);
        }
    }

    public Set<Class<?>> getInterfaces() {
        return interfaces;
    }

    public Set<Method> getMethods() {
        return methods;
    }

    public Object getTarget() {
        return target;
    }

    public CacheConfiguration getCacheConfig() {
        return cacheConfig;
    }

    public boolean isProxyAllInterfaces() {
        return proxyAllInterfaces;
    }

}
