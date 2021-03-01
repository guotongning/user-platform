package com.ning.web.mvc.handler;

import java.lang.reflect.Method;
import java.util.Set;

/**
 * @Author: nicholas
 * @Date: 2021/2/28 16:27
 * @Descreption: 请求路径对应的处理方法以及方法支持的Http方法的对象。
 */
public class HandlerMethod {
    private final String requestURI;
    private final Method handlerMethod;
    private final Set<String> supprotedHttpMethods;

    public HandlerMethod(String requestURI, Method handlerMethod, Set<String> supprotedHttpMethods) {
        this.requestURI = requestURI;
        this.handlerMethod = handlerMethod;
        this.supprotedHttpMethods = supprotedHttpMethods;
    }

    public String getRequestURI() {
        return requestURI;
    }

    public Method getHandlerMethod() {
        return handlerMethod;
    }

    public Set<String> getSupprotedHttpMethods() {
        return supprotedHttpMethods;
    }
}
