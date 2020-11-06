package com.authkit.servlet;

import javax.servlet.FilterConfig;
import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

public class Config {

    private final Map<String, String> initParameters;
    private final ServletContext servletContext;

    // Visible for testing
    Config()  {
        this.initParameters = new HashMap<String, String>();
        this.servletContext = null;
    }

    public Config(FilterConfig filterConfig) {

        Map<String, String> params = new HashMap<String, String>();

        Enumeration<String> names = filterConfig.getInitParameterNames();

        while (names.hasMoreElements()) {
            String name = names.nextElement();
            params.put(name, filterConfig.getInitParameter(name));
        }

        this.initParameters = params;
        this.servletContext = filterConfig.getServletContext();
    }

    // Visible for testing
    public Config(Map<String, String> initParameters, ServletContext servletContext) {

        this.initParameters = initParameters;
        this.servletContext = servletContext;
    }

    public Map<String, String> getInitParameters() {
        return initParameters;
    }

    public ServletContext getServletContext() {
        return servletContext;
    }
}
