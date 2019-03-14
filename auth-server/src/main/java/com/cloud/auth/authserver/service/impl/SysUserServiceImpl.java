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
import com.cloud.auth.authserver.service.inft.ISysUserService;
import com.cloud.auth.authserver.dao.ISysUserDao;
import com.cloud.auth.authserver.entity.SysUser;
import com.cloud.auth.authserver.cache.inft.ISysUserRedis;
import com.cloud.auth.authserver.webentity.SysUserResp;

/**
 * ISysUserService service接口类
 * @author lijun
 */
@Service("sysUserService")
public class SysUserServiceImpl implements ISysUserService{

    private static final Logger logger = LoggerFactory.getLogger(SysUserServiceImpl.class);

    @Autowired
    private ISysUserDao sysUserDao;
    @Autowired
    private ISysUserRedis sysUserRedis;

    /**
     * 根据主键获取对象
     * @param suId
     * @return
     * @throws Exception
     */
    @Override
    public SysUser loadSysUserByKey(Long suId) throws Exception {
        if(Objects.isNull(suId)){
            throw new BusiException("请输入要获取的数据的ID");
        }
        SysUser sysUser;
        sysUser = sysUserRedis.getSysUser(suId);
        if(Objects.nonNull(sysUser)){
            logger.info("===> fetch suId = "+suId+" entity from redis");
            return sysUser;
        }
        logger.info("===> fetch suId = "+suId+" entity from database");
        sysUser = sysUserDao.loadSysUserByKey(suId);
        if(Objects.isNull(sysUser)){
            throw new BusiException("没有符合条件的记录！") ;
        }
        sysUserRedis.setSysUser(sysUser,IConst.MINUTE_15_EXPIRE);
        return sysUser;
    }

    /**
     * 普通查询获取单个结果
     * @param t
     * @param useCache
     * @return
     * @throws Exception
     */
    @Override
    public <T> SysUser selectOneSysUser(T t, Boolean useCache) throws Exception {
        List<SysUser> list = findSysUserList(t, useCache);
        if(!CollectionUtils.isEmpty(list)){
            return list.get(0);
        }
        return null;
    }

    /**
     * 新增对象
     * @param sysUser
     * @return
     * @throws Exception
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public Integer addSysUser(SysUser sysUser) throws Exception {
        if(Objects.isNull(sysUser)){
            return 0;
        }
        if(Objects.isNull(sysUser.getSuId())){
            sysUser.setSuId(sysUserRedis.getSysUserId());
        }
        Integer result =  sysUserDao.addSysUser(sysUser);
        sysUserRedis.deleteAllHashSetByPage();
        return result;
    }

    /**
     * 批量新增对象
     * @param sysUserList
     * @throws Exception
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void addSysUserList(List<SysUser> sysUserList) throws Exception {
        if(CollectionUtils.isEmpty(sysUserList)){
            return ;
        }
        for (SysUser sysUser : sysUserList) {
            if(Objects.isNull(sysUser.getSuId())){
                sysUser.setSuId(sysUserRedis.getSysUserId());
            }
        }
        sysUserDao.addSysUserList(sysUserList);
        sysUserRedis.deleteAllHashSetByPage();
    }

    /**
     * 更新对象
     * @param sysUser
     * @param isFullUpdate
     * @return
     * @throws Exception
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public Integer updateSysUser(SysUser sysUser,Boolean isFullUpdate) throws Exception {
        if(Objects.isNull(sysUser)){
            return 0;
        }
        if(Objects.isNull(sysUser.getSuId())){
            throw new BusiException("更新主键不能为空");
        }
        Integer result;
        if(isFullUpdate){
            result = sysUserDao.fullUpdateSysUser(sysUser);
        } else {
            result = sysUserDao.updateSysUser(sysUser);
        }
        sysUserRedis.deleteAllHashSetByPage();
        sysUserRedis.deleteSysUser(sysUser.getSuId());
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
    public void updateSysUserList(List<SysUser> list,Boolean isFullUpdate) throws Exception {
        if(CollectionUtils.isEmpty(list)){
            return ;
        }
        for (SysUser sysUser : list) {
            if(Objects.isNull(sysUser.getSuId())){
                throw new BusiException("更新主键不能为空");
            }
            sysUserRedis.deleteSysUser(sysUser.getSuId());
        }
        if(isFullUpdate){
            sysUserDao.fullUpdateSysUserList(list);
        } else {
            sysUserDao.updateSysUserList(list);
        }
        sysUserRedis.deleteAllHashSetByPage();
    }

    /**
     * 删除对象
     * @param suId
     * @return
     * @throws Exception
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public Integer deleteSysUser(Long suId) throws Exception {
        if(Objects.isNull(suId)){
            throw new BusiException("请输入要删除的数据的ID");
        }
        Integer result = sysUserDao.deleteSysUser(suId);
        sysUserRedis.deleteAllHashSetByPage();
        sysUserRedis.deleteSysUser(suId);
        return result;
    }

    /**
     * 批量删除对象
     * @param ids
     * @throws Exception
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void deleteSysUserList(List<Long> ids) throws Exception {
        if(CollectionUtils.isEmpty(ids)){
            return ;
        }
        for (Long id : ids) {
            sysUserRedis.deleteSysUser(id);
        }
        sysUserDao.deleteSysUserList(ids);
        sysUserRedis.deleteAllHashSetByPage();
    }

    /**
     * 查询记录总数
     * @param t
     * @param useCache
     * @return
     * @throws Exception
     */
    @Override
    public <T> Long getTotalSysUser(T t,Boolean useCache) throws Exception {
        if(Objects.isNull(t)){
            throw new BusiException("查询参数不能为空");
        }
        Long count;
        if(useCache){
            count = sysUserRedis.getTotalSysUser(t);
            if(Objects.nonNull(count)){
                logger.info("===> fetch count = "+count+" entity from redis");
                return count;
            }
            logger.info("===> fetch count value from database");
            count = geTotalSysUser(t);
            sysUserRedis.setTotalSysUser(t,count,IConst.MINUTE_15_EXPIRE);
        }else{
            count = geTotalSysUser(t);
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
    private <T> Long geTotalSysUser(T t) throws Exception {
        Long count;
        if(t instanceof SysUser){
            count = sysUserDao.getSysUserCount((SysUser) t);
        }else if(t instanceof QueryExample){
            count = sysUserDao.getSysUserCountExample((QueryExample) t);
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
    public <T> List<SysUser> findSysUserList(T t, Boolean useCache) throws Exception {
        List<SysUser> list;
        if(useCache){
            JSONObject redisResult = sysUserRedis.getSysUserList(t);
            if(Objects.isNull(redisResult)){
                logger.info("===> fetch page list from database");
                list = findSysUserList(t);
                sysUserRedis.setSysUserList(t,list,IConst.MINUTE_15_EXPIRE);
                return list;
            }
            List<Long> ids = JSON.parseArray(redisResult.getString("ids"), Long.class);
            List<SysUser> sysUsers = JSON.parseArray(redisResult.getString("sysUsers"), SysUser.class);
            //如果没有值过期,直接返回值,如果有值过期，那么需要重新获取
            if(!sysUsers.contains(null)){
                logger.info("===> fetch page list from redis");
                sysUserRedis.setSysUserList(t,sysUsers,IConst.MINUTE_15_EXPIRE);
                return sysUsers;
            }
            //把已经过期的ID拿出来再一次性的去数据库里面获取出来并转成一个Map
            Map<Long, SysUser> notNullMap = sysUsers.stream().filter(e -> Objects.nonNull(e)).collect(Collectors.toMap(e -> e.getSuId(), e -> e));
            List<Long> nullIds = ids.stream().filter(e->!notNullMap.containsKey(e)).collect(Collectors.toList());
            Map<Long,SysUser> nullMap = findSysUserListByIds(nullIds,false).stream().collect(Collectors.toMap(e->e.getSuId(),e->e));
            if(nullIds.size() != nullMap.size()){
                logger.info("===> fetch page list from database");
                list = findSysUserList(t);
                sysUserRedis.setSysUserList(t,list,IConst.MINUTE_15_EXPIRE);
                return list;
            }else{
                List<SysUser> listForBack = new ArrayList<>() ;
                for(int i=0;i<ids.size();i++){
                    if(notNullMap.containsKey(ids.get(i))){
                        listForBack.add(notNullMap.get(ids.get(i)));
                    }else if(nullMap.containsKey(ids.get(i))){
                        listForBack.add(nullMap.get(ids.get(i)));
                        sysUserRedis.setSysUser(nullMap.get(ids.get(i)),IConst.MINUTE_15_EXPIRE);
                    }else{
                        logger.info("===> fetch page list from database");
                        list = findSysUserList(t);
                        sysUserRedis.setSysUserList(t,list,IConst.MINUTE_15_EXPIRE);
                        return list;
                    }
                }
                return listForBack;
            }
        }else{
            logger.info("===> fetch page list from database");
            list = findSysUserList(t);
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
    private <T> List<SysUser> findSysUserList(T t) throws Exception {
        List<SysUser> list;
        if(t instanceof SysUser){
            list = sysUserDao.getSysUserList((SysUser) t);
        }else if(t instanceof QueryExample){
            list = sysUserDao.getSysUserListExample((QueryExample) t);
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
    public List<SysUser> findSysUserListByIds(List<Long> list,Boolean useCache) throws Exception {
        if(CollectionUtils.isEmpty(list)){
            return Collections.EMPTY_LIST;
        }
        List<SysUser> resList;
        if(useCache){
            resList = sysUserRedis.getSysUserListByIds(list).stream().filter(e -> Objects.nonNull(e)).collect(Collectors.toList());
            List<Long> notNullIds = resList.stream().map(e->e.getSuId()).collect(Collectors.toList());
            List<Long> nullIds = new ArrayList<>(CollectionUtil.aSubtractB(list,notNullIds));
            if(CollectionUtils.isEmpty(nullIds)){
                return resList;
            }else{
                List<SysUser> nullObjList = sysUserDao.findSysUserListByIds(nullIds);
                for(SysUser e : nullObjList){
                    sysUserRedis.setSysUser(e,IConst.MINUTE_15_EXPIRE);
                }
                resList.addAll(nullObjList);
                return resList;
            }
        }else{
            resList = sysUserDao.findSysUserListByIds(list);
            return resList;
        }
    }

    /**
     * 分页查询列表
     * @param sysUser
     * @param useCache
     * @return
     * @throws Exception
     */
    @Override
    public JSONObject getPageSysUser(SysUser sysUser,Boolean useCache) throws Exception{
        if(Objects.isNull(sysUser)){
            throw new BusiException("查询参数不能为空");
        }
        if(Objects.isNull(sysUser.getPage()) || Objects.isNull(sysUser.getPageSize()) || IConst.PAGE_NO_USE.equals(sysUser.getPage())){
            throw new BusiException("分页请求入参异常");
        }
        JSONObject resp = new JSONObject();
        sysUser.setTotal(getTotalSysUser(sysUser, useCache).intValue());
        resp.put("total",sysUser.getTotal());
        resp.put("totalPage",sysUser.getTotalPage());
        resp.put("list",findSysUserList(sysUser, useCache).stream().map(e-> new SysUserResp(e)).collect(Collectors.toList()));
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
    public JSONObject getPageSysUserExample(QueryExample queryExample,Boolean useCache) throws Exception {
        if(Objects.isNull(queryExample)){
            throw new BusiException("查询参数不能为空");
        }
        if(Objects.isNull(queryExample.getPage()) || Objects.isNull(queryExample.getPageSize()) || IConst.PAGE_NO_USE.equals(queryExample.getPage())){
            throw new BusiException("分页请求入参异常");
        }
        JSONObject resp = new JSONObject();
        queryExample.setTotal(getTotalSysUser(queryExample, useCache).intValue());
        resp.put("totalPage",queryExample.getTotalPage());
        resp.put("total",queryExample.getTotal());
        resp.put("list",findSysUserList(queryExample,useCache).stream().map(e-> new SysUserResp(e)).collect(Collectors.toList()));
        return resp;
    }

    /**
     * 保存记录
     * @param sysUser
     * @throws Exception
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void saveSysUser(SysUser sysUser) throws Exception {
        if(Objects.isNull(sysUser)){
           return ;
        }
        if(Objects.isNull(sysUser.getSuId())){
            sysUser.setSuId(sysUserRedis.getSysUserId());
            addSysUser(sysUser);
        }else{
            updateSysUser(sysUser,false);
        }
    }

    /**
     * 批量保存记录
     * @param sysUserList
     * @throws Exception
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void saveSysUserList(List<SysUser> sysUserList) throws Exception {
        if(CollectionUtils.isEmpty(sysUserList)){
            return ;
        }
        List<SysUser> addList = sysUserList.stream().filter(e -> Objects.isNull(e.getSuId())).collect(Collectors.toList());
        List<SysUser> updateList = sysUserList.stream().filter(e -> !Objects.isNull(e.getSuId())).collect(Collectors.toList());
        if(!CollectionUtils.isEmpty(addList)){
            addList = addList.stream().map(e->{
                e.setSuId(sysUserRedis.getSysUserId());
                return e;
            }).collect(Collectors.toList());
            addSysUserList(addList);
        }
        if(!CollectionUtils.isEmpty(updateList)){
            updateSysUserList(updateList,false);
        }
    }
}

