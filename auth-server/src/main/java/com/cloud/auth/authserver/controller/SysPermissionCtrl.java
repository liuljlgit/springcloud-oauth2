package com.cloud.auth.authserver.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import com.cloud.common.base.BaseController;
import com.cloud.common.webcomm.ReqEntity;
import com.cloud.common.exception.BusiException;
import com.alibaba.fastjson.JSONObject;
import com.cloud.common.complexquery.QueryExample;
import java.util.*;
import com.cloud.auth.authserver.service.inft.ISysPermissionService;
import com.cloud.auth.authserver.entity.SysPermission;
import com.cloud.auth.authserver.webentity.SysPermissionResp;

/**
 * SysPermissionCtrl 控制层方法
 * @author lijun
 */
@RestController
public class SysPermissionCtrl extends BaseController {

    @Autowired
    private ISysPermissionService sysPermissionService;

   /**
   * SysPermission 根据主键获取单个数据
   * @return
   * @throws Exception
   */
   @GetMapping(value = "/sysPermission/{spId}")
   public String loadSysPermissionByKey(@PathVariable(value="spId") Long spId) throws Exception {
      if(Objects.isNull(spId)){
         throw new BusiException("请输入要获取的数据的ID") ;
      }
      SysPermission sysPermission = sysPermissionService.loadSysPermissionByKey(spId);
      JSONObject resp = new JSONObject();
      resp.put("sysPermission",new SysPermissionResp(sysPermission));
      return formatResponseParams(EXEC_OK,resp);
   }

   /**
    * SysPermission 根据条件获取单个数据
    * @return
    * @throws Exception
    */
   @PostMapping(value = "/sysPermission/selectone")
   public String selectOneSysPermission(@RequestBody ReqEntity reqEntity) throws Exception {
       SysPermission sysPermissionReq = JSONObject.parseObject(reqEntity.getReqBody().toJSONString(), SysPermission.class);
       SysPermission sysPermission = sysPermissionService.selectOneSysPermission(sysPermissionReq,true);
       if(Objects.isNull(sysPermission)){
           throw new BusiException("没有符合条件的记录");
       }
       JSONObject resp = new JSONObject();
       resp.put("sysPermission",new SysPermissionResp(sysPermission));
       return formatResponseParams(EXEC_OK,resp);
   }

   /**
    * SysPermission 根据条件获取单个数据
    * @return
    * @throws Exception
    */
   @PostMapping(value = "/sysPermission/criteria/selectone")
   public String selectOneSysPermissionExample(@RequestBody ReqEntity reqEntity) throws Exception {
       SysPermission sysPermissionReq = JSONObject.parseObject(reqEntity.getReqBody().toJSONString(), SysPermission.class);
       QueryExample queryExample = new QueryExample();
       SysPermission sysPermission = sysPermissionService.selectOneSysPermission(queryExample,true);
       if(Objects.isNull(sysPermission)){
           throw new BusiException("没有符合条件的记录");
       }
       JSONObject resp = new JSONObject();
       resp.put("sysPermission",new SysPermissionResp(sysPermission));
       return formatResponseParams(EXEC_OK,resp);
   }

  /**
   * SysPermission 根据实体对象查询列表
   * @return
   * @throws Exception
   */
   @PostMapping(value = "/sysPermission/list")
   public String getSysPermissionListByPage(@RequestBody ReqEntity reqEntity) throws Exception {
       SysPermission sysPermission = JSONObject.parseObject(reqEntity.getReqBody().toJSONString(), SysPermission.class);
       JSONObject resp = sysPermissionService.getPageSysPermission(sysPermission,true);
       return formatResponseParams(EXEC_OK,resp);
   }

   /**
    * 复杂查询
    * @return
    * @throws Exception
    */
   @PostMapping(value = "/sysPermission/criteria/list")
   public String getSysPermissionListExampleByPage(@RequestBody ReqEntity reqEntity) throws Exception {
       SysPermission sysPermission = JSONObject.parseObject(reqEntity.getReqBody().toJSONString(), SysPermission.class);
       QueryExample queryExample = new QueryExample();
       JSONObject resp = sysPermissionService.getPageSysPermissionExample(queryExample,true);
       return formatResponseParams(EXEC_OK, resp);
   }

  /**
   * SysPermission 新增或者修改记录，根据主键判断，主键为空则新增，否则修改。
   * @return
   * @throws Exception
   */
   @PostMapping(value = "/sysPermission")
   public String saveSysPermission(@RequestBody ReqEntity reqEntity) throws  Exception{
       SysPermission sysPermission = JSONObject.parseObject(reqEntity.getReqBody().toJSONString(), SysPermission.class);
       sysPermissionService.saveSysPermission(sysPermission,false);
       return formatResponseParams(EXEC_OK,null);
   }

   /**
    * SysPermission 根据主键删除数据
    * @return
    * @throws Exception
    */
    @DeleteMapping(value = "/sysPermission/{spId}")
    public String deleteSysPermissionByKey(@PathVariable(value="spId") Long spId) throws  Exception{
        if(Objects.isNull(spId)){
           throw new BusiException("入参请求异常") ;
        }
        sysPermissionService.deleteSysPermissionByKey(spId);
        return formatResponseParams(EXEC_OK,null);
    }


}

