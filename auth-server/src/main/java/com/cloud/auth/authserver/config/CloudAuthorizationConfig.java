package com.cloud.auth.authserver.config;

import com.cloud.auth.authserver.service.inft.UserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.provider.token.store.redis.RedisTokenStore;

/**
 * 认证服务器
 */
@Configuration
@EnableAuthorizationServer
public class CloudAuthorizationConfig extends AuthorizationServerConfigurerAdapter {

    @Autowired
    private UserDetailsService userDetailsService;

    @Autowired
    private RedisConnectionFactory redisConnectionFactory;

    /**
     * 使用redis的方式来存储token
     * @return
     */
    @Bean
    RedisTokenStore redisTokenStore(){
        return new RedisTokenStore(redisConnectionFactory);
    }

    /**
     * 表示在内存中存储ID为cloud和密码为123456的客户端可以通过密码模式来获取到令牌
     * 也即通过这种方式可以获取令牌:localhost:8080/oauth/token?username=xxx&password=xxx&grant_type=password&client_id=aiqiyi&client_secret=secret
     * 所以我觉得认证流程应该是这样子的：clientID是cloud的客户端通过账号密码来获取授权码。账号密码通过authenticationManager校验,这个Manager会调用userDetailsService
     * ID:cloud
     * 密码:123456
     * @param clients
     * @throws Exception
     */
    @Override
    public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
        clients.inMemory().withClient("cloud")
                .secret("123456")
                .accessTokenValiditySeconds(86400)//一天的过期时间
                .refreshTokenValiditySeconds(100000)
                .authorizedGrantTypes("refresh_token","password","mobile")
                .scopes("all");
        //.scopes("all","read","write");
    }


}
