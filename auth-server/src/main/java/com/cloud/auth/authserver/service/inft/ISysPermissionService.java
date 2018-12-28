package com.cloud.auth.authserver.service.inft;

import java.util.*;
import com.cloud.common.complexquery.QueryExample;
import com.alibaba.fastjson.JSONObject;
import com.cloud.auth.authserver.entity.SysPermission;

/**
 * ISysPermissionService service接口类
 * @author lijun
 */
public interface ISysPermissionService {

    /**
     * 根据主键获取对象
     * @param spId
     * @return
     * @throws Exception
     */
    SysPermission loadSysPermissionByKey(Long spId) throws Exception;

    /**
     * 普通查询获取单个结果
     * @param t
     * @param useCache
     * @return
     * @throws Exception
     */
    <T> SysPermission selectOneSysPermission(T t, Boolean useCache) throws Exception;

    /**
     * 新增对象
     * @param sysPermission
     * @return
     * @throws Exception
     */
    Integer insertSysPermission(SysPermission sysPermission) throws Exception;

    /**
     * 批量新增对象
     * @param sysPermissionList
     * @throws Exception
     */
    void insertSysPermissionList(List<SysPermission> sysPermissionList) throws Exception;

    /**
     * 更新对象
     * @param sysPermission
     * @param isFullUpdate
     * @return
     * @throws Exception
     */
    Integer updateSysPermission(SysPermission sysPermission,Boolean isFullUpdate) throws Exception;

    /**
     * 批量更新
     * @param sysPermissionList
     * @param isFullUpdate
     * @throws Exception
     */
    void updateSysPermissionList(List<SysPermission> sysPermissionList,Boolean isFullUpdate) throws Exception;

    /**
     * 删除对象
     * @param spId
     * @return
     * @throws Exception
     */
    Integer deleteSysPermissionByKey(Long spId) throws Exception;

    /**
     * 批量删除对象
     * @param sysPermissionList
     * @throws Exception
     */
    void deleteSysPermissionList(List<SysPermission> sysPermissionList) throws Exception;

    /**
     * 查询记录总数
     * @param t
     * @param useCache
     * @return
     * @throws Exception
     */
    <T> Long getTotalSysPermission(T t,Boolean useCache) throws Exception;

    /**
     * 查询列表
     * @param t
     * @param useCache
     * @return
     * @throws Exception
     */
    <T> List<SysPermission> findSysPermissionList(T t, Boolean useCache) throws Exception;

    /**
     * 根据ID列表从数据库中查询列表
     * @param list
     * @return
     * @throws Exception
     */
    List<SysPermission> findSysPermissionListByIds(List<Long> list,Boolean useCache) throws Exception;

    /**
     * 分页查询列表
     * @param sysPermission
     * @param useCache
     * @return
     * @throws Exception
     */
    JSONObject getPageSysPermission(SysPermission sysPermission,Boolean useCache) throws Exception;

    /**
     * 分页查询列表 Example
     * @param queryExample
     * @param useCache
     * @return
     * @throws Exception
     */
    JSONObject getPageSysPermissionExample(QueryExample queryExample,Boolean useCache) throws Exception;

    /**
     * 保存记录
     * @param sysPermission
     * @param isFullUpdate
     * @throws Exception
     */
     void saveSysPermission(SysPermission sysPermission,Boolean isFullUpdate) throws Exception;

    /**
     * 批量保存记录
     * @param sysPermissionList
     * @param isFullUpdate
     * @throws Exception
     */
     void saveSysPermissionList(List<SysPermission> sysPermissionList,Boolean isFullUpdate) throws Exception;
}

