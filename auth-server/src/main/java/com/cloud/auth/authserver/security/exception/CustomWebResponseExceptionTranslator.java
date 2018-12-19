package com.cloud.auth.authserver.security.exception;

import org.springframework.http.ResponseEntity;
import org.springframework.security.oauth2.common.exceptions.OAuth2Exception;
import org.springframework.security.oauth2.provider.error.WebResponseExceptionTranslator;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletResponse;

@Component("customWebResponseExceptionTranslator")
public class CustomWebResponseExceptionTranslator implements WebResponseExceptionTranslator {
    @Override
    public ResponseEntity<OAuth2Exception> translate(Exception e) throws Exception {
        OAuth2Exception oAuth2Exception = null;
        if ( e instanceof CustomOauthException){
            oAuth2Exception  = (OAuth2Exception) e;
        }else if ( null != e.getCause() && e.getCause() instanceof CustomOauthException){
            oAuth2Exception  = (OAuth2Exception) e.getCause();
        }
        return ResponseEntity
                .status(null != oAuth2Exception ? oAuth2Exception.getHttpErrorCode() : HttpServletResponse.SC_UNAUTHORIZED)
                .body(oAuth2Exception);
    }
}
