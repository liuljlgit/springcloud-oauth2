package com.cloud.auth.authserver.security.controller;

import com.alibaba.fastjson.JSONObject;
import com.cloud.auth.authserver.entity.SysUser;
import com.cloud.auth.authserver.service.inft.ISysUserService;
import com.cloud.common.exception.BusiException;
import com.cloud.common.webcomm.RespEntity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.MediaType;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.provider.token.DefaultTokenServices;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.Date;
import java.util.Objects;

@RestController
@RequestMapping("/users")
public class UserController {

    Logger logger = LoggerFactory.getLogger(UserController.class);

    @Autowired
    ISysUserService sysUserService;

    @Autowired
    @Qualifier("cloudTokenServices")
    DefaultTokenServices tokenServices;

    @Autowired
    PasswordEncoder passwordEncoder;

    @RequestMapping(value = "/current", method = RequestMethod.GET)
    public Principal getUser(Principal principal) {
        logger.info(">>>>>>>>>>>>>>>>>>>>>>>>");
        logger.info(principal.toString());
        logger.info(">>>>>>>>>>>>>>>>>>>>>>>>");
        return principal;
    }

    /**
     * 用户注册
     * @return
     * @throws Exception
     */
    @PostMapping(value = "/register")
    public String saveSysUser(@RequestBody JSONObject reqEntity) throws  Exception{
        SysUser sysUser = JSONObject.parseObject(reqEntity.toJSONString(), SysUser.class);
        if(Objects.isNull(sysUser.getAccount())){
            throw new BusiException("用户名不能为空");
        }
        if(Objects.isNull(sysUser.getPasswd())){
            throw new BusiException("密码不能为空");
        }
        sysUser.setPasswd(passwordEncoder.encode(sysUser.getPasswd()));
        sysUser.setCreateTime(new Date());
        sysUser.setStatus((byte)1);
        sysUser.setStatusTime(new Date());
        sysUserService.addSysUser(sysUser);
        return RespEntity.ok();
    }

    /**
     * 注销
     * @param reqEntity
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/logout",method = RequestMethod.POST,consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public String logout(@RequestBody JSONObject reqEntity) throws  Exception {
        String accessToken = reqEntity.getJSONArray("accessToken").toJavaList(String.class).get(0);
        tokenServices.revokeToken(accessToken);
        return RespEntity.ok();
    }
}
