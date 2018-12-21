package com.cloud.auth.authserver.dao.inft;

import java.util.*;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;
import com.cloud.common.complexquery.QueryExample;
import com.cloud.auth.authserver.entity.SysMenu;

/**
  * 接口类 ISysMenuDao
  * @author lijun
  */
@Repository
public interface ISysMenuDao {

    /**
     * 根据主键获取对象
     * @param smId
     * @return
     */
    SysMenu loadSysMenuByKey(Long smId);

    /**
     * 新增对象
     * @param sysMenu
     * @return
     */
    Integer insertSysMenu(SysMenu sysMenu);

    /**
     * 批量新增对象
     * @param list
     */
    void insertSysMenuList(List<SysMenu> list);

    /**
     * 更新对象
     * @param sysMenu
     * @return
     */
    Integer updateSysMenu(SysMenu sysMenu);

    /**
     * 更新对象:全更新
     * @param sysMenu
     * @return
     */
    Integer fullUpdateSysMenu(SysMenu sysMenu);

    /**
     * 批量更新
     * @param list
     */
    void updateSysMenuList(List<SysMenu> list);

    /**
     * 批量更新:全更新
     * @param list
     */
    void fullUpdateSysMenuList(List<SysMenu> list);

    /**
     * 删除对象
     * @param smId
     * @return
     */
    Integer deleteSysMenuByKey(Long smId);

    /**
     * 批量删除对象
     * @param list
     */
    void deleteSysMenuList(List<SysMenu> list);

    /**
     * 查询记录总数
     * @param sysMenu
     * @return
     */
    Long getSysMenuCount(SysMenu sysMenu);

    /**
     * 分页查询列表
     * @param sysMenu
     * @return
     */
    List<SysMenu> getSysMenuList(SysMenu sysMenu);

    /**
     * 获取表的最大ID
     * @return
     */
     Long selectMaxSysMenuId();

    /**
     * 查询记录总数
     * @param queryExample
     * @return
     */
     Long getSysMenuCountExample(QueryExample queryExample);

    /**
     * 分页查询列表
     * @param queryExample
     * @return
     */
     List<SysMenu> getSysMenuListExample(QueryExample queryExample);

     /**
      * 根据ID列表从数据库中查询列表
      * @param list
      * @return
      */
      List<SysMenu> findSysMenuListByIds(List<Long> list);
}

