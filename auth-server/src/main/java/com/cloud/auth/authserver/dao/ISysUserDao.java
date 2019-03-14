package com.cloud.auth.authserver.dao;

import java.util.*;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;
import com.cloud.common.complexquery.QueryExample;
import com.cloud.auth.authserver.entity.SysUser;

/**
  * 接口类 ISysUserDao
  * @author lijun
  */
@Repository
public interface ISysUserDao {

    /**
     * 根据主键获取对象
     * @param suId
     * @return
     */
    SysUser loadSysUserByKey(Long suId);

    /**
     * 新增对象
     * @param sysUser
     * @return
     */
    Integer addSysUser(SysUser sysUser);

    /**
     * 批量新增对象
     * @param list
     */
    void addSysUserList(List<SysUser> list);

    /**
     * 更新对象
     * @param sysUser
     * @return
     */
    Integer updateSysUser(SysUser sysUser);

    /**
     * 更新对象:全更新
     * @param sysUser
     * @return
     */
    Integer fullUpdateSysUser(SysUser sysUser);

    /**
     * 批量更新
     * @param list
     */
    void updateSysUserList(List<SysUser> list);

    /**
     * 批量更新:全更新
     * @param list
     */
    void fullUpdateSysUserList(List<SysUser> list);

    /**
     * 删除对象
     * @param suId
     * @return
     */
    Integer deleteSysUser(Long suId);

    /**
     * 批量删除对象
     * @param ids
     */
    void deleteSysUserList(List<Long> ids);

    /**
     * 查询记录总数
     * @param sysUser
     * @return
     */
    Long getSysUserCount(SysUser sysUser);

    /**
     * 分页查询列表
     * @param sysUser
     * @return
     */
    List<SysUser> getSysUserList(SysUser sysUser);

    /**
     * 获取表的最大ID
     * @return
     */
     Long selectMaxSysUserId();

    /**
     * 查询记录总数
     * @param queryExample
     * @return
     */
     Long getSysUserCountExample(QueryExample queryExample);

    /**
     * 分页查询列表
     * @param queryExample
     * @return
     */
     List<SysUser> getSysUserListExample(QueryExample queryExample);

     /**
      * 根据ID列表从数据库中查询列表
      * @param list
      * @return
      */
      List<SysUser> findSysUserListByIds(List<Long> list);
}

