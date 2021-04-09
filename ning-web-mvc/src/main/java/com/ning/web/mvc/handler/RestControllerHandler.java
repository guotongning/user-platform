package com.ning.web.mvc.handler;

import com.ning.web.mvc.controller.Controller;
import com.ning.web.mvc.response.Result;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@FunctionalInterface
public interface RestControllerHandler {
    Result handle(HttpServletRequest request, HttpServletResponse response, Controller controller);
}
