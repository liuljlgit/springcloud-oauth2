package com.cloud.auth.authserver.dao;

import java.util.*;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;
import com.cloud.common.complexquery.QueryExample;
import com.cloud.auth.authserver.entity.SysPermission;

/**
  * 接口类 ISysPermissionDao
  * @author lijun
  */
@Repository
public interface ISysPermissionDao {

    /**
     * 根据主键获取对象
     * @param spId
     * @return
     */
    SysPermission loadSysPermissionByKey(Long spId);

    /**
     * 新增对象
     * @param sysPermission
     * @return
     */
    Integer addSysPermission(SysPermission sysPermission);

    /**
     * 批量新增对象
     * @param list
     */
    void addSysPermissionList(List<SysPermission> list);

    /**
     * 更新对象
     * @param sysPermission
     * @return
     */
    Integer updateSysPermission(SysPermission sysPermission);

    /**
     * 更新对象:全更新
     * @param sysPermission
     * @return
     */
    Integer fullUpdateSysPermission(SysPermission sysPermission);

    /**
     * 批量更新
     * @param list
     */
    void updateSysPermissionList(List<SysPermission> list);

    /**
     * 批量更新:全更新
     * @param list
     */
    void fullUpdateSysPermissionList(List<SysPermission> list);

    /**
     * 删除对象
     * @param spId
     * @return
     */
    Integer deleteSysPermission(Long spId);

    /**
     * 批量删除对象
     * @param ids
     */
    void deleteSysPermissionList(List<Long> ids);

    /**
     * 查询记录总数
     * @param sysPermission
     * @return
     */
    Long getSysPermissionCount(SysPermission sysPermission);

    /**
     * 分页查询列表
     * @param sysPermission
     * @return
     */
    List<SysPermission> getSysPermissionList(SysPermission sysPermission);

    /**
     * 获取表的最大ID
     * @return
     */
     Long selectMaxSysPermissionId();

    /**
     * 查询记录总数
     * @param queryExample
     * @return
     */
     Long getSysPermissionCountExample(QueryExample queryExample);

    /**
     * 分页查询列表
     * @param queryExample
     * @return
     */
     List<SysPermission> getSysPermissionListExample(QueryExample queryExample);

     /**
      * 根据ID列表从数据库中查询列表
      * @param list
      * @return
      */
      List<SysPermission> findSysPermissionListByIds(List<Long> list);
}

