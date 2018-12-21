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
import com.cloud.auth.authserver.service.inft.ISysRoleService;
import com.cloud.auth.authserver.dao.inft.ISysRoleDao;
import com.cloud.auth.authserver.entity.SysRole;
import com.cloud.auth.authserver.cache.inft.ISysRoleRedis;
import com.cloud.auth.authserver.webentity.SysRoleResp;

/**
 * ISysRoleService service接口类
 * @author lijun
 */
@Service("sysRoleService")
public class SysRoleServiceImpl implements ISysRoleService{

    private static final Logger logger = LoggerFactory.getLogger(SysRoleServiceImpl.class);

    @Autowired
    private ISysRoleDao sysRoleDao;
    @Autowired
    private ISysRoleRedis sysRoleRedis;

    /**
     * 根据主键获取对象
     * @param srId
     * @return
     * @throws Exception
     */
    @Override
    public SysRole loadSysRoleByKey(Long srId) throws Exception {
        if(Objects.isNull(srId)){
            throw new BusiException("请输入要获取的数据的ID");
        }
        SysRole sysRole;
        sysRole = sysRoleRedis.getSysRole(srId);
        if(Objects.nonNull(sysRole)){
            logger.info("===> fetch srId = "+srId+" entity from redis");
            return sysRole;
        }
        logger.info("===> fetch srId = "+srId+" entity from database");
        sysRole = sysRoleDao.loadSysRoleByKey(srId);
        if(Objects.isNull(sysRole)){
            throw new BusiException("没有符合条件的记录！") ;
        }
        sysRoleRedis.setSysRole(sysRole,IConst.MINUTE_15_EXPIRE);
        return sysRole;
    }

    /**
     * 普通查询获取单个结果
     * @param t
     * @param useCache
     * @return
     * @throws Exception
     */
    @Override
    public <T> SysRole selectOneSysRole(T t, Boolean useCache) throws Exception {
        List<SysRole> list = findSysRoleList(t, useCache);
        if(!CollectionUtils.isEmpty(list)){
            return list.get(0);
        }
        return null;
    }

    /**
     * 新增对象
     * @param sysRole
     * @return
     * @throws Exception
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public Integer insertSysRole(SysRole sysRole) throws Exception {
        if(Objects.isNull(sysRole)){
            return 0;
        }
        if(Objects.isNull(sysRole.getSrId())){
            sysRole.setSrId(sysRoleRedis.getSysRoleId());
        }
        Integer result =  sysRoleDao.insertSysRole(sysRole);
        sysRoleRedis.deleteAllHashSetByPage();
        return result;
    }

    /**
     * 批量新增对象
     * @param sysRoleList
     * @throws Exception
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void insertSysRoleList(List<SysRole> sysRoleList) throws Exception {
        if(CollectionUtils.isEmpty(sysRoleList)){
            return ;
        }
        for (SysRole sysRole : sysRoleList) {
            if(Objects.isNull(sysRole.getSrId())){
                sysRole.setSrId(sysRoleRedis.getSysRoleId());
            }
        }
        sysRoleDao.insertSysRoleList(sysRoleList);
        sysRoleRedis.deleteAllHashSetByPage();
    }

    /**
     * 更新对象
     * @param sysRole
     * @param isFullUpdate
     * @return
     * @throws Exception
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public Integer updateSysRole(SysRole sysRole,Boolean isFullUpdate) throws Exception {
        if(Objects.isNull(sysRole)){
            return 0;
        }
        if(Objects.isNull(sysRole.getSrId())){
            throw new BusiException("更新主键不能为空");
        }
        Integer result;
        if(isFullUpdate){
            result = sysRoleDao.fullUpdateSysRole(sysRole);
        } else {
            result = sysRoleDao.updateSysRole(sysRole);
        }
        sysRoleRedis.deleteAllHashSetByPage();
        sysRoleRedis.deleteSysRole(sysRole.getSrId());
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
    public void updateSysRoleList(List<SysRole> list,Boolean isFullUpdate) throws Exception {
        if(CollectionUtils.isEmpty(list)){
            return ;
        }
        for (SysRole sysRole : list) {
            if(Objects.isNull(sysRole.getSrId())){
                throw new BusiException("更新主键不能为空");
            }
            sysRoleRedis.deleteSysRole(sysRole.getSrId());
        }
        if(isFullUpdate){
            sysRoleDao.fullUpdateSysRoleList(list);
        } else {
            sysRoleDao.updateSysRoleList(list);
        }
        sysRoleRedis.deleteAllHashSetByPage();
    }

    /**
     * 删除对象
     * @param srId
     * @return
     * @throws Exception
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public Integer deleteSysRoleByKey(Long srId) throws Exception {
        if(Objects.isNull(srId)){
            throw new BusiException("请输入要删除的数据的ID");
        }
        Integer result = sysRoleDao.deleteSysRoleByKey(srId);
        sysRoleRedis.deleteAllHashSetByPage();
        sysRoleRedis.deleteSysRole(srId);
        return result;
    }

    /**
     * 批量删除对象
     * @param list
     * @throws Exception
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void deleteSysRoleList(List<SysRole> list) throws Exception {
        if(CollectionUtils.isEmpty(list)){
            return ;
        }
        for (SysRole sysRole : list) {
            if(Objects.isNull(sysRole.getSrId())){
                throw new BusiException("删除主键不能为空");
            }
            sysRoleRedis.deleteSysRole(sysRole.getSrId());
        }
        sysRoleDao.deleteSysRoleList(list);
        sysRoleRedis.deleteAllHashSetByPage();
    }

    /**
     * 查询记录总数
     * @param t
     * @param useCache
     * @return
     * @throws Exception
     */
    @Override
    public <T> Long getTotalSysRole(T t,Boolean useCache) throws Exception {
        if(Objects.isNull(t)){
            throw new BusiException("查询参数不能为空");
        }
        Long count;
        if(useCache){
            count = sysRoleRedis.getTotalSysRole(t);
            if(Objects.nonNull(count)){
                logger.info("===> fetch count = "+count+" entity from redis");
                return count;
            }
            logger.info("===> fetch count value from database");
            count = geTotalSysRole(t);
            sysRoleRedis.setTotalSysRole(t,count,IConst.MINUTE_15_EXPIRE);
        }else{
            count = geTotalSysRole(t);
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
    private <T> Long geTotalSysRole(T t) throws Exception {
        Long count;
        if(t instanceof SysRole){
            count = sysRoleDao.getSysRoleCount((SysRole) t);
        }else if(t instanceof QueryExample){
            count = sysRoleDao.getSysRoleCountExample((QueryExample) t);
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
    public <T> List<SysRole> findSysRoleList(T t, Boolean useCache) throws Exception {
        List<SysRole> list;
        if(useCache){
            JSONObject redisResult = sysRoleRedis.getSysRoleList(t);
            if(Objects.isNull(redisResult)){
                logger.info("===> fetch page list from database");
                list = findSysRoleList(t);
                sysRoleRedis.setSysRoleList(t,list,IConst.MINUTE_15_EXPIRE);
                return list;
            }
            List<Long> ids = JSON.parseArray(redisResult.getString("ids"), Long.class);
            List<SysRole> sysRoles = JSON.parseArray(redisResult.getString("sysRoles"), SysRole.class);
            //如果没有值过期,直接返回值,如果有值过期，那么需要重新获取
            if(!sysRoles.contains(null)){
                logger.info("===> fetch page list from redis");
                sysRoleRedis.setSysRoleList(t,sysRoles,IConst.MINUTE_15_EXPIRE);
                return sysRoles;
            }
            //把已经过期的ID拿出来再一次性的去数据库里面获取出来并转成一个Map
            Map<Long, SysRole> notNullMap = sysRoles.stream().filter(e -> Objects.nonNull(e)).collect(Collectors.toMap(e -> e.getSrId(), e -> e));
            List<Long> nullIds = ids.stream().filter(e->!notNullMap.containsKey(e)).collect(Collectors.toList());
            Map<Long,SysRole> nullMap = findSysRoleListByIds(nullIds,false).stream().collect(Collectors.toMap(e->e.getSrId(),e->e));
            if(nullIds.size() != nullMap.size()){
                logger.info("===> fetch page list from database");
                list = findSysRoleList(t);
                sysRoleRedis.setSysRoleList(t,list,IConst.MINUTE_15_EXPIRE);
                return list;
            }else{
                List<SysRole> listForBack = new ArrayList<>() ;
                for(int i=0;i<ids.size();i++){
                    if(notNullMap.containsKey(ids.get(i))){
                        listForBack.add(notNullMap.get(ids.get(i)));
                    }else if(nullMap.containsKey(ids.get(i))){
                        listForBack.add(nullMap.get(ids.get(i)));
                        sysRoleRedis.setSysRole(nullMap.get(ids.get(i)),IConst.MINUTE_15_EXPIRE);
                    }else{
                        logger.info("===> fetch page list from database");
                        list = findSysRoleList(t);
                        sysRoleRedis.setSysRoleList(t,list,IConst.MINUTE_15_EXPIRE);
                        return list;
                    }
                }
                return listForBack;
            }
        }else{
            logger.info("===> fetch page list from database");
            list = findSysRoleList(t);
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
    private <T> List<SysRole> findSysRoleList(T t) throws Exception {
        List<SysRole> list;
        if(t instanceof SysRole){
            list = sysRoleDao.getSysRoleList((SysRole) t);
        }else if(t instanceof QueryExample){
            list = sysRoleDao.getSysRoleListExample((QueryExample) t);
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
    public List<SysRole> findSysRoleListByIds(List<Long> list,Boolean useCache) throws Exception {
        if(CollectionUtils.isEmpty(list)){
            return Collections.EMPTY_LIST;
        }
        List<SysRole> resList;
        if(useCache){
            resList = sysRoleRedis.getSysRoleListByIds(list);
            Map<Long, SysRole> sysRoleMap = resList.stream().collect(Collectors.toMap(e -> e.getSrId(), e -> e));
            List<Long> nullList = list.stream().filter(e -> !sysRoleMap.containsKey(e)).collect(Collectors.toList());
            if(CollectionUtils.isEmpty(nullList)){
                return resList;
            }else{
                List<SysRole> nullObjList = sysRoleDao.findSysRoleListByIds(nullList);
                for(SysRole e : nullObjList){
                    sysRoleRedis.setSysRole(e,IConst.MINUTE_15_EXPIRE);
                }
                resList.addAll(nullObjList);
                return resList;
            }
        }else{
            resList = sysRoleDao.findSysRoleListByIds(list);
            return resList;
        }
    }

    /**
     * 分页查询列表
     * @param sysRole
     * @param useCache
     * @return
     * @throws Exception
     */
    @Override
    public JSONObject getPageSysRole(SysRole sysRole,Boolean useCache) throws Exception{
        if(Objects.isNull(sysRole)){
            throw new BusiException("查询参数不能为空");
        }
        if(Objects.isNull(sysRole.getPage()) || Objects.isNull(sysRole.getPageSize()) || IConst.PAGE_NO_USE.equals(sysRole.getPage())){
            throw new BusiException("分页请求入参异常");
        }
        JSONObject resp = new JSONObject();
        sysRole.setTotal(getTotalSysRole(sysRole, useCache).intValue());
        resp.put("total",sysRole.getTotal());
        resp.put("totalPage",sysRole.getTotalPage());
        resp.put("list",findSysRoleList(sysRole, useCache).stream().map(e-> new SysRoleResp(e)).collect(Collectors.toList()));
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
    public JSONObject getPageSysRoleExample(QueryExample queryExample,Boolean useCache) throws Exception {
        if(Objects.isNull(queryExample)){
            throw new BusiException("查询参数不能为空");
        }
        if(Objects.isNull(queryExample.getPage()) || Objects.isNull(queryExample.getPageSize()) || IConst.PAGE_NO_USE.equals(queryExample.getPage())){
            throw new BusiException("分页请求入参异常");
        }
        JSONObject resp = new JSONObject();
        queryExample.setTotal(getTotalSysRole(queryExample, useCache).intValue());
        resp.put("totalPage",queryExample.getTotalPage());
        resp.put("total",queryExample.getTotal());
        resp.put("list",findSysRoleList(queryExample,useCache).stream().map(e-> new SysRoleResp(e)).collect(Collectors.toList()));
        return resp;
    }

    /**
     * 保存记录
     * @param sysRole
     * @param isFullUpdate
     * @throws Exception
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void saveSysRole(SysRole sysRole,Boolean isFullUpdate) throws Exception {
        if(Objects.isNull(sysRole)){
           return ;
        }
        if(Objects.isNull(sysRole.getSrId())){
            sysRole.setSrId(sysRoleRedis.getSysRoleId());
            insertSysRole(sysRole);
        }else{
            updateSysRole(sysRole,isFullUpdate);
        }
    }

    /**
     * 批量保存记录
     * @param sysRoleList
     * @param isFullUpdate
     * @throws Exception
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void saveSysRoleList(List<SysRole> sysRoleList,Boolean isFullUpdate) throws Exception {
        if(CollectionUtils.isEmpty(sysRoleList)){
            return ;
        }
        List<SysRole> insertList = sysRoleList.stream().filter(e -> Objects.isNull(e.getSrId())).collect(Collectors.toList());
        List<SysRole> updateList = sysRoleList.stream().filter(e -> !Objects.isNull(e.getSrId())).collect(Collectors.toList());
        if(!CollectionUtils.isEmpty(insertList)){
            insertList = insertList.stream().map(e->{
                e.setSrId(sysRoleRedis.getSysRoleId());
                return e;
            }).collect(Collectors.toList());
            insertSysRoleList(insertList);
        }
        if(!CollectionUtils.isEmpty(updateList)){
            updateSysRoleList(updateList,isFullUpdate);
        }
    }
}

