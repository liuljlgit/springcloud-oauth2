package com.cloud.auth.authserver.dao.inft;

import java.util.*;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;
import com.cloud.common.complexquery.QueryExample;
import com.cloud.auth.authserver.entity.SysRolePermission;

/**
  * 接口类 ISysRolePermissionDao
  * @author lijun
  */
@Repository
public interface ISysRolePermissionDao {

    /**
     * 根据主键获取对象
     * @param srpId
     * @return
     */
    SysRolePermission loadSysRolePermissionByKey(Long srpId);

    /**
     * 新增对象
     * @param sysRolePermission
     * @return
     */
    Integer insertSysRolePermission(SysRolePermission sysRolePermission);

    /**
     * 批量新增对象
     * @param list
     */
    void insertSysRolePermissionList(List<SysRolePermission> list);

    /**
     * 更新对象
     * @param sysRolePermission
     * @return
     */
    Integer updateSysRolePermission(SysRolePermission sysRolePermission);

    /**
     * 更新对象:全更新
     * @param sysRolePermission
     * @return
     */
    Integer fullUpdateSysRolePermission(SysRolePermission sysRolePermission);

    /**
     * 批量更新
     * @param list
     */
    void updateSysRolePermissionList(List<SysRolePermission> list);

    /**
     * 批量更新:全更新
     * @param list
     */
    void fullUpdateSysRolePermissionList(List<SysRolePermission> list);

    /**
     * 删除对象
     * @param srpId
     * @return
     */
    Integer deleteSysRolePermissionByKey(Long srpId);

    /**
     * 批量删除对象
     * @param list
     */
    void deleteSysRolePermissionList(List<SysRolePermission> list);

    /**
     * 查询记录总数
     * @param sysRolePermission
     * @return
     */
    Long getSysRolePermissionCount(SysRolePermission sysRolePermission);

    /**
     * 分页查询列表
     * @param sysRolePermission
     * @return
     */
    List<SysRolePermission> getSysRolePermissionList(SysRolePermission sysRolePermission);

    /**
     * 获取表的最大ID
     * @return
     */
     Long selectMaxSysRolePermissionId();

    /**
     * 查询记录总数
     * @param queryExample
     * @return
     */
     Long getSysRolePermissionCountExample(QueryExample queryExample);

    /**
     * 分页查询列表
     * @param queryExample
     * @return
     */
     List<SysRolePermission> getSysRolePermissionListExample(QueryExample queryExample);

     /**
      * 根据ID列表从数据库中查询列表
      * @param list
      * @return
      */
      List<SysRolePermission> findSysRolePermissionListByIds(List<Long> list);
}

