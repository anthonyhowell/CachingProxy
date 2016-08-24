package com.pax.ehcache.objectproxy.exception;

public enum ErrorType {

    CONFIG("Config Error");

    private String type;

    ErrorType(String type) {
        this.type = type;
    }

    public String getType() {
        return type;
    }

}
