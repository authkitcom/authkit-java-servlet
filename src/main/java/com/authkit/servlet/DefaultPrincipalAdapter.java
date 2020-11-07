package com.authkit.servlet;

import com.authkit.AuthKitPrincipal;

import javax.servlet.http.HttpServletRequest;
import java.security.Principal;

public class DefaultPrincipalAdapter implements PrincipalAdapter {

    @Override
    public Principal adapt(HttpServletRequest request, AuthKitPrincipal principal) {
        return principal;
    }

    @Override
    public void init(Config config) {

    }

}
