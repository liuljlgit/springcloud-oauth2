package com.cloud.auth.authserver.service.impl;

import com.cloud.auth.authserver.entity.SysUser;
import com.cloud.auth.authserver.service.inft.ISysUserService;
import com.cloud.auth.authserver.service.inft.UserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collection;

/**
 * 自定义获取用户信息service
 * 其中：需实现oauth2包中的UserDetailService接口来获取用户信息
 */
@Service
public class UserDetailServiceImpl implements UserDetailsService {

    @Autowired
    ISysUserService sysUserService;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        SysUser sysUser = new SysUser();
        sysUser.setAccount(username);
        try{
            SysUser userInfo = sysUserService.selectOneSysUser(sysUser, true);
            UserDetails userDetails = new UserDetails() {
                @Override
                public Collection<? extends GrantedAuthority> getAuthorities() {
                    return AuthorityUtils.commaSeparatedStringToAuthorityList("admin,ROLE_USER");
                }

                @Override
                public String getPassword() {
                    return userInfo.getPassword();
                }

                @Override
                public String getUsername() {
                    return userInfo.getAccount();
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
            };
            return userDetails;
        }catch (Exception e){
            throw new UsernameNotFoundException("用户名密码错误");
        }
    }

}
