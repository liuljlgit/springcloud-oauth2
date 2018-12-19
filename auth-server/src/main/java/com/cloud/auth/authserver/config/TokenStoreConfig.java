package com.cloud.auth.authserver.config;

import com.cloud.auth.authserver.security.CloudTokenEnhancer;
import com.cloud.auth.authserver.security.LoginAuthenticationProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.core.io.ClassPathResource;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.provider.ClientDetailsService;
import org.springframework.security.oauth2.provider.OAuth2RequestFactory;
import org.springframework.security.oauth2.provider.request.DefaultOAuth2RequestFactory;
import org.springframework.security.oauth2.provider.token.DefaultTokenServices;
import org.springframework.security.oauth2.provider.token.TokenEnhancer;
import org.springframework.security.oauth2.provider.token.TokenEnhancerChain;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;
import org.springframework.security.oauth2.provider.token.store.KeyStoreKeyFactory;
import org.springframework.security.oauth2.provider.token.store.redis.RedisTokenStore;

import java.security.KeyPair;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * token存储配置
 */
@Configuration
public class TokenStoreConfig {

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
                .getKeyPair("oauth2jks");
        accessTokenConverter.setKeyPair(keyPair);
        return accessTokenConverter;
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

    /**
     * 密码加密器
     * @return
     */
    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

    /**
     * 用户登录
     * @param userDetailsService
     * @param passwordEncoder
     * @return
     */
    @Bean
    public LoginAuthenticationProvider loginAuthenticationProvider(UserDetailsService userDetailsService, PasswordEncoder passwordEncoder){
        return new LoginAuthenticationProvider(userDetailsService,passwordEncoder);
    }

    /**
     * 认证管理器
     * @param userDetailsService
     * @param passwordEncoder
     * @return
     * @throws Exception
     */
    @Bean("cloudAuthenticationManager")
    protected AuthenticationManager authenticationManager(UserDetailsService userDetailsService, PasswordEncoder passwordEncoder) throws Exception {
        ProviderManager authenticationManager = new ProviderManager(Arrays.asList(loginAuthenticationProvider(userDetailsService,passwordEncoder)));
        //不擦除认证密码，擦除会导致TokenBasedRememberMeServices因为找不到Credentials再调用UserDetailsService而抛出UsernameNotFoundException
        authenticationManager.setEraseCredentialsAfterAuthentication(false);
        return authenticationManager;
    }

    /**
     * tokenService
     * @param clientDetailsService
     * @return
     */
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
     * 根据用户权限来进行限制
     * @param clientDetailsService
     * @return
     */
    @Bean(name = "cloudOauth2RequestFactory")
    public OAuth2RequestFactory requestFactory(ClientDetailsService clientDetailsService) {
        return new DefaultOAuth2RequestFactory(clientDetailsService);
    }


}
