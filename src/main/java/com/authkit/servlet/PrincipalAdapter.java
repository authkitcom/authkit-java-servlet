package com.authkit.servlet;

import com.authkit.AuthKitPrincipal;

import java.security.Principal;
import java.util.function.Function;

public interface PrincipalAdapter extends Function<AuthKitPrincipal, Principal> {

    void init(Config config);
}
