package com.authkit.servlet;

import com.authkit.AuthKitPrincipal;
import com.authkit.servlet.Authenticator;

public class DefaultAuthenticator implements Authenticator {

    @Override
    public void init(Config config) {

    }

    @Override
    public AuthKitPrincipal authenticate(String token) {
        return null;
    }
}
