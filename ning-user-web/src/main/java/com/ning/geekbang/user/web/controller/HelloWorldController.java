package com.ning.geekbang.user.web.controller;

import com.ning.web.mvc.controller.PageController;
import jdk.nashorn.internal.objects.annotations.Getter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.GET;
import javax.ws.rs.Path;

/**
 * Hello World Controller
 */
@Path("/hello")
public class HelloWorldController implements PageController {
    @GET
    @Path("/world")
    public String execute(HttpServletRequest request, HttpServletResponse response) throws Throwable {
        return "index.jsp";
    }

}
