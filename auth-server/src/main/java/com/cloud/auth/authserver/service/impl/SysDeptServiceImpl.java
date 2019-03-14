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
import com.cloud.auth.authserver.service.inft.ISysDeptService;
import com.cloud.auth.authserver.dao.ISysDeptDao;
import com.cloud.auth.authserver.entity.SysDept;
import com.cloud.auth.authserver.cache.inft.ISysDeptRedis;
import com.cloud.auth.authserver.webentity.SysDeptResp;

/**
 * ISysDeptService service接口类
 * @author lijun
 */
@Service("sysDeptService")
public class SysDeptServiceImpl implements ISysDeptService{

    private static final Logger logger = LoggerFactory.getLogger(SysDeptServiceImpl.class);

    @Autowired
    private ISysDeptDao sysDeptDao;
    @Autowired
    private ISysDeptRedis sysDeptRedis;

    /**
     * 根据主键获取对象
     * @param sdId
     * @return
     * @throws Exception
     */
    @Override
    public SysDept loadSysDeptByKey(Long sdId) throws Exception {
        if(Objects.isNull(sdId)){
            throw new BusiException("请输入要获取的数据的ID");
        }
        SysDept sysDept;
        sysDept = sysDeptRedis.getSysDept(sdId);
        if(Objects.nonNull(sysDept)){
            logger.info("===> fetch sdId = "+sdId+" entity from redis");
            return sysDept;
        }
        logger.info("===> fetch sdId = "+sdId+" entity from database");
        sysDept = sysDeptDao.loadSysDeptByKey(sdId);
        if(Objects.isNull(sysDept)){
            throw new BusiException("没有符合条件的记录！") ;
        }
        sysDeptRedis.setSysDept(sysDept,IConst.MINUTE_15_EXPIRE);
        return sysDept;
    }

    /**
     * 普通查询获取单个结果
     * @param t
     * @param useCache
     * @return
     * @throws Exception
     */
    @Override
    public <T> SysDept selectOneSysDept(T t, Boolean useCache) throws Exception {
        List<SysDept> list = findSysDeptList(t, useCache);
        if(!CollectionUtils.isEmpty(list)){
            return list.get(0);
        }
        return null;
    }

    /**
     * 新增对象
     * @param sysDept
     * @return
     * @throws Exception
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public Integer addSysDept(SysDept sysDept) throws Exception {
        if(Objects.isNull(sysDept)){
            return 0;
        }
        if(Objects.isNull(sysDept.getSdId())){
            sysDept.setSdId(sysDeptRedis.getSysDeptId());
        }
        Integer result =  sysDeptDao.addSysDept(sysDept);
        sysDeptRedis.deleteAllHashSetByPage();
        return result;
    }

    /**
     * 批量新增对象
     * @param sysDeptList
     * @throws Exception
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void addSysDeptList(List<SysDept> sysDeptList) throws Exception {
        if(CollectionUtils.isEmpty(sysDeptList)){
            return ;
        }
        for (SysDept sysDept : sysDeptList) {
            if(Objects.isNull(sysDept.getSdId())){
                sysDept.setSdId(sysDeptRedis.getSysDeptId());
            }
        }
        sysDeptDao.addSysDeptList(sysDeptList);
        sysDeptRedis.deleteAllHashSetByPage();
    }

    /**
     * 更新对象
     * @param sysDept
     * @param isFullUpdate
     * @return
     * @throws Exception
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public Integer updateSysDept(SysDept sysDept,Boolean isFullUpdate) throws Exception {
        if(Objects.isNull(sysDept)){
            return 0;
        }
        if(Objects.isNull(sysDept.getSdId())){
            throw new BusiException("更新主键不能为空");
        }
        Integer result;
        if(isFullUpdate){
            result = sysDeptDao.fullUpdateSysDept(sysDept);
        } else {
            result = sysDeptDao.updateSysDept(sysDept);
        }
        sysDeptRedis.deleteAllHashSetByPage();
        sysDeptRedis.deleteSysDept(sysDept.getSdId());
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
    public void updateSysDeptList(List<SysDept> list,Boolean isFullUpdate) throws Exception {
        if(CollectionUtils.isEmpty(list)){
            return ;
        }
        for (SysDept sysDept : list) {
            if(Objects.isNull(sysDept.getSdId())){
                throw new BusiException("更新主键不能为空");
            }
            sysDeptRedis.deleteSysDept(sysDept.getSdId());
        }
        if(isFullUpdate){
            sysDeptDao.fullUpdateSysDeptList(list);
        } else {
            sysDeptDao.updateSysDeptList(list);
        }
        sysDeptRedis.deleteAllHashSetByPage();
    }

    /**
     * 删除对象
     * @param sdId
     * @return
     * @throws Exception
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public Integer deleteSysDept(Long sdId) throws Exception {
        if(Objects.isNull(sdId)){
            throw new BusiException("请输入要删除的数据的ID");
        }
        Integer result = sysDeptDao.deleteSysDept(sdId);
        sysDeptRedis.deleteAllHashSetByPage();
        sysDeptRedis.deleteSysDept(sdId);
        return result;
    }

    /**
     * 批量删除对象
     * @param ids
     * @throws Exception
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void deleteSysDeptList(List<Long> ids) throws Exception {
        if(CollectionUtils.isEmpty(ids)){
            return ;
        }
        for (Long id : ids) {
            sysDeptRedis.deleteSysDept(id);
        }
        sysDeptDao.deleteSysDeptList(ids);
        sysDeptRedis.deleteAllHashSetByPage();
    }

    /**
     * 查询记录总数
     * @param t
     * @param useCache
     * @return
     * @throws Exception
     */
    @Override
    public <T> Long getTotalSysDept(T t,Boolean useCache) throws Exception {
        if(Objects.isNull(t)){
            throw new BusiException("查询参数不能为空");
        }
        Long count;
        if(useCache){
            count = sysDeptRedis.getTotalSysDept(t);
            if(Objects.nonNull(count)){
                logger.info("===> fetch count = "+count+" entity from redis");
                return count;
            }
            logger.info("===> fetch count value from database");
            count = geTotalSysDept(t);
            sysDeptRedis.setTotalSysDept(t,count,IConst.MINUTE_15_EXPIRE);
        }else{
            count = geTotalSysDept(t);
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
    private <T> Long geTotalSysDept(T t) throws Exception {
        Long count;
        if(t instanceof SysDept){
            count = sysDeptDao.getSysDeptCount((SysDept) t);
        }else if(t instanceof QueryExample){
            count = sysDeptDao.getSysDeptCountExample((QueryExample) t);
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
    public <T> List<SysDept> findSysDeptList(T t, Boolean useCache) throws Exception {
        List<SysDept> list;
        if(useCache){
            JSONObject redisResult = sysDeptRedis.getSysDeptList(t);
            if(Objects.isNull(redisResult)){
                logger.info("===> fetch page list from database");
                list = findSysDeptList(t);
                sysDeptRedis.setSysDeptList(t,list,IConst.MINUTE_15_EXPIRE);
                return list;
            }
            List<Long> ids = JSON.parseArray(redisResult.getString("ids"), Long.class);
            List<SysDept> sysDepts = JSON.parseArray(redisResult.getString("sysDepts"), SysDept.class);
            //如果没有值过期,直接返回值,如果有值过期，那么需要重新获取
            if(!sysDepts.contains(null)){
                logger.info("===> fetch page list from redis");
                sysDeptRedis.setSysDeptList(t,sysDepts,IConst.MINUTE_15_EXPIRE);
                return sysDepts;
            }
            //把已经过期的ID拿出来再一次性的去数据库里面获取出来并转成一个Map
            Map<Long, SysDept> notNullMap = sysDepts.stream().filter(e -> Objects.nonNull(e)).collect(Collectors.toMap(e -> e.getSdId(), e -> e));
            List<Long> nullIds = ids.stream().filter(e->!notNullMap.containsKey(e)).collect(Collectors.toList());
            Map<Long,SysDept> nullMap = findSysDeptListByIds(nullIds,false).stream().collect(Collectors.toMap(e->e.getSdId(),e->e));
            if(nullIds.size() != nullMap.size()){
                logger.info("===> fetch page list from database");
                list = findSysDeptList(t);
                sysDeptRedis.setSysDeptList(t,list,IConst.MINUTE_15_EXPIRE);
                return list;
            }else{
                List<SysDept> listForBack = new ArrayList<>() ;
                for(int i=0;i<ids.size();i++){
                    if(notNullMap.containsKey(ids.get(i))){
                        listForBack.add(notNullMap.get(ids.get(i)));
                    }else if(nullMap.containsKey(ids.get(i))){
                        listForBack.add(nullMap.get(ids.get(i)));
                        sysDeptRedis.setSysDept(nullMap.get(ids.get(i)),IConst.MINUTE_15_EXPIRE);
                    }else{
                        logger.info("===> fetch page list from database");
                        list = findSysDeptList(t);
                        sysDeptRedis.setSysDeptList(t,list,IConst.MINUTE_15_EXPIRE);
                        return list;
                    }
                }
                return listForBack;
            }
        }else{
            logger.info("===> fetch page list from database");
            list = findSysDeptList(t);
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
    private <T> List<SysDept> findSysDeptList(T t) throws Exception {
        List<SysDept> list;
        if(t instanceof SysDept){
            list = sysDeptDao.getSysDeptList((SysDept) t);
        }else if(t instanceof QueryExample){
            list = sysDeptDao.getSysDeptListExample((QueryExample) t);
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
    public List<SysDept> findSysDeptListByIds(List<Long> list,Boolean useCache) throws Exception {
        if(CollectionUtils.isEmpty(list)){
            return Collections.EMPTY_LIST;
        }
        List<SysDept> resList;
        if(useCache){
            resList = sysDeptRedis.getSysDeptListByIds(list).stream().filter(e -> Objects.nonNull(e)).collect(Collectors.toList());
            List<Long> notNullIds = resList.stream().map(e->e.getSdId()).collect(Collectors.toList());
            List<Long> nullIds = new ArrayList<>(CollectionUtil.aSubtractB(list,notNullIds));
            if(CollectionUtils.isEmpty(nullIds)){
                return resList;
            }else{
                List<SysDept> nullObjList = sysDeptDao.findSysDeptListByIds(nullIds);
                for(SysDept e : nullObjList){
                    sysDeptRedis.setSysDept(e,IConst.MINUTE_15_EXPIRE);
                }
                resList.addAll(nullObjList);
                return resList;
            }
        }else{
            resList = sysDeptDao.findSysDeptListByIds(list);
            return resList;
        }
    }

    /**
     * 分页查询列表
     * @param sysDept
     * @param useCache
     * @return
     * @throws Exception
     */
    @Override
    public JSONObject getPageSysDept(SysDept sysDept,Boolean useCache) throws Exception{
        if(Objects.isNull(sysDept)){
            throw new BusiException("查询参数不能为空");
        }
        if(Objects.isNull(sysDept.getPage()) || Objects.isNull(sysDept.getPageSize()) || IConst.PAGE_NO_USE.equals(sysDept.getPage())){
            throw new BusiException("分页请求入参异常");
        }
        JSONObject resp = new JSONObject();
        sysDept.setTotal(getTotalSysDept(sysDept, useCache).intValue());
        resp.put("total",sysDept.getTotal());
        resp.put("totalPage",sysDept.getTotalPage());
        resp.put("list",findSysDeptList(sysDept, useCache).stream().map(e-> new SysDeptResp(e)).collect(Collectors.toList()));
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
    public JSONObject getPageSysDeptExample(QueryExample queryExample,Boolean useCache) throws Exception {
        if(Objects.isNull(queryExample)){
            throw new BusiException("查询参数不能为空");
        }
        if(Objects.isNull(queryExample.getPage()) || Objects.isNull(queryExample.getPageSize()) || IConst.PAGE_NO_USE.equals(queryExample.getPage())){
            throw new BusiException("分页请求入参异常");
        }
        JSONObject resp = new JSONObject();
        queryExample.setTotal(getTotalSysDept(queryExample, useCache).intValue());
        resp.put("totalPage",queryExample.getTotalPage());
        resp.put("total",queryExample.getTotal());
        resp.put("list",findSysDeptList(queryExample,useCache).stream().map(e-> new SysDeptResp(e)).collect(Collectors.toList()));
        return resp;
    }

    /**
     * 保存记录
     * @param sysDept
     * @throws Exception
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void saveSysDept(SysDept sysDept) throws Exception {
        if(Objects.isNull(sysDept)){
           return ;
        }
        if(Objects.isNull(sysDept.getSdId())){
            sysDept.setSdId(sysDeptRedis.getSysDeptId());
            addSysDept(sysDept);
        }else{
            updateSysDept(sysDept,false);
        }
    }

    /**
     * 批量保存记录
     * @param sysDeptList
     * @throws Exception
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void saveSysDeptList(List<SysDept> sysDeptList) throws Exception {
        if(CollectionUtils.isEmpty(sysDeptList)){
            return ;
        }
        List<SysDept> addList = sysDeptList.stream().filter(e -> Objects.isNull(e.getSdId())).collect(Collectors.toList());
        List<SysDept> updateList = sysDeptList.stream().filter(e -> !Objects.isNull(e.getSdId())).collect(Collectors.toList());
        if(!CollectionUtils.isEmpty(addList)){
            addList = addList.stream().map(e->{
                e.setSdId(sysDeptRedis.getSysDeptId());
                return e;
            }).collect(Collectors.toList());
            addSysDeptList(addList);
        }
        if(!CollectionUtils.isEmpty(updateList)){
            updateSysDeptList(updateList,false);
        }
    }
}

