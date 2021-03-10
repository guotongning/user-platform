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
public class ApplicationInitializerListener implements ServletContextListener {

    private static final Logger logger = Logger.getLogger(ApplicationInitializerListener.class.getName());

    private ServletContext servletContext;

    private ComponentContext context;

    @Override
    public void contextInitialized(ServletContextEvent sce) {
        logger.info("DBConnectionInitializerListener contextInitialized start");
        this.servletContext = sce.getServletContext();
        context = new ComponentContext();
        context.init(servletContext);
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        context.destroy();
    }

}
