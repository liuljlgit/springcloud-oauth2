package com.cloud.auth.authserver.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class AuthExceptionEntryPoint implements AuthenticationEntryPoint {
    public AuthExceptionEntryPoint() {
    }

    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws ServletException {
        Map<String, Object> map = new HashMap();
        map.put("error", "Unauthorized");
        map.put("message", "没有资源的访问权限");
        map.put("path", request.getServletPath());
        map.put("timestamp", String.valueOf((new Date()).getTime()));
        map.put("status", 401);
        response.setContentType("application/json");
        response.setStatus(401);

        try {
            ObjectMapper mapper = new ObjectMapper();
            mapper.writeValue(response.getOutputStream(), map);
        } catch (Exception var6) {
            throw new ServletException();
        }
    }
}
