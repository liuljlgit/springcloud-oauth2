package com.cloud.auth.authserver.security.entity;

import com.cloud.auth.authserver.entity.SysRole;

/**
 * 系统权限类
 */
public class GrantedAuthority extends SysRole implements org.springframework.security.core.GrantedAuthority {

    @Override
    public String getAuthority() {
        return this.getRoleName();
    }
}
