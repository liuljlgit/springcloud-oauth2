package com.cloud.auth.authserver.dao;

import java.util.*;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;
import com.cloud.common.complexquery.QueryExample;
import com.cloud.auth.authserver.entity.SysDeptRole;

/**
  * 接口类 ISysDeptRoleDao
  * @author lijun
  */
@Repository
public interface ISysDeptRoleDao {

    /**
     * 根据主键获取对象
     * @param sdrId
     * @return
     */
    SysDeptRole loadSysDeptRoleByKey(Long sdrId);

    /**
     * 新增对象
     * @param sysDeptRole
     * @return
     */
    Integer addSysDeptRole(SysDeptRole sysDeptRole);

    /**
     * 批量新增对象
     * @param list
     */
    void addSysDeptRoleList(List<SysDeptRole> list);

    /**
     * 更新对象
     * @param sysDeptRole
     * @return
     */
    Integer updateSysDeptRole(SysDeptRole sysDeptRole);

    /**
     * 更新对象:全更新
     * @param sysDeptRole
     * @return
     */
    Integer fullUpdateSysDeptRole(SysDeptRole sysDeptRole);

    /**
     * 批量更新
     * @param list
     */
    void updateSysDeptRoleList(List<SysDeptRole> list);

    /**
     * 批量更新:全更新
     * @param list
     */
    void fullUpdateSysDeptRoleList(List<SysDeptRole> list);

    /**
     * 删除对象
     * @param sdrId
     * @return
     */
    Integer deleteSysDeptRole(Long sdrId);

    /**
     * 批量删除对象
     * @param ids
     */
    void deleteSysDeptRoleList(List<Long> ids);

    /**
     * 查询记录总数
     * @param sysDeptRole
     * @return
     */
    Long getSysDeptRoleCount(SysDeptRole sysDeptRole);

    /**
     * 分页查询列表
     * @param sysDeptRole
     * @return
     */
    List<SysDeptRole> getSysDeptRoleList(SysDeptRole sysDeptRole);

    /**
     * 获取表的最大ID
     * @return
     */
     Long selectMaxSysDeptRoleId();

    /**
     * 查询记录总数
     * @param queryExample
     * @return
     */
     Long getSysDeptRoleCountExample(QueryExample queryExample);

    /**
     * 分页查询列表
     * @param queryExample
     * @return
     */
     List<SysDeptRole> getSysDeptRoleListExample(QueryExample queryExample);

     /**
      * 根据ID列表从数据库中查询列表
      * @param list
      * @return
      */
      List<SysDeptRole> findSysDeptRoleListByIds(List<Long> list);
}

