package com.cloud.auth.authserver.security.entity;

import com.cloud.auth.authserver.entity.SysRole;
import org.springframework.security.core.GrantedAuthority;

import java.io.Serializable;

/**
 * 系统权限类
 */
public class SysGrantedAuthority implements GrantedAuthority,Serializable {

    /**
     * field comment:权限信息
     */
    private SysRole sysRole;

    public SysGrantedAuthority(SysRole sysRole) {
        this.sysRole = sysRole;
    }

    @Override
    public String getAuthority() {
        return this.getSysRole().getRoleName();
    }

    public SysRole getSysRole() {
        return sysRole;
    }

    public void setSysRole(SysRole sysRole) {
        this.sysRole = sysRole;
    }
}
