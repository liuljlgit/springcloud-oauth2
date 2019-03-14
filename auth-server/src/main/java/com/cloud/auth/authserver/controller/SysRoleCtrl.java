package com.cloud.auth.authserver.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import com.cloud.common.exception.BusiException;
import com.alibaba.fastjson.JSONObject;
import com.cloud.common.complexquery.QueryExample;
import com.cloud.common.webcomm.RespEntity;
import java.util.*;
import com.cloud.auth.authserver.service.inft.ISysRoleService;
import com.cloud.auth.authserver.entity.SysRole;
import com.cloud.auth.authserver.webentity.SysRoleResp;

/**
 * SysRoleCtrl 控制层方法
 * @author lijun
 */
@RestController
public class SysRoleCtrl{

    @Autowired
    private ISysRoleService sysRoleService;

   /**
   * SysRole 根据主键获取单个数据
   * @return
   * @throws Exception
   */
   @GetMapping(value = "/sysRole/{srId}")
   public String loadSysRoleByKey(@PathVariable(value="srId") Long srId) throws Exception {
      if(Objects.isNull(srId)){
         throw new BusiException("请输入要获取的数据的ID") ;
      }
      SysRole sysRole = sysRoleService.loadSysRoleByKey(srId);
      return RespEntity.ok(new SysRoleResp(sysRole));
   }

   /**
    * SysRole 根据条件获取单个数据
    * @return
    * @throws Exception
    */
   @PostMapping(value = "/sysRole/selectone")
   public String selectOneSysRole(@RequestBody JSONObject reqEntity) throws Exception {
       SysRole sysRoleReq = JSONObject.parseObject(reqEntity.toJSONString(), SysRole.class);
       SysRole sysRole = sysRoleService.selectOneSysRole(sysRoleReq,true);
       if(Objects.isNull(sysRole)){
           throw new BusiException("没有符合条件的记录");
       }
       return RespEntity.ok(new SysRoleResp(sysRole));
   }

   /**
    * SysRole 根据条件获取单个数据
    * @return
    * @throws Exception
    */
   @PostMapping(value = "/sysRole/criteria/selectone")
   public String selectOneSysRoleExample(@RequestBody JSONObject reqEntity) throws Exception {
       SysRole sysRoleReq = JSONObject.parseObject(reqEntity.toJSONString(), SysRole.class);
       QueryExample queryExample = new QueryExample();
       SysRole sysRole = sysRoleService.selectOneSysRole(queryExample,true);
       if(Objects.isNull(sysRole)){
           throw new BusiException("没有符合条件的记录");
       }
       return RespEntity.ok(new SysRoleResp(sysRole));
   }

  /**
   * SysRole 根据实体对象查询列表
   * @return
   * @throws Exception
   */
   @PostMapping(value = "/sysRole/list")
   public String getSysRoleListByPage(@RequestBody JSONObject reqEntity) throws Exception {
       SysRole sysRole = JSONObject.parseObject(reqEntity.toJSONString(), SysRole.class);
       JSONObject resp = sysRoleService.getPageSysRole(sysRole,true);
       return RespEntity.ok(resp);
   }

   /**
    * 复杂查询
    * @return
    * @throws Exception
    */
   @PostMapping(value = "/sysRole/criteria/list")
   public String getSysRoleListExampleByPage(@RequestBody JSONObject reqEntity) throws Exception {
       SysRole sysRole = JSONObject.parseObject(reqEntity.toJSONString(), SysRole.class);
       QueryExample queryExample = new QueryExample();
       JSONObject resp = sysRoleService.getPageSysRoleExample(queryExample,true);
       return RespEntity.ok(resp);
   }

  /**
   * SysRole 新增或者修改记录，根据主键判断，主键为空则新增，否则修改。
   * @return
   * @throws Exception
   */
   @PostMapping(value = "/sysRole")
   public String saveSysRole(@RequestBody JSONObject reqEntity) throws  Exception{
       SysRole sysRole = JSONObject.parseObject(reqEntity.toJSONString(), SysRole.class);
       sysRoleService.saveSysRole(sysRole);
       return RespEntity.ok();
   }

   /**
    * SysRole 根据主键删除数据
    * @return
    * @throws Exception
    */
    @DeleteMapping(value = "/sysRole/{srId}")
    public String deleteSysRole(@PathVariable(value="srId") Long srId) throws  Exception{
        if(Objects.isNull(srId)){
           throw new BusiException("入参请求异常") ;
        }
        sysRoleService.deleteSysRole(srId);
        return RespEntity.ok();
    }


}

