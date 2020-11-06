package com.authkit.servlet;

import com.authkit.AuthKitPrincipal;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import javax.servlet.http.HttpServletRequest;
import java.security.Principal;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.verifyNoInteractions;

@ExtendWith(MockitoExtension.class)
public class AuthKitRequestWrapperTest {

    @Mock
    private HttpServletRequest request;

    @Test
    public void ctor() {

        Principal p = new AuthKitPrincipal();

        AuthKitRequestWrapper unit = new AuthKitRequestWrapper(request, p);

        assertThat(unit.getUserPrincipal()).isSameAs(p);

        verifyNoInteractions(request);
    }

}