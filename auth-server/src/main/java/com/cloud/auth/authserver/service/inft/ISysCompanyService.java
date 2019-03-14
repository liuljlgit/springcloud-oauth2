package com.cloud.auth.authserver.service.inft;

import java.util.*;
import com.cloud.common.complexquery.QueryExample;
import com.alibaba.fastjson.JSONObject;
import com.cloud.auth.authserver.entity.SysCompany;

/**
 * ISysCompanyService service接口类
 * @author lijun
 */
public interface ISysCompanyService {

    /**
     * 根据主键获取对象
     * @param scId
     * @return
     * @throws Exception
     */
    SysCompany loadSysCompanyByKey(Long scId) throws Exception;

    /**
     * 普通查询获取单个结果
     * @param t
     * @param useCache
     * @return
     * @throws Exception
     */
    <T> SysCompany selectOneSysCompany(T t, Boolean useCache) throws Exception;

    /**
     * 新增对象
     * @param sysCompany
     * @return
     * @throws Exception
     */
    Integer addSysCompany(SysCompany sysCompany) throws Exception;

    /**
     * 批量新增对象
     * @param sysCompanyList
     * @throws Exception
     */
    void addSysCompanyList(List<SysCompany> sysCompanyList) throws Exception;

    /**
     * 更新对象
     * @param sysCompany
     * @param isFullUpdate
     * @return
     * @throws Exception
     */
    Integer updateSysCompany(SysCompany sysCompany,Boolean isFullUpdate) throws Exception;

    /**
     * 批量更新
     * @param sysCompanyList
     * @param isFullUpdate
     * @throws Exception
     */
    void updateSysCompanyList(List<SysCompany> sysCompanyList,Boolean isFullUpdate) throws Exception;

    /**
     * 删除对象
     * @param scId
     * @return
     * @throws Exception
     */
    Integer deleteSysCompany(Long scId) throws Exception;

    /**
     * 批量删除对象
     * @param ids
     * @throws Exception
     */
    void deleteSysCompanyList(List<Long> ids) throws Exception;

    /**
     * 查询记录总数
     * @param t
     * @param useCache
     * @return
     * @throws Exception
     */
    <T> Long getTotalSysCompany(T t,Boolean useCache) throws Exception;

    /**
     * 查询列表
     * @param t
     * @param useCache
     * @return
     * @throws Exception
     */
    <T> List<SysCompany> findSysCompanyList(T t, Boolean useCache) throws Exception;

    /**
     * 根据ID列表从数据库中查询列表
     * @param list
     * @return
     * @throws Exception
     */
    List<SysCompany> findSysCompanyListByIds(List<Long> list,Boolean useCache) throws Exception;

    /**
     * 分页查询列表
     * @param sysCompany
     * @param useCache
     * @return
     * @throws Exception
     */
    JSONObject getPageSysCompany(SysCompany sysCompany,Boolean useCache) throws Exception;

    /**
     * 分页查询列表 Example
     * @param queryExample
     * @param useCache
     * @return
     * @throws Exception
     */
    JSONObject getPageSysCompanyExample(QueryExample queryExample,Boolean useCache) throws Exception;

    /**
     * 保存记录
     * @param sysCompany
     * @throws Exception
     */
     void saveSysCompany(SysCompany sysCompany) throws Exception;

    /**
     * 批量保存记录
     * @param sysCompanyList
     * @throws Exception
     */
     void saveSysCompanyList(List<SysCompany> sysCompanyList) throws Exception;
}

