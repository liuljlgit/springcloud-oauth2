package com.cloud.auth.authserver.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import com.cloud.common.base.BaseController;
import com.cloud.common.webcomm.ReqEntity;
import com.cloud.common.exception.BusiException;
import com.alibaba.fastjson.JSONObject;
import com.cloud.common.complexquery.QueryExample;
import java.util.*;
import com.cloud.auth.authserver.service.inft.ISysDeptService;
import com.cloud.auth.authserver.entity.SysDept;
import com.cloud.auth.authserver.webentity.SysDeptResp;

/**
 * SysDeptCtrl 控制层方法
 * @author lijun
 */
@RestController
public class SysDeptCtrl extends BaseController {

    @Autowired
    private ISysDeptService sysDeptService;

   /**
   * SysDept 根据主键获取单个数据
   * @return
   * @throws Exception
   */
   @GetMapping(value = "/sysDept/{sdId}")
   public String loadSysDeptByKey(@PathVariable(value="sdId") Long sdId) throws Exception {
      if(Objects.isNull(sdId)){
         throw new BusiException("请输入要获取的数据的ID") ;
      }
      SysDept sysDept = sysDeptService.loadSysDeptByKey(sdId);
      JSONObject resp = new JSONObject();
      resp.put("sysDept",new SysDeptResp(sysDept));
      return formatResponseParams(EXEC_OK,resp);
   }

   /**
    * SysDept 根据条件获取单个数据
    * @return
    * @throws Exception
    */
   @PostMapping(value = "/sysDept/selectone")
   public String selectOneSysDept(@RequestBody ReqEntity reqEntity) throws Exception {
       SysDept sysDeptReq = JSONObject.parseObject(reqEntity.getReqBody().toJSONString(), SysDept.class);
       SysDept sysDept = sysDeptService.selectOneSysDept(sysDeptReq,true);
       if(Objects.isNull(sysDept)){
           throw new BusiException("没有符合条件的记录");
       }
       JSONObject resp = new JSONObject();
       resp.put("sysDept",new SysDeptResp(sysDept));
       return formatResponseParams(EXEC_OK,resp);
   }

   /**
    * SysDept 根据条件获取单个数据
    * @return
    * @throws Exception
    */
   @PostMapping(value = "/sysDept/criteria/selectone")
   public String selectOneSysDeptExample(@RequestBody ReqEntity reqEntity) throws Exception {
       SysDept sysDeptReq = JSONObject.parseObject(reqEntity.getReqBody().toJSONString(), SysDept.class);
       QueryExample queryExample = new QueryExample();
       SysDept sysDept = sysDeptService.selectOneSysDept(queryExample,true);
       if(Objects.isNull(sysDept)){
           throw new BusiException("没有符合条件的记录");
       }
       JSONObject resp = new JSONObject();
       resp.put("sysDept",new SysDeptResp(sysDept));
       return formatResponseParams(EXEC_OK,resp);
   }

  /**
   * SysDept 根据实体对象查询列表
   * @return
   * @throws Exception
   */
   @PostMapping(value = "/sysDept/list")
   public String getSysDeptListByPage(@RequestBody ReqEntity reqEntity) throws Exception {
       SysDept sysDept = JSONObject.parseObject(reqEntity.getReqBody().toJSONString(), SysDept.class);
       JSONObject resp = sysDeptService.getPageSysDept(sysDept,true);
       return formatResponseParams(EXEC_OK,resp);
   }

   /**
    * 复杂查询
    * @return
    * @throws Exception
    */
   @PostMapping(value = "/sysDept/criteria/list")
   public String getSysDeptListExampleByPage(@RequestBody ReqEntity reqEntity) throws Exception {
       SysDept sysDept = JSONObject.parseObject(reqEntity.getReqBody().toJSONString(), SysDept.class);
       QueryExample queryExample = new QueryExample();
       JSONObject resp = sysDeptService.getPageSysDeptExample(queryExample,true);
       return formatResponseParams(EXEC_OK, resp);
   }

  /**
   * SysDept 新增或者修改记录，根据主键判断，主键为空则新增，否则修改。
   * @return
   * @throws Exception
   */
   @PostMapping(value = "/sysDept")
   public String saveSysDept(@RequestBody ReqEntity reqEntity) throws  Exception{
       SysDept sysDept = JSONObject.parseObject(reqEntity.getReqBody().toJSONString(), SysDept.class);
       sysDeptService.saveSysDept(sysDept,false);
       return formatResponseParams(EXEC_OK,null);
   }

   /**
    * SysDept 根据主键删除数据
    * @return
    * @throws Exception
    */
    @DeleteMapping(value = "/sysDept/{sdId}")
    public String deleteSysDeptByKey(@PathVariable(value="sdId") Long sdId) throws  Exception{
        if(Objects.isNull(sdId)){
           throw new BusiException("入参请求异常") ;
        }
        sysDeptService.deleteSysDeptByKey(sdId);
        return formatResponseParams(EXEC_OK,null);
    }


}

