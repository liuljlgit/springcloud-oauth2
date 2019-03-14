package com.cloud.auth.authserver.dao;

import java.util.*;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;
import com.cloud.common.complexquery.QueryExample;
import com.cloud.auth.authserver.entity.SysRole;

/**
  * 接口类 ISysRoleDao
  * @author lijun
  */
@Repository
public interface ISysRoleDao {

    /**
     * 根据主键获取对象
     * @param srId
     * @return
     */
    SysRole loadSysRoleByKey(Long srId);

    /**
     * 新增对象
     * @param sysRole
     * @return
     */
    Integer addSysRole(SysRole sysRole);

    /**
     * 批量新增对象
     * @param list
     */
    void addSysRoleList(List<SysRole> list);

    /**
     * 更新对象
     * @param sysRole
     * @return
     */
    Integer updateSysRole(SysRole sysRole);

    /**
     * 更新对象:全更新
     * @param sysRole
     * @return
     */
    Integer fullUpdateSysRole(SysRole sysRole);

    /**
     * 批量更新
     * @param list
     */
    void updateSysRoleList(List<SysRole> list);

    /**
     * 批量更新:全更新
     * @param list
     */
    void fullUpdateSysRoleList(List<SysRole> list);

    /**
     * 删除对象
     * @param srId
     * @return
     */
    Integer deleteSysRole(Long srId);

    /**
     * 批量删除对象
     * @param ids
     */
    void deleteSysRoleList(List<Long> ids);

    /**
     * 查询记录总数
     * @param sysRole
     * @return
     */
    Long getSysRoleCount(SysRole sysRole);

    /**
     * 分页查询列表
     * @param sysRole
     * @return
     */
    List<SysRole> getSysRoleList(SysRole sysRole);

    /**
     * 获取表的最大ID
     * @return
     */
     Long selectMaxSysRoleId();

    /**
     * 查询记录总数
     * @param queryExample
     * @return
     */
     Long getSysRoleCountExample(QueryExample queryExample);

    /**
     * 分页查询列表
     * @param queryExample
     * @return
     */
     List<SysRole> getSysRoleListExample(QueryExample queryExample);

     /**
      * 根据ID列表从数据库中查询列表
      * @param list
      * @return
      */
      List<SysRole> findSysRoleListByIds(List<Long> list);
}

