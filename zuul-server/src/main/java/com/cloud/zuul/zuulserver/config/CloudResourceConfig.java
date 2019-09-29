package com.cloud.zuul.zuulserver.config;

import com.cloud.common.webcomm.CodeEnum;
import com.cloud.common.webcomm.RespEntity;
import com.google.common.collect.Lists;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.oauth2.common.exceptions.InvalidTokenException;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configurers.ResourceServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.token.DefaultTokenServices;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.access.AccessDeniedHandler;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 资源服务器
 * 注意:只是保护网关的资源
 */
@Configuration
@EnableResourceServer
public class CloudResourceConfig extends ResourceServerConfigurerAdapter {

    public static List<String> ignoreUris = Lists.newArrayList("/signin","/signout","/refreshtoken");

    @Autowired
    @Qualifier("tokenServices")
    DefaultTokenServices tokenServices;

    @Value("${spring.application.name}")
    private String RESOURCE_ID;

    //配置资源id
    @Override
    public void configure(ResourceServerSecurityConfigurer resources) {
        //stateless 标志，指示仅允许对这些资源进行基于令牌的身份验证。
        resources.tokenServices(tokenServices).resourceId(RESOURCE_ID).stateless(true);
        resources.authenticationEntryPoint(new AuthExceptionEntryPoint())
                .accessDeniedHandler(new CustomAccessDeniedHandler());
    }

    //配置资源安全规则
    @Override
    public void configure(HttpSecurity http) throws Exception {
        http.
                csrf().disable()
                .exceptionHandling().authenticationEntryPoint(new AuthExceptionEntryPoint())//不存在access_token时候响应
                .and()
                .authorizeRequests()
                .antMatchers(ignoreUris.toArray(new String[ignoreUris.size()])).permitAll()
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
                response.setStatus(401);
                RespEntity.commonResp(CodeEnum.EXEC_401,null);
            }else{
                response.setStatus(403);
                RespEntity.commonResp(CodeEnum.EXEC_403,null);
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
