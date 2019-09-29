package com.cloud.data.dataserver.config;


import com.cloud.common.webcomm.CodeEnum;
import com.cloud.common.webcomm.RespEntity;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.oauth2.common.exceptions.InvalidTokenException;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configurers.ResourceServerSecurityConfigurer;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.util.CollectionUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Configuration
@EnableResourceServer
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class CloudResourceConfig extends ResourceServerConfigurerAdapter{

    @Autowired(required = false)
    private CloudResourceConfiguration cloudResourceConfiguration;

    @Value("${spring.application.name}")
    private String RESOURCE_ID;

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

/**
 * token解析失败异常处理
 */
@Slf4j
class AuthExceptionEntryPoint implements AuthenticationEntryPoint {
    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response,
                         AuthenticationException authException) {
        log.error("鉴权失败",authException);
        Map<String, Object> map = new HashMap<String, Object>();
        Throwable cause = authException.getCause();
        response.setHeader("Content-Type", "application/json;charset=UTF-8");
        try {
            if(cause instanceof InvalidTokenException) {
                //未进行授权UNAUTHORIZED
                response.getWriter().write(RespEntity.commonResp(CodeEnum.EXEC_ERROR,null));
            }else{
                response.getWriter().write(RespEntity.commonResp(CodeEnum.EXEC_403,null));
            }
        } catch (Exception e) {
            log.error("HttpServletResponse异常",e);
        }
    }
}

/**
 * 权限不足异常类
 */
@Slf4j
class CustomAccessDeniedHandler implements AccessDeniedHandler {
    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response,
                       AccessDeniedException accessDeniedException) {
        log.error("权限不足",accessDeniedException);
        RespEntity.commonResp(CodeEnum.EXEC_403,null);
    }
}

/**
 * 资源路径配置类
 */
class CloudResourceConfiguration {
    private List<String> antMatchersList;

    public CloudResourceConfiguration() {

    }

    public List<String> getAntMatchersList() {
        return this.antMatchersList;
    }

    public void setAntMatchersList(List<String> antMatchersList) {
        this.antMatchersList = antMatchersList;
    }
}

