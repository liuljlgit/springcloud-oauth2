package com.cloud.gateway.gatewayserver.filter;

import org.apache.log4j.Logger;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by pc on 2018/7/17.
 */
public class GateWayFilter extends OncePerRequestFilter {


    private static  final Logger logger = Logger.getLogger(GateWayFilter.class);

    @Override
    protected void doFilterInternal(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, FilterChain filterChain) throws ServletException, IOException {
        //判断用户登录授权信息
        filterChain.doFilter(httpServletRequest,httpServletResponse);
    }

}
