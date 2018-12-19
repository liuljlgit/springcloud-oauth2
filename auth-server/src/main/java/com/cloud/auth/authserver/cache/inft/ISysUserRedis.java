package com.cloud.auth.authserver.cache.inft;

import java.util.*;
import com.cloud.common.complexquery.QueryExample;
import com.alibaba.fastjson.JSONObject;
import com.cloud.auth.authserver.entity.SysUser;

/**
 * 缓存接口类 ISysUserRedis
 * @author lijun
 */
public interface ISysUserRedis {

    /**
     * 获取SysUser的ID
     * @return
     */
    Long getSysUserId();

    /**
     * 在Spring容器初始化的时候，初始化该实体ID的最大值。
     * @return
     */
    void initMaxSysUserId();

    /**
     * 得到【ClassName:Id】值
     * @return
     * @throws Exception
     */
    String getSysUserKey(final Long suId);

    /**
     * 把值存储到redis中
     * 1.Key-Value对象
     * 2.把key存储到一个set中,方便删除操作
     */
    String setSysUser(SysUser sysUser, long expire);

    /**
     * 从缓存中得到值
     * @param suId
     * @return
     */
    SysUser getSysUser(final Long suId);

    /**
     * 存储分页数量
     * @param t
     * @param count
     * @param expire
     * @return
     */
    <T> String setTotalSysUser(T t, Long count, long expire) throws Exception;

    /**
     * 获取分页数量
     * @param t
     * @return
     */
    <T> Long getTotalSysUser(T t) throws Exception;

    /**
     * 存储分页列表
     * @param t
     * @param list
     * @param expire
     * @return
     */
    <T> String setSysUserList(T t, List<SysUser> list, long expire) throws Exception ;

    /**
     * 获取分页列表
     * @param t
     * @return
     */
    <T> JSONObject getSysUserList(T t) throws Exception ;

    /**
     * 根据id列表获取列表
     * @param list
     * @return
     * @throws Exception
     */
    List<SysUser> getSysUserListByIds(List<Long> list) throws Exception;

    /**
     * 删除所有redis的值
     * 注意:慎用！！！！
     */
    void clearAllSysUser();

    /**
     * 删除所有分页查询结果
     * 注意:慎用！！！！
     */
    void deleteAllHashSetByPage();

    /**
     * 根据主键删除 simulElecDistri对象
     * @return
     */
    void deleteSysUser(Long suId) ;
}

