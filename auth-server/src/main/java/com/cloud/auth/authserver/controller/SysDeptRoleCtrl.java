package com.cloud.auth.authserver.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import com.cloud.common.base.BaseController;
import com.cloud.common.webcomm.ReqEntity;
import com.cloud.common.exception.BusiException;
import com.alibaba.fastjson.JSONObject;
import com.cloud.common.complexquery.QueryExample;
import java.util.*;
import com.cloud.auth.authserver.service.inft.ISysDeptRoleService;
import com.cloud.auth.authserver.entity.SysDeptRole;
import com.cloud.auth.authserver.webentity.SysDeptRoleResp;

/**
 * SysDeptRoleCtrl 控制层方法
 * @author lijun
 */
@RestController
public class SysDeptRoleCtrl extends BaseController {

    @Autowired
    private ISysDeptRoleService sysDeptRoleService;

   /**
   * SysDeptRole 根据主键获取单个数据
   * @return
   * @throws Exception
   */
   @GetMapping(value = "/sysDeptRole/{sdrId}")
   public String loadSysDeptRoleByKey(@PathVariable(value="sdrId") Long sdrId) throws Exception {
      if(Objects.isNull(sdrId)){
         throw new BusiException("请输入要获取的数据的ID") ;
      }
      SysDeptRole sysDeptRole = sysDeptRoleService.loadSysDeptRoleByKey(sdrId);
      JSONObject resp = new JSONObject();
      resp.put("sysDeptRole",new SysDeptRoleResp(sysDeptRole));
      return formatResponseParams(EXEC_OK,resp);
   }

   /**
    * SysDeptRole 根据条件获取单个数据
    * @return
    * @throws Exception
    */
   @PostMapping(value = "/sysDeptRole/selectone")
   public String selectOneSysDeptRole(@RequestBody ReqEntity reqEntity) throws Exception {
       SysDeptRole sysDeptRoleReq = JSONObject.parseObject(reqEntity.getReqBody().toJSONString(), SysDeptRole.class);
       SysDeptRole sysDeptRole = sysDeptRoleService.selectOneSysDeptRole(sysDeptRoleReq,true);
       if(Objects.isNull(sysDeptRole)){
           throw new BusiException("没有符合条件的记录");
       }
       JSONObject resp = new JSONObject();
       resp.put("sysDeptRole",new SysDeptRoleResp(sysDeptRole));
       return formatResponseParams(EXEC_OK,resp);
   }

   /**
    * SysDeptRole 根据条件获取单个数据
    * @return
    * @throws Exception
    */
   @PostMapping(value = "/sysDeptRole/criteria/selectone")
   public String selectOneSysDeptRoleExample(@RequestBody ReqEntity reqEntity) throws Exception {
       SysDeptRole sysDeptRoleReq = JSONObject.parseObject(reqEntity.getReqBody().toJSONString(), SysDeptRole.class);
       QueryExample queryExample = new QueryExample();
       SysDeptRole sysDeptRole = sysDeptRoleService.selectOneSysDeptRole(queryExample,true);
       if(Objects.isNull(sysDeptRole)){
           throw new BusiException("没有符合条件的记录");
       }
       JSONObject resp = new JSONObject();
       resp.put("sysDeptRole",new SysDeptRoleResp(sysDeptRole));
       return formatResponseParams(EXEC_OK,resp);
   }

  /**
   * SysDeptRole 根据实体对象查询列表
   * @return
   * @throws Exception
   */
   @PostMapping(value = "/sysDeptRole/list")
   public String getSysDeptRoleListByPage(@RequestBody ReqEntity reqEntity) throws Exception {
       SysDeptRole sysDeptRole = JSONObject.parseObject(reqEntity.getReqBody().toJSONString(), SysDeptRole.class);
       JSONObject resp = sysDeptRoleService.getPageSysDeptRole(sysDeptRole,true);
       return formatResponseParams(EXEC_OK,resp);
   }

   /**
    * 复杂查询
    * @return
    * @throws Exception
    */
   @PostMapping(value = "/sysDeptRole/criteria/list")
   public String getSysDeptRoleListExampleByPage(@RequestBody ReqEntity reqEntity) throws Exception {
       SysDeptRole sysDeptRole = JSONObject.parseObject(reqEntity.getReqBody().toJSONString(), SysDeptRole.class);
       QueryExample queryExample = new QueryExample();
       JSONObject resp = sysDeptRoleService.getPageSysDeptRoleExample(queryExample,true);
       return formatResponseParams(EXEC_OK, resp);
   }

  /**
   * SysDeptRole 新增或者修改记录，根据主键判断，主键为空则新增，否则修改。
   * @return
   * @throws Exception
   */
   @PostMapping(value = "/sysDeptRole")
   public String saveSysDeptRole(@RequestBody ReqEntity reqEntity) throws  Exception{
       SysDeptRole sysDeptRole = JSONObject.parseObject(reqEntity.getReqBody().toJSONString(), SysDeptRole.class);
       sysDeptRoleService.saveSysDeptRole(sysDeptRole,false);
       return formatResponseParams(EXEC_OK,null);
   }

   /**
    * SysDeptRole 根据主键删除数据
    * @return
    * @throws Exception
    */
    @DeleteMapping(value = "/sysDeptRole/{sdrId}")
    public String deleteSysDeptRoleByKey(@PathVariable(value="sdrId") Long sdrId) throws  Exception{
        if(Objects.isNull(sdrId)){
           throw new BusiException("入参请求异常") ;
        }
        sysDeptRoleService.deleteSysDeptRoleByKey(sdrId);
        return formatResponseParams(EXEC_OK,null);
    }


}

