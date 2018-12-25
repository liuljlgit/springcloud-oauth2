package com.cloud.zuul.zuulserver.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.core.io.ClassPathResource;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.security.oauth2.provider.token.DefaultTokenServices;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;
import org.springframework.security.oauth2.provider.token.store.KeyStoreKeyFactory;
import org.springframework.security.oauth2.provider.token.store.redis.RedisTokenStore;

import java.security.KeyPair;

@Configuration
public class JwtConfig {

    @Autowired
    private RedisConnectionFactory redisConnectionFactory;

    @Bean
    public TokenStore getRedisTokenStore(){
        return new RedisTokenStore(redisConnectionFactory);
    }

    @Bean
    public JwtAccessTokenConverter accessTokenConverter() {
        JwtAccessTokenConverter converter = new JwtAccessTokenConverter();
        KeyPair keyPair = new KeyStoreKeyFactory(
                new ClassPathResource("keystore.jks"), "123456".toCharArray())
                .getKeyPair("o2jks");
        converter.setKeyPair(keyPair);
        return converter;
    }

    @Bean
    @Primary
    public DefaultTokenServices tokenServices() {
        DefaultTokenServices defaultTokenServices = new DefaultTokenServices();
        defaultTokenServices.setTokenStore(getRedisTokenStore());
        return defaultTokenServices;
    }


}
