package com.cloud.data.dataserver.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import com.cloud.common.base.BaseController;
import com.cloud.common.webcomm.ReqEntity;
import com.cloud.common.exception.BusiException;
import com.alibaba.fastjson.JSONObject;
import com.cloud.common.complexquery.QueryExample;
import java.util.*;
import com.cloud.data.dataserver.service.inft.ILoadTimeService;
import com.cloud.data.dataserver.entity.LoadTime;
import com.cloud.data.dataserver.webentity.LoadTimeResp;

/**
 * LoadTimeCtrl 控制层方法
 * @author lijun
 */
@RestController
public class LoadTimeCtrl extends BaseController {

    @Autowired
    private ILoadTimeService loadTimeService;

   /**
   * LoadTime 根据主键获取单个数据
   * @return
   * @throws Exception
   */
   @GetMapping(value = "/loadTime/{ltId}")
   public String loadLoadTimeByKey(@PathVariable(value="ltId") Long ltId) throws Exception {
      if(Objects.isNull(ltId)){
         throw new BusiException("请输入要获取的数据的ID") ;
      }
      LoadTime loadTime = loadTimeService.loadLoadTimeByKey(ltId);
      JSONObject resp = new JSONObject();
      resp.put("loadTime",new LoadTimeResp(loadTime));
      return formatResponseParams(EXEC_OK,resp);
   }

   /**
    * LoadTime 根据条件获取单个数据
    * @return
    * @throws Exception
    */
   @PostMapping(value = "/loadTime/selectone")
   public String selectOneLoadTime(@RequestBody ReqEntity reqEntity) throws Exception {
       LoadTime loadTimeReq = JSONObject.parseObject(reqEntity.getReqBody().toJSONString(), LoadTime.class);
       LoadTime loadTime = loadTimeService.selectOneLoadTime(loadTimeReq,true);
       if(Objects.isNull(loadTime)){
           throw new BusiException("没有符合条件的记录");
       }
       JSONObject resp = new JSONObject();
       resp.put("loadTime",new LoadTimeResp(loadTime));
       return formatResponseParams(EXEC_OK,resp);
   }

   /**
    * LoadTime 根据条件获取单个数据
    * @return
    * @throws Exception
    */
   @PostMapping(value = "/loadTime/criteria/selectone")
   public String selectOneLoadTimeExample(@RequestBody ReqEntity reqEntity) throws Exception {
       LoadTime loadTimeReq = JSONObject.parseObject(reqEntity.getReqBody().toJSONString(), LoadTime.class);
       QueryExample queryExample = new QueryExample();
       LoadTime loadTime = loadTimeService.selectOneLoadTime(queryExample,true);
       if(Objects.isNull(loadTime)){
           throw new BusiException("没有符合条件的记录");
       }
       JSONObject resp = new JSONObject();
       resp.put("loadTime",new LoadTimeResp(loadTime));
       return formatResponseParams(EXEC_OK,resp);
   }

  /**
   * LoadTime 根据实体对象查询列表
   * @return
   * @throws Exception
   */
   @PostMapping(value = "/loadTime/list")
   public String getLoadTimeListByPage(@RequestBody ReqEntity reqEntity) throws Exception {
       LoadTime loadTime = JSONObject.parseObject(reqEntity.getReqBody().toJSONString(), LoadTime.class);
       JSONObject resp = loadTimeService.getPageLoadTime(loadTime,true);
       return formatResponseParams(EXEC_OK,resp);
   }

   /**
    * 复杂查询
    * @return
    * @throws Exception
    */
   @PostMapping(value = "/loadTime/criteria/list")
   public String getLoadTimeListExampleByPage(@RequestBody ReqEntity reqEntity) throws Exception {
       LoadTime loadTime = JSONObject.parseObject(reqEntity.getReqBody().toJSONString(), LoadTime.class);
       QueryExample queryExample = new QueryExample();
       JSONObject resp = loadTimeService.getPageLoadTimeExample(queryExample,true);
       return formatResponseParams(EXEC_OK, resp);
   }

  /**
   * LoadTime 新增或者修改记录，根据主键判断，主键为空则新增，否则修改。
   * @return
   * @throws Exception
   */
   @PostMapping(value = "/loadTime")
   public String saveLoadTime(@RequestBody ReqEntity reqEntity) throws  Exception{
       LoadTime loadTime = JSONObject.parseObject(reqEntity.getReqBody().toJSONString(), LoadTime.class);
       loadTimeService.saveLoadTime(loadTime);
       return formatResponseParams(EXEC_OK,null);
   }

   /**
    * LoadTime 根据主键删除数据
    * @return
    * @throws Exception
    */
    @DeleteMapping(value = "/loadTime/{ltId}")
    public String deleteLoadTime(@PathVariable(value="ltId") Long ltId) throws  Exception{
        if(Objects.isNull(ltId)){
           throw new BusiException("入参请求异常") ;
        }
        loadTimeService.deleteLoadTime(ltId);
        return formatResponseParams(EXEC_OK,null);
    }


}

