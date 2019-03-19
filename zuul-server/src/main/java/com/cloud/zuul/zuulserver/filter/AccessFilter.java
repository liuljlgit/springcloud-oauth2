package com.cloud.zuul.zuulserver.filter;

import com.cloud.zuul.zuulserver.security.OAuth2CookieHelper;
import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.provider.token.TokenStore;

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

    private static Logger logger = LoggerFactory.getLogger(AccessFilter.class);

    private static final int REFRESH_WINDOW_SECS = 13600; //这个值是认证服务器中auth-server CloudAuthorizationConfig类中配置的 refresh_token的过期时间减去 access_token的过期时间(单位为秒)

    private final  TokenStore tokenStore;
    private OAuth2CookieHelper oAuth2CookieHelper;

    public AccessFilter(TokenStore tokenStore, OAuth2CookieHelper oAuth2CookieHelper) {
        this.tokenStore = tokenStore;
        this.oAuth2CookieHelper = oAuth2CookieHelper;
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
        return 0;
    }

    /**
     * 过滤器是否应该被执行（可以根据请求路径判断是否应该执行）
     * @return true
     */
    @Override
    public boolean shouldFilter() {
        return true;
    }

    /**
     * 进行拦截
     * 1.当access_token == null && refresh == null，证明cookie已经过期，返回401未登录异常。
     * 2.当access_token!=null && refresh_token == null,应该是cookie异常了,直接报token不合法token异常。
     * 3.当access_token != null && refresh_token!=null
     *      3.1）先判断当access_token是否过期，如果没有过期，给请求头部加上Authorization,放行;
     *      3.2）如果access_token过期,判断refresh_token是否过期，如果未过期，请求新access_token，如果过期，返回401未登录;
     * 4.access_token == null && refresh_token!=null，判断refresh_token是否过期，如果未过期，请求新access_token，如果过期，返回401未登录;
     * 5.注意登出的情况：access_token和refresh_token保存在redis中,像flushdb等类似操作可能会造成登出的情况。反正只要是redis中 refresh_token不存在,那么就是已经登出了。
     * @return
     */
    @Override
    public Object run() {
        RequestContext ctx = RequestContext.getCurrentContext();
        HttpServletRequest httpServletRequest = ctx.getRequest();
        HttpServletResponse httpServletResponse = ctx.getResponse();
        try{
            //得到cookie中的token
            /*Cookie accessTokenCookie = OAuth2CookieHelper.getAccessTokenCookie(httpServletRequest);
            Cookie refreshTokenCookie = OAuth2CookieHelper.getRefreshTokenCookie(httpServletRequest);*/
            Cookie cookie = OAuth2CookieHelper.getAccessTokenCookie(httpServletRequest);
            if(Objects.nonNull(cookie)){
                String token = String.format("Bearer %s", cookie.getValue());
                ctx.addZuulRequestHeader("Authorization", token);
            }
        }catch (Exception ex){
            logger.warn("Security exception: could not refresh tokens", ex);
            //清除cookie中的token信息
        }
        return null;
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
