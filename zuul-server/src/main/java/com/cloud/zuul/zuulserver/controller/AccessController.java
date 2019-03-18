package com.cloud.zuul.zuulserver.controller;

import com.alibaba.fastjson.JSONObject;
import com.cloud.common.exception.BusiException;
import com.cloud.common.webcomm.RespEntity;
import com.cloud.zuul.zuulserver.security.OAuth2CookieHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;
import java.util.Objects;

/**
 * 进行登录和登出操作
 */
@RestController
public class AccessController {

    @Autowired
    RestTemplate restTemplate;

    private OAuth2CookieHelper cookieHelper = new OAuth2CookieHelper();

    /**
     * 用户登录（需加密传输）
     * @param reqEntity
     * @param request
     * @param response
     * @return
     */
    @PostMapping(value = "/login")
    public String createAuthenticationToken(@RequestBody JSONObject reqEntity, HttpServletRequest request, HttpServletResponse response) {
        String username = reqEntity.getString("username");
        String password = reqEntity.getString("password");
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
        cookieHelper.createCookies(request, accessToken, Boolean.FALSE,response);
        //登录信息返回
        Map<String,Object> map = accessToken.getAdditionalInformation();
        JSONObject respBody = new JSONObject(map);
        return RespEntity.ok(respBody);
    }

    @PostMapping(value = "/logout")
    public String logout(@RequestBody JSONObject reqEntity, HttpServletRequest request, HttpServletResponse response) throws Exception{
        Cookie cookie = OAuth2CookieHelper.getAccessTokenCookie(request);
        if ( null == cookie){
            throw new BusiException("请先登录!");
        }
        String token = String.format("Bearer %s", cookie.getValue());
        cookieHelper.clearCookies(request, response);
        return RespEntity.ok();
    }

    @PostMapping(value = "/testRestTemplate")
    public String testRestTemplate(@RequestBody JSONObject reqEntity, HttpServletRequest request, HttpServletResponse response) {
        ResponseEntity<String> forEntity = restTemplate.getForEntity("http://auth-server/sysRole/1000001?access_token=eyJhbGciOiJSUzI1NiIsInR5cCI6IkpXVCJ9.eyJ1c2VyX25hbWUiOiI2NDU2MDQ4OTkiLCJzY29wZSI6WyJhbGwiXSwiYWRkaXRpb25hbEluZm8iOiIxMjMiLCJleHAiOjE1NTI3MjUzNTgsImF1dGhvcml0aWVzIjpbIui2hee6p-euoeeQhuWRmCJdLCJqdGkiOiI1OTdlMGE2Ni0xMzRjLTRjNzYtYTIyYi01NDg3NTc1MGI2NTYiLCJjbGllbnRfaWQiOiJnYXRld2F5X2NsaWVudCJ9.XRVEm0zFXdZ6Di_Gkkt5H5NRCLT4DMeb5RsAaJPn4UpZCVlc72xDkkAs9B_UoDBZ9R_yakAk7cuq4ejUpq_B2DYq6C3dgiy3oheO9q2qn4pRgQz3ds8Z-er0KT2MqoSXfqZfz6Y5BQwac09tfLvsyX2ByVBjblWtpi13IvAe6bt1ObTzpwl45gk-QzbRpQCDrqaRJk7O0SGFlRbPbhk0iSEYcGloXVF3c89hnRzok0BAH0tlYUB9k2UEYEkZKaB4rqHAVOoCk-CDbG3uCSrusIYPfNokeYHEPGSVqR4XLbWnA1fWj0oo5W_rhH3Sp1Zs-7jH4HNV5mzC3feVNkshjg", String.class);
        return forEntity.getBody();
    }
}
