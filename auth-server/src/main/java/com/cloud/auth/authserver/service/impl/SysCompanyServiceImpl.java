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
import com.cloud.auth.authserver.service.inft.ISysCompanyService;
import com.cloud.auth.authserver.dao.inft.ISysCompanyDao;
import com.cloud.auth.authserver.entity.SysCompany;
import com.cloud.auth.authserver.cache.inft.ISysCompanyRedis;
import com.cloud.auth.authserver.webentity.SysCompanyResp;

/**
 * ISysCompanyService service接口类
 * @author lijun
 */
@Service("sysCompanyService")
public class SysCompanyServiceImpl implements ISysCompanyService{

    private static final Logger logger = LoggerFactory.getLogger(SysCompanyServiceImpl.class);

    @Autowired
    private ISysCompanyDao sysCompanyDao;
    @Autowired
    private ISysCompanyRedis sysCompanyRedis;

    /**
     * 根据主键获取对象
     * @param scId
     * @return
     * @throws Exception
     */
    @Override
    public SysCompany loadSysCompanyByKey(Long scId) throws Exception {
        if(Objects.isNull(scId)){
            throw new BusiException("请输入要获取的数据的ID");
        }
        SysCompany sysCompany;
        sysCompany = sysCompanyRedis.getSysCompany(scId);
        if(Objects.nonNull(sysCompany)){
            logger.info("===> fetch scId = "+scId+" entity from redis");
            return sysCompany;
        }
        logger.info("===> fetch scId = "+scId+" entity from database");
        sysCompany = sysCompanyDao.loadSysCompanyByKey(scId);
        if(Objects.isNull(sysCompany)){
            throw new BusiException("没有符合条件的记录！") ;
        }
        sysCompanyRedis.setSysCompany(sysCompany,IConst.MINUTE_15_EXPIRE);
        return sysCompany;
    }

    /**
     * 普通查询获取单个结果
     * @param t
     * @param useCache
     * @return
     * @throws Exception
     */
    @Override
    public <T> SysCompany selectOneSysCompany(T t, Boolean useCache) throws Exception {
        List<SysCompany> list = findSysCompanyList(t, useCache);
        if(!CollectionUtils.isEmpty(list)){
            return list.get(0);
        }
        return null;
    }

    /**
     * 新增对象
     * @param sysCompany
     * @return
     * @throws Exception
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public Integer insertSysCompany(SysCompany sysCompany) throws Exception {
        if(Objects.isNull(sysCompany)){
            return 0;
        }
        if(Objects.isNull(sysCompany.getScId())){
            sysCompany.setScId(sysCompanyRedis.getSysCompanyId());
        }
        Integer result =  sysCompanyDao.insertSysCompany(sysCompany);
        sysCompanyRedis.deleteAllHashSetByPage();
        return result;
    }

    /**
     * 批量新增对象
     * @param sysCompanyList
     * @throws Exception
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void insertSysCompanyList(List<SysCompany> sysCompanyList) throws Exception {
        if(CollectionUtils.isEmpty(sysCompanyList)){
            return ;
        }
        for (SysCompany sysCompany : sysCompanyList) {
            if(Objects.isNull(sysCompany.getScId())){
                sysCompany.setScId(sysCompanyRedis.getSysCompanyId());
            }
        }
        sysCompanyDao.insertSysCompanyList(sysCompanyList);
        sysCompanyRedis.deleteAllHashSetByPage();
    }

    /**
     * 更新对象
     * @param sysCompany
     * @param isFullUpdate
     * @return
     * @throws Exception
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public Integer updateSysCompany(SysCompany sysCompany,Boolean isFullUpdate) throws Exception {
        if(Objects.isNull(sysCompany)){
            return 0;
        }
        if(Objects.isNull(sysCompany.getScId())){
            throw new BusiException("更新主键不能为空");
        }
        Integer result;
        if(isFullUpdate){
            result = sysCompanyDao.fullUpdateSysCompany(sysCompany);
        } else {
            result = sysCompanyDao.updateSysCompany(sysCompany);
        }
        sysCompanyRedis.deleteAllHashSetByPage();
        sysCompanyRedis.deleteSysCompany(sysCompany.getScId());
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
    public void updateSysCompanyList(List<SysCompany> list,Boolean isFullUpdate) throws Exception {
        if(CollectionUtils.isEmpty(list)){
            return ;
        }
        for (SysCompany sysCompany : list) {
            if(Objects.isNull(sysCompany.getScId())){
                throw new BusiException("更新主键不能为空");
            }
            sysCompanyRedis.deleteSysCompany(sysCompany.getScId());
        }
        if(isFullUpdate){
            sysCompanyDao.fullUpdateSysCompanyList(list);
        } else {
            sysCompanyDao.updateSysCompanyList(list);
        }
        sysCompanyRedis.deleteAllHashSetByPage();
    }

    /**
     * 删除对象
     * @param scId
     * @return
     * @throws Exception
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public Integer deleteSysCompanyByKey(Long scId) throws Exception {
        if(Objects.isNull(scId)){
            throw new BusiException("请输入要删除的数据的ID");
        }
        Integer result = sysCompanyDao.deleteSysCompanyByKey(scId);
        sysCompanyRedis.deleteAllHashSetByPage();
        sysCompanyRedis.deleteSysCompany(scId);
        return result;
    }

    /**
     * 批量删除对象
     * @param list
     * @throws Exception
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void deleteSysCompanyList(List<SysCompany> list) throws Exception {
        if(CollectionUtils.isEmpty(list)){
            return ;
        }
        for (SysCompany sysCompany : list) {
            if(Objects.isNull(sysCompany.getScId())){
                throw new BusiException("删除主键不能为空");
            }
            sysCompanyRedis.deleteSysCompany(sysCompany.getScId());
        }
        sysCompanyDao.deleteSysCompanyList(list);
        sysCompanyRedis.deleteAllHashSetByPage();
    }

    /**
     * 查询记录总数
     * @param t
     * @param useCache
     * @return
     * @throws Exception
     */
    @Override
    public <T> Long getTotalSysCompany(T t,Boolean useCache) throws Exception {
        if(Objects.isNull(t)){
            throw new BusiException("查询参数不能为空");
        }
        Long count;
        if(useCache){
            count = sysCompanyRedis.getTotalSysCompany(t);
            if(Objects.nonNull(count)){
                logger.info("===> fetch count = "+count+" entity from redis");
                return count;
            }
            logger.info("===> fetch count value from database");
            count = geTotalSysCompany(t);
            sysCompanyRedis.setTotalSysCompany(t,count,IConst.MINUTE_15_EXPIRE);
        }else{
            count = geTotalSysCompany(t);
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
    private <T> Long geTotalSysCompany(T t) throws Exception {
        Long count;
        if(t instanceof SysCompany){
            count = sysCompanyDao.getSysCompanyCount((SysCompany) t);
        }else if(t instanceof QueryExample){
            count = sysCompanyDao.getSysCompanyCountExample((QueryExample) t);
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
    public <T> List<SysCompany> findSysCompanyList(T t, Boolean useCache) throws Exception {
        List<SysCompany> list;
        if(useCache){
            JSONObject redisResult = sysCompanyRedis.getSysCompanyList(t);
            if(Objects.isNull(redisResult)){
                logger.info("===> fetch page list from database");
                list = findSysCompanyList(t);
                sysCompanyRedis.setSysCompanyList(t,list,IConst.MINUTE_15_EXPIRE);
                return list;
            }
            List<Long> ids = JSON.parseArray(redisResult.getString("ids"), Long.class);
            List<SysCompany> sysCompanys = JSON.parseArray(redisResult.getString("sysCompanys"), SysCompany.class);
            //如果没有值过期,直接返回值,如果有值过期，那么需要重新获取
            if(!sysCompanys.contains(null)){
                logger.info("===> fetch page list from redis");
                sysCompanyRedis.setSysCompanyList(t,sysCompanys,IConst.MINUTE_15_EXPIRE);
                return sysCompanys;
            }
            //把已经过期的ID拿出来再一次性的去数据库里面获取出来并转成一个Map
            Map<Long, SysCompany> notNullMap = sysCompanys.stream().filter(e -> Objects.nonNull(e)).collect(Collectors.toMap(e -> e.getScId(), e -> e));
            List<Long> nullIds = ids.stream().filter(e->!notNullMap.containsKey(e)).collect(Collectors.toList());
            Map<Long,SysCompany> nullMap = findSysCompanyListByIds(nullIds,false).stream().collect(Collectors.toMap(e->e.getScId(),e->e));
            if(nullIds.size() != nullMap.size()){
                logger.info("===> fetch page list from database");
                list = findSysCompanyList(t);
                sysCompanyRedis.setSysCompanyList(t,list,IConst.MINUTE_15_EXPIRE);
                return list;
            }else{
                List<SysCompany> listForBack = new ArrayList<>() ;
                for(int i=0;i<ids.size();i++){
                    if(notNullMap.containsKey(ids.get(i))){
                        listForBack.add(notNullMap.get(ids.get(i)));
                    }else if(nullMap.containsKey(ids.get(i))){
                        listForBack.add(nullMap.get(ids.get(i)));
                        sysCompanyRedis.setSysCompany(nullMap.get(ids.get(i)),IConst.MINUTE_15_EXPIRE);
                    }else{
                        logger.info("===> fetch page list from database");
                        list = findSysCompanyList(t);
                        sysCompanyRedis.setSysCompanyList(t,list,IConst.MINUTE_15_EXPIRE);
                        return list;
                    }
                }
                return listForBack;
            }
        }else{
            logger.info("===> fetch page list from database");
            list = findSysCompanyList(t);
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
    private <T> List<SysCompany> findSysCompanyList(T t) throws Exception {
        List<SysCompany> list;
        if(t instanceof SysCompany){
            list = sysCompanyDao.getSysCompanyList((SysCompany) t);
        }else if(t instanceof QueryExample){
            list = sysCompanyDao.getSysCompanyListExample((QueryExample) t);
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
    public List<SysCompany> findSysCompanyListByIds(List<Long> list,Boolean useCache) throws Exception {
        if(CollectionUtils.isEmpty(list)){
            return Collections.EMPTY_LIST;
        }
        List<SysCompany> resList;
        if(useCache){
            resList = sysCompanyRedis.getSysCompanyListByIds(list);
            Map<Long, SysCompany> sysCompanyMap = resList.stream().collect(Collectors.toMap(e -> e.getScId(), e -> e));
            List<Long> nullList = list.stream().filter(e -> !sysCompanyMap.containsKey(e)).collect(Collectors.toList());
            if(CollectionUtils.isEmpty(nullList)){
                return resList;
            }else{
                List<SysCompany> nullObjList = sysCompanyDao.findSysCompanyListByIds(nullList);
                for(SysCompany e : nullObjList){
                    sysCompanyRedis.setSysCompany(e,IConst.MINUTE_15_EXPIRE);
                }
                resList.addAll(nullObjList);
                return resList;
            }
        }else{
            resList = sysCompanyDao.findSysCompanyListByIds(list);
            return resList;
        }
    }

    /**
     * 分页查询列表
     * @param sysCompany
     * @param useCache
     * @return
     * @throws Exception
     */
    @Override
    public JSONObject getPageSysCompany(SysCompany sysCompany,Boolean useCache) throws Exception{
        if(Objects.isNull(sysCompany)){
            throw new BusiException("查询参数不能为空");
        }
        if(Objects.isNull(sysCompany.getPage()) || Objects.isNull(sysCompany.getPageSize()) || IConst.PAGE_NO_USE.equals(sysCompany.getPage())){
            throw new BusiException("分页请求入参异常");
        }
        JSONObject resp = new JSONObject();
        sysCompany.setTotal(getTotalSysCompany(sysCompany, useCache).intValue());
        resp.put("total",sysCompany.getTotal());
        resp.put("totalPage",sysCompany.getTotalPage());
        resp.put("list",findSysCompanyList(sysCompany, useCache).stream().map(e-> new SysCompanyResp(e)).collect(Collectors.toList()));
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
    public JSONObject getPageSysCompanyExample(QueryExample queryExample,Boolean useCache) throws Exception {
        if(Objects.isNull(queryExample)){
            throw new BusiException("查询参数不能为空");
        }
        if(Objects.isNull(queryExample.getPage()) || Objects.isNull(queryExample.getPageSize()) || IConst.PAGE_NO_USE.equals(queryExample.getPage())){
            throw new BusiException("分页请求入参异常");
        }
        JSONObject resp = new JSONObject();
        queryExample.setTotal(getTotalSysCompany(queryExample, useCache).intValue());
        resp.put("totalPage",queryExample.getTotalPage());
        resp.put("total",queryExample.getTotal());
        resp.put("list",findSysCompanyList(queryExample,useCache).stream().map(e-> new SysCompanyResp(e)).collect(Collectors.toList()));
        return resp;
    }

    /**
     * 保存记录
     * @param sysCompany
     * @param isFullUpdate
     * @throws Exception
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void saveSysCompany(SysCompany sysCompany,Boolean isFullUpdate) throws Exception {
        if(Objects.isNull(sysCompany)){
           return ;
        }
        if(Objects.isNull(sysCompany.getScId())){
            sysCompany.setScId(sysCompanyRedis.getSysCompanyId());
            insertSysCompany(sysCompany);
        }else{
            updateSysCompany(sysCompany,isFullUpdate);
        }
    }

    /**
     * 批量保存记录
     * @param sysCompanyList
     * @param isFullUpdate
     * @throws Exception
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void saveSysCompanyList(List<SysCompany> sysCompanyList,Boolean isFullUpdate) throws Exception {
        if(CollectionUtils.isEmpty(sysCompanyList)){
            return ;
        }
        List<SysCompany> insertList = sysCompanyList.stream().filter(e -> Objects.isNull(e.getScId())).collect(Collectors.toList());
        List<SysCompany> updateList = sysCompanyList.stream().filter(e -> !Objects.isNull(e.getScId())).collect(Collectors.toList());
        if(!CollectionUtils.isEmpty(insertList)){
            insertList = insertList.stream().map(e->{
                e.setScId(sysCompanyRedis.getSysCompanyId());
                return e;
            }).collect(Collectors.toList());
            insertSysCompanyList(insertList);
        }
        if(!CollectionUtils.isEmpty(updateList)){
            updateSysCompanyList(updateList,isFullUpdate);
        }
    }
}

