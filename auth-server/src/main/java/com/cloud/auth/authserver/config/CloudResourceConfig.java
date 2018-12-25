package com.cloud.auth.authserver.config;


import com.cloud.auth.authserver.security.resource.AuthExceptionEntryPoint;
import com.cloud.auth.authserver.security.resource.CloudResourceConfiguration;
import com.cloud.auth.authserver.security.resource.CustomAccessDeniedHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ImportResource;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configurers.ResourceServerSecurityConfigurer;
import org.springframework.util.CollectionUtils;

@Configuration
@EnableResourceServer
@ImportResource(locations = {"classpath*:spring-security.xml"})
public class CloudResourceConfig extends ResourceServerConfigurerAdapter{

    @Value("${spring.application.name}")
    private String RESOURCE_ID;

    @Autowired(required = false)
    private CloudResourceConfiguration cloudResourceConfiguration;

    @Override
    public void configure(ResourceServerSecurityConfigurer resources) {
        resources.resourceId(RESOURCE_ID).stateless(true);
        resources.authenticationEntryPoint(new AuthExceptionEntryPoint())
                 .accessDeniedHandler(new CustomAccessDeniedHandler());
    }

    @Override
    public void configure(HttpSecurity http) throws Exception {

        //如果配置了Spring-security.xml就已xml中配置为准
        String[] antMatchers;
        if ( null != cloudResourceConfiguration && !CollectionUtils.isEmpty(cloudResourceConfiguration.getAntMatchersList())){
            antMatchers = new String[cloudResourceConfiguration.getAntMatchersList().size()];
            for ( int i = 0;i< cloudResourceConfiguration.getAntMatchersList().size();i++){
                antMatchers[i] = cloudResourceConfiguration.getAntMatchersList().get(i).trim();
            }
            http.cors().and().csrf().disable()
                    .authorizeRequests()
                    .antMatchers(antMatchers).permitAll()
                    .anyRequest().authenticated()
                    .and()
                    .httpBasic();
            return;
        }

        http.cors().and().csrf().disable()
                .authorizeRequests()
                .anyRequest().authenticated()
                .and()
                .httpBasic();
    }


}
