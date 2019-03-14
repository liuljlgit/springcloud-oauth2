package com.cloud.auth.authserver.dao;

import java.util.*;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;
import com.cloud.common.complexquery.QueryExample;
import com.cloud.auth.authserver.entity.SysRoleMenu;

/**
  * 接口类 ISysRoleMenuDao
  * @author lijun
  */
@Repository
public interface ISysRoleMenuDao {

    /**
     * 根据主键获取对象
     * @param srmId
     * @return
     */
    SysRoleMenu loadSysRoleMenuByKey(Long srmId);

    /**
     * 新增对象
     * @param sysRoleMenu
     * @return
     */
    Integer addSysRoleMenu(SysRoleMenu sysRoleMenu);

    /**
     * 批量新增对象
     * @param list
     */
    void addSysRoleMenuList(List<SysRoleMenu> list);

    /**
     * 更新对象
     * @param sysRoleMenu
     * @return
     */
    Integer updateSysRoleMenu(SysRoleMenu sysRoleMenu);

    /**
     * 更新对象:全更新
     * @param sysRoleMenu
     * @return
     */
    Integer fullUpdateSysRoleMenu(SysRoleMenu sysRoleMenu);

    /**
     * 批量更新
     * @param list
     */
    void updateSysRoleMenuList(List<SysRoleMenu> list);

    /**
     * 批量更新:全更新
     * @param list
     */
    void fullUpdateSysRoleMenuList(List<SysRoleMenu> list);

    /**
     * 删除对象
     * @param srmId
     * @return
     */
    Integer deleteSysRoleMenu(Long srmId);

    /**
     * 批量删除对象
     * @param ids
     */
    void deleteSysRoleMenuList(List<Long> ids);

    /**
     * 查询记录总数
     * @param sysRoleMenu
     * @return
     */
    Long getSysRoleMenuCount(SysRoleMenu sysRoleMenu);

    /**
     * 分页查询列表
     * @param sysRoleMenu
     * @return
     */
    List<SysRoleMenu> getSysRoleMenuList(SysRoleMenu sysRoleMenu);

    /**
     * 获取表的最大ID
     * @return
     */
     Long selectMaxSysRoleMenuId();

    /**
     * 查询记录总数
     * @param queryExample
     * @return
     */
     Long getSysRoleMenuCountExample(QueryExample queryExample);

    /**
     * 分页查询列表
     * @param queryExample
     * @return
     */
     List<SysRoleMenu> getSysRoleMenuListExample(QueryExample queryExample);

     /**
      * 根据ID列表从数据库中查询列表
      * @param list
      * @return
      */
      List<SysRoleMenu> findSysRoleMenuListByIds(List<Long> list);
}

