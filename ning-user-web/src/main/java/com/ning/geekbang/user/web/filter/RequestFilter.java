package com.ning.geekbang.user.web.filter;

import javax.servlet.*;
import java.io.IOException;

/**
 * @Author: nicholas
 * @Date: 2021/3/2 20:41
 * @Descreption:
 */
public class RequestFilter implements Filter {

    private String encoding = "UTF-8";
    private ServletContext context;

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        this.encoding = filterConfig.getInitParameter("encoding");
        this.context = filterConfig.getServletContext();
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {

    }

    @Override
    public void destroy() {
        context.log("User PlatForm RequestFilter Destroy!!!");
    }
}
