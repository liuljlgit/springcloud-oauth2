package com.cloud.auth.authserver.service.inft;

import java.util.*;
import com.cloud.common.complexquery.QueryExample;
import com.alibaba.fastjson.JSONObject;
import com.cloud.auth.authserver.entity.SysDeptRole;

/**
 * ISysDeptRoleService service接口类
 * @author lijun
 */
public interface ISysDeptRoleService {

    /**
     * 根据主键获取对象
     * @param sdrId
     * @return
     * @throws Exception
     */
    SysDeptRole loadSysDeptRoleByKey(Long sdrId) throws Exception;

    /**
     * 普通查询获取单个结果
     * @param t
     * @param useCache
     * @return
     * @throws Exception
     */
    <T> SysDeptRole selectOneSysDeptRole(T t, Boolean useCache) throws Exception;

    /**
     * 新增对象
     * @param sysDeptRole
     * @return
     * @throws Exception
     */
    Integer insertSysDeptRole(SysDeptRole sysDeptRole) throws Exception;

    /**
     * 批量新增对象
     * @param sysDeptRoleList
     * @throws Exception
     */
    void insertSysDeptRoleList(List<SysDeptRole> sysDeptRoleList) throws Exception;

    /**
     * 更新对象
     * @param sysDeptRole
     * @param isFullUpdate
     * @return
     * @throws Exception
     */
    Integer updateSysDeptRole(SysDeptRole sysDeptRole,Boolean isFullUpdate) throws Exception;

    /**
     * 批量更新
     * @param sysDeptRoleList
     * @param isFullUpdate
     * @throws Exception
     */
    void updateSysDeptRoleList(List<SysDeptRole> sysDeptRoleList,Boolean isFullUpdate) throws Exception;

    /**
     * 删除对象
     * @param sdrId
     * @return
     * @throws Exception
     */
    Integer deleteSysDeptRoleByKey(Long sdrId) throws Exception;

    /**
     * 批量删除对象
     * @param sysDeptRoleList
     * @throws Exception
     */
    void deleteSysDeptRoleList(List<SysDeptRole> sysDeptRoleList) throws Exception;

    /**
     * 查询记录总数
     * @param t
     * @param useCache
     * @return
     * @throws Exception
     */
    <T> Long getTotalSysDeptRole(T t,Boolean useCache) throws Exception;

    /**
     * 查询列表
     * @param t
     * @param useCache
     * @return
     * @throws Exception
     */
    <T> List<SysDeptRole> findSysDeptRoleList(T t, Boolean useCache) throws Exception;

    /**
     * 根据ID列表从数据库中查询列表
     * @param list
     * @return
     * @throws Exception
     */
    List<SysDeptRole> findSysDeptRoleListByIds(List<Long> list,Boolean useCache) throws Exception;

    /**
     * 分页查询列表
     * @param sysDeptRole
     * @param useCache
     * @return
     * @throws Exception
     */
    JSONObject getPageSysDeptRole(SysDeptRole sysDeptRole,Boolean useCache) throws Exception;

    /**
     * 分页查询列表 Example
     * @param queryExample
     * @param useCache
     * @return
     * @throws Exception
     */
    JSONObject getPageSysDeptRoleExample(QueryExample queryExample,Boolean useCache) throws Exception;

    /**
     * 保存记录
     * @param sysDeptRole
     * @param isFullUpdate
     * @throws Exception
     */
     void saveSysDeptRole(SysDeptRole sysDeptRole,Boolean isFullUpdate) throws Exception;

    /**
     * 批量保存记录
     * @param sysDeptRoleList
     * @param isFullUpdate
     * @throws Exception
     */
     void saveSysDeptRoleList(List<SysDeptRole> sysDeptRoleList,Boolean isFullUpdate) throws Exception;
}

