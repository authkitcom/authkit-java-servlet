package com.authkit.servlet;

import javax.servlet.http.HttpServletRequest;
import java.util.function.Function;

public class DefaultTokenExtractor implements TokenExtractor {

    private static final String PREFIX = "Bearer ";

    @Override
    public void init(Config config) {

    }

    @Override
    public String apply(HttpServletRequest request) {

        return extractFromHeader(request);
    }

    private String extractFromHeader(HttpServletRequest request) {

        String rawHeader = request.getHeader("Authorization");

        if (rawHeader == null) {
            return null;
        } else if (! rawHeader.startsWith(PREFIX)) {
            return null;
        } else {
            return rawHeader.substring(PREFIX.length(), rawHeader.length());
        }
    }

}
