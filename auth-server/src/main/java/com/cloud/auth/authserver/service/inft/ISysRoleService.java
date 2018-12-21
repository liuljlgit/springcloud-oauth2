package com.cloud.auth.authserver.service.inft;

import java.util.*;
import com.cloud.common.complexquery.QueryExample;
import com.alibaba.fastjson.JSONObject;
import com.cloud.auth.authserver.entity.SysRole;

/**
 * ISysRoleService service接口类
 * @author lijun
 */
public interface ISysRoleService {

    /**
     * 根据主键获取对象
     * @param srId
     * @return
     * @throws Exception
     */
    SysRole loadSysRoleByKey(Long srId) throws Exception;

    /**
     * 普通查询获取单个结果
     * @param t
     * @param useCache
     * @return
     * @throws Exception
     */
    <T> SysRole selectOneSysRole(T t, Boolean useCache) throws Exception;

    /**
     * 新增对象
     * @param sysRole
     * @return
     * @throws Exception
     */
    Integer insertSysRole(SysRole sysRole) throws Exception;

    /**
     * 批量新增对象
     * @param sysRoleList
     * @throws Exception
     */
    void insertSysRoleList(List<SysRole> sysRoleList) throws Exception;

    /**
     * 更新对象
     * @param sysRole
     * @param isFullUpdate
     * @return
     * @throws Exception
     */
    Integer updateSysRole(SysRole sysRole,Boolean isFullUpdate) throws Exception;

    /**
     * 批量更新
     * @param sysRoleList
     * @param isFullUpdate
     * @throws Exception
     */
    void updateSysRoleList(List<SysRole> sysRoleList,Boolean isFullUpdate) throws Exception;

    /**
     * 删除对象
     * @param srId
     * @return
     * @throws Exception
     */
    Integer deleteSysRoleByKey(Long srId) throws Exception;

    /**
     * 批量删除对象
     * @param sysRoleList
     * @throws Exception
     */
    void deleteSysRoleList(List<SysRole> sysRoleList) throws Exception;

    /**
     * 查询记录总数
     * @param t
     * @param useCache
     * @return
     * @throws Exception
     */
    <T> Long getTotalSysRole(T t,Boolean useCache) throws Exception;

    /**
     * 查询列表
     * @param t
     * @param useCache
     * @return
     * @throws Exception
     */
    <T> List<SysRole> findSysRoleList(T t, Boolean useCache) throws Exception;

    /**
     * 根据ID列表从数据库中查询列表
     * @param list
     * @return
     * @throws Exception
     */
    List<SysRole> findSysRoleListByIds(List<Long> list,Boolean useCache) throws Exception;

    /**
     * 分页查询列表
     * @param sysRole
     * @param useCache
     * @return
     * @throws Exception
     */
    JSONObject getPageSysRole(SysRole sysRole,Boolean useCache) throws Exception;

    /**
     * 分页查询列表 Example
     * @param queryExample
     * @param useCache
     * @return
     * @throws Exception
     */
    JSONObject getPageSysRoleExample(QueryExample queryExample,Boolean useCache) throws Exception;

    /**
     * 保存记录
     * @param sysRole
     * @param isFullUpdate
     * @throws Exception
     */
     void saveSysRole(SysRole sysRole,Boolean isFullUpdate) throws Exception;

    /**
     * 批量保存记录
     * @param sysRoleList
     * @param isFullUpdate
     * @throws Exception
     */
     void saveSysRoleList(List<SysRole> sysRoleList,Boolean isFullUpdate) throws Exception;
}

