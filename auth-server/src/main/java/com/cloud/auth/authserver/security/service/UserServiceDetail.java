package com.cloud.auth.authserver.security.service;

import com.cloud.auth.authserver.entity.SysRole;
import com.cloud.auth.authserver.entity.SysUser;
import com.cloud.auth.authserver.entity.SysUserRole;
import com.cloud.auth.authserver.security.entity.SysGrantedAuthority;
import com.cloud.auth.authserver.security.entity.SysUserDetails;
import com.cloud.auth.authserver.service.inft.ISysRoleService;
import com.cloud.auth.authserver.service.inft.ISysUserRoleService;
import com.cloud.auth.authserver.service.inft.ISysUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * 系统获取用户自定义信息类
 */
@Service
public class UserServiceDetail implements UserDetailsService {

    @Autowired
    ISysUserService sysUserService;

    @Autowired
    ISysRoleService sysRoleService;

    @Autowired
    ISysUserRoleService sysUserRoleService;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        SysUser sysUser = new SysUser();
        SysRole role = new SysRole();
        SysUserRole sysUserRole = new SysUserRole();
        SysUserDetails userDetails;
        try{
            //根据用户名获取用户信息
            sysUser.setAccount(username);
            userDetails = new SysUserDetails(sysUserService.selectOneSysUser(sysUser, true));
            //获取所有的权限信息
            Map<Long, SysRole> roleMap = sysRoleService.findSysRoleList(role, true).stream().collect(Collectors.toMap(e -> e.getSrId(), e -> e));
            //获取当前用户的权限信息
            sysUserRole.setSuId(userDetails.getSysUser().getSuId());
            List<SysGrantedAuthority> sysRoles = sysUserRoleService.findSysUserRoleList(sysUserRole, true).stream().map(e ->{
                SysRole r = roleMap.get(e);
                SysGrantedAuthority grantedAuthority = new SysGrantedAuthority(r);
                return grantedAuthority;
            }).collect(Collectors.toList());
            userDetails.setGrantedAuthoritys(sysRoles);
        }catch (Exception e){
            throw new UsernameNotFoundException("用户名不存在");
        }
        return userDetails;
    }
}
