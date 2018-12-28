package com.cloud.auth.authserver.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import com.cloud.common.base.BaseController;
import com.cloud.common.webcomm.ReqEntity;
import com.cloud.common.exception.BusiException;
import com.alibaba.fastjson.JSONObject;
import com.cloud.common.complexquery.QueryExample;
import java.util.*;
import com.cloud.auth.authserver.service.inft.ISysMenuService;
import com.cloud.auth.authserver.entity.SysMenu;
import com.cloud.auth.authserver.webentity.SysMenuResp;

/**
 * SysMenuCtrl 控制层方法
 * @author lijun
 */
@RestController
public class SysMenuCtrl extends BaseController {

    @Autowired
    private ISysMenuService sysMenuService;

   /**
   * SysMenu 根据主键获取单个数据
   * @return
   * @throws Exception
   */
   @GetMapping(value = "/sysMenu/{smId}")
   public String loadSysMenuByKey(@PathVariable(value="smId") Long smId) throws Exception {
      if(Objects.isNull(smId)){
         throw new BusiException("请输入要获取的数据的ID") ;
      }
      SysMenu sysMenu = sysMenuService.loadSysMenuByKey(smId);
      JSONObject resp = new JSONObject();
      resp.put("sysMenu",new SysMenuResp(sysMenu));
      return formatResponseParams(EXEC_OK,resp);
   }

   /**
    * SysMenu 根据条件获取单个数据
    * @return
    * @throws Exception
    */
   @PostMapping(value = "/sysMenu/selectone")
   public String selectOneSysMenu(@RequestBody ReqEntity reqEntity) throws Exception {
       SysMenu sysMenuReq = JSONObject.parseObject(reqEntity.getReqBody().toJSONString(), SysMenu.class);
       SysMenu sysMenu = sysMenuService.selectOneSysMenu(sysMenuReq,true);
       if(Objects.isNull(sysMenu)){
           throw new BusiException("没有符合条件的记录");
       }
       JSONObject resp = new JSONObject();
       resp.put("sysMenu",new SysMenuResp(sysMenu));
       return formatResponseParams(EXEC_OK,resp);
   }

   /**
    * SysMenu 根据条件获取单个数据
    * @return
    * @throws Exception
    */
   @PostMapping(value = "/sysMenu/criteria/selectone")
   public String selectOneSysMenuExample(@RequestBody ReqEntity reqEntity) throws Exception {
       SysMenu sysMenuReq = JSONObject.parseObject(reqEntity.getReqBody().toJSONString(), SysMenu.class);
       QueryExample queryExample = new QueryExample();
       SysMenu sysMenu = sysMenuService.selectOneSysMenu(queryExample,true);
       if(Objects.isNull(sysMenu)){
           throw new BusiException("没有符合条件的记录");
       }
       JSONObject resp = new JSONObject();
       resp.put("sysMenu",new SysMenuResp(sysMenu));
       return formatResponseParams(EXEC_OK,resp);
   }

  /**
   * SysMenu 根据实体对象查询列表
   * @return
   * @throws Exception
   */
   @PostMapping(value = "/sysMenu/list")
   public String getSysMenuListByPage(@RequestBody ReqEntity reqEntity) throws Exception {
       SysMenu sysMenu = JSONObject.parseObject(reqEntity.getReqBody().toJSONString(), SysMenu.class);
       JSONObject resp = sysMenuService.getPageSysMenu(sysMenu,true);
       return formatResponseParams(EXEC_OK,resp);
   }

   /**
    * 复杂查询
    * @return
    * @throws Exception
    */
   @PostMapping(value = "/sysMenu/criteria/list")
   public String getSysMenuListExampleByPage(@RequestBody ReqEntity reqEntity) throws Exception {
       SysMenu sysMenu = JSONObject.parseObject(reqEntity.getReqBody().toJSONString(), SysMenu.class);
       QueryExample queryExample = new QueryExample();
       JSONObject resp = sysMenuService.getPageSysMenuExample(queryExample,true);
       return formatResponseParams(EXEC_OK, resp);
   }

  /**
   * SysMenu 新增或者修改记录，根据主键判断，主键为空则新增，否则修改。
   * @return
   * @throws Exception
   */
   @PostMapping(value = "/sysMenu")
   public String saveSysMenu(@RequestBody ReqEntity reqEntity) throws  Exception{
       SysMenu sysMenu = JSONObject.parseObject(reqEntity.getReqBody().toJSONString(), SysMenu.class);
       sysMenuService.saveSysMenu(sysMenu,false);
       return formatResponseParams(EXEC_OK,null);
   }

   /**
    * SysMenu 根据主键删除数据
    * @return
    * @throws Exception
    */
    @DeleteMapping(value = "/sysMenu/{smId}")
    public String deleteSysMenuByKey(@PathVariable(value="smId") Long smId) throws  Exception{
        if(Objects.isNull(smId)){
           throw new BusiException("入参请求异常") ;
        }
        sysMenuService.deleteSysMenuByKey(smId);
        return formatResponseParams(EXEC_OK,null);
    }


}

