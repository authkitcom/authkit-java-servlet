package com.authkit.servlet;

import javax.servlet.FilterConfig;
import javax.servlet.http.HttpServletRequest;
import java.util.function.Function;

public interface TokenExtractor extends Function<HttpServletRequest, String> {

    void init(Config config);
}
