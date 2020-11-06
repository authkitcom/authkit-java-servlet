package com.authkit.servlet;

import com.authkit.AuthKitPrincipal;

import java.security.Principal;

public class DefaultPrincipalAdapter implements PrincipalAdapter {

    @Override
    public void init(Config config) {

    }

    @Override
    public Principal apply(AuthKitPrincipal authKitPrincipal) {
        return authKitPrincipal;
    }
}
