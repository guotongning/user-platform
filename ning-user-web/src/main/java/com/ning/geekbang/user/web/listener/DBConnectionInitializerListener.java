package com.ning.geekbang.user.web.listener;

import com.ning.geekbang.user.web.context.ComponentContext;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;
import java.util.logging.Logger;


/**
 * {@link ComponentContext} 初始化器
 * ContextLoaderListener
 */
@WebListener
public class DBConnectionInitializerListener implements ServletContextListener {

    private static final Logger logger = Logger.getLogger(DBConnectionInitializerListener.class.getName());

    private ServletContext servletContext;

    @Override
    public void contextInitialized(ServletContextEvent sce) {
        logger.info("DBConnectionInitializerListener contextInitialized start");
        this.servletContext = sce.getServletContext();
        ComponentContext context = new ComponentContext();
        context.init(servletContext);
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {

    }

}
