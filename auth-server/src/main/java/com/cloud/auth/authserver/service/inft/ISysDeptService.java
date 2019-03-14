package com.cloud.auth.authserver.service.inft;

import java.util.*;
import com.cloud.common.complexquery.QueryExample;
import com.alibaba.fastjson.JSONObject;
import com.cloud.auth.authserver.entity.SysDept;

/**
 * ISysDeptService service接口类
 * @author lijun
 */
public interface ISysDeptService {

    /**
     * 根据主键获取对象
     * @param sdId
     * @return
     * @throws Exception
     */
    SysDept loadSysDeptByKey(Long sdId) throws Exception;

    /**
     * 普通查询获取单个结果
     * @param t
     * @param useCache
     * @return
     * @throws Exception
     */
    <T> SysDept selectOneSysDept(T t, Boolean useCache) throws Exception;

    /**
     * 新增对象
     * @param sysDept
     * @return
     * @throws Exception
     */
    Integer addSysDept(SysDept sysDept) throws Exception;

    /**
     * 批量新增对象
     * @param sysDeptList
     * @throws Exception
     */
    void addSysDeptList(List<SysDept> sysDeptList) throws Exception;

    /**
     * 更新对象
     * @param sysDept
     * @param isFullUpdate
     * @return
     * @throws Exception
     */
    Integer updateSysDept(SysDept sysDept,Boolean isFullUpdate) throws Exception;

    /**
     * 批量更新
     * @param sysDeptList
     * @param isFullUpdate
     * @throws Exception
     */
    void updateSysDeptList(List<SysDept> sysDeptList,Boolean isFullUpdate) throws Exception;

    /**
     * 删除对象
     * @param sdId
     * @return
     * @throws Exception
     */
    Integer deleteSysDept(Long sdId) throws Exception;

    /**
     * 批量删除对象
     * @param ids
     * @throws Exception
     */
    void deleteSysDeptList(List<Long> ids) throws Exception;

    /**
     * 查询记录总数
     * @param t
     * @param useCache
     * @return
     * @throws Exception
     */
    <T> Long getTotalSysDept(T t,Boolean useCache) throws Exception;

    /**
     * 查询列表
     * @param t
     * @param useCache
     * @return
     * @throws Exception
     */
    <T> List<SysDept> findSysDeptList(T t, Boolean useCache) throws Exception;

    /**
     * 根据ID列表从数据库中查询列表
     * @param list
     * @return
     * @throws Exception
     */
    List<SysDept> findSysDeptListByIds(List<Long> list,Boolean useCache) throws Exception;

    /**
     * 分页查询列表
     * @param sysDept
     * @param useCache
     * @return
     * @throws Exception
     */
    JSONObject getPageSysDept(SysDept sysDept,Boolean useCache) throws Exception;

    /**
     * 分页查询列表 Example
     * @param queryExample
     * @param useCache
     * @return
     * @throws Exception
     */
    JSONObject getPageSysDeptExample(QueryExample queryExample,Boolean useCache) throws Exception;

    /**
     * 保存记录
     * @param sysDept
     * @throws Exception
     */
     void saveSysDept(SysDept sysDept) throws Exception;

    /**
     * 批量保存记录
     * @param sysDeptList
     * @throws Exception
     */
     void saveSysDeptList(List<SysDept> sysDeptList) throws Exception;
}

