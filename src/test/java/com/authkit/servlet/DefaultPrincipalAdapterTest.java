package com.authkit.servlet;

import com.authkit.AuthKitPrincipal;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class DefaultPrincipalAdapterTest {

    @Test
    public void apply() {

        DefaultPrincipalAdapter unit = new DefaultPrincipalAdapter();

        AuthKitPrincipal principal = new AuthKitPrincipal();

        assertThat(unit.adapt(null, principal)).isSameAs(principal);
    }
}