package com.cloud.auth.authserver.cache.inft;

import java.util.*;
import com.cloud.common.complexquery.QueryExample;
import com.alibaba.fastjson.JSONObject;
import com.cloud.auth.authserver.entity.SysPermission;

/**
 * 缓存接口类 ISysPermissionRedis
 * @author lijun
 */
public interface ISysPermissionRedis {

    /**
     * 获取SysPermission的ID
     * @return
     */
    Long getSysPermissionId();

    /**
     * 在Spring容器初始化的时候，初始化该实体ID的最大值。
     * @return
     */
    void initMaxSysPermissionId();

    /**
     * 得到【ClassName:Id】值
     * @return
     * @throws Exception
     */
    String getSysPermissionKey(final Long spId);

    /**
     * 把值存储到redis中
     * 1.Key-Value对象
     * 2.把key存储到一个set中,方便删除操作
     */
    String setSysPermission(SysPermission sysPermission, long expire);

    /**
     * 从缓存中得到值
     * @param spId
     * @return
     */
    SysPermission getSysPermission(final Long spId);

    /**
     * 存储分页数量
     * @param t
     * @param count
     * @param expire
     * @return
     */
    <T> String setTotalSysPermission(T t, Long count, long expire) throws Exception;

    /**
     * 获取分页数量
     * @param t
     * @return
     */
    <T> Long getTotalSysPermission(T t) throws Exception;

    /**
     * 存储分页列表
     * @param t
     * @param list
     * @param expire
     * @return
     */
    <T> String setSysPermissionList(T t, List<SysPermission> list, long expire) throws Exception ;

    /**
     * 获取分页列表
     * @param t
     * @return
     */
    <T> JSONObject getSysPermissionList(T t) throws Exception ;

    /**
     * 根据id列表获取列表
     * @param list
     * @return
     * @throws Exception
     */
    List<SysPermission> getSysPermissionListByIds(List<Long> list) throws Exception;

    /**
     * 删除所有redis的值
     * 注意:慎用！！！！
     */
    void clearAllSysPermission();

    /**
     * 删除所有分页查询结果
     * 注意:慎用！！！！
     */
    void deleteAllHashSetByPage();

    /**
     * 根据主键删除 simulElecDistri对象
     * @return
     */
    void deleteSysPermission(Long spId) ;
}

