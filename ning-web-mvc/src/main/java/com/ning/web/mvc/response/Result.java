package com.ning.web.mvc.response;

import javax.servlet.http.HttpServletResponse;

/**
 * @Author: nicholas
 * @Date: 2021/2/28 19:16
 * @Descreption: 返回给前端的Result对象
 */
public class Result {
    private int code;
    private String message;
    private Object data;

    public Result(Object data) {
        this.code = HttpServletResponse.SC_OK;
        this.message = "success";
        this.data = data;
    }

    public Result(int code, String message) {
        this.code = code;
        this.message = message;
    }

    public Result(int code, String message, Object data) {
        this.code = code;
        this.message = message;
        this.data = data;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }
}
