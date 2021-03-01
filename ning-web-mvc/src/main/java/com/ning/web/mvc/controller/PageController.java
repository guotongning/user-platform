package com.ning.web.mvc.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @Author: nicholas
 * @Date: 2021/2/28 16:12
 * @Descreption: 跳转页面的Controller
 */
public interface PageController extends Controller {

    /**
     * @param request  请求
     * @param response 响应
     * @return 对应的jsp文件名称
     * @throws Throwable 异常抛出
     */
    String execute(HttpServletRequest request, HttpServletResponse response) throws Throwable;
}
