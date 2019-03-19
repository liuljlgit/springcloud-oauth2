package com.cloud.zuul.zuulserver.security;

import com.alibaba.fastjson.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.json.JsonParser;
import org.springframework.boot.json.JsonParserFactory;
import org.springframework.security.jwt.Jwt;
import org.springframework.security.jwt.JwtHelper;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.common.OAuth2RefreshToken;
import org.springframework.security.oauth2.common.exceptions.InvalidTokenException;
import org.springframework.security.oauth2.provider.token.AccessTokenConverter;
import org.springframework.util.StringUtils;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.net.URLEncoder;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

/**
 * OAuth2CookieHelper
 */
public class OAuth2CookieHelper {

    public static Logger log = LoggerFactory.getLogger(OAuth2CookieHelper.class);

    public static final String ACCESS_TOKEN_COOKIE = "access_token";        //access_token
    public static final String REFRESH_TOKEN_COOKIE = "refresh_token";      //refresh_token
    public static final String ACCESS_LOGIN_ACCOUNT = "login-account";      //token中的additionalInformation信息
    //清除的时候批量清除的cookie
    private static final List<String> COOKIE_NAMES = Arrays.asList(ACCESS_TOKEN_COOKIE, REFRESH_TOKEN_COOKIE, ACCESS_LOGIN_ACCOUNT);

    /**
     * 用来解析token claims的
     */
    private JsonParser jsonParser = JsonParserFactory.getJsonParser();

    /**
     * 得到cookie
     * @param request
     * @param cookieName
     * @return
     */
    private static Cookie getCookie(HttpServletRequest request, String cookieName) {
        if (request.getCookies() != null) {
            for (Cookie cookie : request.getCookies()) {
                if (cookie.getName().equals(cookieName)) {
                    String value = cookie.getValue();
                    if (StringUtils.hasText(value)) {
                        return cookie;
                    }
                }
            }
        }
        return null;
    }

    /**
     * 创建cookie
     * @param request
     * @param accessToken
     * @param rememberMe
     * @param response
     */
    public void createCookies(HttpServletRequest request, OAuth2AccessToken accessToken, boolean rememberMe, HttpServletResponse response) {
        //当前cookie域,建议做成可配置的方式
        String domain = "localhost";
        //access_token cookie
        Cookie accessTokenCookie = createTokenCookie(ACCESS_TOKEN_COOKIE,accessToken.getValue(),rememberMe);
        setCookieProperties(accessTokenCookie, request.isSecure(), domain);
        //refresh_token cookie
        OAuth2RefreshToken refreshToken = accessToken.getRefreshToken();
        Cookie refreshTokenCookie = createTokenCookie(REFRESH_TOKEN_COOKIE,refreshToken.getValue(), rememberMe);
        setCookieProperties(refreshTokenCookie, request.isSecure(), domain);
        //设置cookie到response中
        response.addCookie(accessTokenCookie);
        response.addCookie(refreshTokenCookie);
        writeOauth2AccessToken2Resp(ACCESS_LOGIN_ACCOUNT,domain,accessToken,response);
    }

    /**
     * 创建access_token和refresh_token的cookie值
     * @param cookieName
     * @param tokenValue
     * @param rememberMe
     * @return
     */
    private Cookie createTokenCookie(String cookieName, String tokenValue, boolean rememberMe) {
        int maxAge = -1;
        //cookie的存活时间就是token的过期时间
        Integer exp = getClaim(tokenValue, AccessTokenConverter.EXP, Integer.class);
        if (exp != null) {
            int now = (int) (System.currentTimeMillis() / 1000L);
            maxAge = exp - now;
        }
        Cookie cookie = new Cookie(cookieName, tokenValue);
        cookie.setMaxAge(maxAge);
        return cookie;
    }

    /**
     * 根据claimName从Token中得到相应的值
     * @param refreshToken
     * @param claimName
     * @param clazz
     * @param <T>
     * @return
     */
    private <T> T getClaim(String refreshToken, String claimName, Class<T> clazz) {
        Jwt jwt = JwtHelper.decode(refreshToken);
        String claims = jwt.getClaims();
        Map<String, Object> claimsMap = jsonParser.parseMap(claims);
        Object claimValue = claimsMap.get(claimName);
        if (claimValue == null) {
            return null;
        }
        if (!clazz.isAssignableFrom(claimValue.getClass())) {
            throw new InvalidTokenException("claim is not of expected type: " + claimName);
        }
        return (T) claimValue;
    }

    /**
     * 设置cookie的属性值
     * @param cookie
     * @param isSecure
     * @param domain
     */
    private void setCookieProperties(Cookie cookie, boolean isSecure, String domain) {
        cookie.setHttpOnly(true);
        cookie.setPath("/");
        cookie.setSecure(isSecure);       //if the request comes per HTTPS set the secure option on the cookie
        if (domain != null) {
            cookie.setDomain(domain);
        }
    }

    private void writeOauth2AccessToken2Resp(String cookieName,String domain,OAuth2AccessToken oauth2AccessToken,HttpServletResponse response) {
        JSONObject jsonObject = new JSONObject(oauth2AccessToken.getAdditionalInformation());
        String resultMsg = "";
        try{
            resultMsg = URLEncoder.encode(jsonObject.toJSONString(),"utf-8");
        }catch (Exception ex){
            log.error("写登录信息到返回response失败,"  + ex.getMessage());
        }

        Cookie loginAccountCookie = new Cookie(cookieName,resultMsg);
        loginAccountCookie.setPath("/");
        //设置下过期时间为刷新token的过期时间
        Integer exp = getClaim(oauth2AccessToken.getRefreshToken().getValue(), AccessTokenConverter.EXP, Integer.class);
        int maxAge = -1;
        if (exp != null) {
            int now = (int) (System.currentTimeMillis() / 1000L);
            maxAge = exp - now;
        }
        loginAccountCookie.setMaxAge(maxAge);
        if (org.apache.commons.lang.StringUtils.isNotBlank(domain)){
            loginAccountCookie.setDomain(domain);
        }
        response.addCookie(loginAccountCookie);
    }

    /**
     * 得到access_token
     * @param request
     * @return
     */
    public static Cookie getAccessTokenCookie(HttpServletRequest request) {
        return getCookie(request, ACCESS_TOKEN_COOKIE);
    }

    /**
     * 得到refresh_token
     * @param request
     * @return
     */
    public static Cookie getRefreshTokenCookie(HttpServletRequest request) {
        return getCookie(request, REFRESH_TOKEN_COOKIE);
    }

    /**
     * 清除cookie
     * @param httpServletRequest
     * @param httpServletResponse
     */
    public void clearCookies(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) {
        String domain = "localhost";
        for (String cookieName : COOKIE_NAMES) {
            clearCookie(httpServletRequest, httpServletResponse, domain, cookieName);
        }
    }

    /**
     * 清除cookie
     * @param httpServletRequest
     * @param httpServletResponse
     * @param domain
     * @param cookieName
     */
    private void clearCookie(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse,
                             String domain, String cookieName) {
        Cookie cookie = new Cookie(cookieName, "");
        setCookieProperties(cookie, httpServletRequest.isSecure(), domain);
        cookie.setMaxAge(0);
        httpServletResponse.addCookie(cookie);
        log.debug("clearing cookie {}", cookie.getName());
    }
}
