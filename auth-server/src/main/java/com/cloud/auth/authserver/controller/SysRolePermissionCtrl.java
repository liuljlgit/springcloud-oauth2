package com.cloud.auth.authserver.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import com.cloud.common.exception.BusiException;
import com.alibaba.fastjson.JSONObject;
import com.cloud.common.complexquery.QueryExample;
import com.cloud.common.webcomm.RespEntity;
import java.util.*;
import com.cloud.auth.authserver.service.inft.ISysRolePermissionService;
import com.cloud.auth.authserver.entity.SysRolePermission;
import com.cloud.auth.authserver.webentity.SysRolePermissionResp;

/**
 * SysRolePermissionCtrl 控制层方法
 * @author lijun
 */
@RestController
public class SysRolePermissionCtrl{

    @Autowired
    private ISysRolePermissionService sysRolePermissionService;

   /**
   * SysRolePermission 根据主键获取单个数据
   * @return
   * @throws Exception
   */
   @GetMapping(value = "/sysRolePermission/{srpId}")
   public String loadSysRolePermissionByKey(@PathVariable(value="srpId") Long srpId) throws Exception {
      if(Objects.isNull(srpId)){
         throw new BusiException("请输入要获取的数据的ID") ;
      }
      SysRolePermission sysRolePermission = sysRolePermissionService.loadSysRolePermissionByKey(srpId);
      return RespEntity.ok(new SysRolePermissionResp(sysRolePermission));
   }

   /**
    * SysRolePermission 根据条件获取单个数据
    * @return
    * @throws Exception
    */
   @PostMapping(value = "/sysRolePermission/selectone")
   public String selectOneSysRolePermission(@RequestBody JSONObject reqEntity) throws Exception {
       SysRolePermission sysRolePermissionReq = JSONObject.parseObject(reqEntity.toJSONString(), SysRolePermission.class);
       SysRolePermission sysRolePermission = sysRolePermissionService.selectOneSysRolePermission(sysRolePermissionReq,true);
       if(Objects.isNull(sysRolePermission)){
           throw new BusiException("没有符合条件的记录");
       }
       return RespEntity.ok(new SysRolePermissionResp(sysRolePermission));
   }

   /**
    * SysRolePermission 根据条件获取单个数据
    * @return
    * @throws Exception
    */
   @PostMapping(value = "/sysRolePermission/criteria/selectone")
   public String selectOneSysRolePermissionExample(@RequestBody JSONObject reqEntity) throws Exception {
       SysRolePermission sysRolePermissionReq = JSONObject.parseObject(reqEntity.toJSONString(), SysRolePermission.class);
       QueryExample queryExample = new QueryExample();
       SysRolePermission sysRolePermission = sysRolePermissionService.selectOneSysRolePermission(queryExample,true);
       if(Objects.isNull(sysRolePermission)){
           throw new BusiException("没有符合条件的记录");
       }
       return RespEntity.ok(new SysRolePermissionResp(sysRolePermission));
   }

  /**
   * SysRolePermission 根据实体对象查询列表
   * @return
   * @throws Exception
   */
   @PostMapping(value = "/sysRolePermission/list")
   public String getSysRolePermissionListByPage(@RequestBody JSONObject reqEntity) throws Exception {
       SysRolePermission sysRolePermission = JSONObject.parseObject(reqEntity.toJSONString(), SysRolePermission.class);
       JSONObject resp = sysRolePermissionService.getPageSysRolePermission(sysRolePermission,true);
       return RespEntity.ok(resp);
   }

   /**
    * 复杂查询
    * @return
    * @throws Exception
    */
   @PostMapping(value = "/sysRolePermission/criteria/list")
   public String getSysRolePermissionListExampleByPage(@RequestBody JSONObject reqEntity) throws Exception {
       SysRolePermission sysRolePermission = JSONObject.parseObject(reqEntity.toJSONString(), SysRolePermission.class);
       QueryExample queryExample = new QueryExample();
       JSONObject resp = sysRolePermissionService.getPageSysRolePermissionExample(queryExample,true);
       return RespEntity.ok(resp);
   }

  /**
   * SysRolePermission 新增或者修改记录，根据主键判断，主键为空则新增，否则修改。
   * @return
   * @throws Exception
   */
   @PostMapping(value = "/sysRolePermission")
   public String saveSysRolePermission(@RequestBody JSONObject reqEntity) throws  Exception{
       SysRolePermission sysRolePermission = JSONObject.parseObject(reqEntity.toJSONString(), SysRolePermission.class);
       sysRolePermissionService.saveSysRolePermission(sysRolePermission);
       return RespEntity.ok();
   }

   /**
    * SysRolePermission 根据主键删除数据
    * @return
    * @throws Exception
    */
    @DeleteMapping(value = "/sysRolePermission/{srpId}")
    public String deleteSysRolePermission(@PathVariable(value="srpId") Long srpId) throws  Exception{
        if(Objects.isNull(srpId)){
           throw new BusiException("入参请求异常") ;
        }
        sysRolePermissionService.deleteSysRolePermission(srpId);
        return RespEntity.ok();
    }


}

