package com.cloud.auth.authserver.cache.inft;

import java.util.*;
import com.cloud.common.complexquery.QueryExample;
import com.alibaba.fastjson.JSONObject;
import com.cloud.auth.authserver.entity.SysRole;

/**
 * 缓存接口类 ISysRoleRedis
 * @author lijun
 */
public interface ISysRoleRedis {

    /**
     * 获取SysRole的ID
     * @return
     */
    Long getSysRoleId();

    /**
     * 在Spring容器初始化的时候，初始化该实体ID的最大值。
     * @return
     */
    void initMaxSysRoleId();

    /**
     * 得到【ClassName:Id】值
     * @return
     * @throws Exception
     */
    String getSysRoleKey(final Long srId);

    /**
     * 把值存储到redis中
     * 1.Key-Value对象
     * 2.把key存储到一个set中,方便删除操作
     */
    String setSysRole(SysRole sysRole, long expire);

    /**
     * 从缓存中得到值
     * @param srId
     * @return
     */
    SysRole getSysRole(final Long srId);

    /**
     * 存储分页数量
     * @param t
     * @param count
     * @param expire
     * @return
     */
    <T> String setTotalSysRole(T t, Long count, long expire) throws Exception;

    /**
     * 获取分页数量
     * @param t
     * @return
     */
    <T> Long getTotalSysRole(T t) throws Exception;

    /**
     * 存储分页列表
     * @param t
     * @param list
     * @param expire
     * @return
     */
    <T> String setSysRoleList(T t, List<SysRole> list, long expire) throws Exception ;

    /**
     * 获取分页列表
     * @param t
     * @return
     */
    <T> JSONObject getSysRoleList(T t) throws Exception ;

    /**
     * 根据id列表获取列表
     * @param list
     * @return
     * @throws Exception
     */
    List<SysRole> getSysRoleListByIds(List<Long> list) throws Exception;

    /**
     * 删除所有redis的值
     * 注意:慎用！！！！
     */
    void clearAllSysRole();

    /**
     * 删除所有分页查询结果
     * 注意:慎用！！！！
     */
    void deleteAllHashSetByPage();

    /**
     * 根据主键删除 simulElecDistri对象
     * @return
     */
    void deleteSysRole(Long srId) ;
}

