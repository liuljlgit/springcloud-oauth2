package com.cloud.auth.authserver.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import com.cloud.common.exception.BusiException;
import com.alibaba.fastjson.JSONObject;
import com.cloud.common.complexquery.QueryExample;
import com.cloud.common.webcomm.RespEntity;
import java.util.*;
import com.cloud.auth.authserver.service.inft.ISysUserRoleService;
import com.cloud.auth.authserver.entity.SysUserRole;
import com.cloud.auth.authserver.webentity.SysUserRoleResp;

/**
 * SysUserRoleCtrl 控制层方法
 * @author lijun
 */
@RestController
public class SysUserRoleCtrl{

    @Autowired
    private ISysUserRoleService sysUserRoleService;

   /**
   * SysUserRole 根据主键获取单个数据
   * @return
   * @throws Exception
   */
   @GetMapping(value = "/sysUserRole/{surId}")
   public String loadSysUserRoleByKey(@PathVariable(value="surId") Long surId) throws Exception {
      if(Objects.isNull(surId)){
         throw new BusiException("请输入要获取的数据的ID") ;
      }
      SysUserRole sysUserRole = sysUserRoleService.loadSysUserRoleByKey(surId);
      return RespEntity.ok(new SysUserRoleResp(sysUserRole));
   }

   /**
    * SysUserRole 根据条件获取单个数据
    * @return
    * @throws Exception
    */
   @PostMapping(value = "/sysUserRole/selectone")
   public String selectOneSysUserRole(@RequestBody JSONObject reqEntity) throws Exception {
       SysUserRole sysUserRoleReq = JSONObject.parseObject(reqEntity.toJSONString(), SysUserRole.class);
       SysUserRole sysUserRole = sysUserRoleService.selectOneSysUserRole(sysUserRoleReq,true);
       if(Objects.isNull(sysUserRole)){
           throw new BusiException("没有符合条件的记录");
       }
       return RespEntity.ok(new SysUserRoleResp(sysUserRole));
   }

   /**
    * SysUserRole 根据条件获取单个数据
    * @return
    * @throws Exception
    */
   @PostMapping(value = "/sysUserRole/criteria/selectone")
   public String selectOneSysUserRoleExample(@RequestBody JSONObject reqEntity) throws Exception {
       SysUserRole sysUserRoleReq = JSONObject.parseObject(reqEntity.toJSONString(), SysUserRole.class);
       QueryExample queryExample = new QueryExample();
       SysUserRole sysUserRole = sysUserRoleService.selectOneSysUserRole(queryExample,true);
       if(Objects.isNull(sysUserRole)){
           throw new BusiException("没有符合条件的记录");
       }
       return RespEntity.ok(new SysUserRoleResp(sysUserRole));
   }

  /**
   * SysUserRole 根据实体对象查询列表
   * @return
   * @throws Exception
   */
   @PostMapping(value = "/sysUserRole/list")
   public String getSysUserRoleListByPage(@RequestBody JSONObject reqEntity) throws Exception {
       SysUserRole sysUserRole = JSONObject.parseObject(reqEntity.toJSONString(), SysUserRole.class);
       JSONObject resp = sysUserRoleService.getPageSysUserRole(sysUserRole,true);
       return RespEntity.ok(resp);
   }

   /**
    * 复杂查询
    * @return
    * @throws Exception
    */
   @PostMapping(value = "/sysUserRole/criteria/list")
   public String getSysUserRoleListExampleByPage(@RequestBody JSONObject reqEntity) throws Exception {
       SysUserRole sysUserRole = JSONObject.parseObject(reqEntity.toJSONString(), SysUserRole.class);
       QueryExample queryExample = new QueryExample();
       JSONObject resp = sysUserRoleService.getPageSysUserRoleExample(queryExample,true);
       return RespEntity.ok(resp);
   }

  /**
   * SysUserRole 新增或者修改记录，根据主键判断，主键为空则新增，否则修改。
   * @return
   * @throws Exception
   */
   @PostMapping(value = "/sysUserRole")
   public String saveSysUserRole(@RequestBody JSONObject reqEntity) throws  Exception{
       SysUserRole sysUserRole = JSONObject.parseObject(reqEntity.toJSONString(), SysUserRole.class);
       sysUserRoleService.saveSysUserRole(sysUserRole);
       return RespEntity.ok();
   }

   /**
    * SysUserRole 根据主键删除数据
    * @return
    * @throws Exception
    */
    @DeleteMapping(value = "/sysUserRole/{surId}")
    public String deleteSysUserRole(@PathVariable(value="surId") Long surId) throws  Exception{
        if(Objects.isNull(surId)){
           throw new BusiException("入参请求异常") ;
        }
        sysUserRoleService.deleteSysUserRole(surId);
        return RespEntity.ok();
    }


}

