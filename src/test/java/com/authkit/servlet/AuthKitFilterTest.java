package com.authkit.servlet;

import com.authkit.AuthKitPrincipal;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.security.Principal;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class AuthKitFilterTest {

    private AuthKitFilter unit;

    @Mock private TokenExtractor teMock;
    @Mock private PrincipalAdapter paMock;
    @Mock private Handler hMock;
    @Mock private Authenticator aMock;
    @Mock private FilterConfig fcMock;
    @Mock private HttpServletRequest reqMock;
    @Mock private HttpServletResponse respMock;
    @Mock private FilterChain fchnMock;
    private Config config;
    private final String token = "test-token";
    private final AuthKitPrincipal principal = new AuthKitPrincipal();
    @Mock private Principal translatedPrincipal;

    @BeforeEach
    public void setUp() {

        config = new Config();
        unit = new AuthKitFilter(teMock, paMock, hMock, aMock);
        unit.configBuilder = (fc) -> {
            assertThat(fc).isSameAs(fcMock);
            return config;
        };
    }

    private void verifyNoMoreInteractions() {
        Mockito.verifyNoMoreInteractions(teMock, paMock, hMock, aMock, fcMock, reqMock, respMock, fchnMock, translatedPrincipal);
    }

    @Test
    public void init() throws ServletException {

        unit.init(fcMock);

        verify(teMock).init(config);
        verify(paMock).init(config);
        verify(hMock).init(config);
        verify(aMock).init(config);

    }

    @Test
    public void doFilter_noToken() throws IOException, ServletException {

        when(teMock.apply(reqMock)).thenReturn(null);

        unit.doFilter(reqMock, respMock, fchnMock);

        verify(teMock).apply(reqMock);
        verify(hMock).handleUnauthenticated(reqMock, respMock, fchnMock);

        verifyNoMoreInteractions();
    }

    @Test
    public void doFilter_noPrincipal() throws IOException, ServletException {

        when(teMock.apply(reqMock)).thenReturn(token);
        when(aMock.authenticate(token)).thenReturn(null);

        unit.doFilter(reqMock, respMock, fchnMock);

        verify(teMock).apply(reqMock);
        verify(aMock).authenticate(token);
        verify(hMock).handleUnauthenticated(reqMock, respMock, fchnMock);

        verifyNoMoreInteractions();
    }

    @Test
    public void doFilter_noTranslatedPrincipal() throws IOException, ServletException {

        when(teMock.apply(reqMock)).thenReturn(token);
        when(aMock.authenticate(token)).thenReturn(principal);
        when(paMock.apply(principal)).thenReturn(null);

        unit.doFilter(reqMock, respMock, fchnMock);

        verify(teMock).apply(reqMock);
        verify(aMock).authenticate(token);
        verify(paMock).apply(principal);
        verify(hMock).handleUnauthenticated(reqMock, respMock, fchnMock);

        verifyNoMoreInteractions();
    }

    @Test
    public void doFilter_authenticated() throws IOException, ServletException {

        when(teMock.apply(reqMock)).thenReturn(token);
        when(aMock.authenticate(token)).thenReturn(principal);
        when(paMock.apply(principal)).thenReturn(translatedPrincipal);

        unit.doFilter(reqMock, respMock, fchnMock);

        verify(teMock).apply(reqMock);
        verify(aMock).authenticate(token);
        verify(paMock).apply(principal);
        verify(hMock).handleAuthenticated(translatedPrincipal, reqMock, respMock, fchnMock);

        verifyNoMoreInteractions();
    }
}
