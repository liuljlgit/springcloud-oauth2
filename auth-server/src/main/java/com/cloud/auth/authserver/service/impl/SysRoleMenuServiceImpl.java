package com.cloud.auth.authserver.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.cloud.common.complexquery.QueryExample;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.util.*;
import org.springframework.util.CollectionUtils;
import com.cloud.common.utils.CollectionUtil;
import com.cloud.common.constant.IConst;
import org.springframework.cache.annotation.*;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.data.redis.core.RedisTemplate;
import com.cloud.common.exception.BusiException;
import java.util.stream.Collectors;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.JSON;
import com.cloud.auth.authserver.service.inft.ISysRoleMenuService;
import com.cloud.auth.authserver.dao.ISysRoleMenuDao;
import com.cloud.auth.authserver.entity.SysRoleMenu;
import com.cloud.auth.authserver.cache.inft.ISysRoleMenuRedis;
import com.cloud.auth.authserver.webentity.SysRoleMenuResp;

/**
 * ISysRoleMenuService service接口类
 * @author lijun
 */
@Service("sysRoleMenuService")
public class SysRoleMenuServiceImpl implements ISysRoleMenuService{

    private static final Logger logger = LoggerFactory.getLogger(SysRoleMenuServiceImpl.class);

    @Autowired
    private ISysRoleMenuDao sysRoleMenuDao;
    @Autowired
    private ISysRoleMenuRedis sysRoleMenuRedis;

    /**
     * 根据主键获取对象
     * @param srmId
     * @return
     * @throws Exception
     */
    @Override
    public SysRoleMenu loadSysRoleMenuByKey(Long srmId) throws Exception {
        if(Objects.isNull(srmId)){
            throw new BusiException("请输入要获取的数据的ID");
        }
        SysRoleMenu sysRoleMenu;
        sysRoleMenu = sysRoleMenuRedis.getSysRoleMenu(srmId);
        if(Objects.nonNull(sysRoleMenu)){
            logger.info("===> fetch srmId = "+srmId+" entity from redis");
            return sysRoleMenu;
        }
        logger.info("===> fetch srmId = "+srmId+" entity from database");
        sysRoleMenu = sysRoleMenuDao.loadSysRoleMenuByKey(srmId);
        if(Objects.isNull(sysRoleMenu)){
            throw new BusiException("没有符合条件的记录！") ;
        }
        sysRoleMenuRedis.setSysRoleMenu(sysRoleMenu,IConst.MINUTE_15_EXPIRE);
        return sysRoleMenu;
    }

    /**
     * 普通查询获取单个结果
     * @param t
     * @param useCache
     * @return
     * @throws Exception
     */
    @Override
    public <T> SysRoleMenu selectOneSysRoleMenu(T t, Boolean useCache) throws Exception {
        List<SysRoleMenu> list = findSysRoleMenuList(t, useCache);
        if(!CollectionUtils.isEmpty(list)){
            return list.get(0);
        }
        return null;
    }

    /**
     * 新增对象
     * @param sysRoleMenu
     * @return
     * @throws Exception
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public Integer addSysRoleMenu(SysRoleMenu sysRoleMenu) throws Exception {
        if(Objects.isNull(sysRoleMenu)){
            return 0;
        }
        if(Objects.isNull(sysRoleMenu.getSrmId())){
            sysRoleMenu.setSrmId(sysRoleMenuRedis.getSysRoleMenuId());
        }
        Integer result =  sysRoleMenuDao.addSysRoleMenu(sysRoleMenu);
        sysRoleMenuRedis.deleteAllHashSetByPage();
        return result;
    }

    /**
     * 批量新增对象
     * @param sysRoleMenuList
     * @throws Exception
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void addSysRoleMenuList(List<SysRoleMenu> sysRoleMenuList) throws Exception {
        if(CollectionUtils.isEmpty(sysRoleMenuList)){
            return ;
        }
        for (SysRoleMenu sysRoleMenu : sysRoleMenuList) {
            if(Objects.isNull(sysRoleMenu.getSrmId())){
                sysRoleMenu.setSrmId(sysRoleMenuRedis.getSysRoleMenuId());
            }
        }
        sysRoleMenuDao.addSysRoleMenuList(sysRoleMenuList);
        sysRoleMenuRedis.deleteAllHashSetByPage();
    }

    /**
     * 更新对象
     * @param sysRoleMenu
     * @param isFullUpdate
     * @return
     * @throws Exception
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public Integer updateSysRoleMenu(SysRoleMenu sysRoleMenu,Boolean isFullUpdate) throws Exception {
        if(Objects.isNull(sysRoleMenu)){
            return 0;
        }
        if(Objects.isNull(sysRoleMenu.getSrmId())){
            throw new BusiException("更新主键不能为空");
        }
        Integer result;
        if(isFullUpdate){
            result = sysRoleMenuDao.fullUpdateSysRoleMenu(sysRoleMenu);
        } else {
            result = sysRoleMenuDao.updateSysRoleMenu(sysRoleMenu);
        }
        sysRoleMenuRedis.deleteAllHashSetByPage();
        sysRoleMenuRedis.deleteSysRoleMenu(sysRoleMenu.getSrmId());
        return result;
    }

    /**
     * 批量更新
     * @param list
     * @param isFullUpdate
     * @throws Exception
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void updateSysRoleMenuList(List<SysRoleMenu> list,Boolean isFullUpdate) throws Exception {
        if(CollectionUtils.isEmpty(list)){
            return ;
        }
        for (SysRoleMenu sysRoleMenu : list) {
            if(Objects.isNull(sysRoleMenu.getSrmId())){
                throw new BusiException("更新主键不能为空");
            }
            sysRoleMenuRedis.deleteSysRoleMenu(sysRoleMenu.getSrmId());
        }
        if(isFullUpdate){
            sysRoleMenuDao.fullUpdateSysRoleMenuList(list);
        } else {
            sysRoleMenuDao.updateSysRoleMenuList(list);
        }
        sysRoleMenuRedis.deleteAllHashSetByPage();
    }

    /**
     * 删除对象
     * @param srmId
     * @return
     * @throws Exception
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public Integer deleteSysRoleMenu(Long srmId) throws Exception {
        if(Objects.isNull(srmId)){
            throw new BusiException("请输入要删除的数据的ID");
        }
        Integer result = sysRoleMenuDao.deleteSysRoleMenu(srmId);
        sysRoleMenuRedis.deleteAllHashSetByPage();
        sysRoleMenuRedis.deleteSysRoleMenu(srmId);
        return result;
    }

    /**
     * 批量删除对象
     * @param ids
     * @throws Exception
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void deleteSysRoleMenuList(List<Long> ids) throws Exception {
        if(CollectionUtils.isEmpty(ids)){
            return ;
        }
        for (Long id : ids) {
            sysRoleMenuRedis.deleteSysRoleMenu(id);
        }
        sysRoleMenuDao.deleteSysRoleMenuList(ids);
        sysRoleMenuRedis.deleteAllHashSetByPage();
    }

    /**
     * 查询记录总数
     * @param t
     * @param useCache
     * @return
     * @throws Exception
     */
    @Override
    public <T> Long getTotalSysRoleMenu(T t,Boolean useCache) throws Exception {
        if(Objects.isNull(t)){
            throw new BusiException("查询参数不能为空");
        }
        Long count;
        if(useCache){
            count = sysRoleMenuRedis.getTotalSysRoleMenu(t);
            if(Objects.nonNull(count)){
                logger.info("===> fetch count = "+count+" entity from redis");
                return count;
            }
            logger.info("===> fetch count value from database");
            count = geTotalSysRoleMenu(t);
            sysRoleMenuRedis.setTotalSysRoleMenu(t,count,IConst.MINUTE_15_EXPIRE);
        }else{
            count = geTotalSysRoleMenu(t);
        }
        return count;
    }

    /**
     * 从dao中查询总记录数
     * @param t
     * @param <T>
     * @return
     * @throws Exception
     */
    private <T> Long geTotalSysRoleMenu(T t) throws Exception {
        Long count;
        if(t instanceof SysRoleMenu){
            count = sysRoleMenuDao.getSysRoleMenuCount((SysRoleMenu) t);
        }else if(t instanceof QueryExample){
            count = sysRoleMenuDao.getSysRoleMenuCountExample((QueryExample) t);
        }else{
            throw new BusiException("选择类型不正确");
        }
        return count;
    }

    /**
     * 查询列表
     * @param t
     * @param useCache
     * @return
     * @throws Exception
     */
    @Override
    public <T> List<SysRoleMenu> findSysRoleMenuList(T t, Boolean useCache) throws Exception {
        List<SysRoleMenu> list;
        if(useCache){
            JSONObject redisResult = sysRoleMenuRedis.getSysRoleMenuList(t);
            if(Objects.isNull(redisResult)){
                logger.info("===> fetch page list from database");
                list = findSysRoleMenuList(t);
                sysRoleMenuRedis.setSysRoleMenuList(t,list,IConst.MINUTE_15_EXPIRE);
                return list;
            }
            List<Long> ids = JSON.parseArray(redisResult.getString("ids"), Long.class);
            List<SysRoleMenu> sysRoleMenus = JSON.parseArray(redisResult.getString("sysRoleMenus"), SysRoleMenu.class);
            //如果没有值过期,直接返回值,如果有值过期，那么需要重新获取
            if(!sysRoleMenus.contains(null)){
                logger.info("===> fetch page list from redis");
                sysRoleMenuRedis.setSysRoleMenuList(t,sysRoleMenus,IConst.MINUTE_15_EXPIRE);
                return sysRoleMenus;
            }
            //把已经过期的ID拿出来再一次性的去数据库里面获取出来并转成一个Map
            Map<Long, SysRoleMenu> notNullMap = sysRoleMenus.stream().filter(e -> Objects.nonNull(e)).collect(Collectors.toMap(e -> e.getSrmId(), e -> e));
            List<Long> nullIds = ids.stream().filter(e->!notNullMap.containsKey(e)).collect(Collectors.toList());
            Map<Long,SysRoleMenu> nullMap = findSysRoleMenuListByIds(nullIds,false).stream().collect(Collectors.toMap(e->e.getSrmId(),e->e));
            if(nullIds.size() != nullMap.size()){
                logger.info("===> fetch page list from database");
                list = findSysRoleMenuList(t);
                sysRoleMenuRedis.setSysRoleMenuList(t,list,IConst.MINUTE_15_EXPIRE);
                return list;
            }else{
                List<SysRoleMenu> listForBack = new ArrayList<>() ;
                for(int i=0;i<ids.size();i++){
                    if(notNullMap.containsKey(ids.get(i))){
                        listForBack.add(notNullMap.get(ids.get(i)));
                    }else if(nullMap.containsKey(ids.get(i))){
                        listForBack.add(nullMap.get(ids.get(i)));
                        sysRoleMenuRedis.setSysRoleMenu(nullMap.get(ids.get(i)),IConst.MINUTE_15_EXPIRE);
                    }else{
                        logger.info("===> fetch page list from database");
                        list = findSysRoleMenuList(t);
                        sysRoleMenuRedis.setSysRoleMenuList(t,list,IConst.MINUTE_15_EXPIRE);
                        return list;
                    }
                }
                return listForBack;
            }
        }else{
            logger.info("===> fetch page list from database");
            list = findSysRoleMenuList(t);
            return list;
        }
    }

    /**
     * 从dao中查询列表
     * @param t
     * @param <T>
     * @return
     * @throws Exception
     */
    private <T> List<SysRoleMenu> findSysRoleMenuList(T t) throws Exception {
        List<SysRoleMenu> list;
        if(t instanceof SysRoleMenu){
            list = sysRoleMenuDao.getSysRoleMenuList((SysRoleMenu) t);
        }else if(t instanceof QueryExample){
            list = sysRoleMenuDao.getSysRoleMenuListExample((QueryExample) t);
        }else{
            throw new BusiException("选择类型不正确");
        }
        return list;
    }

    /**
     * 根据ID列表从数据库中查询列表
     * @param list
     * @return
     * @throws Exception
     */
    @Override
    public List<SysRoleMenu> findSysRoleMenuListByIds(List<Long> list,Boolean useCache) throws Exception {
        if(CollectionUtils.isEmpty(list)){
            return Collections.EMPTY_LIST;
        }
        List<SysRoleMenu> resList;
        if(useCache){
            resList = sysRoleMenuRedis.getSysRoleMenuListByIds(list).stream().filter(e -> Objects.nonNull(e)).collect(Collectors.toList());
            List<Long> notNullIds = resList.stream().map(e->e.getSrmId()).collect(Collectors.toList());
            List<Long> nullIds = new ArrayList<>(CollectionUtil.aSubtractB(list,notNullIds));
            if(CollectionUtils.isEmpty(nullIds)){
                return resList;
            }else{
                List<SysRoleMenu> nullObjList = sysRoleMenuDao.findSysRoleMenuListByIds(nullIds);
                for(SysRoleMenu e : nullObjList){
                    sysRoleMenuRedis.setSysRoleMenu(e,IConst.MINUTE_15_EXPIRE);
                }
                resList.addAll(nullObjList);
                return resList;
            }
        }else{
            resList = sysRoleMenuDao.findSysRoleMenuListByIds(list);
            return resList;
        }
    }

    /**
     * 分页查询列表
     * @param sysRoleMenu
     * @param useCache
     * @return
     * @throws Exception
     */
    @Override
    public JSONObject getPageSysRoleMenu(SysRoleMenu sysRoleMenu,Boolean useCache) throws Exception{
        if(Objects.isNull(sysRoleMenu)){
            throw new BusiException("查询参数不能为空");
        }
        if(Objects.isNull(sysRoleMenu.getPage()) || Objects.isNull(sysRoleMenu.getPageSize()) || IConst.PAGE_NO_USE.equals(sysRoleMenu.getPage())){
            throw new BusiException("分页请求入参异常");
        }
        JSONObject resp = new JSONObject();
        sysRoleMenu.setTotal(getTotalSysRoleMenu(sysRoleMenu, useCache).intValue());
        resp.put("total",sysRoleMenu.getTotal());
        resp.put("totalPage",sysRoleMenu.getTotalPage());
        resp.put("list",findSysRoleMenuList(sysRoleMenu, useCache).stream().map(e-> new SysRoleMenuResp(e)).collect(Collectors.toList()));
        return resp;
    }

    /**
     * 分页查询列表 Example
     * @param queryExample
     * @param useCache
     * @return
     * @throws Exception
     */
    @Override
    public JSONObject getPageSysRoleMenuExample(QueryExample queryExample,Boolean useCache) throws Exception {
        if(Objects.isNull(queryExample)){
            throw new BusiException("查询参数不能为空");
        }
        if(Objects.isNull(queryExample.getPage()) || Objects.isNull(queryExample.getPageSize()) || IConst.PAGE_NO_USE.equals(queryExample.getPage())){
            throw new BusiException("分页请求入参异常");
        }
        JSONObject resp = new JSONObject();
        queryExample.setTotal(getTotalSysRoleMenu(queryExample, useCache).intValue());
        resp.put("totalPage",queryExample.getTotalPage());
        resp.put("total",queryExample.getTotal());
        resp.put("list",findSysRoleMenuList(queryExample,useCache).stream().map(e-> new SysRoleMenuResp(e)).collect(Collectors.toList()));
        return resp;
    }

    /**
     * 保存记录
     * @param sysRoleMenu
     * @throws Exception
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void saveSysRoleMenu(SysRoleMenu sysRoleMenu) throws Exception {
        if(Objects.isNull(sysRoleMenu)){
           return ;
        }
        if(Objects.isNull(sysRoleMenu.getSrmId())){
            sysRoleMenu.setSrmId(sysRoleMenuRedis.getSysRoleMenuId());
            addSysRoleMenu(sysRoleMenu);
        }else{
            updateSysRoleMenu(sysRoleMenu,false);
        }
    }

    /**
     * 批量保存记录
     * @param sysRoleMenuList
     * @throws Exception
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void saveSysRoleMenuList(List<SysRoleMenu> sysRoleMenuList) throws Exception {
        if(CollectionUtils.isEmpty(sysRoleMenuList)){
            return ;
        }
        List<SysRoleMenu> addList = sysRoleMenuList.stream().filter(e -> Objects.isNull(e.getSrmId())).collect(Collectors.toList());
        List<SysRoleMenu> updateList = sysRoleMenuList.stream().filter(e -> !Objects.isNull(e.getSrmId())).collect(Collectors.toList());
        if(!CollectionUtils.isEmpty(addList)){
            addList = addList.stream().map(e->{
                e.setSrmId(sysRoleMenuRedis.getSysRoleMenuId());
                return e;
            }).collect(Collectors.toList());
            addSysRoleMenuList(addList);
        }
        if(!CollectionUtils.isEmpty(updateList)){
            updateSysRoleMenuList(updateList,false);
        }
    }
}

