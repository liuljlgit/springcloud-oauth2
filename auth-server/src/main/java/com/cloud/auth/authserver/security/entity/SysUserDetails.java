package com.cloud.auth.authserver.security.entity;

import com.cloud.auth.authserver.entity.SysUser;
import org.springframework.security.core.userdetails.UserDetails;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;

/**
 * 系统用户信息类
 */
public class SysUserDetails implements UserDetails,Serializable {

    /**
     * field comment:用户信息
     */
    private SysUser sysUser;

    /**
     * field comment:权限列表
     */
    List<SysGrantedAuthority> grantedAuthoritys;

    public SysUserDetails(SysUser sysUser) {
        this.sysUser = sysUser;
    }

    public void setGrantedAuthoritys(List<SysGrantedAuthority> grantedAuthoritys) {
        this.grantedAuthoritys = grantedAuthoritys;
    }

    @Override
    public Collection<? extends SysGrantedAuthority> getAuthorities() {
        return this.grantedAuthoritys;
    }

    @Override
    public String getPassword() {
        return this.getSysUser().getPasswd();
    }

    @Override
    public String getUsername() {
        return this.getSysUser().getAccount();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    public SysUser getSysUser() {
        return sysUser;
    }

    public void setSysUser(SysUser sysUser) {
        this.sysUser = sysUser;
    }
}
