package com.cloud.auth.authserver.config;

import com.cloud.auth.authserver.security.login.UserServiceDetail;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.token.TokenEnhancer;
import org.springframework.security.oauth2.provider.token.TokenEnhancerChain;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;

import java.util.ArrayList;
import java.util.List;

/**
 * 认证服务器
 */
@Configuration
@EnableAuthorizationServer
public class CloudAuthorizationConfig extends AuthorizationServerConfigurerAdapter {

    @Autowired
    @Qualifier("cloudAuthenticationManager")
    AuthenticationManager authenticationManager;

    @Autowired
    TokenStore tokenStore;

    @Autowired
    UserServiceDetail userServiceDetail;

    @Autowired(required = false)

    private JwtAccessTokenConverter jwtAccessTokenConverter;

    @Autowired(required = false)
    @Qualifier("jwtTokenEnhancer")
    private TokenEnhancer jwtTokenEnhancer;

    /**
     * 配置客户端信息
     * @param clients
     * @throws Exception
     */
    @Override
    public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
        //配置一个客户端使用password认证,下游服务只需要通过gateway-server进行代理就可以了
        clients.inMemory().withClient("gateway_client")
                .secret("123456")
                .accessTokenValiditySeconds(86400) //一天的过期时间
                .refreshTokenValiditySeconds(100000)
                .authorizedGrantTypes("password", "refresh_token")
                .scopes("all");
    }

    /**
     * 配置endpoints端点信息
     * @param endpoints
     * @throws Exception
     */
    @Override
    public void configure(AuthorizationServerEndpointsConfigurer endpoints) throws Exception {
        endpoints.tokenStore(tokenStore)
                .authenticationManager(authenticationManager)
                .userDetailsService(userServiceDetail)
                .allowedTokenEndpointRequestMethods(HttpMethod.GET, HttpMethod.POST);
        //加入jwt增强
        TokenEnhancerChain tokenEnhancerChain;
        if ( null != jwtAccessTokenConverter && null != jwtTokenEnhancer){
            tokenEnhancerChain = new TokenEnhancerChain();
            List<TokenEnhancer> enhancers = new ArrayList<>();
            enhancers.add(jwtTokenEnhancer);//这个必须在前面
            enhancers.add(jwtAccessTokenConverter);
            tokenEnhancerChain.setTokenEnhancers(enhancers);
            endpoints.tokenEnhancer(tokenEnhancerChain);
        }
    }

    /**
     * 配置端点安全约束规则
     * @param security
     * @throws Exception
     */
    @Override
    public void configure(AuthorizationServerSecurityConfigurer security) throws Exception {
        security.tokenKeyAccess("permitAll()").checkTokenAccess("isAuthenticated()").allowFormAuthenticationForClients();
    }

}
