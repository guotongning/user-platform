package com.ning.geekbang.user.web.controller;

import com.ning.web.mvc.controller.RestController;
import com.ning.web.mvc.response.Result;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import java.util.Map;
import java.util.logging.Logger;

/**
 * @Author: nicholas
 * @Date: 2021/3/30 21:44
 * @Descreption:
 */
@Path("rest")
public class RestControllerTest implements RestController {

    private static final Logger logger = Logger.getLogger(UserRegisterController.class.getName());

    @Path("test")
    @POST
    @Override
    public Result execute(HttpServletRequest request, HttpServletResponse response) {
        Map<String, String[]> map = request.getParameterMap();
        map.entrySet().forEach(entry -> logger.info(entry.getKey() + " - " + entry.getValue()));
        return new Result("Success!");
    }
}
