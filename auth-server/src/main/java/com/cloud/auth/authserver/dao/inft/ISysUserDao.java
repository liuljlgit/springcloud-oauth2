package com.cloud.auth.authserver.dao.inft;

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
    Integer insertSysUser(SysUser sysUser);

    /**
     * 批量新增对象
     * @param list
     */
    void insertSysUserList(List<SysUser> list);

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
    Integer deleteSysUserByKey(Long suId);

    /**
     * 批量删除对象
     * @param list
     */
    void deleteSysUserList(List<SysUser> list);

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

