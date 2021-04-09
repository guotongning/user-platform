package com.ning.web.mvc.handler;

import com.ning.web.mvc.controller.Controller;
import com.ning.web.mvc.controller.PageController;
import com.ning.web.mvc.controller.RestController;
import com.ning.web.mvc.response.Result;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import static com.ning.web.mvc.utils.RequestMappingUtils.handleRequestMapping;

/**
 * @Author: nicholas
 * @Date: 2021/2/28 17:31
 * @Descreption:
 */
public class DefaultRestControllerHandler implements RestControllerHandler {

    @Override
    public Result handle(HttpServletRequest request, HttpServletResponse response, Controller controller) {
        try {
            RestController pageController = (RestController) controller;
            return pageController.execute(request, response);
        } catch (Throwable throwable) {
            throwable.printStackTrace();
        }
        return new Result(500, "fail");
    }
}
