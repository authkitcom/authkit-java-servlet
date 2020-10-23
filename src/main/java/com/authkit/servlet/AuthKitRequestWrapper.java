package com.authkit.servlet;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;
import java.security.Principal;

public class AuthKitRequestWrapper extends HttpServletRequestWrapper {

    private final Principal userPrincipal;

    /**
     * Constructs a request object wrapping the given request.
     *
     * @param request
     * @throws IllegalArgumentException if the request is null
     */
    public AuthKitRequestWrapper(HttpServletRequest request, Principal userPrincipal) {
        super(request);
        this.userPrincipal = userPrincipal;
    }

    @Override
    public Principal getUserPrincipal() {
        return userPrincipal;
    }
}
