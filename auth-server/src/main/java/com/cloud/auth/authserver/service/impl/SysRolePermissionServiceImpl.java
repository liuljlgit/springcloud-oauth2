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
import com.cloud.auth.authserver.service.inft.ISysRolePermissionService;
import com.cloud.auth.authserver.dao.ISysRolePermissionDao;
import com.cloud.auth.authserver.entity.SysRolePermission;
import com.cloud.auth.authserver.cache.inft.ISysRolePermissionRedis;
import com.cloud.auth.authserver.webentity.SysRolePermissionResp;

/**
 * ISysRolePermissionService service接口类
 * @author lijun
 */
@Service("sysRolePermissionService")
public class SysRolePermissionServiceImpl implements ISysRolePermissionService{

    private static final Logger logger = LoggerFactory.getLogger(SysRolePermissionServiceImpl.class);

    @Autowired
    private ISysRolePermissionDao sysRolePermissionDao;
    @Autowired
    private ISysRolePermissionRedis sysRolePermissionRedis;

    /**
     * 根据主键获取对象
     * @param srpId
     * @return
     * @throws Exception
     */
    @Override
    public SysRolePermission loadSysRolePermissionByKey(Long srpId) throws Exception {
        if(Objects.isNull(srpId)){
            throw new BusiException("请输入要获取的数据的ID");
        }
        SysRolePermission sysRolePermission;
        sysRolePermission = sysRolePermissionRedis.getSysRolePermission(srpId);
        if(Objects.nonNull(sysRolePermission)){
            logger.info("===> fetch srpId = "+srpId+" entity from redis");
            return sysRolePermission;
        }
        logger.info("===> fetch srpId = "+srpId+" entity from database");
        sysRolePermission = sysRolePermissionDao.loadSysRolePermissionByKey(srpId);
        if(Objects.isNull(sysRolePermission)){
            throw new BusiException("没有符合条件的记录！") ;
        }
        sysRolePermissionRedis.setSysRolePermission(sysRolePermission,IConst.MINUTE_15_EXPIRE);
        return sysRolePermission;
    }

    /**
     * 普通查询获取单个结果
     * @param t
     * @param useCache
     * @return
     * @throws Exception
     */
    @Override
    public <T> SysRolePermission selectOneSysRolePermission(T t, Boolean useCache) throws Exception {
        List<SysRolePermission> list = findSysRolePermissionList(t, useCache);
        if(!CollectionUtils.isEmpty(list)){
            return list.get(0);
        }
        return null;
    }

    /**
     * 新增对象
     * @param sysRolePermission
     * @return
     * @throws Exception
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public Integer addSysRolePermission(SysRolePermission sysRolePermission) throws Exception {
        if(Objects.isNull(sysRolePermission)){
            return 0;
        }
        if(Objects.isNull(sysRolePermission.getSrpId())){
            sysRolePermission.setSrpId(sysRolePermissionRedis.getSysRolePermissionId());
        }
        Integer result =  sysRolePermissionDao.addSysRolePermission(sysRolePermission);
        sysRolePermissionRedis.deleteAllHashSetByPage();
        return result;
    }

    /**
     * 批量新增对象
     * @param sysRolePermissionList
     * @throws Exception
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void addSysRolePermissionList(List<SysRolePermission> sysRolePermissionList) throws Exception {
        if(CollectionUtils.isEmpty(sysRolePermissionList)){
            return ;
        }
        for (SysRolePermission sysRolePermission : sysRolePermissionList) {
            if(Objects.isNull(sysRolePermission.getSrpId())){
                sysRolePermission.setSrpId(sysRolePermissionRedis.getSysRolePermissionId());
            }
        }
        sysRolePermissionDao.addSysRolePermissionList(sysRolePermissionList);
        sysRolePermissionRedis.deleteAllHashSetByPage();
    }

    /**
     * 更新对象
     * @param sysRolePermission
     * @param isFullUpdate
     * @return
     * @throws Exception
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public Integer updateSysRolePermission(SysRolePermission sysRolePermission,Boolean isFullUpdate) throws Exception {
        if(Objects.isNull(sysRolePermission)){
            return 0;
        }
        if(Objects.isNull(sysRolePermission.getSrpId())){
            throw new BusiException("更新主键不能为空");
        }
        Integer result;
        if(isFullUpdate){
            result = sysRolePermissionDao.fullUpdateSysRolePermission(sysRolePermission);
        } else {
            result = sysRolePermissionDao.updateSysRolePermission(sysRolePermission);
        }
        sysRolePermissionRedis.deleteAllHashSetByPage();
        sysRolePermissionRedis.deleteSysRolePermission(sysRolePermission.getSrpId());
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
    public void updateSysRolePermissionList(List<SysRolePermission> list,Boolean isFullUpdate) throws Exception {
        if(CollectionUtils.isEmpty(list)){
            return ;
        }
        for (SysRolePermission sysRolePermission : list) {
            if(Objects.isNull(sysRolePermission.getSrpId())){
                throw new BusiException("更新主键不能为空");
            }
            sysRolePermissionRedis.deleteSysRolePermission(sysRolePermission.getSrpId());
        }
        if(isFullUpdate){
            sysRolePermissionDao.fullUpdateSysRolePermissionList(list);
        } else {
            sysRolePermissionDao.updateSysRolePermissionList(list);
        }
        sysRolePermissionRedis.deleteAllHashSetByPage();
    }

    /**
     * 删除对象
     * @param srpId
     * @return
     * @throws Exception
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public Integer deleteSysRolePermission(Long srpId) throws Exception {
        if(Objects.isNull(srpId)){
            throw new BusiException("请输入要删除的数据的ID");
        }
        Integer result = sysRolePermissionDao.deleteSysRolePermission(srpId);
        sysRolePermissionRedis.deleteAllHashSetByPage();
        sysRolePermissionRedis.deleteSysRolePermission(srpId);
        return result;
    }

    /**
     * 批量删除对象
     * @param ids
     * @throws Exception
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void deleteSysRolePermissionList(List<Long> ids) throws Exception {
        if(CollectionUtils.isEmpty(ids)){
            return ;
        }
        for (Long id : ids) {
            sysRolePermissionRedis.deleteSysRolePermission(id);
        }
        sysRolePermissionDao.deleteSysRolePermissionList(ids);
        sysRolePermissionRedis.deleteAllHashSetByPage();
    }

    /**
     * 查询记录总数
     * @param t
     * @param useCache
     * @return
     * @throws Exception
     */
    @Override
    public <T> Long getTotalSysRolePermission(T t,Boolean useCache) throws Exception {
        if(Objects.isNull(t)){
            throw new BusiException("查询参数不能为空");
        }
        Long count;
        if(useCache){
            count = sysRolePermissionRedis.getTotalSysRolePermission(t);
            if(Objects.nonNull(count)){
                logger.info("===> fetch count = "+count+" entity from redis");
                return count;
            }
            logger.info("===> fetch count value from database");
            count = geTotalSysRolePermission(t);
            sysRolePermissionRedis.setTotalSysRolePermission(t,count,IConst.MINUTE_15_EXPIRE);
        }else{
            count = geTotalSysRolePermission(t);
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
    private <T> Long geTotalSysRolePermission(T t) throws Exception {
        Long count;
        if(t instanceof SysRolePermission){
            count = sysRolePermissionDao.getSysRolePermissionCount((SysRolePermission) t);
        }else if(t instanceof QueryExample){
            count = sysRolePermissionDao.getSysRolePermissionCountExample((QueryExample) t);
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
    public <T> List<SysRolePermission> findSysRolePermissionList(T t, Boolean useCache) throws Exception {
        List<SysRolePermission> list;
        if(useCache){
            JSONObject redisResult = sysRolePermissionRedis.getSysRolePermissionList(t);
            if(Objects.isNull(redisResult)){
                logger.info("===> fetch page list from database");
                list = findSysRolePermissionList(t);
                sysRolePermissionRedis.setSysRolePermissionList(t,list,IConst.MINUTE_15_EXPIRE);
                return list;
            }
            List<Long> ids = JSON.parseArray(redisResult.getString("ids"), Long.class);
            List<SysRolePermission> sysRolePermissions = JSON.parseArray(redisResult.getString("sysRolePermissions"), SysRolePermission.class);
            //如果没有值过期,直接返回值,如果有值过期，那么需要重新获取
            if(!sysRolePermissions.contains(null)){
                logger.info("===> fetch page list from redis");
                sysRolePermissionRedis.setSysRolePermissionList(t,sysRolePermissions,IConst.MINUTE_15_EXPIRE);
                return sysRolePermissions;
            }
            //把已经过期的ID拿出来再一次性的去数据库里面获取出来并转成一个Map
            Map<Long, SysRolePermission> notNullMap = sysRolePermissions.stream().filter(e -> Objects.nonNull(e)).collect(Collectors.toMap(e -> e.getSrpId(), e -> e));
            List<Long> nullIds = ids.stream().filter(e->!notNullMap.containsKey(e)).collect(Collectors.toList());
            Map<Long,SysRolePermission> nullMap = findSysRolePermissionListByIds(nullIds,false).stream().collect(Collectors.toMap(e->e.getSrpId(),e->e));
            if(nullIds.size() != nullMap.size()){
                logger.info("===> fetch page list from database");
                list = findSysRolePermissionList(t);
                sysRolePermissionRedis.setSysRolePermissionList(t,list,IConst.MINUTE_15_EXPIRE);
                return list;
            }else{
                List<SysRolePermission> listForBack = new ArrayList<>() ;
                for(int i=0;i<ids.size();i++){
                    if(notNullMap.containsKey(ids.get(i))){
                        listForBack.add(notNullMap.get(ids.get(i)));
                    }else if(nullMap.containsKey(ids.get(i))){
                        listForBack.add(nullMap.get(ids.get(i)));
                        sysRolePermissionRedis.setSysRolePermission(nullMap.get(ids.get(i)),IConst.MINUTE_15_EXPIRE);
                    }else{
                        logger.info("===> fetch page list from database");
                        list = findSysRolePermissionList(t);
                        sysRolePermissionRedis.setSysRolePermissionList(t,list,IConst.MINUTE_15_EXPIRE);
                        return list;
                    }
                }
                return listForBack;
            }
        }else{
            logger.info("===> fetch page list from database");
            list = findSysRolePermissionList(t);
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
    private <T> List<SysRolePermission> findSysRolePermissionList(T t) throws Exception {
        List<SysRolePermission> list;
        if(t instanceof SysRolePermission){
            list = sysRolePermissionDao.getSysRolePermissionList((SysRolePermission) t);
        }else if(t instanceof QueryExample){
            list = sysRolePermissionDao.getSysRolePermissionListExample((QueryExample) t);
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
    public List<SysRolePermission> findSysRolePermissionListByIds(List<Long> list,Boolean useCache) throws Exception {
        if(CollectionUtils.isEmpty(list)){
            return Collections.EMPTY_LIST;
        }
        List<SysRolePermission> resList;
        if(useCache){
            resList = sysRolePermissionRedis.getSysRolePermissionListByIds(list).stream().filter(e -> Objects.nonNull(e)).collect(Collectors.toList());
            List<Long> notNullIds = resList.stream().map(e->e.getSrpId()).collect(Collectors.toList());
            List<Long> nullIds = new ArrayList<>(CollectionUtil.aSubtractB(list,notNullIds));
            if(CollectionUtils.isEmpty(nullIds)){
                return resList;
            }else{
                List<SysRolePermission> nullObjList = sysRolePermissionDao.findSysRolePermissionListByIds(nullIds);
                for(SysRolePermission e : nullObjList){
                    sysRolePermissionRedis.setSysRolePermission(e,IConst.MINUTE_15_EXPIRE);
                }
                resList.addAll(nullObjList);
                return resList;
            }
        }else{
            resList = sysRolePermissionDao.findSysRolePermissionListByIds(list);
            return resList;
        }
    }

    /**
     * 分页查询列表
     * @param sysRolePermission
     * @param useCache
     * @return
     * @throws Exception
     */
    @Override
    public JSONObject getPageSysRolePermission(SysRolePermission sysRolePermission,Boolean useCache) throws Exception{
        if(Objects.isNull(sysRolePermission)){
            throw new BusiException("查询参数不能为空");
        }
        if(Objects.isNull(sysRolePermission.getPage()) || Objects.isNull(sysRolePermission.getPageSize()) || IConst.PAGE_NO_USE.equals(sysRolePermission.getPage())){
            throw new BusiException("分页请求入参异常");
        }
        JSONObject resp = new JSONObject();
        sysRolePermission.setTotal(getTotalSysRolePermission(sysRolePermission, useCache).intValue());
        resp.put("total",sysRolePermission.getTotal());
        resp.put("totalPage",sysRolePermission.getTotalPage());
        resp.put("list",findSysRolePermissionList(sysRolePermission, useCache).stream().map(e-> new SysRolePermissionResp(e)).collect(Collectors.toList()));
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
    public JSONObject getPageSysRolePermissionExample(QueryExample queryExample,Boolean useCache) throws Exception {
        if(Objects.isNull(queryExample)){
            throw new BusiException("查询参数不能为空");
        }
        if(Objects.isNull(queryExample.getPage()) || Objects.isNull(queryExample.getPageSize()) || IConst.PAGE_NO_USE.equals(queryExample.getPage())){
            throw new BusiException("分页请求入参异常");
        }
        JSONObject resp = new JSONObject();
        queryExample.setTotal(getTotalSysRolePermission(queryExample, useCache).intValue());
        resp.put("totalPage",queryExample.getTotalPage());
        resp.put("total",queryExample.getTotal());
        resp.put("list",findSysRolePermissionList(queryExample,useCache).stream().map(e-> new SysRolePermissionResp(e)).collect(Collectors.toList()));
        return resp;
    }

    /**
     * 保存记录
     * @param sysRolePermission
     * @throws Exception
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void saveSysRolePermission(SysRolePermission sysRolePermission) throws Exception {
        if(Objects.isNull(sysRolePermission)){
           return ;
        }
        if(Objects.isNull(sysRolePermission.getSrpId())){
            sysRolePermission.setSrpId(sysRolePermissionRedis.getSysRolePermissionId());
            addSysRolePermission(sysRolePermission);
        }else{
            updateSysRolePermission(sysRolePermission,false);
        }
    }

    /**
     * 批量保存记录
     * @param sysRolePermissionList
     * @throws Exception
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void saveSysRolePermissionList(List<SysRolePermission> sysRolePermissionList) throws Exception {
        if(CollectionUtils.isEmpty(sysRolePermissionList)){
            return ;
        }
        List<SysRolePermission> addList = sysRolePermissionList.stream().filter(e -> Objects.isNull(e.getSrpId())).collect(Collectors.toList());
        List<SysRolePermission> updateList = sysRolePermissionList.stream().filter(e -> !Objects.isNull(e.getSrpId())).collect(Collectors.toList());
        if(!CollectionUtils.isEmpty(addList)){
            addList = addList.stream().map(e->{
                e.setSrpId(sysRolePermissionRedis.getSysRolePermissionId());
                return e;
            }).collect(Collectors.toList());
            addSysRolePermissionList(addList);
        }
        if(!CollectionUtils.isEmpty(updateList)){
            updateSysRolePermissionList(updateList,false);
        }
    }
}

