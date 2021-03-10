package com.ning.geekbang.user.web.controller;

import com.ning.commons.utils.StringUtils;
import com.ning.geekbang.user.web.domain.User;
import com.ning.geekbang.user.web.exception.RequestParamException;
import com.ning.geekbang.user.web.service.UserService;
import com.ning.web.mvc.controller.PageController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import java.util.logging.Logger;

/**
 * @Author: nicholas
 * @Date: 2021/2/28 19:14
 * @Descreption: 用户注册Controller
 */
@Path("user")
public class UserRegisterController implements PageController {

    private static final Logger logger = Logger.getLogger(UserRegisterController.class.getName());

    @Resource(name = "bean/UserService")
    private UserService userService;

    @POST
    @Path("register")
    @Override
    public String execute(HttpServletRequest request, HttpServletResponse response) {
        User user;
        try {
            user = getCheckedUserFromRequest(request);
        } catch (Exception e) {
            System.err.println(e.getMessage());
            return "register_fail.jsp";
        }
        boolean remember = Boolean.parseBoolean(request.getParameter("remember"));
        logger.info(String.format("用户注册登录 User = %s,remember = %s", user.toString(), remember));
        boolean register = userService.register(user);
        if (!register) {
            return "register_fail.jsp";
        }
        return "register_success.jsp";
    }


    private User getCheckedUserFromRequest(HttpServletRequest request) throws Exception {
        String name = request.getParameter("name");
        String phoneNumber = request.getParameter("phoneNumber");
        String email = request.getParameter("email");
        String password = request.getParameter("password");
        if (StringUtils.isEmpty(email) || StringUtils.isEmpty(password)) {
            throw new RequestParamException("参数不完整！");
        }
        return new User(name, email, password, phoneNumber);
    }

}
