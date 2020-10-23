package com.authkit.servlet;

import com.authkit.AuthKitPrincipal;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.security.Principal;

public class AuthKitFilter implements Filter {

    public static final TokenExtractor DEFAULT_EXTRACTOR = new DefaultTokenExtractor();
    public static final PrincipalAdapter DEFAULT_PRINCIPAL_ADAPTER = new DefaultPrinciplaAdapter();
    public static final Handler DEFAULT_HANDLER = new DefaultHandler();

    private final TokenExtractor extractor;
    private final PrincipalAdapter adapter;
    private final Handler handler;
    private final Authenticator authenticator;

    public AuthKitFilter() {
        this(DEFAULT_EXTRACTOR, DEFAULT_PRINCIPAL_ADAPTER, DEFAULT_HANDLER);
    }

    public AuthKitFilter(TokenExtractor extractor,
                         PrincipalAdapter adapter,
                         Handler handler) {
        this.extractor = extractor;
        this.adapter = adapter;
        this.handler = handler;
        this.authenticator = new DefaultAuthenticator();
    }

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

        Config config = new Config(filterConfig);

        this.extractor.init(config);
        this.adapter.init(config);
        this.handler.init(config);
        this.adapter.init(config);

    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {

        HttpServletRequest req = (HttpServletRequest)  request;

        String token = extractor.apply(req);

        if (token == null) {
            handler.handleUnauthenticated(request, response, chain);
            return;
        }

        AuthKitPrincipal principal = authenticator.authenticate(token);

        if (principal == null) {
            handler.handleUnauthenticated(request, response, chain);
            return;
        }

        Principal translatedPrincipal = adapter.apply(principal);

        if (translatedPrincipal == null) {
            handler.handleUnauthenticated(request, response, chain);
            return;
        }

        handler.handleAuthenticated(translatedPrincipal, request, response, chain);
    }

    @Override
    public void destroy() {

    }
}