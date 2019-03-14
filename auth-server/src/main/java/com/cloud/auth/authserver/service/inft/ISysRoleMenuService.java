package com.cloud.auth.authserver.service.inft;

import java.util.*;
import com.cloud.common.complexquery.QueryExample;
import com.alibaba.fastjson.JSONObject;
import com.cloud.auth.authserver.entity.SysRoleMenu;

/**
 * ISysRoleMenuService service接口类
 * @author lijun
 */
public interface ISysRoleMenuService {

    /**
     * 根据主键获取对象
     * @param srmId
     * @return
     * @throws Exception
     */
    SysRoleMenu loadSysRoleMenuByKey(Long srmId) throws Exception;

    /**
     * 普通查询获取单个结果
     * @param t
     * @param useCache
     * @return
     * @throws Exception
     */
    <T> SysRoleMenu selectOneSysRoleMenu(T t, Boolean useCache) throws Exception;

    /**
     * 新增对象
     * @param sysRoleMenu
     * @return
     * @throws Exception
     */
    Integer addSysRoleMenu(SysRoleMenu sysRoleMenu) throws Exception;

    /**
     * 批量新增对象
     * @param sysRoleMenuList
     * @throws Exception
     */
    void addSysRoleMenuList(List<SysRoleMenu> sysRoleMenuList) throws Exception;

    /**
     * 更新对象
     * @param sysRoleMenu
     * @param isFullUpdate
     * @return
     * @throws Exception
     */
    Integer updateSysRoleMenu(SysRoleMenu sysRoleMenu,Boolean isFullUpdate) throws Exception;

    /**
     * 批量更新
     * @param sysRoleMenuList
     * @param isFullUpdate
     * @throws Exception
     */
    void updateSysRoleMenuList(List<SysRoleMenu> sysRoleMenuList,Boolean isFullUpdate) throws Exception;

    /**
     * 删除对象
     * @param srmId
     * @return
     * @throws Exception
     */
    Integer deleteSysRoleMenu(Long srmId) throws Exception;

    /**
     * 批量删除对象
     * @param ids
     * @throws Exception
     */
    void deleteSysRoleMenuList(List<Long> ids) throws Exception;

    /**
     * 查询记录总数
     * @param t
     * @param useCache
     * @return
     * @throws Exception
     */
    <T> Long getTotalSysRoleMenu(T t,Boolean useCache) throws Exception;

    /**
     * 查询列表
     * @param t
     * @param useCache
     * @return
     * @throws Exception
     */
    <T> List<SysRoleMenu> findSysRoleMenuList(T t, Boolean useCache) throws Exception;

    /**
     * 根据ID列表从数据库中查询列表
     * @param list
     * @return
     * @throws Exception
     */
    List<SysRoleMenu> findSysRoleMenuListByIds(List<Long> list,Boolean useCache) throws Exception;

    /**
     * 分页查询列表
     * @param sysRoleMenu
     * @param useCache
     * @return
     * @throws Exception
     */
    JSONObject getPageSysRoleMenu(SysRoleMenu sysRoleMenu,Boolean useCache) throws Exception;

    /**
     * 分页查询列表 Example
     * @param queryExample
     * @param useCache
     * @return
     * @throws Exception
     */
    JSONObject getPageSysRoleMenuExample(QueryExample queryExample,Boolean useCache) throws Exception;

    /**
     * 保存记录
     * @param sysRoleMenu
     * @throws Exception
     */
     void saveSysRoleMenu(SysRoleMenu sysRoleMenu) throws Exception;

    /**
     * 批量保存记录
     * @param sysRoleMenuList
     * @throws Exception
     */
     void saveSysRoleMenuList(List<SysRoleMenu> sysRoleMenuList) throws Exception;
}

