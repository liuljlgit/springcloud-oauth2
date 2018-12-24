package com.cloud.auth.authserver.security.service;

import com.cloud.auth.authserver.entity.SysUser;
import com.cloud.auth.authserver.service.inft.ISysRoleService;
import com.cloud.auth.authserver.service.inft.ISysUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

/**
 * 系统获取用户自定义信息类
 */
@Service
public class UserServiceDetail implements UserDetailsService {

    @Autowired
    ISysUserService sysUserService;

    @Autowired
    ISysRoleService sysRoleService;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        SysUser sysUser = new SysUser();
        sysUser.setAccount(username);
        try{
            SysUser userDetails = sysUserService.selectOneSysUser(sysUser, true);
        }catch (Exception e){
            throw new UsernameNotFoundException("用户名不存在");
        }
        return null;
    }
}
