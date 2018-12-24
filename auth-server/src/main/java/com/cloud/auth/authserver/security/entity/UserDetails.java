package com.cloud.auth.authserver.security.entity;

import com.cloud.auth.authserver.entity.SysUser;
import org.springframework.security.core.GrantedAuthority;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;

/**
 * 系统用户信息类
 */
public class UserDetails extends SysUser implements org.springframework.security.core.userdetails.UserDetails,Serializable {

    /**
     * field comment:权限列表
     */
    List<GrantedAuthority> grantedAuthoritys;

    public void setGrantedAuthoritys(List<GrantedAuthority> grantedAuthoritys) {
        this.grantedAuthoritys = grantedAuthoritys;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return this.grantedAuthoritys;
    }

    @Override
    public String getPassword() {
        return this.getPasswd();
    }

    @Override
    public String getUsername() {
        return this.getAccount();
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
}
