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
import com.cloud.auth.authserver.service.inft.ISysPermissionService;
import com.cloud.auth.authserver.dao.inft.ISysPermissionDao;
import com.cloud.auth.authserver.entity.SysPermission;
import com.cloud.auth.authserver.cache.inft.ISysPermissionRedis;
import com.cloud.auth.authserver.webentity.SysPermissionResp;

/**
 * ISysPermissionService service接口类
 * @author lijun
 */
@Service("sysPermissionService")
public class SysPermissionServiceImpl implements ISysPermissionService{

    private static final Logger logger = LoggerFactory.getLogger(SysPermissionServiceImpl.class);

    @Autowired
    private ISysPermissionDao sysPermissionDao;
    @Autowired
    private ISysPermissionRedis sysPermissionRedis;

    /**
     * 根据主键获取对象
     * @param spId
     * @return
     * @throws Exception
     */
    @Override
    public SysPermission loadSysPermissionByKey(Long spId) throws Exception {
        if(Objects.isNull(spId)){
            throw new BusiException("请输入要获取的数据的ID");
        }
        SysPermission sysPermission;
        sysPermission = sysPermissionRedis.getSysPermission(spId);
        if(Objects.nonNull(sysPermission)){
            logger.info("===> fetch spId = "+spId+" entity from redis");
            return sysPermission;
        }
        logger.info("===> fetch spId = "+spId+" entity from database");
        sysPermission = sysPermissionDao.loadSysPermissionByKey(spId);
        if(Objects.isNull(sysPermission)){
            throw new BusiException("没有符合条件的记录！") ;
        }
        sysPermissionRedis.setSysPermission(sysPermission,IConst.MINUTE_15_EXPIRE);
        return sysPermission;
    }

    /**
     * 普通查询获取单个结果
     * @param t
     * @param useCache
     * @return
     * @throws Exception
     */
    @Override
    public <T> SysPermission selectOneSysPermission(T t, Boolean useCache) throws Exception {
        List<SysPermission> list = findSysPermissionList(t, useCache);
        if(!CollectionUtils.isEmpty(list)){
            return list.get(0);
        }
        return null;
    }

    /**
     * 新增对象
     * @param sysPermission
     * @return
     * @throws Exception
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public Integer insertSysPermission(SysPermission sysPermission) throws Exception {
        if(Objects.isNull(sysPermission)){
            return 0;
        }
        if(Objects.isNull(sysPermission.getSpId())){
            sysPermission.setSpId(sysPermissionRedis.getSysPermissionId());
        }
        Integer result =  sysPermissionDao.insertSysPermission(sysPermission);
        sysPermissionRedis.deleteAllHashSetByPage();
        return result;
    }

    /**
     * 批量新增对象
     * @param sysPermissionList
     * @throws Exception
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void insertSysPermissionList(List<SysPermission> sysPermissionList) throws Exception {
        if(CollectionUtils.isEmpty(sysPermissionList)){
            return ;
        }
        for (SysPermission sysPermission : sysPermissionList) {
            if(Objects.isNull(sysPermission.getSpId())){
                sysPermission.setSpId(sysPermissionRedis.getSysPermissionId());
            }
        }
        sysPermissionDao.insertSysPermissionList(sysPermissionList);
        sysPermissionRedis.deleteAllHashSetByPage();
    }

    /**
     * 更新对象
     * @param sysPermission
     * @param isFullUpdate
     * @return
     * @throws Exception
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public Integer updateSysPermission(SysPermission sysPermission,Boolean isFullUpdate) throws Exception {
        if(Objects.isNull(sysPermission)){
            return 0;
        }
        if(Objects.isNull(sysPermission.getSpId())){
            throw new BusiException("更新主键不能为空");
        }
        Integer result;
        if(isFullUpdate){
            result = sysPermissionDao.fullUpdateSysPermission(sysPermission);
        } else {
            result = sysPermissionDao.updateSysPermission(sysPermission);
        }
        sysPermissionRedis.deleteAllHashSetByPage();
        sysPermissionRedis.deleteSysPermission(sysPermission.getSpId());
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
    public void updateSysPermissionList(List<SysPermission> list,Boolean isFullUpdate) throws Exception {
        if(CollectionUtils.isEmpty(list)){
            return ;
        }
        for (SysPermission sysPermission : list) {
            if(Objects.isNull(sysPermission.getSpId())){
                throw new BusiException("更新主键不能为空");
            }
            sysPermissionRedis.deleteSysPermission(sysPermission.getSpId());
        }
        if(isFullUpdate){
            sysPermissionDao.fullUpdateSysPermissionList(list);
        } else {
            sysPermissionDao.updateSysPermissionList(list);
        }
        sysPermissionRedis.deleteAllHashSetByPage();
    }

    /**
     * 删除对象
     * @param spId
     * @return
     * @throws Exception
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public Integer deleteSysPermissionByKey(Long spId) throws Exception {
        if(Objects.isNull(spId)){
            throw new BusiException("请输入要删除的数据的ID");
        }
        Integer result = sysPermissionDao.deleteSysPermissionByKey(spId);
        sysPermissionRedis.deleteAllHashSetByPage();
        sysPermissionRedis.deleteSysPermission(spId);
        return result;
    }

    /**
     * 批量删除对象
     * @param list
     * @throws Exception
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void deleteSysPermissionList(List<SysPermission> list) throws Exception {
        if(CollectionUtils.isEmpty(list)){
            return ;
        }
        for (SysPermission sysPermission : list) {
            if(Objects.isNull(sysPermission.getSpId())){
                throw new BusiException("删除主键不能为空");
            }
            sysPermissionRedis.deleteSysPermission(sysPermission.getSpId());
        }
        sysPermissionDao.deleteSysPermissionList(list);
        sysPermissionRedis.deleteAllHashSetByPage();
    }

    /**
     * 查询记录总数
     * @param t
     * @param useCache
     * @return
     * @throws Exception
     */
    @Override
    public <T> Long getTotalSysPermission(T t,Boolean useCache) throws Exception {
        if(Objects.isNull(t)){
            throw new BusiException("查询参数不能为空");
        }
        Long count;
        if(useCache){
            count = sysPermissionRedis.getTotalSysPermission(t);
            if(Objects.nonNull(count)){
                logger.info("===> fetch count = "+count+" entity from redis");
                return count;
            }
            logger.info("===> fetch count value from database");
            count = geTotalSysPermission(t);
            sysPermissionRedis.setTotalSysPermission(t,count,IConst.MINUTE_15_EXPIRE);
        }else{
            count = geTotalSysPermission(t);
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
    private <T> Long geTotalSysPermission(T t) throws Exception {
        Long count;
        if(t instanceof SysPermission){
            count = sysPermissionDao.getSysPermissionCount((SysPermission) t);
        }else if(t instanceof QueryExample){
            count = sysPermissionDao.getSysPermissionCountExample((QueryExample) t);
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
    public <T> List<SysPermission> findSysPermissionList(T t, Boolean useCache) throws Exception {
        List<SysPermission> list;
        if(useCache){
            JSONObject redisResult = sysPermissionRedis.getSysPermissionList(t);
            if(Objects.isNull(redisResult)){
                logger.info("===> fetch page list from database");
                list = findSysPermissionList(t);
                sysPermissionRedis.setSysPermissionList(t,list,IConst.MINUTE_15_EXPIRE);
                return list;
            }
            List<Long> ids = JSON.parseArray(redisResult.getString("ids"), Long.class);
            List<SysPermission> sysPermissions = JSON.parseArray(redisResult.getString("sysPermissions"), SysPermission.class);
            //如果没有值过期,直接返回值,如果有值过期，那么需要重新获取
            if(!sysPermissions.contains(null)){
                logger.info("===> fetch page list from redis");
                sysPermissionRedis.setSysPermissionList(t,sysPermissions,IConst.MINUTE_15_EXPIRE);
                return sysPermissions;
            }
            //把已经过期的ID拿出来再一次性的去数据库里面获取出来并转成一个Map
            Map<Long, SysPermission> notNullMap = sysPermissions.stream().filter(e -> Objects.nonNull(e)).collect(Collectors.toMap(e -> e.getSpId(), e -> e));
            List<Long> nullIds = ids.stream().filter(e->!notNullMap.containsKey(e)).collect(Collectors.toList());
            Map<Long,SysPermission> nullMap = findSysPermissionListByIds(nullIds,false).stream().collect(Collectors.toMap(e->e.getSpId(),e->e));
            if(nullIds.size() != nullMap.size()){
                logger.info("===> fetch page list from database");
                list = findSysPermissionList(t);
                sysPermissionRedis.setSysPermissionList(t,list,IConst.MINUTE_15_EXPIRE);
                return list;
            }else{
                List<SysPermission> listForBack = new ArrayList<>() ;
                for(int i=0;i<ids.size();i++){
                    if(notNullMap.containsKey(ids.get(i))){
                        listForBack.add(notNullMap.get(ids.get(i)));
                    }else if(nullMap.containsKey(ids.get(i))){
                        listForBack.add(nullMap.get(ids.get(i)));
                        sysPermissionRedis.setSysPermission(nullMap.get(ids.get(i)),IConst.MINUTE_15_EXPIRE);
                    }else{
                        logger.info("===> fetch page list from database");
                        list = findSysPermissionList(t);
                        sysPermissionRedis.setSysPermissionList(t,list,IConst.MINUTE_15_EXPIRE);
                        return list;
                    }
                }
                return listForBack;
            }
        }else{
            logger.info("===> fetch page list from database");
            list = findSysPermissionList(t);
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
    private <T> List<SysPermission> findSysPermissionList(T t) throws Exception {
        List<SysPermission> list;
        if(t instanceof SysPermission){
            list = sysPermissionDao.getSysPermissionList((SysPermission) t);
        }else if(t instanceof QueryExample){
            list = sysPermissionDao.getSysPermissionListExample((QueryExample) t);
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
    public List<SysPermission> findSysPermissionListByIds(List<Long> list,Boolean useCache) throws Exception {
        if(CollectionUtils.isEmpty(list)){
            return Collections.EMPTY_LIST;
        }
        List<SysPermission> resList;
        if(useCache){
            resList = sysPermissionRedis.getSysPermissionListByIds(list);
            Map<Long, SysPermission> sysPermissionMap = resList.stream().collect(Collectors.toMap(e -> e.getSpId(), e -> e));
            List<Long> nullList = list.stream().filter(e -> !sysPermissionMap.containsKey(e)).collect(Collectors.toList());
            if(CollectionUtils.isEmpty(nullList)){
                return resList;
            }else{
                List<SysPermission> nullObjList = sysPermissionDao.findSysPermissionListByIds(nullList);
                for(SysPermission e : nullObjList){
                    sysPermissionRedis.setSysPermission(e,IConst.MINUTE_15_EXPIRE);
                }
                resList.addAll(nullObjList);
                return resList;
            }
        }else{
            resList = sysPermissionDao.findSysPermissionListByIds(list);
            return resList;
        }
    }

    /**
     * 分页查询列表
     * @param sysPermission
     * @param useCache
     * @return
     * @throws Exception
     */
    @Override
    public JSONObject getPageSysPermission(SysPermission sysPermission,Boolean useCache) throws Exception{
        if(Objects.isNull(sysPermission)){
            throw new BusiException("查询参数不能为空");
        }
        if(Objects.isNull(sysPermission.getPage()) || Objects.isNull(sysPermission.getPageSize()) || IConst.PAGE_NO_USE.equals(sysPermission.getPage())){
            throw new BusiException("分页请求入参异常");
        }
        JSONObject resp = new JSONObject();
        sysPermission.setTotal(getTotalSysPermission(sysPermission, useCache).intValue());
        resp.put("total",sysPermission.getTotal());
        resp.put("totalPage",sysPermission.getTotalPage());
        resp.put("list",findSysPermissionList(sysPermission, useCache).stream().map(e-> new SysPermissionResp(e)).collect(Collectors.toList()));
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
    public JSONObject getPageSysPermissionExample(QueryExample queryExample,Boolean useCache) throws Exception {
        if(Objects.isNull(queryExample)){
            throw new BusiException("查询参数不能为空");
        }
        if(Objects.isNull(queryExample.getPage()) || Objects.isNull(queryExample.getPageSize()) || IConst.PAGE_NO_USE.equals(queryExample.getPage())){
            throw new BusiException("分页请求入参异常");
        }
        JSONObject resp = new JSONObject();
        queryExample.setTotal(getTotalSysPermission(queryExample, useCache).intValue());
        resp.put("totalPage",queryExample.getTotalPage());
        resp.put("total",queryExample.getTotal());
        resp.put("list",findSysPermissionList(queryExample,useCache).stream().map(e-> new SysPermissionResp(e)).collect(Collectors.toList()));
        return resp;
    }

    /**
     * 保存记录
     * @param sysPermission
     * @param isFullUpdate
     * @throws Exception
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void saveSysPermission(SysPermission sysPermission,Boolean isFullUpdate) throws Exception {
        if(Objects.isNull(sysPermission)){
           return ;
        }
        if(Objects.isNull(sysPermission.getSpId())){
            sysPermission.setSpId(sysPermissionRedis.getSysPermissionId());
            insertSysPermission(sysPermission);
        }else{
            updateSysPermission(sysPermission,isFullUpdate);
        }
    }

    /**
     * 批量保存记录
     * @param sysPermissionList
     * @param isFullUpdate
     * @throws Exception
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void saveSysPermissionList(List<SysPermission> sysPermissionList,Boolean isFullUpdate) throws Exception {
        if(CollectionUtils.isEmpty(sysPermissionList)){
            return ;
        }
        List<SysPermission> insertList = sysPermissionList.stream().filter(e -> Objects.isNull(e.getSpId())).collect(Collectors.toList());
        List<SysPermission> updateList = sysPermissionList.stream().filter(e -> !Objects.isNull(e.getSpId())).collect(Collectors.toList());
        if(!CollectionUtils.isEmpty(insertList)){
            insertList = insertList.stream().map(e->{
                e.setSpId(sysPermissionRedis.getSysPermissionId());
                return e;
            }).collect(Collectors.toList());
            insertSysPermissionList(insertList);
        }
        if(!CollectionUtils.isEmpty(updateList)){
            updateSysPermissionList(updateList,isFullUpdate);
        }
    }
}

