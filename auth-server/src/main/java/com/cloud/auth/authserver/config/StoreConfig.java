package com.cloud.auth.authserver.config;

import com.cloud.auth.authserver.security.login.LoginAuthenticationProvider;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Arrays;

/**
 * PasswordEncoder、AuthenticationManager、LoginAuthenticationProvider、UserServiceDetail是我们登录所需要的
 */
@Configuration
public class StoreConfig {

    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

    @Bean("cloudAuthenticationManager")
    public AuthenticationManager authenticationManager(UserDetailsService userDetailsService, PasswordEncoder passwordEncoder) throws Exception {
        ProviderManager authenticationManager = new ProviderManager(Arrays.asList(loginAuthenticationProvider(userDetailsService,passwordEncoder)));
        authenticationManager.setEraseCredentialsAfterAuthentication(false);
        return authenticationManager;
    }

    @Bean
    public LoginAuthenticationProvider loginAuthenticationProvider(UserDetailsService userDetailsService, PasswordEncoder passwordEncoder){
        return new LoginAuthenticationProvider(userDetailsService,passwordEncoder);
    }

}
