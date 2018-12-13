package com.cloud.auth.authserver.security.exception;


import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import org.springframework.security.oauth2.common.exceptions.OAuth2Exception;

@JsonSerialize(using = CustomOauthExceptionSerializer.class)
public class CustomOauthException extends OAuth2Exception {
    private Integer exeCode;//认证的错误编码

    public CustomOauthException(String msg) {
        super(msg);
    }

    public CustomOauthException(String msg,Integer exeCode){
        super(msg);
        this.exeCode = exeCode;
    }

    public Integer getExeCode() {
        return exeCode;
    }

    public void setExeCode(Integer exeCode) {
        this.exeCode = exeCode;
    }
}
