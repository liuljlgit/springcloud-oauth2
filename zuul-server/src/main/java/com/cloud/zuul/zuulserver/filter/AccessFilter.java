package com.cloud.zuul.zuulserver.filter;

import com.cloud.zuul.zuulserver.security.Cookie.CookieCollection;
import com.cloud.zuul.zuulserver.security.Cookie.CookiesHttpServletRequestWrapper;
import com.cloud.zuul.zuulserver.security.Cookie.OAuth2CookieHelper;
import com.cloud.zuul.zuulserver.security.OAuth2AuthenticationService;
import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.common.OAuth2RefreshToken;
import org.springframework.security.oauth2.common.exceptions.InvalidTokenException;
import org.springframework.security.oauth2.common.exceptions.UnauthorizedClientException;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.web.client.HttpClientErrorException;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Objects;

/**
 * 资源过滤器
 * 所有的资源请求在路由之前进行前置过滤
 * 如果请求头不包含 Authorization参数值，直接拦截不再路由
 */
public class AccessFilter extends ZuulFilter {

    private final Logger log = LoggerFactory.getLogger(AccessFilter.class);

    private static final int REFRESH_WINDOW_SECS = 13600; //这个值是认证服务器中auth-server CloudAuthorizationConfig类中配置的 refresh_token的过期时间减去 access_token的过期时间(单位为秒)

    private final OAuth2AuthenticationService authenticationService;
    private final TokenStore tokenStore;
    private OAuth2CookieHelper oAuth2CookieHelper;

    public AccessFilter(TokenStore tokenStore, OAuth2CookieHelper oAuth2CookieHelper,OAuth2AuthenticationService authenticationService) {
        this.tokenStore = tokenStore;
        this.oAuth2CookieHelper = oAuth2CookieHelper;
        this.authenticationService = authenticationService;
    }

    /**
     * 过滤器的类型 pre表示请求在路由之前被过滤
     * @return 类型
     */
    @Override
    public String filterType() {
        return "pre";
    }

    /**
     * 过滤器的执行顺序
     * @return 顺序 数字越大表示优先级越低，越后执行
     */
    @Override
    public int filterOrder() {
        return 1;
    }

    /**
     * 过滤器是否应该被执行（可以根据请求路径判断是否应该执行）
     * @return true
     */
    @Override
    public boolean shouldFilter() {
        return true;
    }

    @Override
    public Object run() {
        RequestContext ctx = RequestContext.getCurrentContext();
        HttpServletRequest httpServletRequest = ctx.getRequest();
        HttpServletResponse httpServletResponse = ctx.getResponse();
        try {
            httpServletRequest = refreshTokensIfExpiring(httpServletRequest, httpServletResponse);
            Boolean ifOtherLogOut = false;
            if ( httpServletRequest instanceof CookiesHttpServletRequestWrapper){
                ifOtherLogOut = ((CookiesHttpServletRequestWrapper) httpServletRequest).getIfOtherLoginOut();
            }
            //如果其他端已经注销了,本地也注销（看业务情况，也可以其他端注销的时候只清除本地的cookie,这样子不会影响其他端）
            Cookie cookie = OAuth2CookieHelper.getAccessTokenCookie(httpServletRequest);
            if(ifOtherLogOut){
                ctx.setResponseStatusCode(HttpStatus.UNAUTHORIZED.value());
                authenticationService.logout(httpServletRequest, httpServletResponse);
            }
            if((null == ifOtherLogOut || Boolean.FALSE.equals(ifOtherLogOut)) && Objects.nonNull(cookie)){
                String token = String.format("Bearer %s", cookie.getValue());
                ctx.addZuulRequestHeader("Authorization", token);
            }
        } catch (Exception ex) {
            log.warn("Security exception: could not refresh tokens", ex);
            authenticationService.stripTokens(httpServletRequest);
        }
        return null;
    }

    /**
     * Refresh the access and refresh tokens if they are about to expire.
     *
     * @param httpServletRequest  the servlet request holding the current cookies. If no refresh cookie is present,
     *                            then we are out of luck.
     * @param httpServletResponse the servlet response that gets the new set-cookie headers, if they had to be
     *                            refreshed.
     * @return a new request to use downstream that contains the new cookies, if they had to be refreshed.
     * @throws InvalidTokenException if the tokens could not be refreshed.
     */
    public HttpServletRequest refreshTokensIfExpiring(HttpServletRequest httpServletRequest, HttpServletResponse
            httpServletResponse) throws Exception{
        HttpServletRequest newHttpServletRequest = httpServletRequest;

        //get access token from cookie
        Cookie accessTokenCookie = OAuth2CookieHelper.getAccessTokenCookie(httpServletRequest);
        //判断刷新token在redis存不存在,如果不存在，说明其它端的退出后，把redis中的token删除了.
        Cookie refreshCookie = OAuth2CookieHelper.getRefreshTokenCookie(httpServletRequest);
        OAuth2RefreshToken oAuth2RefreshToken;
        if ( null != refreshCookie){
            oAuth2RefreshToken = tokenStore.readRefreshToken(refreshCookie.getValue());
            if ( null == oAuth2RefreshToken){
                oAuth2CookieHelper.clearCookies(httpServletRequest,httpServletResponse);
                CookieCollection requestCookies = new CookieCollection(httpServletRequest.getCookies());
                CookiesHttpServletRequestWrapper cookiesHttpServletRequestWrapper = new CookiesHttpServletRequestWrapper(httpServletRequest, requestCookies.toArray());
                cookiesHttpServletRequestWrapper.setIfOtherLoginOut(true);
                return cookiesHttpServletRequestWrapper;
            }
        }

        if (mustRefreshToken(accessTokenCookie)) {        //we either have no access token, or it is expired, or it is about to expire
            //get the refresh token cookie and, if present, request new tokens
            //Cookie refreshCookie = OAuth2CookieHelper.getRefreshTokenCookie(httpServletRequest);
            if (refreshCookie != null) {
                try {
                    newHttpServletRequest = authenticationService.refreshToken(httpServletRequest, httpServletResponse, refreshCookie);
                } catch (HttpClientErrorException ex) {
                    throw new UnauthorizedClientException("could not refresh OAuth2 token", ex);
                }
            } else if (accessTokenCookie != null) {
                log.warn("access token found, but no refresh token, stripping them all");
                OAuth2AccessToken token = tokenStore.readAccessToken(accessTokenCookie.getValue());
                if (token.isExpired()) {
                    throw new InvalidTokenException("access token has expired, but there's no refresh token");
                }
            }
        }
        return newHttpServletRequest;
    }

    /**
     * 是否必须刷新token,其实就是判定access_token是否已经过期
     * @param accessCookie
     * @return
     */
    private boolean mustRefreshToken(Cookie accessCookie) {
        if (accessCookie == null) {
            return true;
        }
        //check if token is expired or about to expire
        OAuth2AccessToken token = tokenStore.readAccessToken(accessCookie.getValue());
        //token.isExpired():token是否已经过期  token.getExpiresIn():token的过期时长，如果token的过期时长小于refresh_token比access_token多的时间，证明refresh_token仍旧有效
        if (token.isExpired() || token.getExpiresIn() < REFRESH_WINDOW_SECS) {
            return true;
        }
        return false;       //access token is still fine
    }
}
