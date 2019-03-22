package com.cloud.zuul.zuulserver.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.cloud.common.exception.BusiException;
import com.cloud.common.webcomm.RespEntity;
import com.cloud.zuul.zuulserver.security.OAuth2CookieHelper;
import com.cloud.zuul.zuulserver.service.inft.IAccessService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.*;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.provider.token.DefaultTokenServices;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;
import java.util.Objects;

/**
 * 进行登录认证的service
 */
@Service
public class AccessServiceImpl implements IAccessService {

    private Logger logger  = LoggerFactory.getLogger(AccessServiceImpl.class);

    @Autowired
    OAuth2CookieHelper oAuth2CookieHelper;

    @Autowired
    TokenStore tokenStore;

    @Qualifier("tokenServices")
    @Autowired
    DefaultTokenServices tokenService;

    @Autowired
    RestTemplate restTemplate;

    @Override
    public String createAuthenticationToken(String username,String password, HttpServletRequest request, HttpServletResponse response) throws Exception {
        if(Objects.isNull(username) || Objects.isNull(password)){
            throw new BusiException("用户名密码不能为空");
        }
        //封装请求/oauth/token
        HttpHeaders reqHeaders = new HttpHeaders();
        //请求设置成表单模式，否则无法进行请求
        reqHeaders.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
        MultiValueMap<String, String> formParams = new LinkedMultiValueMap<>();
        formParams.set("username", username);
        formParams.set("password", password);
        formParams.set("grant_type", "password");
        formParams.set("scope", "all");
        formParams.set("client_id", "gateway_client");
        formParams.set("client_secret", "123456");
        HttpEntity<MultiValueMap<String, String>> entity = new HttpEntity<>(formParams, reqHeaders);
        ResponseEntity<OAuth2AccessToken> responseEntity = restTemplate.postForEntity("http://auth-server/oauth/token", entity, OAuth2AccessToken.class);
        OAuth2AccessToken accessToken = responseEntity.getBody();
        //把token设置到cookie中
        oAuth2CookieHelper.createCookies(request, accessToken, Boolean.FALSE,response);
        //登录信息返回
        Map<String,Object> map = accessToken.getAdditionalInformation();
        JSONObject respBody = new JSONObject(map);
        return RespEntity.ok(respBody);
    }

    @Override
    public String logout(HttpServletRequest request, HttpServletResponse response) {
        Cookie cookie = OAuth2CookieHelper.getAccessTokenCookie(request);
        if ( null == cookie){
            throw new BusiException("请先登录!");
        }
        String token = cookie.getValue();
        OAuth2AccessToken oAuth2AccessToken = tokenStore.readAccessToken(token);
        //tokenService.revokeToken(token);
        oAuth2CookieHelper.clearCookies(request, response);
        return RespEntity.ok();
    }

    @Override
    public String refreshToken(HttpServletRequest request, HttpServletResponse response) {
        Cookie refreshTokenCookie = OAuth2CookieHelper.getRefreshTokenCookie(request);
        if(Objects.isNull(refreshTokenCookie)){
            throw new BusiException("请先登录!");
        }
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.set("grant_type", "refresh_token");
        params.set("refresh_token", refreshTokenCookie.getValue().trim());
        params.set("client_id", "gateway_client");
        params.set("client_secret", "123456");
        HttpEntity<MultiValueMap<String, String>> entity = new HttpEntity<>(params, headers);
        ResponseEntity<OAuth2AccessToken> responseEntity = restTemplate.postForEntity("http://auth-server/oauth/token", entity, OAuth2AccessToken.class);
        if (responseEntity.getStatusCode() != HttpStatus.OK) {
            throw new HttpClientErrorException(responseEntity.getStatusCode());
        }
        OAuth2AccessToken accessToken = responseEntity.getBody();
        //把token设置到cookie中
        oAuth2CookieHelper.createCookies(request, accessToken, Boolean.FALSE,response);
        //登录信息返回
        Map<String,Object> map = accessToken.getAdditionalInformation();
        JSONObject respBody = new JSONObject(map);
        return RespEntity.ok(respBody);
    }
}
