package com.cloud.zuul.zuulserver.config;

import com.cloud.zuul.zuulserver.security.Cookie.OAuth2CookieHelper;
import com.cloud.zuul.zuulserver.security.OAuth2AuthenticationService;
import com.cloud.zuul.zuulserver.security.OAuth2Properties;
import com.cloud.zuul.zuulserver.security.UaaTokenEndpointClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

@Configuration
public class SpringConfig {

    @Autowired
    private OAuth2Properties oAuth2Properties;

    /**
     * restTemplate
     * @return
     */
    @Bean
    @LoadBalanced
    @Qualifier("restTemplate")
    RestTemplate restTemplate() {
        return new RestTemplate();
    }

    /**
     * cookieHelper
     * @return
     */
    @Bean
    public OAuth2CookieHelper cookieHelper() {
        return new OAuth2CookieHelper(oAuth2Properties);
    }

    /**
     * UaaTokenEndpointClient
     * @return
     */
    @Bean
    public UaaTokenEndpointClient uaaTokenEndpointClient(){
        return new UaaTokenEndpointClient(restTemplate(),oAuth2Properties);
    }

    /**
     * OAuth2AuthenticationService
     * @return
     */
    @Bean
    public OAuth2AuthenticationService uaaAuthenticationService() {
        return new OAuth2AuthenticationService(uaaTokenEndpointClient(), cookieHelper());
    }

}
