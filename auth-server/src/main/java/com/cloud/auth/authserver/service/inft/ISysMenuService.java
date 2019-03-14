package com.cloud.auth.authserver.service.inft;

import java.util.*;
import com.cloud.common.complexquery.QueryExample;
import com.alibaba.fastjson.JSONObject;
import com.cloud.auth.authserver.entity.SysMenu;

/**
 * ISysMenuService service接口类
 * @author lijun
 */
public interface ISysMenuService {

    /**
     * 根据主键获取对象
     * @param smId
     * @return
     * @throws Exception
     */
    SysMenu loadSysMenuByKey(Long smId) throws Exception;

    /**
     * 普通查询获取单个结果
     * @param t
     * @param useCache
     * @return
     * @throws Exception
     */
    <T> SysMenu selectOneSysMenu(T t, Boolean useCache) throws Exception;

    /**
     * 新增对象
     * @param sysMenu
     * @return
     * @throws Exception
     */
    Integer addSysMenu(SysMenu sysMenu) throws Exception;

    /**
     * 批量新增对象
     * @param sysMenuList
     * @throws Exception
     */
    void addSysMenuList(List<SysMenu> sysMenuList) throws Exception;

    /**
     * 更新对象
     * @param sysMenu
     * @param isFullUpdate
     * @return
     * @throws Exception
     */
    Integer updateSysMenu(SysMenu sysMenu,Boolean isFullUpdate) throws Exception;

    /**
     * 批量更新
     * @param sysMenuList
     * @param isFullUpdate
     * @throws Exception
     */
    void updateSysMenuList(List<SysMenu> sysMenuList,Boolean isFullUpdate) throws Exception;

    /**
     * 删除对象
     * @param smId
     * @return
     * @throws Exception
     */
    Integer deleteSysMenu(Long smId) throws Exception;

    /**
     * 批量删除对象
     * @param ids
     * @throws Exception
     */
    void deleteSysMenuList(List<Long> ids) throws Exception;

    /**
     * 查询记录总数
     * @param t
     * @param useCache
     * @return
     * @throws Exception
     */
    <T> Long getTotalSysMenu(T t,Boolean useCache) throws Exception;

    /**
     * 查询列表
     * @param t
     * @param useCache
     * @return
     * @throws Exception
     */
    <T> List<SysMenu> findSysMenuList(T t, Boolean useCache) throws Exception;

    /**
     * 根据ID列表从数据库中查询列表
     * @param list
     * @return
     * @throws Exception
     */
    List<SysMenu> findSysMenuListByIds(List<Long> list,Boolean useCache) throws Exception;

    /**
     * 分页查询列表
     * @param sysMenu
     * @param useCache
     * @return
     * @throws Exception
     */
    JSONObject getPageSysMenu(SysMenu sysMenu,Boolean useCache) throws Exception;

    /**
     * 分页查询列表 Example
     * @param queryExample
     * @param useCache
     * @return
     * @throws Exception
     */
    JSONObject getPageSysMenuExample(QueryExample queryExample,Boolean useCache) throws Exception;

    /**
     * 保存记录
     * @param sysMenu
     * @throws Exception
     */
     void saveSysMenu(SysMenu sysMenu) throws Exception;

    /**
     * 批量保存记录
     * @param sysMenuList
     * @throws Exception
     */
     void saveSysMenuList(List<SysMenu> sysMenuList) throws Exception;
}

