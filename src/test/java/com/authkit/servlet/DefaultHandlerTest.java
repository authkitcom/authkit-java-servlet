package com.authkit.servlet;

import com.authkit.AuthKitPrincipal;
import net.bytebuddy.asm.Advice;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.security.Principal;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;

@ExtendWith(MockitoExtension.class)
public class DefaultHandlerTest {

    @Mock
    private HttpServletRequest reqMock;
    @Mock
    private HttpServletResponse resMock;
    @Mock
    private FilterChain fcMock;

    private Principal principal;

    private DefaultHandler unit;

    @BeforeEach
    public void setUp() {

        principal = new AuthKitPrincipal();
        unit = new DefaultHandler();
    }

    private void assertNoMoreInteractions() {

        verifyNoMoreInteractions(reqMock, resMock, fcMock);
    }

    @Test
    public void handleUnauthenticated() throws IOException, ServletException {

        unit.handleUnauthenticated(reqMock, resMock, fcMock);
        verify(resMock).sendError(HttpServletResponse.SC_UNAUTHORIZED, "Unauthorized");
        assertNoMoreInteractions();
    }

    @Test
    public void handleAuthenticated() throws IOException, ServletException {

        ArgumentCaptor<ServletRequest> reqCaptor = ArgumentCaptor.forClass(ServletRequest.class);
        unit.handleAuthenticated(principal, reqMock, resMock, fcMock);
        verify(fcMock).doFilter(reqCaptor.capture(), eq(resMock));

        ServletRequest gotReq = reqCaptor.getValue();

        assertThat(gotReq).isInstanceOf(AuthKitRequestWrapper.class);
        assertThat(((HttpServletRequest)gotReq).getUserPrincipal()).isSameAs(principal);

        assertNoMoreInteractions();
    }
}