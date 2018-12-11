package com.cloud.auth.authserver.config;

import com.cloud.auth.authserver.service.inft.UserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.ClientDetailsService;
import org.springframework.security.oauth2.provider.CompositeTokenGranter;
import org.springframework.security.oauth2.provider.OAuth2RequestFactory;
import org.springframework.security.oauth2.provider.TokenGranter;
import org.springframework.security.oauth2.provider.client.ClientCredentialsTokenGranter;
import org.springframework.security.oauth2.provider.code.AuthorizationCodeServices;
import org.springframework.security.oauth2.provider.code.AuthorizationCodeTokenGranter;
import org.springframework.security.oauth2.provider.code.InMemoryAuthorizationCodeServices;
import org.springframework.security.oauth2.provider.implicit.ImplicitTokenGranter;
import org.springframework.security.oauth2.provider.password.ResourceOwnerPasswordTokenGranter;
import org.springframework.security.oauth2.provider.refresh.RefreshTokenGranter;
import org.springframework.security.oauth2.provider.token.DefaultTokenServices;
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
    private AuthenticationManager authenticationManager;

    @Autowired
    private UserDetailsService userDetailsService;

    @Autowired
    private TokenStore tokenStore;

    @Autowired(required = false)
    @Qualifier("jwtAccessTokenConverter")
    private JwtAccessTokenConverter jwtAccessTokenConverter;

    @Autowired(required = false)
    @Qualifier("jwtTokenEnhancer")
    private TokenEnhancer jwtTokenEnhancer;

    @Autowired
    private ClientDetailsService clientDetailsService;

    @Autowired
    @Qualifier("cloudTokenServices")
    private DefaultTokenServices tokenServices;

    @Autowired
    @Qualifier("cloudOauth2RequestFactory")
    private OAuth2RequestFactory requestFacotry;

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

    @Override
    public void configure(AuthorizationServerEndpointsConfigurer endpoints) throws Exception {
        endpoints.tokenStore(tokenStore)
                .authenticationManager(authenticationManager)
                .userDetailsService(userDetailsService);

        TokenEnhancerChain tokenEnhancerChain;
        if ( null != jwtAccessTokenConverter && null != jwtTokenEnhancer){
            tokenEnhancerChain = new TokenEnhancerChain();
            List<TokenEnhancer> enhancers = new ArrayList<>();
            enhancers.add(jwtTokenEnhancer);//这个必须在前面
            enhancers.add(jwtAccessTokenConverter);
            tokenEnhancerChain.setTokenEnhancers(enhancers);
            endpoints.tokenEnhancer(tokenEnhancerChain);
        }
        CompositeTokenGranter tokenGranter = new CompositeTokenGranter(getDefaultTokenGranters(tokenServices,clientDetailsService,requestFacotry));
        endpoints.tokenGranter(tokenGranter);
    }

    private List<TokenGranter> getDefaultTokenGranters(@Qualifier("cloudTokenServices") DefaultTokenServices tokenServices, ClientDetailsService clientDetails, @Qualifier("cloudOauth2RequestFactory") OAuth2RequestFactory requestFactory) {
        AuthorizationCodeServices authorizationCodeServices = authorizationCodeServices();
        List<TokenGranter> tokenGranters = new ArrayList<>();
        tokenGranters.add(new AuthorizationCodeTokenGranter(tokenServices, authorizationCodeServices, clientDetails,
                requestFactory));
        tokenGranters.add(new RefreshTokenGranter(tokenServices, clientDetails, requestFactory));
        ImplicitTokenGranter implicit = new ImplicitTokenGranter(tokenServices, clientDetails, requestFactory);
        tokenGranters.add(implicit);
        tokenGranters.add(new ClientCredentialsTokenGranter(tokenServices, clientDetails, requestFactory));
        if (authenticationManager != null) {
            tokenGranters.add(new ResourceOwnerPasswordTokenGranter(authenticationManager, tokenServices,
                    clientDetails, requestFactory));
        }
        return tokenGranters;
    }

    private AuthorizationCodeServices authorizationCodeServices() {
        return new InMemoryAuthorizationCodeServices();
    }

    @Override
    public void configure(AuthorizationServerSecurityConfigurer security) throws Exception {
        security.tokenKeyAccess("permitAll()").checkTokenAccess("isAuthenticated()");
    }


}
