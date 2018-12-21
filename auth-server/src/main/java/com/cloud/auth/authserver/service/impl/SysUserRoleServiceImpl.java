package com.cloud.auth.authserver.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.cloud.common.complexquery.QueryExample;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.util.*;
import org.springframework.util.CollectionUtils;
import com.cloud.common.constant.IConst;
import org.springframework.cache.annotation.*;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.data.redis.core.RedisTemplate;
import com.cloud.common.exception.BusiException;
import java.util.stream.Collectors;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.JSON;
import com.cloud.auth.authserver.service.inft.ISysUserRoleService;
import com.cloud.auth.authserver.dao.inft.ISysUserRoleDao;
import com.cloud.auth.authserver.entity.SysUserRole;
import com.cloud.auth.authserver.cache.inft.ISysUserRoleRedis;
import com.cloud.auth.authserver.webentity.SysUserRoleResp;

/**
 * ISysUserRoleService service接口类
 * @author lijun
 */
@Service("sysUserRoleService")
public class SysUserRoleServiceImpl implements ISysUserRoleService{

    private static final Logger logger = LoggerFactory.getLogger(SysUserRoleServiceImpl.class);

    @Autowired
    private ISysUserRoleDao sysUserRoleDao;
    @Autowired
    private ISysUserRoleRedis sysUserRoleRedis;

    /**
     * 根据主键获取对象
     * @param surId
     * @return
     * @throws Exception
     */
    @Override
    public SysUserRole loadSysUserRoleByKey(Long surId) throws Exception {
        if(Objects.isNull(surId)){
            throw new BusiException("请输入要获取的数据的ID");
        }
        SysUserRole sysUserRole;
        sysUserRole = sysUserRoleRedis.getSysUserRole(surId);
        if(Objects.nonNull(sysUserRole)){
            logger.info("===> fetch surId = "+surId+" entity from redis");
            return sysUserRole;
        }
        logger.info("===> fetch surId = "+surId+" entity from database");
        sysUserRole = sysUserRoleDao.loadSysUserRoleByKey(surId);
        if(Objects.isNull(sysUserRole)){
            throw new BusiException("没有符合条件的记录！") ;
        }
        sysUserRoleRedis.setSysUserRole(sysUserRole,IConst.MINUTE_15_EXPIRE);
        return sysUserRole;
    }

    /**
     * 普通查询获取单个结果
     * @param t
     * @param useCache
     * @return
     * @throws Exception
     */
    @Override
    public <T> SysUserRole selectOneSysUserRole(T t, Boolean useCache) throws Exception {
        List<SysUserRole> list = findSysUserRoleList(t, useCache);
        if(!CollectionUtils.isEmpty(list)){
            return list.get(0);
        }
        return null;
    }

    /**
     * 新增对象
     * @param sysUserRole
     * @return
     * @throws Exception
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public Integer insertSysUserRole(SysUserRole sysUserRole) throws Exception {
        if(Objects.isNull(sysUserRole)){
            return 0;
        }
        if(Objects.isNull(sysUserRole.getSurId())){
            sysUserRole.setSurId(sysUserRoleRedis.getSysUserRoleId());
        }
        Integer result =  sysUserRoleDao.insertSysUserRole(sysUserRole);
        sysUserRoleRedis.deleteAllHashSetByPage();
        return result;
    }

    /**
     * 批量新增对象
     * @param sysUserRoleList
     * @throws Exception
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void insertSysUserRoleList(List<SysUserRole> sysUserRoleList) throws Exception {
        if(CollectionUtils.isEmpty(sysUserRoleList)){
            return ;
        }
        for (SysUserRole sysUserRole : sysUserRoleList) {
            if(Objects.isNull(sysUserRole.getSurId())){
                sysUserRole.setSurId(sysUserRoleRedis.getSysUserRoleId());
            }
        }
        sysUserRoleDao.insertSysUserRoleList(sysUserRoleList);
        sysUserRoleRedis.deleteAllHashSetByPage();
    }

    /**
     * 更新对象
     * @param sysUserRole
     * @param isFullUpdate
     * @return
     * @throws Exception
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public Integer updateSysUserRole(SysUserRole sysUserRole,Boolean isFullUpdate) throws Exception {
        if(Objects.isNull(sysUserRole)){
            return 0;
        }
        if(Objects.isNull(sysUserRole.getSurId())){
            throw new BusiException("更新主键不能为空");
        }
        Integer result;
        if(isFullUpdate){
            result = sysUserRoleDao.fullUpdateSysUserRole(sysUserRole);
        } else {
            result = sysUserRoleDao.updateSysUserRole(sysUserRole);
        }
        sysUserRoleRedis.deleteAllHashSetByPage();
        sysUserRoleRedis.deleteSysUserRole(sysUserRole.getSurId());
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
    public void updateSysUserRoleList(List<SysUserRole> list,Boolean isFullUpdate) throws Exception {
        if(CollectionUtils.isEmpty(list)){
            return ;
        }
        for (SysUserRole sysUserRole : list) {
            if(Objects.isNull(sysUserRole.getSurId())){
                throw new BusiException("更新主键不能为空");
            }
            sysUserRoleRedis.deleteSysUserRole(sysUserRole.getSurId());
        }
        if(isFullUpdate){
            sysUserRoleDao.fullUpdateSysUserRoleList(list);
        } else {
            sysUserRoleDao.updateSysUserRoleList(list);
        }
        sysUserRoleRedis.deleteAllHashSetByPage();
    }

    /**
     * 删除对象
     * @param surId
     * @return
     * @throws Exception
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public Integer deleteSysUserRoleByKey(Long surId) throws Exception {
        if(Objects.isNull(surId)){
            throw new BusiException("请输入要删除的数据的ID");
        }
        Integer result = sysUserRoleDao.deleteSysUserRoleByKey(surId);
        sysUserRoleRedis.deleteAllHashSetByPage();
        sysUserRoleRedis.deleteSysUserRole(surId);
        return result;
    }

    /**
     * 批量删除对象
     * @param list
     * @throws Exception
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void deleteSysUserRoleList(List<SysUserRole> list) throws Exception {
        if(CollectionUtils.isEmpty(list)){
            return ;
        }
        for (SysUserRole sysUserRole : list) {
            if(Objects.isNull(sysUserRole.getSurId())){
                throw new BusiException("删除主键不能为空");
            }
            sysUserRoleRedis.deleteSysUserRole(sysUserRole.getSurId());
        }
        sysUserRoleDao.deleteSysUserRoleList(list);
        sysUserRoleRedis.deleteAllHashSetByPage();
    }

    /**
     * 查询记录总数
     * @param t
     * @param useCache
     * @return
     * @throws Exception
     */
    @Override
    public <T> Long getTotalSysUserRole(T t,Boolean useCache) throws Exception {
        if(Objects.isNull(t)){
            throw new BusiException("查询参数不能为空");
        }
        Long count;
        if(useCache){
            count = sysUserRoleRedis.getTotalSysUserRole(t);
            if(Objects.nonNull(count)){
                logger.info("===> fetch count = "+count+" entity from redis");
                return count;
            }
            logger.info("===> fetch count value from database");
            count = geTotalSysUserRole(t);
            sysUserRoleRedis.setTotalSysUserRole(t,count,IConst.MINUTE_15_EXPIRE);
        }else{
            count = geTotalSysUserRole(t);
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
    private <T> Long geTotalSysUserRole(T t) throws Exception {
        Long count;
        if(t instanceof SysUserRole){
            count = sysUserRoleDao.getSysUserRoleCount((SysUserRole) t);
        }else if(t instanceof QueryExample){
            count = sysUserRoleDao.getSysUserRoleCountExample((QueryExample) t);
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
    public <T> List<SysUserRole> findSysUserRoleList(T t, Boolean useCache) throws Exception {
        List<SysUserRole> list;
        if(useCache){
            JSONObject redisResult = sysUserRoleRedis.getSysUserRoleList(t);
            if(Objects.isNull(redisResult)){
                logger.info("===> fetch page list from database");
                list = findSysUserRoleList(t);
                sysUserRoleRedis.setSysUserRoleList(t,list,IConst.MINUTE_15_EXPIRE);
                return list;
            }
            List<Long> ids = JSON.parseArray(redisResult.getString("ids"), Long.class);
            List<SysUserRole> sysUserRoles = JSON.parseArray(redisResult.getString("sysUserRoles"), SysUserRole.class);
            //如果没有值过期,直接返回值,如果有值过期，那么需要重新获取
            if(!sysUserRoles.contains(null)){
                logger.info("===> fetch page list from redis");
                sysUserRoleRedis.setSysUserRoleList(t,sysUserRoles,IConst.MINUTE_15_EXPIRE);
                return sysUserRoles;
            }
            //把已经过期的ID拿出来再一次性的去数据库里面获取出来并转成一个Map
            Map<Long, SysUserRole> notNullMap = sysUserRoles.stream().filter(e -> Objects.nonNull(e)).collect(Collectors.toMap(e -> e.getSurId(), e -> e));
            List<Long> nullIds = ids.stream().filter(e->!notNullMap.containsKey(e)).collect(Collectors.toList());
            Map<Long,SysUserRole> nullMap = findSysUserRoleListByIds(nullIds,false).stream().collect(Collectors.toMap(e->e.getSurId(),e->e));
            if(nullIds.size() != nullMap.size()){
                logger.info("===> fetch page list from database");
                list = findSysUserRoleList(t);
                sysUserRoleRedis.setSysUserRoleList(t,list,IConst.MINUTE_15_EXPIRE);
                return list;
            }else{
                List<SysUserRole> listForBack = new ArrayList<>() ;
                for(int i=0;i<ids.size();i++){
                    if(notNullMap.containsKey(ids.get(i))){
                        listForBack.add(notNullMap.get(ids.get(i)));
                    }else if(nullMap.containsKey(ids.get(i))){
                        listForBack.add(nullMap.get(ids.get(i)));
                        sysUserRoleRedis.setSysUserRole(nullMap.get(ids.get(i)),IConst.MINUTE_15_EXPIRE);
                    }else{
                        logger.info("===> fetch page list from database");
                        list = findSysUserRoleList(t);
                        sysUserRoleRedis.setSysUserRoleList(t,list,IConst.MINUTE_15_EXPIRE);
                        return list;
                    }
                }
                return listForBack;
            }
        }else{
            logger.info("===> fetch page list from database");
            list = findSysUserRoleList(t);
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
    private <T> List<SysUserRole> findSysUserRoleList(T t) throws Exception {
        List<SysUserRole> list;
        if(t instanceof SysUserRole){
            list = sysUserRoleDao.getSysUserRoleList((SysUserRole) t);
        }else if(t instanceof QueryExample){
            list = sysUserRoleDao.getSysUserRoleListExample((QueryExample) t);
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
    public List<SysUserRole> findSysUserRoleListByIds(List<Long> list,Boolean useCache) throws Exception {
        if(CollectionUtils.isEmpty(list)){
            return Collections.EMPTY_LIST;
        }
        List<SysUserRole> resList;
        if(useCache){
            resList = sysUserRoleRedis.getSysUserRoleListByIds(list);
            Map<Long, SysUserRole> sysUserRoleMap = resList.stream().collect(Collectors.toMap(e -> e.getSurId(), e -> e));
            List<Long> nullList = list.stream().filter(e -> !sysUserRoleMap.containsKey(e)).collect(Collectors.toList());
            if(CollectionUtils.isEmpty(nullList)){
                return resList;
            }else{
                List<SysUserRole> nullObjList = sysUserRoleDao.findSysUserRoleListByIds(nullList);
                for(SysUserRole e : nullObjList){
                    sysUserRoleRedis.setSysUserRole(e,IConst.MINUTE_15_EXPIRE);
                }
                resList.addAll(nullObjList);
                return resList;
            }
        }else{
            resList = sysUserRoleDao.findSysUserRoleListByIds(list);
            return resList;
        }
    }

    /**
     * 分页查询列表
     * @param sysUserRole
     * @param useCache
     * @return
     * @throws Exception
     */
    @Override
    public JSONObject getPageSysUserRole(SysUserRole sysUserRole,Boolean useCache) throws Exception{
        if(Objects.isNull(sysUserRole)){
            throw new BusiException("查询参数不能为空");
        }
        if(Objects.isNull(sysUserRole.getPage()) || Objects.isNull(sysUserRole.getPageSize()) || IConst.PAGE_NO_USE.equals(sysUserRole.getPage())){
            throw new BusiException("分页请求入参异常");
        }
        JSONObject resp = new JSONObject();
        sysUserRole.setTotal(getTotalSysUserRole(sysUserRole, useCache).intValue());
        resp.put("total",sysUserRole.getTotal());
        resp.put("totalPage",sysUserRole.getTotalPage());
        resp.put("list",findSysUserRoleList(sysUserRole, useCache).stream().map(e-> new SysUserRoleResp(e)).collect(Collectors.toList()));
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
    public JSONObject getPageSysUserRoleExample(QueryExample queryExample,Boolean useCache) throws Exception {
        if(Objects.isNull(queryExample)){
            throw new BusiException("查询参数不能为空");
        }
        if(Objects.isNull(queryExample.getPage()) || Objects.isNull(queryExample.getPageSize()) || IConst.PAGE_NO_USE.equals(queryExample.getPage())){
            throw new BusiException("分页请求入参异常");
        }
        JSONObject resp = new JSONObject();
        queryExample.setTotal(getTotalSysUserRole(queryExample, useCache).intValue());
        resp.put("totalPage",queryExample.getTotalPage());
        resp.put("total",queryExample.getTotal());
        resp.put("list",findSysUserRoleList(queryExample,useCache).stream().map(e-> new SysUserRoleResp(e)).collect(Collectors.toList()));
        return resp;
    }

    /**
     * 保存记录
     * @param sysUserRole
     * @param isFullUpdate
     * @throws Exception
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void saveSysUserRole(SysUserRole sysUserRole,Boolean isFullUpdate) throws Exception {
        if(Objects.isNull(sysUserRole)){
           return ;
        }
        if(Objects.isNull(sysUserRole.getSurId())){
            sysUserRole.setSurId(sysUserRoleRedis.getSysUserRoleId());
            insertSysUserRole(sysUserRole);
        }else{
            updateSysUserRole(sysUserRole,isFullUpdate);
        }
    }

    /**
     * 批量保存记录
     * @param sysUserRoleList
     * @param isFullUpdate
     * @throws Exception
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void saveSysUserRoleList(List<SysUserRole> sysUserRoleList,Boolean isFullUpdate) throws Exception {
        if(CollectionUtils.isEmpty(sysUserRoleList)){
            return ;
        }
        List<SysUserRole> insertList = sysUserRoleList.stream().filter(e -> Objects.isNull(e.getSurId())).collect(Collectors.toList());
        List<SysUserRole> updateList = sysUserRoleList.stream().filter(e -> !Objects.isNull(e.getSurId())).collect(Collectors.toList());
        if(!CollectionUtils.isEmpty(insertList)){
            insertList = insertList.stream().map(e->{
                e.setSurId(sysUserRoleRedis.getSysUserRoleId());
                return e;
            }).collect(Collectors.toList());
            insertSysUserRoleList(insertList);
        }
        if(!CollectionUtils.isEmpty(updateList)){
            updateSysUserRoleList(updateList,isFullUpdate);
        }
    }
}

