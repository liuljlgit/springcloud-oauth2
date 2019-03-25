package com.cloud.auth.authserver.controller;

import com.alibaba.fastjson.JSONObject;
import com.cloud.auth.authserver.entity.SysCompany;
import com.cloud.auth.authserver.service.inft.ISysCompanyService;
import com.cloud.auth.authserver.webentity.SysCompanyResp;
import com.cloud.common.complexquery.QueryExample;
import com.cloud.common.exception.BusiException;
import com.cloud.common.webcomm.RespEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Objects;

/**
 * SysCompanyCtrl 控制层方法
 * @author lijun
 */
@RestController
public class SysCompanyCtrl{

    @Autowired
    private ISysCompanyService sysCompanyService;

   /**
   * SysCompany 根据主键获取单个数据
   * @return
   * @throws Exception
   */
   @GetMapping(value = "/sysCompany/{scId}")
   public String loadSysCompanyByKey(@PathVariable(value="scId") Long scId) throws Exception {
      if(Objects.isNull(scId)){
         throw new BusiException("请输入要获取的数据的ID") ;
      }
      SysCompany sysCompany = sysCompanyService.loadSysCompanyByKey(scId);
      return RespEntity.ok(new SysCompanyResp(sysCompany));
   }

   /**
    * SysCompany 根据条件获取单个数据
    * @return
    * @throws Exception
    */
   @PostMapping(value = "/sysCompany/selectone")
   public String selectOneSysCompany(@RequestBody JSONObject reqEntity) throws Exception {
       SysCompany sysCompanyReq = JSONObject.parseObject(reqEntity.toJSONString(), SysCompany.class);
       SysCompany sysCompany = sysCompanyService.selectOneSysCompany(sysCompanyReq,true);
       if(Objects.isNull(sysCompany)){
           throw new BusiException("没有符合条件的记录");
       }
       return RespEntity.ok(new SysCompanyResp(sysCompany));
   }

   /**
    * SysCompany 根据条件获取单个数据
    * @return
    * @throws Exception
    */
   @PostMapping(value = "/sysCompany/criteria/selectone")
   public String selectOneSysCompanyExample(@RequestBody JSONObject reqEntity) throws Exception {
       SysCompany sysCompanyReq = JSONObject.parseObject(reqEntity.toJSONString(), SysCompany.class);
       QueryExample queryExample = new QueryExample();
       SysCompany sysCompany = sysCompanyService.selectOneSysCompany(queryExample,true);
       if(Objects.isNull(sysCompany)){
           throw new BusiException("没有符合条件的记录");
       }
       return RespEntity.ok(new SysCompanyResp(sysCompany));
   }

  /**
   * SysCompany 根据实体对象查询列表
   * @return
   * @throws Exception
   */
   @PostMapping(value = "/sysCompany/list")
   public String getSysCompanyListByPage(@RequestBody JSONObject reqEntity) throws Exception {
       SysCompany sysCompany = JSONObject.parseObject(reqEntity.toJSONString(), SysCompany.class);
       JSONObject resp = sysCompanyService.getPageSysCompany(sysCompany,true);
       return RespEntity.ok(resp);
   }

   /**
    * 复杂查询
    * @return
    * @throws Exception
    */
   @PostMapping(value = "/sysCompany/criteria/list")
   public String getSysCompanyListExampleByPage(@RequestBody JSONObject reqEntity) throws Exception {
       SysCompany sysCompany = JSONObject.parseObject(reqEntity.toJSONString(), SysCompany.class);
       QueryExample queryExample = new QueryExample();
       JSONObject resp = sysCompanyService.getPageSysCompanyExample(queryExample,true);
       return RespEntity.ok(resp);
   }

  /**
   * SysCompany 新增或者修改记录，根据主键判断，主键为空则新增，否则修改。
   * @return
   * @throws Exception
   */
   @PostMapping(value = "/sysCompany")
   public String saveSysCompany(@RequestBody JSONObject reqEntity) throws  Exception{
       SysCompany sysCompany = JSONObject.parseObject(reqEntity.toJSONString(), SysCompany.class);
       sysCompanyService.saveSysCompany(sysCompany);
       return RespEntity.ok();
   }

   /**
    * SysCompany 根据主键删除数据
    * @return
    * @throws Exception
    */
    @DeleteMapping(value = "/sysCompany/{scId}")
    public String deleteSysCompany(@PathVariable(value="scId") Long scId) throws  Exception{
        if(Objects.isNull(scId)){
           throw new BusiException("入参请求异常") ;
        }
        sysCompanyService.deleteSysCompany(scId);
        return RespEntity.ok();
    }

}

