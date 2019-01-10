package com.cloud.data.dataserver.dao;

import java.util.*;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;
import com.cloud.common.complexquery.QueryExample;
import com.cloud.data.dataserver.entity.LoadTime;

/**
  * 接口类 ILoadTimeDao
  * @author lijun
  */
@Repository
public interface ILoadTimeDao {

    /**
     * 根据主键获取对象
     * @param ltId
     * @return
     */
    LoadTime loadLoadTimeByKey(Long ltId);

    /**
     * 新增对象
     * @param loadTime
     * @return
     */
    Integer addLoadTime(LoadTime loadTime);

    /**
     * 批量新增对象
     * @param list
     */
    void addLoadTimeList(List<LoadTime> list);

    /**
     * 更新对象
     * @param loadTime
     * @return
     */
    Integer updateLoadTime(LoadTime loadTime);

    /**
     * 更新对象:全更新
     * @param loadTime
     * @return
     */
    Integer fullUpdateLoadTime(LoadTime loadTime);

    /**
     * 批量更新
     * @param list
     */
    void updateLoadTimeList(List<LoadTime> list);

    /**
     * 批量更新:全更新
     * @param list
     */
    void fullUpdateLoadTimeList(List<LoadTime> list);

    /**
     * 删除对象
     * @param ltId
     * @return
     */
    Integer deleteLoadTime(Long ltId);

    /**
     * 批量删除对象
     * @param ids
     */
    void deleteLoadTimeList(List<Long> ids);

    /**
     * 查询记录总数
     * @param loadTime
     * @return
     */
    Long getLoadTimeCount(LoadTime loadTime);

    /**
     * 分页查询列表
     * @param loadTime
     * @return
     */
    List<LoadTime> getLoadTimeList(LoadTime loadTime);

    /**
     * 获取表的最大ID
     * @return
     */
     Long selectMaxLoadTimeId();

    /**
     * 查询记录总数
     * @param queryExample
     * @return
     */
     Long getLoadTimeCountExample(QueryExample queryExample);

    /**
     * 分页查询列表
     * @param queryExample
     * @return
     */
     List<LoadTime> getLoadTimeListExample(QueryExample queryExample);

     /**
      * 根据ID列表从数据库中查询列表
      * @param list
      * @return
      */
      List<LoadTime> findLoadTimeListByIds(List<Long> list);
}

