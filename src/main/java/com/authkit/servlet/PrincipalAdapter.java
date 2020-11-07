package com.authkit.servlet;

import com.authkit.AuthKitPrincipal;

import javax.servlet.http.HttpServletRequest;
import java.security.Principal;
import java.util.function.Function;

public interface PrincipalAdapter {

    public Principal adapt(HttpServletRequest request, AuthKitPrincipal principal);

    void init(Config config);
}
