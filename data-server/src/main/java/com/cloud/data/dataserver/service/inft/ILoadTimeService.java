package com.cloud.data.dataserver.service.inft;

import java.util.*;
import com.cloud.common.complexquery.QueryExample;
import com.alibaba.fastjson.JSONObject;
import com.cloud.data.dataserver.entity.LoadTime;

/**
 * ILoadTimeService service接口类
 * @author lijun
 */
public interface ILoadTimeService {

    /**
     * 根据主键获取对象
     * @param ltId
     * @return
     * @throws Exception
     */
    LoadTime loadLoadTimeByKey(Long ltId) throws Exception;

    /**
     * 普通查询获取单个结果
     * @param t
     * @param useCache
     * @return
     * @throws Exception
     */
    <T> LoadTime selectOneLoadTime(T t, Boolean useCache) throws Exception;

    /**
     * 新增对象
     * @param loadTime
     * @return
     * @throws Exception
     */
    Integer addLoadTime(LoadTime loadTime) throws Exception;

    /**
     * 批量新增对象
     * @param loadTimeList
     * @throws Exception
     */
    void addLoadTimeList(List<LoadTime> loadTimeList) throws Exception;

    /**
     * 更新对象
     * @param loadTime
     * @param isFullUpdate
     * @return
     * @throws Exception
     */
    Integer updateLoadTime(LoadTime loadTime,Boolean isFullUpdate) throws Exception;

    /**
     * 批量更新
     * @param loadTimeList
     * @param isFullUpdate
     * @throws Exception
     */
    void updateLoadTimeList(List<LoadTime> loadTimeList,Boolean isFullUpdate) throws Exception;

    /**
     * 删除对象
     * @param ltId
     * @return
     * @throws Exception
     */
    Integer deleteLoadTime(Long ltId) throws Exception;

    /**
     * 批量删除对象
     * @param ids
     * @throws Exception
     */
    void deleteLoadTimeList(List<Long> ids) throws Exception;

    /**
     * 查询记录总数
     * @param t
     * @param useCache
     * @return
     * @throws Exception
     */
    <T> Long getTotalLoadTime(T t,Boolean useCache) throws Exception;

    /**
     * 查询列表
     * @param t
     * @param useCache
     * @return
     * @throws Exception
     */
    <T> List<LoadTime> findLoadTimeList(T t, Boolean useCache) throws Exception;

    /**
     * 根据ID列表从数据库中查询列表
     * @param list
     * @return
     * @throws Exception
     */
    List<LoadTime> findLoadTimeListByIds(List<Long> list,Boolean useCache) throws Exception;

    /**
     * 分页查询列表
     * @param loadTime
     * @param useCache
     * @return
     * @throws Exception
     */
    JSONObject getPageLoadTime(LoadTime loadTime,Boolean useCache) throws Exception;

    /**
     * 分页查询列表 Example
     * @param queryExample
     * @param useCache
     * @return
     * @throws Exception
     */
    JSONObject getPageLoadTimeExample(QueryExample queryExample,Boolean useCache) throws Exception;

    /**
     * 保存记录
     * @param loadTime
     * @throws Exception
     */
     void saveLoadTime(LoadTime loadTime) throws Exception;

    /**
     * 批量保存记录
     * @param loadTimeList
     * @throws Exception
     */
     void saveLoadTimeList(List<LoadTime> loadTimeList) throws Exception;
}

