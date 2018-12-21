package com.cloud.auth.authserver.service.inft;

import java.util.*;
import com.cloud.common.complexquery.QueryExample;
import com.alibaba.fastjson.JSONObject;
import com.cloud.auth.authserver.entity.SysRolePermission;

/**
 * ISysRolePermissionService service接口类
 * @author lijun
 */
public interface ISysRolePermissionService {

    /**
     * 根据主键获取对象
     * @param srpId
     * @return
     * @throws Exception
     */
    SysRolePermission loadSysRolePermissionByKey(Long srpId) throws Exception;

    /**
     * 普通查询获取单个结果
     * @param t
     * @param useCache
     * @return
     * @throws Exception
     */
    <T> SysRolePermission selectOneSysRolePermission(T t, Boolean useCache) throws Exception;

    /**
     * 新增对象
     * @param sysRolePermission
     * @return
     * @throws Exception
     */
    Integer insertSysRolePermission(SysRolePermission sysRolePermission) throws Exception;

    /**
     * 批量新增对象
     * @param sysRolePermissionList
     * @throws Exception
     */
    void insertSysRolePermissionList(List<SysRolePermission> sysRolePermissionList) throws Exception;

    /**
     * 更新对象
     * @param sysRolePermission
     * @param isFullUpdate
     * @return
     * @throws Exception
     */
    Integer updateSysRolePermission(SysRolePermission sysRolePermission,Boolean isFullUpdate) throws Exception;

    /**
     * 批量更新
     * @param sysRolePermissionList
     * @param isFullUpdate
     * @throws Exception
     */
    void updateSysRolePermissionList(List<SysRolePermission> sysRolePermissionList,Boolean isFullUpdate) throws Exception;

    /**
     * 删除对象
     * @param srpId
     * @return
     * @throws Exception
     */
    Integer deleteSysRolePermissionByKey(Long srpId) throws Exception;

    /**
     * 批量删除对象
     * @param sysRolePermissionList
     * @throws Exception
     */
    void deleteSysRolePermissionList(List<SysRolePermission> sysRolePermissionList) throws Exception;

    /**
     * 查询记录总数
     * @param t
     * @param useCache
     * @return
     * @throws Exception
     */
    <T> Long getTotalSysRolePermission(T t,Boolean useCache) throws Exception;

    /**
     * 查询列表
     * @param t
     * @param useCache
     * @return
     * @throws Exception
     */
    <T> List<SysRolePermission> findSysRolePermissionList(T t, Boolean useCache) throws Exception;

    /**
     * 根据ID列表从数据库中查询列表
     * @param list
     * @return
     * @throws Exception
     */
    List<SysRolePermission> findSysRolePermissionListByIds(List<Long> list,Boolean useCache) throws Exception;

    /**
     * 分页查询列表
     * @param sysRolePermission
     * @param useCache
     * @return
     * @throws Exception
     */
    JSONObject getPageSysRolePermission(SysRolePermission sysRolePermission,Boolean useCache) throws Exception;

    /**
     * 分页查询列表 Example
     * @param queryExample
     * @param useCache
     * @return
     * @throws Exception
     */
    JSONObject getPageSysRolePermissionExample(QueryExample queryExample,Boolean useCache) throws Exception;

    /**
     * 保存记录
     * @param sysRolePermission
     * @param isFullUpdate
     * @throws Exception
     */
     void saveSysRolePermission(SysRolePermission sysRolePermission,Boolean isFullUpdate) throws Exception;

    /**
     * 批量保存记录
     * @param sysRolePermissionList
     * @param isFullUpdate
     * @throws Exception
     */
     void saveSysRolePermissionList(List<SysRolePermission> sysRolePermissionList,Boolean isFullUpdate) throws Exception;
}

