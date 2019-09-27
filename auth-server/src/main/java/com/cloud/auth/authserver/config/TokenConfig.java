package com.cloud.auth.authserver.config;

import com.cloud.auth.authserver.security.token.CloudTokenEnhancer;
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
 * token存储配置和JWT增强
 */
@Configuration
public class TokenConfig {

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

    /**
     * 配置jwt之后需加上这个配置
     * @param clientDetailsService
     * @return
     */
    @Bean("cloudTokenServices")
    @Primary
    public DefaultTokenServices createDefaultTokenServices(ClientDetailsService clientDetailsService) {
        DefaultTokenServices tokenServices = new DefaultTokenServices();
        tokenServices.setTokenStore(getRedisTokenStore());
        tokenServices.setSupportRefreshToken(true);
        tokenServices.setReuseRefreshToken(false); //该字段设置设置refresh token是否重复使用,true:reuse;false:no reuse.(就是说使用refresh token请求新token的时候是使用第一次生成的refresh token还是刷新后每次重新生成的token)
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
