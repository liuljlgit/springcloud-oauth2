package com.cloud.auth.authserver.config;

import com.cloud.auth.authserver.security.login.LoginAuthenticationProvider;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Collections;

/**
 * 用户认证和授权校验相关配置
 * AuthenticationManager需要一个DaoAuthenticationProvider
 * DaoAuthenticationProvider需要UserDetailsService（获取数据库用户信息）和passwordEncoder（密码加密）
 * 自定义的UserDetailsService需要实现oauth2的UserDetailsService的loadUserByUsername方法
 */
@Configuration
public class AuthConfig {

    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

    @Bean("cloudAuthenticationManager")
    public AuthenticationManager authenticationManager(UserDetailsService userDetailsService, PasswordEncoder passwordEncoder) {
        ProviderManager authenticationManager = new ProviderManager(Collections.singletonList(loginAuthenticationProvider(userDetailsService, passwordEncoder)));
        authenticationManager.setEraseCredentialsAfterAuthentication(false);
        return authenticationManager;
    }

    @Bean
    public LoginAuthenticationProvider loginAuthenticationProvider(UserDetailsService userDetailsService, PasswordEncoder passwordEncoder){
        return new LoginAuthenticationProvider(userDetailsService,passwordEncoder);
    }

}
