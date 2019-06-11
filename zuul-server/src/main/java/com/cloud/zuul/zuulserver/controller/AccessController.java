package com.cloud.zuul.zuulserver.controller;

import com.alibaba.fastjson.JSONObject;
import com.cloud.common.exception.BusiException;
import com.cloud.common.webcomm.CodeEnum;
import com.cloud.common.webcomm.RespEntity;
import com.cloud.zuul.zuulserver.security.OAuth2AuthenticationService;
import com.cloud.zuul.zuulserver.security.TokenAuthenticationReq;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/**
 * 进行登录和登出操作
 */
@RestController
public class AccessController {

    private Logger log  = LoggerFactory.getLogger(AccessController.class);

    @Autowired
    private OAuth2AuthenticationService authenticationService;

    /**
     * 用户登录（需加密传输）
     * @param tokenAuthenticationReq
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    @PostMapping(value = "/signin")
    public String createAuthenticationToken(@RequestBody TokenAuthenticationReq tokenAuthenticationReq, HttpServletRequest request, HttpServletResponse response) throws Exception {
        if(StringUtils.isEmpty(tokenAuthenticationReq.getUsername()) || StringUtils.isEmpty(tokenAuthenticationReq.getPassword())){
            throw new BusiException("用户不存在或密码错误");
        }
        Map<String, String> params = new HashMap<>();
        params.put("username",tokenAuthenticationReq.getUsername());
        params.put("password", tokenAuthenticationReq.getPassword());
        try {
            ResponseEntity<OAuth2AccessToken> accessTokenResponseEntity = authenticationService.authenticate(request, response, params);
            if (Objects.isNull(accessTokenResponseEntity)){
                return RespEntity.commonResp(CodeEnum.EXEC_ERROR.getCode(),"登录失败",null);
            }
            OAuth2AccessToken auth2AccessToken = accessTokenResponseEntity.getBody();
            Map<String,Object> map = auth2AccessToken.getAdditionalInformation();
            JSONObject respBody = new JSONObject(map);
            return RespEntity.commonResp(CodeEnum.EXEC_OK.getCode(),"登录成功",respBody);
        }catch (Exception e){
            log.error(e.getMessage(),e);
            return RespEntity.commonResp(CodeEnum.EXEC_ERROR.getCode(),"登录失败",null);
        }
    }

    /**
     * 注销登录
     * @param request
     * @param response
     * @return
     */
    @PostMapping(value = "/logout")
    public String logout(HttpServletRequest request, HttpServletResponse response) throws Exception{
        authenticationService.logout(request, response);
        return RespEntity.ok();
    }

}
