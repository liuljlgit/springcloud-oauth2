package com.cloud.zuul.zuulserver.security;

import java.io.Serializable;

public class TokenAuthenticationReq implements Serializable {

    private String username;
    private String password;

    public TokenAuthenticationReq(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public TokenAuthenticationReq() {}

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}
