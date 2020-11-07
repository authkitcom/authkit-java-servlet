package com.authkit.servlet;

import com.authkit.AuthKitPrincipal;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.security.Principal;
import java.util.function.Function;

public class AuthKitFilter implements Filter {

    public static final TokenExtractor DEFAULT_EXTRACTOR = new DefaultTokenExtractor();
    public static final PrincipalAdapter DEFAULT_PRINCIPAL_ADAPTER = new DefaultPrincipalAdapter();
    public static final Handler DEFAULT_HANDLER = new DefaultHandler();

    private final TokenExtractor extractor;
    private final PrincipalAdapter adapter;
    private final Handler handler;
    private final Authenticator authenticator;

    Function<FilterConfig, Config> configBuilder = new Function<FilterConfig, Config>() {
        @Override
        public Config apply(FilterConfig filterConfig) {
            return new Config(filterConfig);
        }
    };

    public AuthKitFilter() {
        this(DEFAULT_EXTRACTOR, DEFAULT_PRINCIPAL_ADAPTER, DEFAULT_HANDLER,
                new DefaultAuthenticator());
    }

    public AuthKitFilter(TokenExtractor extractor,
                         PrincipalAdapter adapter,
                         Handler handler,
                         Authenticator authenticator) {
        this.extractor = extractor;
        this.adapter = adapter;
        this.handler = handler;
        this.authenticator = authenticator;
    }

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

        Config config = configBuilder.apply(filterConfig);

        this.extractor.init(config);
        this.adapter.init(config);
        this.handler.init(config);
        this.authenticator.init(config);

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

        Principal translatedPrincipal = adapter.adapt(req, principal);

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