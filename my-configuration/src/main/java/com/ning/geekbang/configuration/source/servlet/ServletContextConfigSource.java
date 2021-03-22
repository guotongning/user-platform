package com.ning.geekbang.configuration.source.servlet;

import com.ning.geekbang.configuration.source.MapBasedConfigSource;

import javax.servlet.ServletContext;
import java.util.Enumeration;
import java.util.Map;

public class ServletContextConfigSource extends MapBasedConfigSource {

    private static ServletContext servletContext;


    private ServletContextConfigSource() {
        super("ServletContext Init Parameters", 500);
    }

    public static ServletContextConfigSource getInstance(ServletContext servletContext) {
        ServletContextConfigSource.servletContext = servletContext;
        return new ServletContextConfigSource();
    }

    @Override
    protected void prepareConfigData(Map configData) {
        Enumeration<String> parameterNames = servletContext.getInitParameterNames();
        while (parameterNames.hasMoreElements()) {
            String parameterName = parameterNames.nextElement();
            configData.put(parameterName, servletContext.getInitParameter(parameterName));
        }
    }
}
