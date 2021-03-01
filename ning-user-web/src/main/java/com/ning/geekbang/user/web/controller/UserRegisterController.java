package com.ning.geekbang.user.web.controller;

import com.ning.geekbang.user.web.domain.User;
import com.ning.geekbang.user.web.service.UserService;
import com.ning.geekbang.user.web.service.impl.UserServiceImpl;
import com.ning.web.mvc.controller.PageController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.POST;
import javax.ws.rs.Path;

/**
 * @Author: nicholas
 * @Date: 2021/2/28 19:14
 * @Descreption: 用户注册Controller
 */
@Path("user")
public class UserRegisterController implements PageController {

    private UserService userService = new UserServiceImpl();

    @POST
    @Path("register")
    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) {
        User user;
        try {
            user = User.getCheckedUserFromRequest(request);
        } catch (Exception e) {
            System.err.println(e.getMessage());
            return "register_fail.jsp";
        }
        boolean remember = Boolean.parseBoolean(request.getParameter("remember"));
        System.out.printf("用户注册登录 User = %s,remember = %s\r\n", user.toString(), remember);
        userService.register(user);
        return "register_success.jsp";
    }

}
