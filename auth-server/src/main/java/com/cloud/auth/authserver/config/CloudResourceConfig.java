package com.cloud.auth.authserver.config;

import com.cloud.common.webcomm.CodeEnum;
import com.cloud.common.webcomm.RespEntity;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.oauth2.common.exceptions.InvalidTokenException;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configurers.ResourceServerSecurityConfigurer;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.access.AccessDeniedHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.Map;

/**
 * 资源服务器中配置的ignoreUris只会对本服务起作用
 * ignoreUris带上网关前缀也是起作用的
 */
@Configuration
@EnableResourceServer
public class CloudResourceConfig extends ResourceServerConfigurerAdapter{

    public static List<String> ignoreUris = Lists.newArrayList("/**");

    @Value("${spring.application.name}")
    private String RESOURCE_ID;

    @Override
    public void configure(ResourceServerSecurityConfigurer resources) {
        //stateless 标志，指示仅允许对这些资源进行基于令牌的身份验证。
        resources.resourceId(RESOURCE_ID).stateless(true);
        //这里配置的错误拦截的时校验的路径出现的错误
        resources.authenticationEntryPoint(new AuthExceptionEntryPoint())
                 .accessDeniedHandler(new CustomAccessDeniedHandler());
    }

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
                         AuthenticationException authException) throws IOException, ServletException {
        log.error("鉴权失败",authException);
        Map<String, Object> map = Maps.newHashMap();
        Throwable cause = authException.getCause();
        response.setHeader("Content-Type", "application/json;charset=UTF-8");
        try {
            if(cause instanceof InvalidTokenException) {
                //未进行授权UNAUTHORIZED
                response.setStatus(401);
                response.getWriter().write(RespEntity.commonResp(CodeEnum.EXEC_401,null));
            }else{
                response.setStatus(403);
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
