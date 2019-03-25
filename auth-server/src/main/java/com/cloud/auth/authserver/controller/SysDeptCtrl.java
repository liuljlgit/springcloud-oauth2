package com.cloud.auth.authserver.controller;

import com.alibaba.fastjson.JSONObject;
import com.cloud.auth.authserver.entity.SysDept;
import com.cloud.auth.authserver.service.inft.ISysDeptService;
import com.cloud.auth.authserver.webentity.SysDeptResp;
import com.cloud.common.complexquery.QueryExample;
import com.cloud.common.exception.BusiException;
import com.cloud.common.webcomm.RespEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Objects;

/**
 * SysDeptCtrl 控制层方法
 * @author lijun
 */
@RestController
public class SysDeptCtrl{

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
      return RespEntity.ok(new SysDeptResp(sysDept));
   }

   /**
    * SysDept 根据条件获取单个数据
    * @return
    * @throws Exception
    */
   @PostMapping(value = "/sysDept/selectone")
   public String selectOneSysDept(@RequestBody JSONObject reqEntity) throws Exception {
       SysDept sysDeptReq = JSONObject.parseObject(reqEntity.toJSONString(), SysDept.class);
       SysDept sysDept = sysDeptService.selectOneSysDept(sysDeptReq,true);
       if(Objects.isNull(sysDept)){
           throw new BusiException("没有符合条件的记录");
       }
       return RespEntity.ok(new SysDeptResp(sysDept));
   }

   /**
    * SysDept 根据条件获取单个数据
    * @return
    * @throws Exception
    */
   @PostMapping(value = "/sysDept/criteria/selectone")
   public String selectOneSysDeptExample(@RequestBody JSONObject reqEntity) throws Exception {
       SysDept sysDeptReq = JSONObject.parseObject(reqEntity.toJSONString(), SysDept.class);
       QueryExample queryExample = new QueryExample();
       SysDept sysDept = sysDeptService.selectOneSysDept(queryExample,true);
       if(Objects.isNull(sysDept)){
           throw new BusiException("没有符合条件的记录");
       }
       return RespEntity.ok(new SysDeptResp(sysDept));
   }

  /**
   * SysDept 根据实体对象查询列表
   * @return
   * @throws Exception
   */
   @PostMapping(value = "/sysDept/list")
   public String getSysDeptListByPage(@RequestBody JSONObject reqEntity) throws Exception {
       SysDept sysDept = JSONObject.parseObject(reqEntity.toJSONString(), SysDept.class);
       JSONObject resp = sysDeptService.getPageSysDept(sysDept,true);
       return RespEntity.ok(resp);
   }

   /**
    * 复杂查询
    * @return
    * @throws Exception
    */
   @PostMapping(value = "/sysDept/criteria/list")
   public String getSysDeptListExampleByPage(@RequestBody JSONObject reqEntity) throws Exception {
       SysDept sysDept = JSONObject.parseObject(reqEntity.toJSONString(), SysDept.class);
       QueryExample queryExample = new QueryExample();
       JSONObject resp = sysDeptService.getPageSysDeptExample(queryExample,true);
       return RespEntity.ok(resp);
   }

  /**
   * SysDept 新增或者修改记录，根据主键判断，主键为空则新增，否则修改。
   * @return
   * @throws Exception
   */
   @PostMapping(value = "/sysDept")
   public String saveSysDept(@RequestBody JSONObject reqEntity) throws  Exception{
       SysDept sysDept = JSONObject.parseObject(reqEntity.toJSONString(), SysDept.class);
       sysDeptService.saveSysDept(sysDept);
       return RespEntity.ok();
   }

   /**
    * SysDept 根据主键删除数据
    * @return
    * @throws Exception
    */
    @DeleteMapping(value = "/sysDept/{sdId}")
    public String deleteSysDept(@PathVariable(value="sdId") Long sdId) throws  Exception{
        if(Objects.isNull(sdId)){
           throw new BusiException("入参请求异常") ;
        }
        sysDeptService.deleteSysDept(sdId);
        return RespEntity.ok();
    }

    public void test1(){

    }


}

