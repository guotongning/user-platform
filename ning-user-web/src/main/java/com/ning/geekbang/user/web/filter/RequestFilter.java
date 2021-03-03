package com.ning.geekbang.user.web.filter;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @Author: nicholas
 * @Date: 2021/3/2 20:41
 * @Descreption:
 */
public class RequestFilter implements Filter {

    private ServletContext context;

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        this.context = filterConfig.getServletContext();
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        if (request instanceof HttpServletRequest) {
            HttpServletRequest req = (HttpServletRequest) request;
            String requestURI = req.getRequestURI();
            context.log("收到请求：" + requestURI);
        }
        chain.doFilter(request, response);
    }

    @Override
    public void destroy() {
        context.log("User PlatForm RequestFilter Destroy!!!");
    }
}
