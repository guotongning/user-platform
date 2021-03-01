package com.ning.web.mvc.exception;

/**
 * @Author: nicholas
 * @Date: 2021/2/28 16:33
 * @Descreption:
 */
public class WebMvcException extends RuntimeException {
    public WebMvcException() {
    }

    public WebMvcException(String message) {
        super(message);
    }

    public WebMvcException(String message, Throwable cause) {
        super(message, cause);
    }

    public WebMvcException(Throwable cause) {
        super(cause);
    }

    public WebMvcException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
