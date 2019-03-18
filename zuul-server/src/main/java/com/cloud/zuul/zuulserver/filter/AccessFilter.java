package com.cloud.zuul.zuulserver.filter;

import com.cloud.zuul.zuulserver.security.OAuth2CookieHelper;
import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

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

    private OAuth2CookieHelper oAuth2CookieHelper;

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
     * 过滤器是否会被执行
     * @return true
     */
    @Override
    public boolean shouldFilter() {
        return true;
    }

    /**
     * 过滤逻辑
     * @return 过滤结果
     */
    @Override
    public Object run() {
        RequestContext ctx = RequestContext.getCurrentContext();
        HttpServletRequest httpServletRequest = ctx.getRequest();
        HttpServletResponse httpServletResponse = ctx.getResponse();
        Cookie cookie = OAuth2CookieHelper.getAccessTokenCookie(httpServletRequest);
        if(Objects.nonNull(cookie)){
            String token = String.format("Bearer %s", cookie.getValue());
            ctx.addZuulRequestHeader("Authorization", token);
        }
        return null;
    }
}
