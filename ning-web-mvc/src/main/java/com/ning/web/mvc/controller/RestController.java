package com.ning.web.mvc.controller;

import com.ning.web.mvc.response.Result;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @Author: nicholas
 * @Date: 2021/2/28 16:12
 * @Descreption: 处理业务的Controller 返回json格式的内容
 */
public interface RestController extends Controller {
    Result execute(HttpServletRequest request, HttpServletResponse response);
}
