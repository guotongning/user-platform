package com.ning.geekbang.user.web.context;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.servlet.ServletContext;
import java.util.NoSuchElementException;

/**
 * @Author: nicholas
 * @Date: 2021/3/5 23:47
 * @Descreption: 组件上下文
 */
public class ComponentContext {


    public static final String CONTEXT_NAME = "ComponentContext";

    private static ServletContext servletContext;

    private Context context;


    /**
     * 获取 ComponentContext
     *
     * @return
     */
    public static ComponentContext getInstance() {
        return (ComponentContext) servletContext.getAttribute(CONTEXT_NAME);
    }


    public void init(ServletContext servletContext) {
        try {
            this.context = (Context) new InitialContext().lookup("java:comp/env");
        } catch (NamingException e) {
            throw new RuntimeException(e);
        }
        servletContext.setAttribute(CONTEXT_NAME, this);
        ComponentContext.servletContext = servletContext;
    }


    /**
     * 通过名称进行依赖查找
     *
     * @param name
     * @param <T>
     * @return
     */
    public <T> T getComponent(String name) {
        T component;
        try {
            component = (T) context.lookup(name);
        } catch (NamingException e) {
            throw new NoSuchElementException(name);
        }
        return component;
    }

    public void destroy() {
        if (this.context != null) {
            try {
                context.close();
            } catch (NamingException e) {
                throw new RuntimeException(e);
            }
        }
    }
}
