package com.cloud.zuul.zuulserver.config;

import com.cloud.zuul.zuulserver.filter.AccessFilter;
import com.cloud.zuul.zuulserver.filter.CorsZuulFilter;
import com.cloud.zuul.zuulserver.security.Cookie.OAuth2CookieHelper;
import com.cloud.zuul.zuulserver.security.OAuth2AuthenticationService;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.oauth2.provider.token.TokenStore;

/**
 * 拦截器配置
 */
@Configuration
public class FilterConfig {

    /**
     * Cors拦截器
     * @return
     */
    @Bean
    public CorsZuulFilter corsZuulFilter(){
        return new CorsZuulFilter();
    }


    /**
     * access拦截器
     * @return access拦截器
     */
    @Bean
    public AccessFilter accessFilter(@Qualifier("getRedisTokenStore") TokenStore tokenStore, OAuth2CookieHelper oAuth2CookieHelper, OAuth2AuthenticationService authenticationService){
        return new AccessFilter(tokenStore,oAuth2CookieHelper,authenticationService);
    }

}
