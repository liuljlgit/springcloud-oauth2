package com.cloud.auth.authserver.service.inft;

import java.util.*;
import com.cloud.common.complexquery.QueryExample;
import com.alibaba.fastjson.JSONObject;
import com.cloud.auth.authserver.entity.SysUser;

/**
 * ISysUserService service接口类
 * @author lijun
 */
public interface ISysUserService {

    /**
     * 根据主键获取对象
     * @param suId
     * @return
     * @throws Exception
     */
    SysUser loadSysUserByKey(Long suId) throws Exception;

    /**
     * 普通查询获取单个结果
     * @param t
     * @param useCache
     * @return
     * @throws Exception
     */
    <T> SysUser selectOneSysUser(T t, Boolean useCache) throws Exception;

    /**
     * 新增对象
     * @param sysUser
     * @return
     * @throws Exception
     */
    Integer insertSysUser(SysUser sysUser) throws Exception;

    /**
     * 批量新增对象
     * @param sysUserList
     * @throws Exception
     */
    void insertSysUserList(List<SysUser> sysUserList) throws Exception;

    /**
     * 更新对象
     * @param sysUser
     * @return
     * @throws Exception
     */
    Integer updateSysUser(SysUser sysUser) throws Exception;

    /**
     * 批量更新
     * @param sysUserList
     * @throws Exception
     */
    void updateSysUserList(List<SysUser> sysUserList) throws Exception;

    /**
     * 删除对象
     * @param suId
     * @return
     * @throws Exception
     */
    Integer deleteSysUserByKey(Long suId) throws Exception;

    /**
     * 批量删除对象
     * @param sysUserList
     * @throws Exception
     */
    void deleteSysUserList(List<SysUser> sysUserList) throws Exception;

    /**
     * 查询记录总数
     * @param t
     * @param useCache
     * @return
     * @throws Exception
     */
    <T> Long getTotalSysUser(T t,Boolean useCache) throws Exception;

    /**
     * 查询列表
     * @param t
     * @param useCache
     * @return
     * @throws Exception
     */
    <T> List<SysUser> findSysUserList(T t, Boolean useCache) throws Exception;

    /**
     * 分页查询列表
     * @param sysUser
     * @param useCache
     * @return
     * @throws Exception
     */
    JSONObject getPageSysUser(SysUser sysUser,Boolean useCache) throws Exception;

    /**
     * 分页查询列表 Example
     * @param queryExample
     * @param useCache
     * @return
     * @throws Exception
     */
    JSONObject getPageSysUserExample(QueryExample queryExample,Boolean useCache) throws Exception;

    /**
     * 保存记录
     * @param sysUser
     * @throws Exception
     */
     void saveSysUser(SysUser sysUser) throws Exception;

    /**
     * 批量保存记录
     * @param sysUserList
     * @throws Exception
     */
     void saveSysUserList(List<SysUser> sysUserList) throws Exception;
}

