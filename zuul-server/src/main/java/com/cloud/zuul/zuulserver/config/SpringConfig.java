package com.cloud.zuul.zuulserver.config;

import com.cloud.zuul.zuulserver.filter.AccessFilter;
import com.cloud.zuul.zuulserver.filter.CorsZuulFilter;
import com.cloud.zuul.zuulserver.security.OAuth2CookieHelper;
import com.cloud.zuul.zuulserver.service.inft.IAccessService;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.web.client.RestTemplate;

@Configuration
public class SpringConfig {

    /**
     * access拦截器
     * @return access拦截器
     */
    @Bean
    public AccessFilter accessFilter(@Qualifier("tokenStore") TokenStore tokenStore, OAuth2CookieHelper oAuth2CookieHelper, IAccessService accessService){
        return new AccessFilter(tokenStore,oAuth2CookieHelper,accessService);
    }

    /**
     * Cors过滤器
     * @return
     */
    @Bean
    public CorsZuulFilter corsZuulFilter(){
        return new CorsZuulFilter();
    }


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
        return new OAuth2CookieHelper();
    }


}
