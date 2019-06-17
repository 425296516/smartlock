package com.anlida.smartlock.model;

public class HttpResult<T> {
    private String code;
    private String msg;
    private T data;
    public static final String NORMAL_10000 = "10000", NORMAL_10015 = "10015";

    public String getCode() {
        return code;
    }

    public String getMsg() {
        return msg;
    }

    public T getData() {
        return data;
    }

    public boolean isNormal() {
        return NORMAL_10000.equals(code);
    }
}
