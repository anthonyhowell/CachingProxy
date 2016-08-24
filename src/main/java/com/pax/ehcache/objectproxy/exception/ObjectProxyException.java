package com.pax.ehcache.objectproxy.exception;

import com.pax.ehcache.objectproxy.Version;

public class ObjectProxyException extends Exception {

    private static final long serialVersionUID = -8133978773909212764L;

    private String errorType;
    private String errorMessage;
    private int errorCode = -1;
    private int errorSubcode = -1;

    public ObjectProxyException(String message, Throwable cause) {
        super(message, cause);
    }

    public ObjectProxyException(String message) {
        this(message, (Throwable) null);
    }

    public ObjectProxyException(Throwable cause) {
        this(cause.getMessage(), cause);
    }

    public ObjectProxyException(Error error) {
        super(error.getMessage());
        this.errorType = error.getType().toString();
        this.errorMessage = error.getMessage();
        this.errorCode = error.getCode();
    }

    @Override
    public String getMessage() {
        StringBuilder value = new StringBuilder();
        if (errorMessage != null && errorCode != -1) {
            value.append("message - ").append(errorMessage).append("\n");
            value.append("code - ").append(errorCode).append("\n");
            if (errorSubcode != -1) {
                value.append("subcode - ").append(errorSubcode).append("\n");
            }
        } else {
            value.append(super.getMessage());
        }
        return value.toString();
    }

    public String getErrorType() {
        return errorType;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public int getErrorCode() {
        return errorCode;
    }

    public int getErrorSubcode() {
        return errorSubcode;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ObjectProxyException)) return false;

        ObjectProxyException that = (ObjectProxyException) o;

        if (errorCode != that.errorCode) return false;
        if (errorSubcode != that.errorSubcode) return false;
        if (errorMessage != null ? !errorMessage.equals(that.errorMessage) : that.errorMessage != null) return false;
        if (errorType != null ? !errorType.equals(that.errorType) : that.errorType != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = 0;
        result = 31 * result + (errorType != null ? errorType.hashCode() : 0);
        result = 31 * result + (errorMessage != null ? errorMessage.hashCode() : 0);
        result = 31 * result + errorCode;
        result = 31 * result + errorSubcode;
        return result;
    }

    @Override
    public String toString() {
        return getMessage() +
                "\nObjectProxyException{" +
                "errorType='" + errorType + '\'' +
                ", errorMessage='" + errorMessage + '\'' +
                ", errorCode=" + errorCode +
                ", errorSubcode=" + errorSubcode +
                ", version=" + Version.getVersion() +
                '}';
    }

}
