package com.authkit.servlet;

import com.authkit.AuthKitPrincipal;

public interface Authenticator {

    void init(Config config);

    AuthKitPrincipal authenticate(String token);
}
