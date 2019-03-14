package com.cloud.auth.authserver.dao;

import java.util.*;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;
import com.cloud.common.complexquery.QueryExample;
import com.cloud.auth.authserver.entity.SysCompany;

/**
  * 接口类 ISysCompanyDao
  * @author lijun
  */
@Repository
public interface ISysCompanyDao {

    /**
     * 根据主键获取对象
     * @param scId
     * @return
     */
    SysCompany loadSysCompanyByKey(Long scId);

    /**
     * 新增对象
     * @param sysCompany
     * @return
     */
    Integer addSysCompany(SysCompany sysCompany);

    /**
     * 批量新增对象
     * @param list
     */
    void addSysCompanyList(List<SysCompany> list);

    /**
     * 更新对象
     * @param sysCompany
     * @return
     */
    Integer updateSysCompany(SysCompany sysCompany);

    /**
     * 更新对象:全更新
     * @param sysCompany
     * @return
     */
    Integer fullUpdateSysCompany(SysCompany sysCompany);

    /**
     * 批量更新
     * @param list
     */
    void updateSysCompanyList(List<SysCompany> list);

    /**
     * 批量更新:全更新
     * @param list
     */
    void fullUpdateSysCompanyList(List<SysCompany> list);

    /**
     * 删除对象
     * @param scId
     * @return
     */
    Integer deleteSysCompany(Long scId);

    /**
     * 批量删除对象
     * @param ids
     */
    void deleteSysCompanyList(List<Long> ids);

    /**
     * 查询记录总数
     * @param sysCompany
     * @return
     */
    Long getSysCompanyCount(SysCompany sysCompany);

    /**
     * 分页查询列表
     * @param sysCompany
     * @return
     */
    List<SysCompany> getSysCompanyList(SysCompany sysCompany);

    /**
     * 获取表的最大ID
     * @return
     */
     Long selectMaxSysCompanyId();

    /**
     * 查询记录总数
     * @param queryExample
     * @return
     */
     Long getSysCompanyCountExample(QueryExample queryExample);

    /**
     * 分页查询列表
     * @param queryExample
     * @return
     */
     List<SysCompany> getSysCompanyListExample(QueryExample queryExample);

     /**
      * 根据ID列表从数据库中查询列表
      * @param list
      * @return
      */
      List<SysCompany> findSysCompanyListByIds(List<Long> list);
}

