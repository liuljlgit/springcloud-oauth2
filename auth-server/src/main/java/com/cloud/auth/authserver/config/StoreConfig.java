package com.cloud.auth.authserver.config;

import com.cloud.auth.authserver.security.CloudTokenEnhancer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.core.io.ClassPathResource;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.security.oauth2.provider.ClientDetailsService;
import org.springframework.security.oauth2.provider.token.DefaultTokenServices;
import org.springframework.security.oauth2.provider.token.TokenEnhancer;
import org.springframework.security.oauth2.provider.token.TokenEnhancerChain;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;
import org.springframework.security.oauth2.provider.token.store.KeyStoreKeyFactory;
import org.springframework.security.oauth2.provider.token.store.redis.RedisTokenStore;

import java.security.KeyPair;
import java.util.ArrayList;
import java.util.List;

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
     * jwt转换器
     * keyPair就是私钥签名（JWT协议中的signature部分）
     * @return
     */
    @Bean(name = "jwtAccessTokenConverter")
    @ConditionalOnMissingBean
    public JwtAccessTokenConverter jwtAccessTokenConverter(){
        JwtAccessTokenConverter accessTokenConverter = new JwtAccessTokenConverter();
        KeyPair keyPair = new KeyStoreKeyFactory(
                new ClassPathResource("keystore.jks"), "123456".toCharArray())
                .getKeyPair("o2jks");
        accessTokenConverter.setKeyPair(keyPair);
        return accessTokenConverter;
    }

    @Bean("cloudTokenServices")
    @Primary
    public DefaultTokenServices createDefaultTokenServices(ClientDetailsService clientDetailsService) {
        DefaultTokenServices tokenServices = new DefaultTokenServices();
        tokenServices.setTokenStore(getRedisTokenStore());
        tokenServices.setSupportRefreshToken(true);
        tokenServices.setReuseRefreshToken(false);
        tokenServices.setClientDetailsService(clientDetailsService);

        List<TokenEnhancer> enhancers = new ArrayList<>();
        enhancers.add(tokenEnhancer());//这个必须在前面
        enhancers.add(jwtAccessTokenConverter());
        TokenEnhancerChain tokenEnhancer = new TokenEnhancerChain();
        tokenEnhancer.setTokenEnhancers(enhancers);
        tokenServices.setTokenEnhancer(tokenEnhancer);
        return tokenServices;
    }

    /**
     * jwt增强:增加我们自定义的信息
     * @return
     */
    @Bean(name = "jwtTokenEnhancer")
    @ConditionalOnMissingBean( name = "jwtTokenEnhancer")
    public TokenEnhancer tokenEnhancer(){
        return new CloudTokenEnhancer();
    }

}
