package com.authkit.servlet;


import kong.unirest.HttpResponse;
import kong.unirest.JsonNode;
import kong.unirest.Unirest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

public class DefaultAuthenticatorTest {

    private Config config;
    private DefaultAuthenticator unit;
    private final String audience = "test-audience";

    @BeforeEach
    public void setUp() {

        Map<String, String> initParams = new HashMap<String, String>();
        initParams.put("issuer", "http://localhost:9999");
        initParams.put("audience", audience);
        config = new Config(initParams, null);

        unit = new DefaultAuthenticator();
    }

    @Test
    public void init() {

        unit.init(config);

        assertThat(unit.openIdConfiguration).isNotNull();
        assertThat(unit.jwks).isNotNull();
        assertThat(unit.publicKey).isNotNull();
        assertThat(unit.jwtParser).isNotNull();
    }

    @Test
    public void authenticate() {

        HttpResponse<JsonNode> resp = Unirest.get("http://localhost:9999/authorize?sub=a&json=true").asJson();

        if (resp.getStatus() != 200) {
            throw new RuntimeException("Authorize status code: " + resp.getStatus());
        }

        String code = resp.getBody().getObject().getString("code");

        HttpResponse<JsonNode> tokenResp = Unirest.post("http://localhost:9999/oauth/token")
                .field("code", code)
                .field("audience", audience)
                .field("redirect_uri", "http://localhost:8080")
                .asJson();

        if (tokenResp.getStatus() != 200) {
            throw new RuntimeException("Token status code: " + resp.getStatus());
        }

        String accessToken = tokenResp.getBody().getObject().getString("access_token");

        unit.init(config);

        unit.authenticate(accessToken);

    }
}