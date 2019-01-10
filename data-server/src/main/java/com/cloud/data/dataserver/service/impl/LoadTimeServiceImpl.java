package com.cloud.data.dataserver.service.impl;

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
import com.cloud.data.dataserver.service.inft.ILoadTimeService;
import com.cloud.data.dataserver.dao.ILoadTimeDao;
import com.cloud.data.dataserver.entity.LoadTime;
import com.cloud.data.dataserver.cache.inft.ILoadTimeRedis;
import com.cloud.data.dataserver.webentity.LoadTimeResp;

/**
 * ILoadTimeService service接口类
 * @author lijun
 */
@Service("loadTimeService")
public class LoadTimeServiceImpl implements ILoadTimeService{

    private static final Logger logger = LoggerFactory.getLogger(LoadTimeServiceImpl.class);

    @Autowired
    private ILoadTimeDao loadTimeDao;
    @Autowired
    private ILoadTimeRedis loadTimeRedis;

    /**
     * 根据主键获取对象
     * @param ltId
     * @return
     * @throws Exception
     */
    @Override
    public LoadTime loadLoadTimeByKey(Long ltId) throws Exception {
        if(Objects.isNull(ltId)){
            throw new BusiException("请输入要获取的数据的ID");
        }
        LoadTime loadTime;
        loadTime = loadTimeRedis.getLoadTime(ltId);
        if(Objects.nonNull(loadTime)){
            logger.info("===> fetch ltId = "+ltId+" entity from redis");
            return loadTime;
        }
        logger.info("===> fetch ltId = "+ltId+" entity from database");
        loadTime = loadTimeDao.loadLoadTimeByKey(ltId);
        if(Objects.isNull(loadTime)){
            throw new BusiException("没有符合条件的记录！") ;
        }
        loadTimeRedis.setLoadTime(loadTime,IConst.MINUTE_15_EXPIRE);
        return loadTime;
    }

    /**
     * 普通查询获取单个结果
     * @param t
     * @param useCache
     * @return
     * @throws Exception
     */
    @Override
    public <T> LoadTime selectOneLoadTime(T t, Boolean useCache) throws Exception {
        List<LoadTime> list = findLoadTimeList(t, useCache);
        if(!CollectionUtils.isEmpty(list)){
            return list.get(0);
        }
        return null;
    }

    /**
     * 新增对象
     * @param loadTime
     * @return
     * @throws Exception
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public Integer addLoadTime(LoadTime loadTime) throws Exception {
        if(Objects.isNull(loadTime)){
            return 0;
        }
        if(Objects.isNull(loadTime.getLtId())){
            loadTime.setLtId(loadTimeRedis.getLoadTimeId());
        }
        Integer result =  loadTimeDao.addLoadTime(loadTime);
        loadTimeRedis.deleteAllHashSetByPage();
        return result;
    }

    /**
     * 批量新增对象
     * @param loadTimeList
     * @throws Exception
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void addLoadTimeList(List<LoadTime> loadTimeList) throws Exception {
        if(CollectionUtils.isEmpty(loadTimeList)){
            return ;
        }
        for (LoadTime loadTime : loadTimeList) {
            if(Objects.isNull(loadTime.getLtId())){
                loadTime.setLtId(loadTimeRedis.getLoadTimeId());
            }
        }
        loadTimeDao.addLoadTimeList(loadTimeList);
        loadTimeRedis.deleteAllHashSetByPage();
    }

    /**
     * 更新对象
     * @param loadTime
     * @param isFullUpdate
     * @return
     * @throws Exception
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public Integer updateLoadTime(LoadTime loadTime,Boolean isFullUpdate) throws Exception {
        if(Objects.isNull(loadTime)){
            return 0;
        }
        if(Objects.isNull(loadTime.getLtId())){
            throw new BusiException("更新主键不能为空");
        }
        Integer result;
        if(isFullUpdate){
            result = loadTimeDao.fullUpdateLoadTime(loadTime);
        } else {
            result = loadTimeDao.updateLoadTime(loadTime);
        }
        loadTimeRedis.deleteAllHashSetByPage();
        loadTimeRedis.deleteLoadTime(loadTime.getLtId());
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
    public void updateLoadTimeList(List<LoadTime> list,Boolean isFullUpdate) throws Exception {
        if(CollectionUtils.isEmpty(list)){
            return ;
        }
        for (LoadTime loadTime : list) {
            if(Objects.isNull(loadTime.getLtId())){
                throw new BusiException("更新主键不能为空");
            }
            loadTimeRedis.deleteLoadTime(loadTime.getLtId());
        }
        if(isFullUpdate){
            loadTimeDao.fullUpdateLoadTimeList(list);
        } else {
            loadTimeDao.updateLoadTimeList(list);
        }
        loadTimeRedis.deleteAllHashSetByPage();
    }

    /**
     * 删除对象
     * @param ltId
     * @return
     * @throws Exception
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public Integer deleteLoadTime(Long ltId) throws Exception {
        if(Objects.isNull(ltId)){
            throw new BusiException("请输入要删除的数据的ID");
        }
        Integer result = loadTimeDao.deleteLoadTime(ltId);
        loadTimeRedis.deleteAllHashSetByPage();
        loadTimeRedis.deleteLoadTime(ltId);
        return result;
    }

    /**
     * 批量删除对象
     * @param ids
     * @throws Exception
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void deleteLoadTimeList(List<Long> ids) throws Exception {
        if(CollectionUtils.isEmpty(ids)){
            return ;
        }
        for (Long id : ids) {
            loadTimeRedis.deleteLoadTime(id);
        }
        loadTimeDao.deleteLoadTimeList(ids);
        loadTimeRedis.deleteAllHashSetByPage();
    }

    /**
     * 查询记录总数
     * @param t
     * @param useCache
     * @return
     * @throws Exception
     */
    @Override
    public <T> Long getTotalLoadTime(T t,Boolean useCache) throws Exception {
        if(Objects.isNull(t)){
            throw new BusiException("查询参数不能为空");
        }
        Long count;
        if(useCache){
            count = loadTimeRedis.getTotalLoadTime(t);
            if(Objects.nonNull(count)){
                logger.info("===> fetch count = "+count+" entity from redis");
                return count;
            }
            logger.info("===> fetch count value from database");
            count = geTotalLoadTime(t);
            loadTimeRedis.setTotalLoadTime(t,count,IConst.MINUTE_15_EXPIRE);
        }else{
            count = geTotalLoadTime(t);
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
    private <T> Long geTotalLoadTime(T t) throws Exception {
        Long count;
        if(t instanceof LoadTime){
            count = loadTimeDao.getLoadTimeCount((LoadTime) t);
        }else if(t instanceof QueryExample){
            count = loadTimeDao.getLoadTimeCountExample((QueryExample) t);
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
    public <T> List<LoadTime> findLoadTimeList(T t, Boolean useCache) throws Exception {
        List<LoadTime> list;
        if(useCache){
            JSONObject redisResult = loadTimeRedis.getLoadTimeList(t);
            if(Objects.isNull(redisResult)){
                logger.info("===> fetch page list from database");
                list = findLoadTimeList(t);
                loadTimeRedis.setLoadTimeList(t,list,IConst.MINUTE_15_EXPIRE);
                return list;
            }
            List<Long> ids = JSON.parseArray(redisResult.getString("ids"), Long.class);
            List<LoadTime> loadTimes = JSON.parseArray(redisResult.getString("loadTimes"), LoadTime.class);
            //如果没有值过期,直接返回值,如果有值过期，那么需要重新获取
            if(!loadTimes.contains(null)){
                logger.info("===> fetch page list from redis");
                loadTimeRedis.setLoadTimeList(t,loadTimes,IConst.MINUTE_15_EXPIRE);
                return loadTimes;
            }
            //把已经过期的ID拿出来再一次性的去数据库里面获取出来并转成一个Map
            Map<Long, LoadTime> notNullMap = loadTimes.stream().filter(e -> Objects.nonNull(e)).collect(Collectors.toMap(e -> e.getLtId(), e -> e));
            List<Long> nullIds = ids.stream().filter(e->!notNullMap.containsKey(e)).collect(Collectors.toList());
            Map<Long,LoadTime> nullMap = findLoadTimeListByIds(nullIds,false).stream().collect(Collectors.toMap(e->e.getLtId(),e->e));
            if(nullIds.size() != nullMap.size()){
                logger.info("===> fetch page list from database");
                list = findLoadTimeList(t);
                loadTimeRedis.setLoadTimeList(t,list,IConst.MINUTE_15_EXPIRE);
                return list;
            }else{
                List<LoadTime> listForBack = new ArrayList<>() ;
                for(int i=0;i<ids.size();i++){
                    if(notNullMap.containsKey(ids.get(i))){
                        listForBack.add(notNullMap.get(ids.get(i)));
                    }else if(nullMap.containsKey(ids.get(i))){
                        listForBack.add(nullMap.get(ids.get(i)));
                        loadTimeRedis.setLoadTime(nullMap.get(ids.get(i)),IConst.MINUTE_15_EXPIRE);
                    }else{
                        logger.info("===> fetch page list from database");
                        list = findLoadTimeList(t);
                        loadTimeRedis.setLoadTimeList(t,list,IConst.MINUTE_15_EXPIRE);
                        return list;
                    }
                }
                return listForBack;
            }
        }else{
            logger.info("===> fetch page list from database");
            list = findLoadTimeList(t);
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
    private <T> List<LoadTime> findLoadTimeList(T t) throws Exception {
        List<LoadTime> list;
        if(t instanceof LoadTime){
            list = loadTimeDao.getLoadTimeList((LoadTime) t);
        }else if(t instanceof QueryExample){
            list = loadTimeDao.getLoadTimeListExample((QueryExample) t);
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
    public List<LoadTime> findLoadTimeListByIds(List<Long> list,Boolean useCache) throws Exception {
        if(CollectionUtils.isEmpty(list)){
            return Collections.EMPTY_LIST;
        }
        List<LoadTime> resList;
        if(useCache){
            resList = loadTimeRedis.getLoadTimeListByIds(list).stream().filter(e -> Objects.nonNull(e)).collect(Collectors.toList());
            List<Long> notNullIds = resList.stream().map(e->e.getLtId()).collect(Collectors.toList());
            List<Long> nullIds = new ArrayList<>(CollectionUtil.aSubtractB(list,notNullIds));
            if(CollectionUtils.isEmpty(nullIds)){
                return resList;
            }else{
                List<LoadTime> nullObjList = loadTimeDao.findLoadTimeListByIds(nullIds);
                for(LoadTime e : nullObjList){
                    loadTimeRedis.setLoadTime(e,IConst.MINUTE_15_EXPIRE);
                }
                resList.addAll(nullObjList);
                return resList;
            }
        }else{
            resList = loadTimeDao.findLoadTimeListByIds(list);
            return resList;
        }
    }

    /**
     * 分页查询列表
     * @param loadTime
     * @param useCache
     * @return
     * @throws Exception
     */
    @Override
    public JSONObject getPageLoadTime(LoadTime loadTime,Boolean useCache) throws Exception{
        if(Objects.isNull(loadTime)){
            throw new BusiException("查询参数不能为空");
        }
        if(Objects.isNull(loadTime.getPage()) || Objects.isNull(loadTime.getPageSize()) || IConst.PAGE_NO_USE.equals(loadTime.getPage())){
            throw new BusiException("分页请求入参异常");
        }
        JSONObject resp = new JSONObject();
        loadTime.setTotal(getTotalLoadTime(loadTime, useCache).intValue());
        resp.put("total",loadTime.getTotal());
        resp.put("totalPage",loadTime.getTotalPage());
        resp.put("list",findLoadTimeList(loadTime, useCache).stream().map(e-> new LoadTimeResp(e)).collect(Collectors.toList()));
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
    public JSONObject getPageLoadTimeExample(QueryExample queryExample,Boolean useCache) throws Exception {
        if(Objects.isNull(queryExample)){
            throw new BusiException("查询参数不能为空");
        }
        if(Objects.isNull(queryExample.getPage()) || Objects.isNull(queryExample.getPageSize()) || IConst.PAGE_NO_USE.equals(queryExample.getPage())){
            throw new BusiException("分页请求入参异常");
        }
        JSONObject resp = new JSONObject();
        queryExample.setTotal(getTotalLoadTime(queryExample, useCache).intValue());
        resp.put("totalPage",queryExample.getTotalPage());
        resp.put("total",queryExample.getTotal());
        resp.put("list",findLoadTimeList(queryExample,useCache).stream().map(e-> new LoadTimeResp(e)).collect(Collectors.toList()));
        return resp;
    }

    /**
     * 保存记录
     * @param loadTime
     * @throws Exception
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void saveLoadTime(LoadTime loadTime) throws Exception {
        if(Objects.isNull(loadTime)){
           return ;
        }
        if(Objects.isNull(loadTime.getLtId())){
            loadTime.setLtId(loadTimeRedis.getLoadTimeId());
            addLoadTime(loadTime);
        }else{
            updateLoadTime(loadTime,false);
        }
    }

    /**
     * 批量保存记录
     * @param loadTimeList
     * @throws Exception
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void saveLoadTimeList(List<LoadTime> loadTimeList) throws Exception {
        if(CollectionUtils.isEmpty(loadTimeList)){
            return ;
        }
        List<LoadTime> addList = loadTimeList.stream().filter(e -> Objects.isNull(e.getLtId())).collect(Collectors.toList());
        List<LoadTime> updateList = loadTimeList.stream().filter(e -> !Objects.isNull(e.getLtId())).collect(Collectors.toList());
        if(!CollectionUtils.isEmpty(addList)){
            addList = addList.stream().map(e->{
                e.setLtId(loadTimeRedis.getLoadTimeId());
                return e;
            }).collect(Collectors.toList());
            addLoadTimeList(addList);
        }
        if(!CollectionUtils.isEmpty(updateList)){
            updateLoadTimeList(updateList,false);
        }
    }
}

