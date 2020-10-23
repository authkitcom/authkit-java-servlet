package com.authkit.servlet;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import java.io.IOException;
import java.security.Principal;

public interface Handler {

    void init(Config config);

    void handleUnauthenticated(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException;

    void handleAuthenticated(Principal principal, ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException;
}