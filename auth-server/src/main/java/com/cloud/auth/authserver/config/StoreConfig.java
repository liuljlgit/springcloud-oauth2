package com.cloud.auth.authserver.config;

import com.cloud.auth.authserver.security.CloudTokenEnhancer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.security.oauth2.provider.token.TokenEnhancer;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;
import org.springframework.security.oauth2.provider.token.store.KeyStoreKeyFactory;
import org.springframework.security.oauth2.provider.token.store.redis.RedisTokenStore;

import java.security.KeyPair;

/**
 * token存储配置
 */
@Configuration
public class StoreConfig {

    @Autowired
    private RedisConnectionFactory redisConnectionFactory;

    /**
     * token存储
     * @return
     */
    @Bean(name = "tokenStore")
    public TokenStore getRedisTokenStore(){
        return new RedisTokenStore(redisConnectionFactory);
    }

    /**
     * token转换,其实就是对jwt进行签名转换
     * @return
     */
    @Bean(name = "jwtAccessTokenConverter")
    @ConditionalOnMissingBean
    public JwtAccessTokenConverter jwtAccessTokenConverter(){
        JwtAccessTokenConverter accessTokenConverter = new JwtAccessTokenConverter();
        KeyPair keyPair = new KeyStoreKeyFactory(
                new ClassPathResource("keystore_prod.jks"), "junengcloud".toCharArray())
                .getKeyPair("huidian_key");
        accessTokenConverter.setKeyPair(keyPair);
        return accessTokenConverter;
    }

    /**
     * 进行token增强,加入自定义信息
     * @return
     */
    @Bean(name = "jwtTokenEnhancer")
    @ConditionalOnMissingBean( name = "jwtTokenEnhancer")
    public TokenEnhancer tokenEnhancer(){
        return new CloudTokenEnhancer();
    }

}
