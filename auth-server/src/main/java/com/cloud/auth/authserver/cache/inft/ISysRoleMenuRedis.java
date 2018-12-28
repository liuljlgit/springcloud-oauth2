package com.cloud.auth.authserver.cache.inft;

import java.util.*;
import com.cloud.common.complexquery.QueryExample;
import com.alibaba.fastjson.JSONObject;
import com.cloud.auth.authserver.entity.SysRoleMenu;

/**
 * 缓存接口类 ISysRoleMenuRedis
 * @author lijun
 */
public interface ISysRoleMenuRedis {

    /**
     * 获取SysRoleMenu的ID
     * @return
     */
    Long getSysRoleMenuId();

    /**
     * 在Spring容器初始化的时候，初始化该实体ID的最大值。
     * @return
     */
    void initMaxSysRoleMenuId();

    /**
     * 得到【ClassName:Id】值
     * @return
     * @throws Exception
     */
    String getSysRoleMenuKey(final Long srmId);

    /**
     * 把值存储到redis中
     * 1.Key-Value对象
     * 2.把key存储到一个set中,方便删除操作
     */
    String setSysRoleMenu(SysRoleMenu sysRoleMenu, long expire);

    /**
     * 从缓存中得到值
     * @param srmId
     * @return
     */
    SysRoleMenu getSysRoleMenu(final Long srmId);

    /**
     * 存储分页数量
     * @param t
     * @param count
     * @param expire
     * @return
     */
    <T> String setTotalSysRoleMenu(T t, Long count, long expire) throws Exception;

    /**
     * 获取分页数量
     * @param t
     * @return
     */
    <T> Long getTotalSysRoleMenu(T t) throws Exception;

    /**
     * 存储分页列表
     * @param t
     * @param list
     * @param expire
     * @return
     */
    <T> String setSysRoleMenuList(T t, List<SysRoleMenu> list, long expire) throws Exception ;

    /**
     * 获取分页列表
     * @param t
     * @return
     */
    <T> JSONObject getSysRoleMenuList(T t) throws Exception ;

    /**
     * 根据id列表获取列表
     * @param list
     * @return
     * @throws Exception
     */
    List<SysRoleMenu> getSysRoleMenuListByIds(List<Long> list) throws Exception;

    /**
     * 删除所有redis的值
     * 注意:慎用！！！！
     */
    void clearAllSysRoleMenu();

    /**
     * 删除所有分页查询结果
     * 注意:慎用！！！！
     */
    void deleteAllHashSetByPage();

    /**
     * 根据主键删除 simulElecDistri对象
     * @return
     */
    void deleteSysRoleMenu(Long srmId) ;
}

