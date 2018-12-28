package com.cloud.auth.authserver.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import com.cloud.common.base.BaseController;
import com.cloud.common.webcomm.ReqEntity;
import com.cloud.common.exception.BusiException;
import com.alibaba.fastjson.JSONObject;
import com.cloud.common.complexquery.QueryExample;
import java.util.*;
import com.cloud.auth.authserver.service.inft.ISysRoleMenuService;
import com.cloud.auth.authserver.entity.SysRoleMenu;
import com.cloud.auth.authserver.webentity.SysRoleMenuResp;

/**
 * SysRoleMenuCtrl 控制层方法
 * @author lijun
 */
@RestController
public class SysRoleMenuCtrl extends BaseController {

    @Autowired
    private ISysRoleMenuService sysRoleMenuService;

   /**
   * SysRoleMenu 根据主键获取单个数据
   * @return
   * @throws Exception
   */
   @GetMapping(value = "/sysRoleMenu/{srmId}")
   public String loadSysRoleMenuByKey(@PathVariable(value="srmId") Long srmId) throws Exception {
      if(Objects.isNull(srmId)){
         throw new BusiException("请输入要获取的数据的ID") ;
      }
      SysRoleMenu sysRoleMenu = sysRoleMenuService.loadSysRoleMenuByKey(srmId);
      JSONObject resp = new JSONObject();
      resp.put("sysRoleMenu",new SysRoleMenuResp(sysRoleMenu));
      return formatResponseParams(EXEC_OK,resp);
   }

   /**
    * SysRoleMenu 根据条件获取单个数据
    * @return
    * @throws Exception
    */
   @PostMapping(value = "/sysRoleMenu/selectone")
   public String selectOneSysRoleMenu(@RequestBody ReqEntity reqEntity) throws Exception {
       SysRoleMenu sysRoleMenuReq = JSONObject.parseObject(reqEntity.getReqBody().toJSONString(), SysRoleMenu.class);
       SysRoleMenu sysRoleMenu = sysRoleMenuService.selectOneSysRoleMenu(sysRoleMenuReq,true);
       if(Objects.isNull(sysRoleMenu)){
           throw new BusiException("没有符合条件的记录");
       }
       JSONObject resp = new JSONObject();
       resp.put("sysRoleMenu",new SysRoleMenuResp(sysRoleMenu));
       return formatResponseParams(EXEC_OK,resp);
   }

   /**
    * SysRoleMenu 根据条件获取单个数据
    * @return
    * @throws Exception
    */
   @PostMapping(value = "/sysRoleMenu/criteria/selectone")
   public String selectOneSysRoleMenuExample(@RequestBody ReqEntity reqEntity) throws Exception {
       SysRoleMenu sysRoleMenuReq = JSONObject.parseObject(reqEntity.getReqBody().toJSONString(), SysRoleMenu.class);
       QueryExample queryExample = new QueryExample();
       SysRoleMenu sysRoleMenu = sysRoleMenuService.selectOneSysRoleMenu(queryExample,true);
       if(Objects.isNull(sysRoleMenu)){
           throw new BusiException("没有符合条件的记录");
       }
       JSONObject resp = new JSONObject();
       resp.put("sysRoleMenu",new SysRoleMenuResp(sysRoleMenu));
       return formatResponseParams(EXEC_OK,resp);
   }

  /**
   * SysRoleMenu 根据实体对象查询列表
   * @return
   * @throws Exception
   */
   @PostMapping(value = "/sysRoleMenu/list")
   public String getSysRoleMenuListByPage(@RequestBody ReqEntity reqEntity) throws Exception {
       SysRoleMenu sysRoleMenu = JSONObject.parseObject(reqEntity.getReqBody().toJSONString(), SysRoleMenu.class);
       JSONObject resp = sysRoleMenuService.getPageSysRoleMenu(sysRoleMenu,true);
       return formatResponseParams(EXEC_OK,resp);
   }

   /**
    * 复杂查询
    * @return
    * @throws Exception
    */
   @PostMapping(value = "/sysRoleMenu/criteria/list")
   public String getSysRoleMenuListExampleByPage(@RequestBody ReqEntity reqEntity) throws Exception {
       SysRoleMenu sysRoleMenu = JSONObject.parseObject(reqEntity.getReqBody().toJSONString(), SysRoleMenu.class);
       QueryExample queryExample = new QueryExample();
       JSONObject resp = sysRoleMenuService.getPageSysRoleMenuExample(queryExample,true);
       return formatResponseParams(EXEC_OK, resp);
   }

  /**
   * SysRoleMenu 新增或者修改记录，根据主键判断，主键为空则新增，否则修改。
   * @return
   * @throws Exception
   */
   @PostMapping(value = "/sysRoleMenu")
   public String saveSysRoleMenu(@RequestBody ReqEntity reqEntity) throws  Exception{
       SysRoleMenu sysRoleMenu = JSONObject.parseObject(reqEntity.getReqBody().toJSONString(), SysRoleMenu.class);
       sysRoleMenuService.saveSysRoleMenu(sysRoleMenu,false);
       return formatResponseParams(EXEC_OK,null);
   }

   /**
    * SysRoleMenu 根据主键删除数据
    * @return
    * @throws Exception
    */
    @DeleteMapping(value = "/sysRoleMenu/{srmId}")
    public String deleteSysRoleMenuByKey(@PathVariable(value="srmId") Long srmId) throws  Exception{
        if(Objects.isNull(srmId)){
           throw new BusiException("入参请求异常") ;
        }
        sysRoleMenuService.deleteSysRoleMenuByKey(srmId);
        return formatResponseParams(EXEC_OK,null);
    }


}
