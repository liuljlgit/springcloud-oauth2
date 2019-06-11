package com.cloud.zuul.zuulserver.security;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.client.RestTemplate;

/**
 * Client talking to UAA's token endpoint to do different OAuth2 grants.
 */
public class UaaTokenEndpointClient extends OAuth2TokenEndpointClientAdapter implements OAuth2TokenEndpointClient {
    private final Logger log = LoggerFactory.getLogger(UaaTokenEndpointClient.class);

    public UaaTokenEndpointClient(RestTemplate restTemplate, OAuth2Properties oAuth2Properties) {
        super(restTemplate, oAuth2Properties);
    }

}
