package com.cloud.auth.authserver.dao.inft;

import java.util.*;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;
import com.cloud.common.complexquery.QueryExample;
import com.cloud.auth.authserver.entity.SysDept;

/**
  * 接口类 ISysDeptDao
  * @author lijun
  */
@Repository
public interface ISysDeptDao {

    /**
     * 根据主键获取对象
     * @param sdId
     * @return
     */
    SysDept loadSysDeptByKey(Long sdId);

    /**
     * 新增对象
     * @param sysDept
     * @return
     */
    Integer insertSysDept(SysDept sysDept);

    /**
     * 批量新增对象
     * @param list
     */
    void insertSysDeptList(List<SysDept> list);

    /**
     * 更新对象
     * @param sysDept
     * @return
     */
    Integer updateSysDept(SysDept sysDept);

    /**
     * 更新对象:全更新
     * @param sysDept
     * @return
     */
    Integer fullUpdateSysDept(SysDept sysDept);

    /**
     * 批量更新
     * @param list
     */
    void updateSysDeptList(List<SysDept> list);

    /**
     * 批量更新:全更新
     * @param list
     */
    void fullUpdateSysDeptList(List<SysDept> list);

    /**
     * 删除对象
     * @param sdId
     * @return
     */
    Integer deleteSysDeptByKey(Long sdId);

    /**
     * 批量删除对象
     * @param list
     */
    void deleteSysDeptList(List<SysDept> list);

    /**
     * 查询记录总数
     * @param sysDept
     * @return
     */
    Long getSysDeptCount(SysDept sysDept);

    /**
     * 分页查询列表
     * @param sysDept
     * @return
     */
    List<SysDept> getSysDeptList(SysDept sysDept);

    /**
     * 获取表的最大ID
     * @return
     */
     Long selectMaxSysDeptId();

    /**
     * 查询记录总数
     * @param queryExample
     * @return
     */
     Long getSysDeptCountExample(QueryExample queryExample);

    /**
     * 分页查询列表
     * @param queryExample
     * @return
     */
     List<SysDept> getSysDeptListExample(QueryExample queryExample);

     /**
      * 根据ID列表从数据库中查询列表
      * @param list
      * @return
      */
      List<SysDept> findSysDeptListByIds(List<Long> list);
}

