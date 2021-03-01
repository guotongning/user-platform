package com.ning.geekbang.user.web.exception;

/**
 * @Author: nicholas
 * @Date: 2021/3/1 21:31
 * @Descreption:
 */
public class RequestParamException extends RuntimeException {
    public RequestParamException() {
    }

    public RequestParamException(String message) {
        super(message);
    }

    public RequestParamException(String message, Throwable cause) {
        super(message, cause);
    }

    public RequestParamException(Throwable cause) {
        super(cause);
    }

    public RequestParamException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
