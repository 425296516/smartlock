package com.anlida.smartlock.network.exception;

/**
 * 自定义异常类，根据响应code抛出异常捕获处理
 */
public class ApiException extends RuntimeException {
    private String errorCode;
    private String errorMessage;

    public ApiException(String code, String message) {
        this.errorCode = code;
        this.errorMessage = message;
    }

    public String getErrorCode() {
        return errorCode;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

}
