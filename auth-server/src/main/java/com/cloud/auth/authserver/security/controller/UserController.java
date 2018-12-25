package com.cloud.auth.authserver.security.controller;

import com.alibaba.fastjson.JSONObject;
import com.cloud.auth.authserver.entity.SysUser;
import com.cloud.auth.authserver.service.inft.ISysUserService;
import com.cloud.common.base.BaseController;
import com.cloud.common.exception.BusiException;
import com.cloud.common.webcomm.ReqEntity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.Date;
import java.util.Objects;

@RestController
@RequestMapping("/users")
public class UserController extends BaseController {

    Logger logger = LoggerFactory.getLogger(UserController.class);

    @Autowired
    ISysUserService sysUserService;

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
    public String saveSysUser(@RequestBody ReqEntity reqEntity) throws  Exception{
        SysUser sysUser = JSONObject.parseObject(reqEntity.getReqBody().toJSONString(), SysUser.class);
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
        sysUserService.insertSysUser(sysUser);
        return formatResponseParams(EXEC_OK,null);
    }
}
