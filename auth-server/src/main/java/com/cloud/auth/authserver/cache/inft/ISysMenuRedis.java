package com.cloud.auth.authserver.cache.inft;

import java.util.*;
import com.cloud.common.complexquery.QueryExample;
import com.alibaba.fastjson.JSONObject;
import com.cloud.auth.authserver.entity.SysMenu;

/**
 * 缓存接口类 ISysMenuRedis
 * @author lijun
 */
public interface ISysMenuRedis {

    /**
     * 获取SysMenu的ID
     * @return
     */
    Long getSysMenuId();

    /**
     * 在Spring容器初始化的时候，初始化该实体ID的最大值。
     * @return
     */
    void initMaxSysMenuId();

    /**
     * 得到【ClassName:Id】值
     * @return
     * @throws Exception
     */
    String getSysMenuKey(final Long smId);

    /**
     * 把值存储到redis中
     * 1.Key-Value对象
     * 2.把key存储到一个set中,方便删除操作
     */
    String setSysMenu(SysMenu sysMenu, long expire);

    /**
     * 从缓存中得到值
     * @param smId
     * @return
     */
    SysMenu getSysMenu(final Long smId);

    /**
     * 存储分页数量
     * @param t
     * @param count
     * @param expire
     * @return
     */
    <T> String setTotalSysMenu(T t, Long count, long expire) throws Exception;

    /**
     * 获取分页数量
     * @param t
     * @return
     */
    <T> Long getTotalSysMenu(T t) throws Exception;

    /**
     * 存储分页列表
     * @param t
     * @param list
     * @param expire
     * @return
     */
    <T> String setSysMenuList(T t, List<SysMenu> list, long expire) throws Exception ;

    /**
     * 获取分页列表
     * @param t
     * @return
     */
    <T> JSONObject getSysMenuList(T t) throws Exception ;

    /**
     * 根据id列表获取列表
     * @param list
     * @return
     * @throws Exception
     */
    List<SysMenu> getSysMenuListByIds(List<Long> list) throws Exception;

    /**
     * 删除所有redis的值
     * 注意:慎用！！！！
     */
    void clearAllSysMenu();

    /**
     * 删除所有分页查询结果
     * 注意:慎用！！！！
     */
    void deleteAllHashSetByPage();

    /**
     * 根据主键删除 simulElecDistri对象
     * @return
     */
    void deleteSysMenu(Long smId) ;
}

