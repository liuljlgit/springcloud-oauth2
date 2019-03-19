package com.cloud.zuul.zuulserver.controller;

import com.alibaba.fastjson.JSONObject;
import com.cloud.zuul.zuulserver.service.inft.IAccessService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 进行登录和登出操作
 */
@RestController
public class AccessController {

    private Logger logger  = LoggerFactory.getLogger(AccessController.class);

    @Autowired
    IAccessService accessService;

    /**
     * 用户登录（需加密传输）
     * @param reqEntity
     * @param request
     * @param response
     * @return
     */
    @PostMapping(value = "/signin")
    public String createAuthenticationToken(@RequestBody JSONObject reqEntity, HttpServletRequest request, HttpServletResponse response) throws Exception {
        return accessService.createAuthenticationToken(reqEntity.getString("username"),reqEntity.getString("password"),request,response);
    }

    /**
     * 注销登录，只是清空了cookie,但是token在redis还是存在的（后续也可以去掉）。
     * @param request
     * @param response
     * @return
     */
    @PostMapping(value = "/signout")
    public String logout(HttpServletRequest request, HttpServletResponse response) {
        return accessService.logout(request,response);
    }


    /**
     * 主动刷新refresh token（获取refresh token一般都是写在filter中的,此处只是为了测试）
     * @param reqEntity
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    @PostMapping(value = "/refreshtoken")
    public String refreshToken(@RequestBody JSONObject reqEntity, HttpServletRequest request, HttpServletResponse response){
        return accessService.refreshToken(request,response);
    }

}
