package com.cloud.auth.authserver.service.impl;

import com.cloud.auth.authserver.service.inft.UserDetailsService;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

/**
 * 自定义获取用户信息service
 * 其中：需实现oauth2包中的UserDetailService接口来获取用户信息
 */
public class UserDetailServiceImpl implements UserDetailsService {

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return null;
    }

}
