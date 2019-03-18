package com.cloud.auth.authserver.security.token;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.oauth2.common.DefaultOAuth2AccessToken;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.token.TokenEnhancer;

import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

public class CloudTokenEnhancer implements TokenEnhancer {
    @Override
    public OAuth2AccessToken enhance(OAuth2AccessToken accessToken, OAuth2Authentication authentication) {
        final Map<String,Object> addtionalInfo = new HashMap<>();
        UserDetails userDetails = (UserDetails)authentication.getPrincipal();
        addtionalInfo.put("user_name",userDetails.getUsername());
        addtionalInfo.put("role",userDetails.getAuthorities().stream().map(e->e.getAuthority()).collect(Collectors.toList()));
        //加入自定义信息
        ((DefaultOAuth2AccessToken)accessToken).setAdditionalInformation(addtionalInfo);
        return accessToken;
    }
}
