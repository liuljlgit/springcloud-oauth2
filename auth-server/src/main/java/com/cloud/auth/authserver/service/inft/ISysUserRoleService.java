package com.cloud.auth.authserver.service.inft;

import java.util.*;
import com.cloud.common.complexquery.QueryExample;
import com.alibaba.fastjson.JSONObject;
import com.cloud.auth.authserver.entity.SysUserRole;

/**
 * ISysUserRoleService service接口类
 * @author lijun
 */
public interface ISysUserRoleService {

    /**
     * 根据主键获取对象
     * @param surId
     * @return
     * @throws Exception
     */
    SysUserRole loadSysUserRoleByKey(Long surId) throws Exception;

    /**
     * 普通查询获取单个结果
     * @param t
     * @param useCache
     * @return
     * @throws Exception
     */
    <T> SysUserRole selectOneSysUserRole(T t, Boolean useCache) throws Exception;

    /**
     * 新增对象
     * @param sysUserRole
     * @return
     * @throws Exception
     */
    Integer insertSysUserRole(SysUserRole sysUserRole) throws Exception;

    /**
     * 批量新增对象
     * @param sysUserRoleList
     * @throws Exception
     */
    void insertSysUserRoleList(List<SysUserRole> sysUserRoleList) throws Exception;

    /**
     * 更新对象
     * @param sysUserRole
     * @param isFullUpdate
     * @return
     * @throws Exception
     */
    Integer updateSysUserRole(SysUserRole sysUserRole,Boolean isFullUpdate) throws Exception;

    /**
     * 批量更新
     * @param sysUserRoleList
     * @param isFullUpdate
     * @throws Exception
     */
    void updateSysUserRoleList(List<SysUserRole> sysUserRoleList,Boolean isFullUpdate) throws Exception;

    /**
     * 删除对象
     * @param surId
     * @return
     * @throws Exception
     */
    Integer deleteSysUserRoleByKey(Long surId) throws Exception;

    /**
     * 批量删除对象
     * @param sysUserRoleList
     * @throws Exception
     */
    void deleteSysUserRoleList(List<SysUserRole> sysUserRoleList) throws Exception;

    /**
     * 查询记录总数
     * @param t
     * @param useCache
     * @return
     * @throws Exception
     */
    <T> Long getTotalSysUserRole(T t,Boolean useCache) throws Exception;

    /**
     * 查询列表
     * @param t
     * @param useCache
     * @return
     * @throws Exception
     */
    <T> List<SysUserRole> findSysUserRoleList(T t, Boolean useCache) throws Exception;

    /**
     * 根据ID列表从数据库中查询列表
     * @param list
     * @return
     * @throws Exception
     */
    List<SysUserRole> findSysUserRoleListByIds(List<Long> list,Boolean useCache) throws Exception;

    /**
     * 分页查询列表
     * @param sysUserRole
     * @param useCache
     * @return
     * @throws Exception
     */
    JSONObject getPageSysUserRole(SysUserRole sysUserRole,Boolean useCache) throws Exception;

    /**
     * 分页查询列表 Example
     * @param queryExample
     * @param useCache
     * @return
     * @throws Exception
     */
    JSONObject getPageSysUserRoleExample(QueryExample queryExample,Boolean useCache) throws Exception;

    /**
     * 保存记录
     * @param sysUserRole
     * @param isFullUpdate
     * @throws Exception
     */
     void saveSysUserRole(SysUserRole sysUserRole,Boolean isFullUpdate) throws Exception;

    /**
     * 批量保存记录
     * @param sysUserRoleList
     * @param isFullUpdate
     * @throws Exception
     */
     void saveSysUserRoleList(List<SysUserRole> sysUserRoleList,Boolean isFullUpdate) throws Exception;
}

