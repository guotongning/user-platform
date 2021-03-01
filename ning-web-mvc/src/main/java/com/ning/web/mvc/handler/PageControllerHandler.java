package com.ning.web.mvc.handler;

import com.ning.web.mvc.controller.Controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@FunctionalInterface
public interface PageControllerHandler {
    void handle(HttpServletRequest request, HttpServletResponse response, Controller controller);
}
