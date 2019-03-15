package com.cloud.zuul.zuulserver.filter;

import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.json.JsonParser;
import org.springframework.boot.json.JsonParserFactory;
import org.springframework.security.jwt.Jwt;
import org.springframework.security.jwt.JwtHelper;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;
import java.util.Objects;

/**
 * 资源过滤器
 * 所有的资源请求在路由之前进行前置过滤
 * 如果请求头不包含 Authorization参数值，直接拦截不再路由
 */
public class JwtTokenParseFilter extends ZuulFilter {
    private static Logger logger = LoggerFactory.getLogger(JwtTokenParseFilter.class);

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
        RequestContext requestContext = RequestContext.getCurrentContext();
        HttpServletRequest request = requestContext.getRequest();
        Object authorization = request.getHeader("Authorization");
        //解析jwt
        if(Objects.nonNull(authorization)){
            JsonParser jsonParser = JsonParserFactory.getJsonParser();
            String accessToken = authorization.toString().substring(6).trim();
            Jwt jwt = JwtHelper.decode(accessToken);
            String claims = jwt.getClaims();
            Map claimsMap = jsonParser.parseMap(claims);
            String userId = String.valueOf(claimsMap.get("user"));
        }
        return null;
    }
}
