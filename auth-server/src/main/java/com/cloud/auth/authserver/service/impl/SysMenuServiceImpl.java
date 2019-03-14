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
import com.cloud.auth.authserver.service.inft.ISysMenuService;
import com.cloud.auth.authserver.dao.ISysMenuDao;
import com.cloud.auth.authserver.entity.SysMenu;
import com.cloud.auth.authserver.cache.inft.ISysMenuRedis;
import com.cloud.auth.authserver.webentity.SysMenuResp;

/**
 * ISysMenuService service接口类
 * @author lijun
 */
@Service("sysMenuService")
public class SysMenuServiceImpl implements ISysMenuService{

    private static final Logger logger = LoggerFactory.getLogger(SysMenuServiceImpl.class);

    @Autowired
    private ISysMenuDao sysMenuDao;
    @Autowired
    private ISysMenuRedis sysMenuRedis;

    /**
     * 根据主键获取对象
     * @param smId
     * @return
     * @throws Exception
     */
    @Override
    public SysMenu loadSysMenuByKey(Long smId) throws Exception {
        if(Objects.isNull(smId)){
            throw new BusiException("请输入要获取的数据的ID");
        }
        SysMenu sysMenu;
        sysMenu = sysMenuRedis.getSysMenu(smId);
        if(Objects.nonNull(sysMenu)){
            logger.info("===> fetch smId = "+smId+" entity from redis");
            return sysMenu;
        }
        logger.info("===> fetch smId = "+smId+" entity from database");
        sysMenu = sysMenuDao.loadSysMenuByKey(smId);
        if(Objects.isNull(sysMenu)){
            throw new BusiException("没有符合条件的记录！") ;
        }
        sysMenuRedis.setSysMenu(sysMenu,IConst.MINUTE_15_EXPIRE);
        return sysMenu;
    }

    /**
     * 普通查询获取单个结果
     * @param t
     * @param useCache
     * @return
     * @throws Exception
     */
    @Override
    public <T> SysMenu selectOneSysMenu(T t, Boolean useCache) throws Exception {
        List<SysMenu> list = findSysMenuList(t, useCache);
        if(!CollectionUtils.isEmpty(list)){
            return list.get(0);
        }
        return null;
    }

    /**
     * 新增对象
     * @param sysMenu
     * @return
     * @throws Exception
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public Integer addSysMenu(SysMenu sysMenu) throws Exception {
        if(Objects.isNull(sysMenu)){
            return 0;
        }
        if(Objects.isNull(sysMenu.getSmId())){
            sysMenu.setSmId(sysMenuRedis.getSysMenuId());
        }
        Integer result =  sysMenuDao.addSysMenu(sysMenu);
        sysMenuRedis.deleteAllHashSetByPage();
        return result;
    }

    /**
     * 批量新增对象
     * @param sysMenuList
     * @throws Exception
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void addSysMenuList(List<SysMenu> sysMenuList) throws Exception {
        if(CollectionUtils.isEmpty(sysMenuList)){
            return ;
        }
        for (SysMenu sysMenu : sysMenuList) {
            if(Objects.isNull(sysMenu.getSmId())){
                sysMenu.setSmId(sysMenuRedis.getSysMenuId());
            }
        }
        sysMenuDao.addSysMenuList(sysMenuList);
        sysMenuRedis.deleteAllHashSetByPage();
    }

    /**
     * 更新对象
     * @param sysMenu
     * @param isFullUpdate
     * @return
     * @throws Exception
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public Integer updateSysMenu(SysMenu sysMenu,Boolean isFullUpdate) throws Exception {
        if(Objects.isNull(sysMenu)){
            return 0;
        }
        if(Objects.isNull(sysMenu.getSmId())){
            throw new BusiException("更新主键不能为空");
        }
        Integer result;
        if(isFullUpdate){
            result = sysMenuDao.fullUpdateSysMenu(sysMenu);
        } else {
            result = sysMenuDao.updateSysMenu(sysMenu);
        }
        sysMenuRedis.deleteAllHashSetByPage();
        sysMenuRedis.deleteSysMenu(sysMenu.getSmId());
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
    public void updateSysMenuList(List<SysMenu> list,Boolean isFullUpdate) throws Exception {
        if(CollectionUtils.isEmpty(list)){
            return ;
        }
        for (SysMenu sysMenu : list) {
            if(Objects.isNull(sysMenu.getSmId())){
                throw new BusiException("更新主键不能为空");
            }
            sysMenuRedis.deleteSysMenu(sysMenu.getSmId());
        }
        if(isFullUpdate){
            sysMenuDao.fullUpdateSysMenuList(list);
        } else {
            sysMenuDao.updateSysMenuList(list);
        }
        sysMenuRedis.deleteAllHashSetByPage();
    }

    /**
     * 删除对象
     * @param smId
     * @return
     * @throws Exception
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public Integer deleteSysMenu(Long smId) throws Exception {
        if(Objects.isNull(smId)){
            throw new BusiException("请输入要删除的数据的ID");
        }
        Integer result = sysMenuDao.deleteSysMenu(smId);
        sysMenuRedis.deleteAllHashSetByPage();
        sysMenuRedis.deleteSysMenu(smId);
        return result;
    }

    /**
     * 批量删除对象
     * @param ids
     * @throws Exception
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void deleteSysMenuList(List<Long> ids) throws Exception {
        if(CollectionUtils.isEmpty(ids)){
            return ;
        }
        for (Long id : ids) {
            sysMenuRedis.deleteSysMenu(id);
        }
        sysMenuDao.deleteSysMenuList(ids);
        sysMenuRedis.deleteAllHashSetByPage();
    }

    /**
     * 查询记录总数
     * @param t
     * @param useCache
     * @return
     * @throws Exception
     */
    @Override
    public <T> Long getTotalSysMenu(T t,Boolean useCache) throws Exception {
        if(Objects.isNull(t)){
            throw new BusiException("查询参数不能为空");
        }
        Long count;
        if(useCache){
            count = sysMenuRedis.getTotalSysMenu(t);
            if(Objects.nonNull(count)){
                logger.info("===> fetch count = "+count+" entity from redis");
                return count;
            }
            logger.info("===> fetch count value from database");
            count = geTotalSysMenu(t);
            sysMenuRedis.setTotalSysMenu(t,count,IConst.MINUTE_15_EXPIRE);
        }else{
            count = geTotalSysMenu(t);
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
    private <T> Long geTotalSysMenu(T t) throws Exception {
        Long count;
        if(t instanceof SysMenu){
            count = sysMenuDao.getSysMenuCount((SysMenu) t);
        }else if(t instanceof QueryExample){
            count = sysMenuDao.getSysMenuCountExample((QueryExample) t);
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
    public <T> List<SysMenu> findSysMenuList(T t, Boolean useCache) throws Exception {
        List<SysMenu> list;
        if(useCache){
            JSONObject redisResult = sysMenuRedis.getSysMenuList(t);
            if(Objects.isNull(redisResult)){
                logger.info("===> fetch page list from database");
                list = findSysMenuList(t);
                sysMenuRedis.setSysMenuList(t,list,IConst.MINUTE_15_EXPIRE);
                return list;
            }
            List<Long> ids = JSON.parseArray(redisResult.getString("ids"), Long.class);
            List<SysMenu> sysMenus = JSON.parseArray(redisResult.getString("sysMenus"), SysMenu.class);
            //如果没有值过期,直接返回值,如果有值过期，那么需要重新获取
            if(!sysMenus.contains(null)){
                logger.info("===> fetch page list from redis");
                sysMenuRedis.setSysMenuList(t,sysMenus,IConst.MINUTE_15_EXPIRE);
                return sysMenus;
            }
            //把已经过期的ID拿出来再一次性的去数据库里面获取出来并转成一个Map
            Map<Long, SysMenu> notNullMap = sysMenus.stream().filter(e -> Objects.nonNull(e)).collect(Collectors.toMap(e -> e.getSmId(), e -> e));
            List<Long> nullIds = ids.stream().filter(e->!notNullMap.containsKey(e)).collect(Collectors.toList());
            Map<Long,SysMenu> nullMap = findSysMenuListByIds(nullIds,false).stream().collect(Collectors.toMap(e->e.getSmId(),e->e));
            if(nullIds.size() != nullMap.size()){
                logger.info("===> fetch page list from database");
                list = findSysMenuList(t);
                sysMenuRedis.setSysMenuList(t,list,IConst.MINUTE_15_EXPIRE);
                return list;
            }else{
                List<SysMenu> listForBack = new ArrayList<>() ;
                for(int i=0;i<ids.size();i++){
                    if(notNullMap.containsKey(ids.get(i))){
                        listForBack.add(notNullMap.get(ids.get(i)));
                    }else if(nullMap.containsKey(ids.get(i))){
                        listForBack.add(nullMap.get(ids.get(i)));
                        sysMenuRedis.setSysMenu(nullMap.get(ids.get(i)),IConst.MINUTE_15_EXPIRE);
                    }else{
                        logger.info("===> fetch page list from database");
                        list = findSysMenuList(t);
                        sysMenuRedis.setSysMenuList(t,list,IConst.MINUTE_15_EXPIRE);
                        return list;
                    }
                }
                return listForBack;
            }
        }else{
            logger.info("===> fetch page list from database");
            list = findSysMenuList(t);
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
    private <T> List<SysMenu> findSysMenuList(T t) throws Exception {
        List<SysMenu> list;
        if(t instanceof SysMenu){
            list = sysMenuDao.getSysMenuList((SysMenu) t);
        }else if(t instanceof QueryExample){
            list = sysMenuDao.getSysMenuListExample((QueryExample) t);
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
    public List<SysMenu> findSysMenuListByIds(List<Long> list,Boolean useCache) throws Exception {
        if(CollectionUtils.isEmpty(list)){
            return Collections.EMPTY_LIST;
        }
        List<SysMenu> resList;
        if(useCache){
            resList = sysMenuRedis.getSysMenuListByIds(list).stream().filter(e -> Objects.nonNull(e)).collect(Collectors.toList());
            List<Long> notNullIds = resList.stream().map(e->e.getSmId()).collect(Collectors.toList());
            List<Long> nullIds = new ArrayList<>(CollectionUtil.aSubtractB(list,notNullIds));
            if(CollectionUtils.isEmpty(nullIds)){
                return resList;
            }else{
                List<SysMenu> nullObjList = sysMenuDao.findSysMenuListByIds(nullIds);
                for(SysMenu e : nullObjList){
                    sysMenuRedis.setSysMenu(e,IConst.MINUTE_15_EXPIRE);
                }
                resList.addAll(nullObjList);
                return resList;
            }
        }else{
            resList = sysMenuDao.findSysMenuListByIds(list);
            return resList;
        }
    }

    /**
     * 分页查询列表
     * @param sysMenu
     * @param useCache
     * @return
     * @throws Exception
     */
    @Override
    public JSONObject getPageSysMenu(SysMenu sysMenu,Boolean useCache) throws Exception{
        if(Objects.isNull(sysMenu)){
            throw new BusiException("查询参数不能为空");
        }
        if(Objects.isNull(sysMenu.getPage()) || Objects.isNull(sysMenu.getPageSize()) || IConst.PAGE_NO_USE.equals(sysMenu.getPage())){
            throw new BusiException("分页请求入参异常");
        }
        JSONObject resp = new JSONObject();
        sysMenu.setTotal(getTotalSysMenu(sysMenu, useCache).intValue());
        resp.put("total",sysMenu.getTotal());
        resp.put("totalPage",sysMenu.getTotalPage());
        resp.put("list",findSysMenuList(sysMenu, useCache).stream().map(e-> new SysMenuResp(e)).collect(Collectors.toList()));
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
    public JSONObject getPageSysMenuExample(QueryExample queryExample,Boolean useCache) throws Exception {
        if(Objects.isNull(queryExample)){
            throw new BusiException("查询参数不能为空");
        }
        if(Objects.isNull(queryExample.getPage()) || Objects.isNull(queryExample.getPageSize()) || IConst.PAGE_NO_USE.equals(queryExample.getPage())){
            throw new BusiException("分页请求入参异常");
        }
        JSONObject resp = new JSONObject();
        queryExample.setTotal(getTotalSysMenu(queryExample, useCache).intValue());
        resp.put("totalPage",queryExample.getTotalPage());
        resp.put("total",queryExample.getTotal());
        resp.put("list",findSysMenuList(queryExample,useCache).stream().map(e-> new SysMenuResp(e)).collect(Collectors.toList()));
        return resp;
    }

    /**
     * 保存记录
     * @param sysMenu
     * @throws Exception
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void saveSysMenu(SysMenu sysMenu) throws Exception {
        if(Objects.isNull(sysMenu)){
           return ;
        }
        if(Objects.isNull(sysMenu.getSmId())){
            sysMenu.setSmId(sysMenuRedis.getSysMenuId());
            addSysMenu(sysMenu);
        }else{
            updateSysMenu(sysMenu,false);
        }
    }

    /**
     * 批量保存记录
     * @param sysMenuList
     * @throws Exception
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void saveSysMenuList(List<SysMenu> sysMenuList) throws Exception {
        if(CollectionUtils.isEmpty(sysMenuList)){
            return ;
        }
        List<SysMenu> addList = sysMenuList.stream().filter(e -> Objects.isNull(e.getSmId())).collect(Collectors.toList());
        List<SysMenu> updateList = sysMenuList.stream().filter(e -> !Objects.isNull(e.getSmId())).collect(Collectors.toList());
        if(!CollectionUtils.isEmpty(addList)){
            addList = addList.stream().map(e->{
                e.setSmId(sysMenuRedis.getSysMenuId());
                return e;
            }).collect(Collectors.toList());
            addSysMenuList(addList);
        }
        if(!CollectionUtils.isEmpty(updateList)){
            updateSysMenuList(updateList,false);
        }
    }
}

