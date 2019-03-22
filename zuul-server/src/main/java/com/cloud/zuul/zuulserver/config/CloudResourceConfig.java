package com.cloud.zuul.zuulserver.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configurers.ResourceServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.token.DefaultTokenServices;

/**
 * 资源服务器
 * 注意:只是保护网关的资源
 */
@Configuration
@EnableResourceServer
public class CloudResourceConfig extends ResourceServerConfigurerAdapter {

    @Autowired
    @Qualifier("tokenServices")
    DefaultTokenServices tokenServices;

    @Value("${spring.application.name}")
    private String RESOURCE_ID;

    //配置资源id
    @Override
    public void configure(ResourceServerSecurityConfigurer resources) {
        resources.tokenServices(tokenServices).resourceId(RESOURCE_ID).stateless(true);
    }

    //配置资源安全规则
    @Override
    public void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()
            .antMatchers("/app/zuul/signin").permitAll()
            .antMatchers("/app/zuul/signout").permitAll()
            .antMatchers("/app/zuul/refreshtoken").permitAll()
            .anyRequest()
            .authenticated();
    }
}
