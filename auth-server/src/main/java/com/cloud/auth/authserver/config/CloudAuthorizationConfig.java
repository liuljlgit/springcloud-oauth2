package com.cloud.auth.authserver.config;

import com.cloud.auth.authserver.security.exception.CustomWebResponseExceptionTranslator;
import com.cloud.auth.authserver.security.login.UserServiceDetail;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
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
import org.springframework.security.oauth2.provider.request.DefaultOAuth2RequestFactory;
import org.springframework.security.oauth2.provider.token.DefaultTokenServices;

import java.util.ArrayList;
import java.util.List;

/**
 * 认证服务器
 */
@Configuration
@EnableAuthorizationServer
public class CloudAuthorizationConfig extends AuthorizationServerConfigurerAdapter {

    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    ClientConfig.CustomJdbcClientDetailsService customJdbcClientDetailsService;

    @Autowired
    UserServiceDetail userServiceDetail;

    @Autowired
    DefaultTokenServices cloudTokenServices;

    @Autowired
    private CustomWebResponseExceptionTranslator customWebResponseExceptionTranslator;

    /**
     * 配置客户端信息
     * @param clients
     * @throws Exception
     */
    @Override
    public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
        clients.withClientDetails(customJdbcClientDetailsService);
    }

    /**
     * 配置endpoints端点信息
     * @param endpoints
     */
    @Override
    public void configure(AuthorizationServerEndpointsConfigurer endpoints) {
        endpoints.authenticationManager(authenticationManager)
                .userDetailsService(userServiceDetail)
                .allowedTokenEndpointRequestMethods(HttpMethod.GET, HttpMethod.POST)
                .tokenServices(cloudTokenServices)
                .exceptionTranslator(customWebResponseExceptionTranslator);

        CompositeTokenGranter tokenGranter = new CompositeTokenGranter(getDefaultTokenGranters(cloudTokenServices,customJdbcClientDetailsService));
        endpoints.tokenGranter(tokenGranter);
    }

    /**
     * 配置端点安全约束规则
     * @param security
     */
    @Override
    public void configure(AuthorizationServerSecurityConfigurer security) {
        //配置allowFormAuthenticationForClients,允许表单请求,使用restTemplate的时候需设置成MediaType.APPLICATION_FORM_URLENCODED
        security.tokenKeyAccess("permitAll()")
                .checkTokenAccess("isAuthenticated()")
                .allowFormAuthenticationForClients();
    }

    /**
     * token生成方式，以下配置的都是默认的
     * 也可以自定义一些其他的登录方式,比如手机登录或者sms登录
     * 每增加一种登录方式，需要在AuthConfig的设置AuthenticationManager的ProviderManager列表中多增加一种验证方式（需自定义）
     * @param tokenServices
     * @param clientDetails
     * @return
     */
    private List<TokenGranter> getDefaultTokenGranters(DefaultTokenServices tokenServices, ClientDetailsService clientDetails) {
        OAuth2RequestFactory requestFactory = new DefaultOAuth2RequestFactory(customJdbcClientDetailsService);
        AuthorizationCodeServices authorizationCodeServices = new InMemoryAuthorizationCodeServices();
        List<TokenGranter> tokenGranters = new ArrayList<>();
        tokenGranters.add(new AuthorizationCodeTokenGranter(tokenServices, authorizationCodeServices, clientDetails, requestFactory));
        tokenGranters.add(new RefreshTokenGranter(tokenServices, clientDetails, requestFactory));
        tokenGranters.add(new ImplicitTokenGranter(tokenServices, clientDetails, requestFactory));
        tokenGranters.add(new ClientCredentialsTokenGranter(tokenServices, clientDetails, requestFactory));
        if (authenticationManager != null) {
            tokenGranters.add(new ResourceOwnerPasswordTokenGranter(authenticationManager, tokenServices, clientDetails, requestFactory));
        }
        return tokenGranters;
    }

}
