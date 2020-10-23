package com.authkit.servlet;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.security.Principal;

public class DefaultHandler implements Handler {

    @Override
    public void init(Config config) {

    }

    @Override
    public void handleUnauthenticated(ServletRequest request, ServletResponse response, FilterChain chain)  throws IOException, ServletException {

        HttpServletResponse resp = (HttpServletResponse) response;
        resp.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Unauthorized");
    }

    @Override
    public void handleAuthenticated(Principal principal, ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {

        HttpServletRequest req = new AuthKitRequestWrapper((HttpServletRequest) request, principal);
        chain.doFilter(req, response);
    }
}
