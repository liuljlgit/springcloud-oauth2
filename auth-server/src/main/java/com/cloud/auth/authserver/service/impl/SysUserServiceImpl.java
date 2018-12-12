package com.cloud.auth.authserver.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.cloud.auth.authserver.cache.inft.ISysUserRedis;
import com.cloud.auth.authserver.dao.inft.ISysUserDao;
import com.cloud.auth.authserver.entity.SysUser;
import com.cloud.auth.authserver.service.inft.ISysUserService;
import com.cloud.common.complexquery.QueryExample;
import com.cloud.common.constant.IConst;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

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
            throw new Exception("请输入要获取的数据的ID");
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
            throw new Exception("没有符合条件的记录！") ;
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
    public Integer insertSysUser(SysUser sysUser) throws Exception {
        if(Objects.isNull(sysUser)){
            return 0;
        }
        if(Objects.isNull(sysUser.getSuId())){
            sysUser.setSuId(sysUserRedis.getSysUserId());
        }
        Integer result =  sysUserDao.insertSysUser(sysUser);
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
    public void insertSysUserList(List<SysUser> sysUserList) throws Exception {
        if(CollectionUtils.isEmpty(sysUserList)){
            return ;
        }
        for (SysUser sysUser : sysUserList) {
            if(Objects.isNull(sysUser.getSuId())){
                sysUser.setSuId(sysUserRedis.getSysUserId());
            }
        }
        sysUserDao.insertSysUserList(sysUserList);
        sysUserRedis.deleteAllHashSetByPage();
    }

    /**
     * 更新对象
     * @param sysUser
     * @return
     * @throws Exception
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public Integer updateSysUser(SysUser sysUser) throws Exception {
        if(Objects.isNull(sysUser)){
            return 0;
        }
        if(Objects.isNull(sysUser.getSuId())){
            throw new Exception("更新主键不能为空");
        }
        Integer result = sysUserDao.updateSysUser(sysUser);
        sysUserRedis.deleteAllHashSetByPage();
        sysUserRedis.deleteSysUser(sysUser.getSuId());
        return result;
    }

    /**
     * 批量更新
     * @param list
     * @throws Exception
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void updateSysUserList(List<SysUser> list) throws Exception {
        if(CollectionUtils.isEmpty(list)){
            return ;
        }
        for (SysUser sysUser : list) {
            if(Objects.isNull(sysUser.getSuId())){
                throw new Exception("更新主键不能为空");
            }
            sysUserRedis.deleteSysUser(sysUser.getSuId());
        }
        sysUserDao.updateSysUserList(list);
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
    public Integer deleteSysUserByKey(Long suId) throws Exception {
        if(Objects.isNull(suId)){
            throw new Exception("请输入要删除的数据的ID");
        }
        Integer result = sysUserDao.deleteSysUserByKey(suId);
        sysUserRedis.deleteAllHashSetByPage();
        sysUserRedis.deleteSysUser(suId);
        return result;
    }

    /**
     * 批量删除对象
     * @param list
     * @throws Exception
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void deleteSysUserList(List<SysUser> list) throws Exception {
        if(CollectionUtils.isEmpty(list)){
            return ;
        }
        for (SysUser sysUser : list) {
            if(Objects.isNull(sysUser.getSuId())){
                throw new Exception("删除主键不能为空");
            }
            sysUserRedis.deleteSysUser(sysUser.getSuId());
        }
        sysUserDao.deleteSysUserList(list);
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
            throw new Exception("查询参数不能为空");
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
            throw new Exception("选择类型不正确");
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
            //把已经过期的值重新获取到redis缓存中。如果数据库由于某种原因丢失了数据,那么删除缓存,并且重新从数据库中获取值,注意list中的列表顺序不能改变。
            //只有丢失率小于1/3 && 获取数量小于1000条的时候选择重新一条条的去获取记录,否则从数据库中直接查询
            Map<Long, SysUser> notNullList = sysUsers.stream().filter(e -> Objects.nonNull(e)).collect(Collectors.toMap(e -> e.getSuId(), e -> e));
            Integer notNullCount = notNullList.size();
            Integer nullCount = ids.size()-notNullCount;
            if(notNullCount >= ids.size()*(2/3) && nullCount<1000){
                logger.info("===> fetch page list from redis");
                List<SysUser> listForBack = new ArrayList<>() ;
                for(Long suId : ids){
                    if(notNullList.containsKey(suId)){
                        listForBack.add(notNullList.get(suId));
                    }else{
                        SysUser item = loadSysUserByKey(suId);
                        //如果我们在获取记录的期间,这个item被并发删除了,那么我们需重新执行一遍查询。
                        if(Objects.isNull(item)){
                            logger.info("===> appear null item,so fetch page list again from database");
                            list = findSysUserList(t);
                            sysUserRedis.setSysUserList(t,list,IConst.MINUTE_15_EXPIRE);
                            return list;
                        }else{
                            listForBack.add(item);
                        }
                    }
                }
                return listForBack;
            }else{
                logger.info("===> fetch page list from database");
                list = findSysUserList(t);
                sysUserRedis.setSysUserList(t,list,IConst.MINUTE_15_EXPIRE);
                return list;
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
            throw new Exception("选择类型不正确");
        }
        return list;
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
            throw new Exception("查询参数不能为空");
        }
        if(Objects.isNull(sysUser.getPage()) || Objects.isNull(sysUser.getPageSize()) || IConst.PAGE_NO_USE.equals(sysUser.getPage())){
            throw new Exception("分页请求入参异常");
        }
        JSONObject resp = new JSONObject();
        sysUser.setTotal(getTotalSysUser(sysUser, useCache).intValue());
        resp.put("total",sysUser.getTotal());
        resp.put("totalPage",sysUser.getTotalPage());
        resp.put("list",findSysUserList(sysUser, useCache));
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
            throw new Exception("查询参数不能为空");
        }
        if(Objects.isNull(queryExample.getPage()) || Objects.isNull(queryExample.getPageSize()) || IConst.PAGE_NO_USE.equals(queryExample.getPage())){
            throw new Exception("分页请求入参异常");
        }
        JSONObject resp = new JSONObject();
        queryExample.setTotal(getTotalSysUser(queryExample, useCache).intValue());
        resp.put("totalPage",queryExample.getTotalPage());
        resp.put("total",queryExample.getTotal());
        resp.put("list",findSysUserList(queryExample,useCache));
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
            insertSysUser(sysUser);
        }else{
            updateSysUser(sysUser);
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
        List<SysUser> insertList = sysUserList.stream().filter(e -> Objects.isNull(e.getSuId())).collect(Collectors.toList());
        List<SysUser> updateList = sysUserList.stream().filter(e -> !Objects.isNull(e.getSuId())).collect(Collectors.toList());
        if(!CollectionUtils.isEmpty(insertList)){
            insertList = insertList.stream().map(e->{
                e.setSuId(sysUserRedis.getSysUserId());
                return e;
            }).collect(Collectors.toList());
            insertSysUserList(insertList);
        }
        if(!CollectionUtils.isEmpty(updateList)){
            updateSysUserList(updateList);
        }
    }
}

