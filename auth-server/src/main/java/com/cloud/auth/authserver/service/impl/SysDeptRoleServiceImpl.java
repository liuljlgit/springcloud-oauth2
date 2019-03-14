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
import com.cloud.auth.authserver.service.inft.ISysDeptRoleService;
import com.cloud.auth.authserver.dao.ISysDeptRoleDao;
import com.cloud.auth.authserver.entity.SysDeptRole;
import com.cloud.auth.authserver.cache.inft.ISysDeptRoleRedis;
import com.cloud.auth.authserver.webentity.SysDeptRoleResp;

/**
 * ISysDeptRoleService service接口类
 * @author lijun
 */
@Service("sysDeptRoleService")
public class SysDeptRoleServiceImpl implements ISysDeptRoleService{

    private static final Logger logger = LoggerFactory.getLogger(SysDeptRoleServiceImpl.class);

    @Autowired
    private ISysDeptRoleDao sysDeptRoleDao;
    @Autowired
    private ISysDeptRoleRedis sysDeptRoleRedis;

    /**
     * 根据主键获取对象
     * @param sdrId
     * @return
     * @throws Exception
     */
    @Override
    public SysDeptRole loadSysDeptRoleByKey(Long sdrId) throws Exception {
        if(Objects.isNull(sdrId)){
            throw new BusiException("请输入要获取的数据的ID");
        }
        SysDeptRole sysDeptRole;
        sysDeptRole = sysDeptRoleRedis.getSysDeptRole(sdrId);
        if(Objects.nonNull(sysDeptRole)){
            logger.info("===> fetch sdrId = "+sdrId+" entity from redis");
            return sysDeptRole;
        }
        logger.info("===> fetch sdrId = "+sdrId+" entity from database");
        sysDeptRole = sysDeptRoleDao.loadSysDeptRoleByKey(sdrId);
        if(Objects.isNull(sysDeptRole)){
            throw new BusiException("没有符合条件的记录！") ;
        }
        sysDeptRoleRedis.setSysDeptRole(sysDeptRole,IConst.MINUTE_15_EXPIRE);
        return sysDeptRole;
    }

    /**
     * 普通查询获取单个结果
     * @param t
     * @param useCache
     * @return
     * @throws Exception
     */
    @Override
    public <T> SysDeptRole selectOneSysDeptRole(T t, Boolean useCache) throws Exception {
        List<SysDeptRole> list = findSysDeptRoleList(t, useCache);
        if(!CollectionUtils.isEmpty(list)){
            return list.get(0);
        }
        return null;
    }

    /**
     * 新增对象
     * @param sysDeptRole
     * @return
     * @throws Exception
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public Integer addSysDeptRole(SysDeptRole sysDeptRole) throws Exception {
        if(Objects.isNull(sysDeptRole)){
            return 0;
        }
        if(Objects.isNull(sysDeptRole.getSdrId())){
            sysDeptRole.setSdrId(sysDeptRoleRedis.getSysDeptRoleId());
        }
        Integer result =  sysDeptRoleDao.addSysDeptRole(sysDeptRole);
        sysDeptRoleRedis.deleteAllHashSetByPage();
        return result;
    }

    /**
     * 批量新增对象
     * @param sysDeptRoleList
     * @throws Exception
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void addSysDeptRoleList(List<SysDeptRole> sysDeptRoleList) throws Exception {
        if(CollectionUtils.isEmpty(sysDeptRoleList)){
            return ;
        }
        for (SysDeptRole sysDeptRole : sysDeptRoleList) {
            if(Objects.isNull(sysDeptRole.getSdrId())){
                sysDeptRole.setSdrId(sysDeptRoleRedis.getSysDeptRoleId());
            }
        }
        sysDeptRoleDao.addSysDeptRoleList(sysDeptRoleList);
        sysDeptRoleRedis.deleteAllHashSetByPage();
    }

    /**
     * 更新对象
     * @param sysDeptRole
     * @param isFullUpdate
     * @return
     * @throws Exception
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public Integer updateSysDeptRole(SysDeptRole sysDeptRole,Boolean isFullUpdate) throws Exception {
        if(Objects.isNull(sysDeptRole)){
            return 0;
        }
        if(Objects.isNull(sysDeptRole.getSdrId())){
            throw new BusiException("更新主键不能为空");
        }
        Integer result;
        if(isFullUpdate){
            result = sysDeptRoleDao.fullUpdateSysDeptRole(sysDeptRole);
        } else {
            result = sysDeptRoleDao.updateSysDeptRole(sysDeptRole);
        }
        sysDeptRoleRedis.deleteAllHashSetByPage();
        sysDeptRoleRedis.deleteSysDeptRole(sysDeptRole.getSdrId());
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
    public void updateSysDeptRoleList(List<SysDeptRole> list,Boolean isFullUpdate) throws Exception {
        if(CollectionUtils.isEmpty(list)){
            return ;
        }
        for (SysDeptRole sysDeptRole : list) {
            if(Objects.isNull(sysDeptRole.getSdrId())){
                throw new BusiException("更新主键不能为空");
            }
            sysDeptRoleRedis.deleteSysDeptRole(sysDeptRole.getSdrId());
        }
        if(isFullUpdate){
            sysDeptRoleDao.fullUpdateSysDeptRoleList(list);
        } else {
            sysDeptRoleDao.updateSysDeptRoleList(list);
        }
        sysDeptRoleRedis.deleteAllHashSetByPage();
    }

    /**
     * 删除对象
     * @param sdrId
     * @return
     * @throws Exception
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public Integer deleteSysDeptRole(Long sdrId) throws Exception {
        if(Objects.isNull(sdrId)){
            throw new BusiException("请输入要删除的数据的ID");
        }
        Integer result = sysDeptRoleDao.deleteSysDeptRole(sdrId);
        sysDeptRoleRedis.deleteAllHashSetByPage();
        sysDeptRoleRedis.deleteSysDeptRole(sdrId);
        return result;
    }

    /**
     * 批量删除对象
     * @param ids
     * @throws Exception
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void deleteSysDeptRoleList(List<Long> ids) throws Exception {
        if(CollectionUtils.isEmpty(ids)){
            return ;
        }
        for (Long id : ids) {
            sysDeptRoleRedis.deleteSysDeptRole(id);
        }
        sysDeptRoleDao.deleteSysDeptRoleList(ids);
        sysDeptRoleRedis.deleteAllHashSetByPage();
    }

    /**
     * 查询记录总数
     * @param t
     * @param useCache
     * @return
     * @throws Exception
     */
    @Override
    public <T> Long getTotalSysDeptRole(T t,Boolean useCache) throws Exception {
        if(Objects.isNull(t)){
            throw new BusiException("查询参数不能为空");
        }
        Long count;
        if(useCache){
            count = sysDeptRoleRedis.getTotalSysDeptRole(t);
            if(Objects.nonNull(count)){
                logger.info("===> fetch count = "+count+" entity from redis");
                return count;
            }
            logger.info("===> fetch count value from database");
            count = geTotalSysDeptRole(t);
            sysDeptRoleRedis.setTotalSysDeptRole(t,count,IConst.MINUTE_15_EXPIRE);
        }else{
            count = geTotalSysDeptRole(t);
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
    private <T> Long geTotalSysDeptRole(T t) throws Exception {
        Long count;
        if(t instanceof SysDeptRole){
            count = sysDeptRoleDao.getSysDeptRoleCount((SysDeptRole) t);
        }else if(t instanceof QueryExample){
            count = sysDeptRoleDao.getSysDeptRoleCountExample((QueryExample) t);
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
    public <T> List<SysDeptRole> findSysDeptRoleList(T t, Boolean useCache) throws Exception {
        List<SysDeptRole> list;
        if(useCache){
            JSONObject redisResult = sysDeptRoleRedis.getSysDeptRoleList(t);
            if(Objects.isNull(redisResult)){
                logger.info("===> fetch page list from database");
                list = findSysDeptRoleList(t);
                sysDeptRoleRedis.setSysDeptRoleList(t,list,IConst.MINUTE_15_EXPIRE);
                return list;
            }
            List<Long> ids = JSON.parseArray(redisResult.getString("ids"), Long.class);
            List<SysDeptRole> sysDeptRoles = JSON.parseArray(redisResult.getString("sysDeptRoles"), SysDeptRole.class);
            //如果没有值过期,直接返回值,如果有值过期，那么需要重新获取
            if(!sysDeptRoles.contains(null)){
                logger.info("===> fetch page list from redis");
                sysDeptRoleRedis.setSysDeptRoleList(t,sysDeptRoles,IConst.MINUTE_15_EXPIRE);
                return sysDeptRoles;
            }
            //把已经过期的ID拿出来再一次性的去数据库里面获取出来并转成一个Map
            Map<Long, SysDeptRole> notNullMap = sysDeptRoles.stream().filter(e -> Objects.nonNull(e)).collect(Collectors.toMap(e -> e.getSdrId(), e -> e));
            List<Long> nullIds = ids.stream().filter(e->!notNullMap.containsKey(e)).collect(Collectors.toList());
            Map<Long,SysDeptRole> nullMap = findSysDeptRoleListByIds(nullIds,false).stream().collect(Collectors.toMap(e->e.getSdrId(),e->e));
            if(nullIds.size() != nullMap.size()){
                logger.info("===> fetch page list from database");
                list = findSysDeptRoleList(t);
                sysDeptRoleRedis.setSysDeptRoleList(t,list,IConst.MINUTE_15_EXPIRE);
                return list;
            }else{
                List<SysDeptRole> listForBack = new ArrayList<>() ;
                for(int i=0;i<ids.size();i++){
                    if(notNullMap.containsKey(ids.get(i))){
                        listForBack.add(notNullMap.get(ids.get(i)));
                    }else if(nullMap.containsKey(ids.get(i))){
                        listForBack.add(nullMap.get(ids.get(i)));
                        sysDeptRoleRedis.setSysDeptRole(nullMap.get(ids.get(i)),IConst.MINUTE_15_EXPIRE);
                    }else{
                        logger.info("===> fetch page list from database");
                        list = findSysDeptRoleList(t);
                        sysDeptRoleRedis.setSysDeptRoleList(t,list,IConst.MINUTE_15_EXPIRE);
                        return list;
                    }
                }
                return listForBack;
            }
        }else{
            logger.info("===> fetch page list from database");
            list = findSysDeptRoleList(t);
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
    private <T> List<SysDeptRole> findSysDeptRoleList(T t) throws Exception {
        List<SysDeptRole> list;
        if(t instanceof SysDeptRole){
            list = sysDeptRoleDao.getSysDeptRoleList((SysDeptRole) t);
        }else if(t instanceof QueryExample){
            list = sysDeptRoleDao.getSysDeptRoleListExample((QueryExample) t);
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
    public List<SysDeptRole> findSysDeptRoleListByIds(List<Long> list,Boolean useCache) throws Exception {
        if(CollectionUtils.isEmpty(list)){
            return Collections.EMPTY_LIST;
        }
        List<SysDeptRole> resList;
        if(useCache){
            resList = sysDeptRoleRedis.getSysDeptRoleListByIds(list).stream().filter(e -> Objects.nonNull(e)).collect(Collectors.toList());
            List<Long> notNullIds = resList.stream().map(e->e.getSdrId()).collect(Collectors.toList());
            List<Long> nullIds = new ArrayList<>(CollectionUtil.aSubtractB(list,notNullIds));
            if(CollectionUtils.isEmpty(nullIds)){
                return resList;
            }else{
                List<SysDeptRole> nullObjList = sysDeptRoleDao.findSysDeptRoleListByIds(nullIds);
                for(SysDeptRole e : nullObjList){
                    sysDeptRoleRedis.setSysDeptRole(e,IConst.MINUTE_15_EXPIRE);
                }
                resList.addAll(nullObjList);
                return resList;
            }
        }else{
            resList = sysDeptRoleDao.findSysDeptRoleListByIds(list);
            return resList;
        }
    }

    /**
     * 分页查询列表
     * @param sysDeptRole
     * @param useCache
     * @return
     * @throws Exception
     */
    @Override
    public JSONObject getPageSysDeptRole(SysDeptRole sysDeptRole,Boolean useCache) throws Exception{
        if(Objects.isNull(sysDeptRole)){
            throw new BusiException("查询参数不能为空");
        }
        if(Objects.isNull(sysDeptRole.getPage()) || Objects.isNull(sysDeptRole.getPageSize()) || IConst.PAGE_NO_USE.equals(sysDeptRole.getPage())){
            throw new BusiException("分页请求入参异常");
        }
        JSONObject resp = new JSONObject();
        sysDeptRole.setTotal(getTotalSysDeptRole(sysDeptRole, useCache).intValue());
        resp.put("total",sysDeptRole.getTotal());
        resp.put("totalPage",sysDeptRole.getTotalPage());
        resp.put("list",findSysDeptRoleList(sysDeptRole, useCache).stream().map(e-> new SysDeptRoleResp(e)).collect(Collectors.toList()));
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
    public JSONObject getPageSysDeptRoleExample(QueryExample queryExample,Boolean useCache) throws Exception {
        if(Objects.isNull(queryExample)){
            throw new BusiException("查询参数不能为空");
        }
        if(Objects.isNull(queryExample.getPage()) || Objects.isNull(queryExample.getPageSize()) || IConst.PAGE_NO_USE.equals(queryExample.getPage())){
            throw new BusiException("分页请求入参异常");
        }
        JSONObject resp = new JSONObject();
        queryExample.setTotal(getTotalSysDeptRole(queryExample, useCache).intValue());
        resp.put("totalPage",queryExample.getTotalPage());
        resp.put("total",queryExample.getTotal());
        resp.put("list",findSysDeptRoleList(queryExample,useCache).stream().map(e-> new SysDeptRoleResp(e)).collect(Collectors.toList()));
        return resp;
    }

    /**
     * 保存记录
     * @param sysDeptRole
     * @throws Exception
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void saveSysDeptRole(SysDeptRole sysDeptRole) throws Exception {
        if(Objects.isNull(sysDeptRole)){
           return ;
        }
        if(Objects.isNull(sysDeptRole.getSdrId())){
            sysDeptRole.setSdrId(sysDeptRoleRedis.getSysDeptRoleId());
            addSysDeptRole(sysDeptRole);
        }else{
            updateSysDeptRole(sysDeptRole,false);
        }
    }

    /**
     * 批量保存记录
     * @param sysDeptRoleList
     * @throws Exception
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void saveSysDeptRoleList(List<SysDeptRole> sysDeptRoleList) throws Exception {
        if(CollectionUtils.isEmpty(sysDeptRoleList)){
            return ;
        }
        List<SysDeptRole> addList = sysDeptRoleList.stream().filter(e -> Objects.isNull(e.getSdrId())).collect(Collectors.toList());
        List<SysDeptRole> updateList = sysDeptRoleList.stream().filter(e -> !Objects.isNull(e.getSdrId())).collect(Collectors.toList());
        if(!CollectionUtils.isEmpty(addList)){
            addList = addList.stream().map(e->{
                e.setSdrId(sysDeptRoleRedis.getSysDeptRoleId());
                return e;
            }).collect(Collectors.toList());
            addSysDeptRoleList(addList);
        }
        if(!CollectionUtils.isEmpty(updateList)){
            updateSysDeptRoleList(updateList,false);
        }
    }
}

