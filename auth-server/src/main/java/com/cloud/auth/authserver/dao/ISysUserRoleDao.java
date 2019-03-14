package com.cloud.auth.authserver.dao;

import java.util.*;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;
import com.cloud.common.complexquery.QueryExample;
import com.cloud.auth.authserver.entity.SysUserRole;

/**
  * 接口类 ISysUserRoleDao
  * @author lijun
  */
@Repository
public interface ISysUserRoleDao {

    /**
     * 根据主键获取对象
     * @param surId
     * @return
     */
    SysUserRole loadSysUserRoleByKey(Long surId);

    /**
     * 新增对象
     * @param sysUserRole
     * @return
     */
    Integer addSysUserRole(SysUserRole sysUserRole);

    /**
     * 批量新增对象
     * @param list
     */
    void addSysUserRoleList(List<SysUserRole> list);

    /**
     * 更新对象
     * @param sysUserRole
     * @return
     */
    Integer updateSysUserRole(SysUserRole sysUserRole);

    /**
     * 更新对象:全更新
     * @param sysUserRole
     * @return
     */
    Integer fullUpdateSysUserRole(SysUserRole sysUserRole);

    /**
     * 批量更新
     * @param list
     */
    void updateSysUserRoleList(List<SysUserRole> list);

    /**
     * 批量更新:全更新
     * @param list
     */
    void fullUpdateSysUserRoleList(List<SysUserRole> list);

    /**
     * 删除对象
     * @param surId
     * @return
     */
    Integer deleteSysUserRole(Long surId);

    /**
     * 批量删除对象
     * @param ids
     */
    void deleteSysUserRoleList(List<Long> ids);

    /**
     * 查询记录总数
     * @param sysUserRole
     * @return
     */
    Long getSysUserRoleCount(SysUserRole sysUserRole);

    /**
     * 分页查询列表
     * @param sysUserRole
     * @return
     */
    List<SysUserRole> getSysUserRoleList(SysUserRole sysUserRole);

    /**
     * 获取表的最大ID
     * @return
     */
     Long selectMaxSysUserRoleId();

    /**
     * 查询记录总数
     * @param queryExample
     * @return
     */
     Long getSysUserRoleCountExample(QueryExample queryExample);

    /**
     * 分页查询列表
     * @param queryExample
     * @return
     */
     List<SysUserRole> getSysUserRoleListExample(QueryExample queryExample);

     /**
      * 根据ID列表从数据库中查询列表
      * @param list
      * @return
      */
      List<SysUserRole> findSysUserRoleListByIds(List<Long> list);
}

