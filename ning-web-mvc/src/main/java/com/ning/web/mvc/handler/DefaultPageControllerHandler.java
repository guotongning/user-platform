package com.ning.web.mvc.handler;

import com.ning.web.mvc.controller.Controller;
import com.ning.web.mvc.controller.PageController;

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
public class DefaultPageControllerHandler implements PageControllerHandler {

    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response, Controller controller) {
        try {
            PageController pageController = (PageController) controller;
            String viewPath = handleRequestMapping(pageController.execute(request, response));
            ServletContext servletContext = request.getServletContext();
            RequestDispatcher requestDispatcher = servletContext.getRequestDispatcher(viewPath);
            response.setHeader("ForwardMarker","1");
            requestDispatcher.forward(request, response);
        } catch (Throwable throwable) {
            throwable.printStackTrace();
        }
    }
}
