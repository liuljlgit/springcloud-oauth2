package com.cloud.auth.authserver.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;

/**
 * 资源服务器
 * 因为我们auth-server也会用来获取用户信息，所以也是一个资源服务器
 * 需要进行拦截链配置
 */
@Configuration
@EnableResourceServer
public class CloudResourceConfig {
}
