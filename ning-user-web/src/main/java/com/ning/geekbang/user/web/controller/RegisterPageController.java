package com.ning.geekbang.user.web.controller;

import com.ning.web.mvc.controller.PageController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.GET;
import javax.ws.rs.Path;

/**
 * @Author: nicholas
 * @Date: 2021/2/28 18:23
 * @Descreption: 跳转去注册页面的Controller
 */
@Path("register")
public class RegisterPageController implements PageController {

    @GET
    public String execute(HttpServletRequest request, HttpServletResponse response) throws Throwable {
        return "register.jsp";
    }
}
