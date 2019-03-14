package com.cloud.auth.authserver.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import com.cloud.common.exception.BusiException;
import com.alibaba.fastjson.JSONObject;
import com.cloud.common.complexquery.QueryExample;
import com.cloud.common.webcomm.RespEntity;
import java.util.*;
import com.cloud.auth.authserver.service.inft.ISysUserService;
import com.cloud.auth.authserver.entity.SysUser;
import com.cloud.auth.authserver.webentity.SysUserResp;

/**
 * SysUserCtrl 控制层方法
 * @author lijun
 */
@RestController
public class SysUserCtrl{

    @Autowired
    private ISysUserService sysUserService;

   /**
   * SysUser 根据主键获取单个数据
   * @return
   * @throws Exception
   */
   @GetMapping(value = "/sysUser/{suId}")
   public String loadSysUserByKey(@PathVariable(value="suId") Long suId) throws Exception {
      if(Objects.isNull(suId)){
         throw new BusiException("请输入要获取的数据的ID") ;
      }
      SysUser sysUser = sysUserService.loadSysUserByKey(suId);
      return RespEntity.ok(new SysUserResp(sysUser));
   }

   /**
    * SysUser 根据条件获取单个数据
    * @return
    * @throws Exception
    */
   @PostMapping(value = "/sysUser/selectone")
   public String selectOneSysUser(@RequestBody JSONObject reqEntity) throws Exception {
       SysUser sysUserReq = JSONObject.parseObject(reqEntity.toJSONString(), SysUser.class);
       SysUser sysUser = sysUserService.selectOneSysUser(sysUserReq,true);
       if(Objects.isNull(sysUser)){
           throw new BusiException("没有符合条件的记录");
       }
       return RespEntity.ok(new SysUserResp(sysUser));
   }

   /**
    * SysUser 根据条件获取单个数据
    * @return
    * @throws Exception
    */
   @PostMapping(value = "/sysUser/criteria/selectone")
   public String selectOneSysUserExample(@RequestBody JSONObject reqEntity) throws Exception {
       SysUser sysUserReq = JSONObject.parseObject(reqEntity.toJSONString(), SysUser.class);
       QueryExample queryExample = new QueryExample();
       SysUser sysUser = sysUserService.selectOneSysUser(queryExample,true);
       if(Objects.isNull(sysUser)){
           throw new BusiException("没有符合条件的记录");
       }
       return RespEntity.ok(new SysUserResp(sysUser));
   }

  /**
   * SysUser 根据实体对象查询列表
   * @return
   * @throws Exception
   */
   @PostMapping(value = "/sysUser/list")
   public String getSysUserListByPage(@RequestBody JSONObject reqEntity) throws Exception {
       SysUser sysUser = JSONObject.parseObject(reqEntity.toJSONString(), SysUser.class);
       JSONObject resp = sysUserService.getPageSysUser(sysUser,true);
       return RespEntity.ok(resp);
   }

   /**
    * 复杂查询
    * @return
    * @throws Exception
    */
   @PostMapping(value = "/sysUser/criteria/list")
   public String getSysUserListExampleByPage(@RequestBody JSONObject reqEntity) throws Exception {
       SysUser sysUser = JSONObject.parseObject(reqEntity.toJSONString(), SysUser.class);
       QueryExample queryExample = new QueryExample();
       JSONObject resp = sysUserService.getPageSysUserExample(queryExample,true);
       return RespEntity.ok(resp);
   }

  /**
   * SysUser 新增或者修改记录，根据主键判断，主键为空则新增，否则修改。
   * @return
   * @throws Exception
   */
   @PostMapping(value = "/sysUser")
   public String saveSysUser(@RequestBody JSONObject reqEntity) throws  Exception{
       SysUser sysUser = JSONObject.parseObject(reqEntity.toJSONString(), SysUser.class);
       sysUserService.saveSysUser(sysUser);
       return RespEntity.ok();
   }

   /**
    * SysUser 根据主键删除数据
    * @return
    * @throws Exception
    */
    @DeleteMapping(value = "/sysUser/{suId}")
    public String deleteSysUser(@PathVariable(value="suId") Long suId) throws  Exception{
        if(Objects.isNull(suId)){
           throw new BusiException("入参请求异常") ;
        }
        sysUserService.deleteSysUser(suId);
        return RespEntity.ok();
    }


}

