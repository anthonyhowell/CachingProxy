package com.pax.ehcache.objectproxy.exception;

public enum Error {

    NO_DEFAULT_CACHE(ErrorType.CONFIG, "No default cache configuration has been provided", 10);

    private ErrorType type;
    private String message;
    private int code;

    Error(ErrorType type, String message, int code) {
        this.type = type;
        this.message = message;
        this.code = code;
    }

    public ErrorType getType() {
        return type;
    }

    public String getMessage() {
        return message;
    }

    public int getCode() {
        return code;
    }

}
