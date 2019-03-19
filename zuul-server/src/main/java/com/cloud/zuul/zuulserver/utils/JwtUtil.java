package com.cloud.zuul.zuulserver.utils;

import org.springframework.boot.json.JsonParser;
import org.springframework.boot.json.JsonParserFactory;
import org.springframework.security.jwt.Jwt;
import org.springframework.security.jwt.JwtHelper;

import java.util.Map;

public class JwtUtil {

    public static Map getJwtClaimMap(String accessToken){
        JsonParser jsonParser = JsonParserFactory.getJsonParser();
        Jwt jwt = JwtHelper.decode(accessToken);
        String claims = jwt.getClaims();
        //String user_name = String.valueOf(claimsMap.get("user_name"));    //这样获取claim中的内容
        return jsonParser.parseMap(claims);
    }
}
