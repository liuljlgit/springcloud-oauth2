package com.cloud.zuul.zuulserver.service.inft;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 进行登录认证的service
 */
public interface IAccessService {

    /**
     * 使用账户名密码进行登录（此处先不加密username,password,但后续应该加上）
     * @param username
     * @param password
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    String createAuthenticationToken(String username,String password, HttpServletRequest request, HttpServletResponse response) throws Exception;

    /**
     * 进行登出操作
     * @param request
     * @param response
     * @return
     */
    String logout(HttpServletRequest request, HttpServletResponse response);

    /**
     * 刷新token
     * @param request
     * @param response
     * @return
     */
    String refreshToken(HttpServletRequest request, HttpServletResponse response);

}
