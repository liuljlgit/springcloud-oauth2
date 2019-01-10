package com.cloud.data.dataserver.cache.inft;

import java.util.*;
import com.cloud.common.complexquery.QueryExample;
import com.alibaba.fastjson.JSONObject;
import com.cloud.data.dataserver.entity.LoadTime;

/**
 * 缓存接口类 ILoadTimeRedis
 * @author lijun
 */
public interface ILoadTimeRedis {

    /**
     * 获取LoadTime的ID
     * @return
     */
    Long getLoadTimeId();

    /**
     * 在Spring容器初始化的时候，初始化该实体ID的最大值。
     * @return
     */
    void initMaxLoadTimeId();

    /**
     * 得到【ClassName:Id】值
     * @return
     * @throws Exception
     */
    String getLoadTimeKey(final Long ltId);

    /**
     * 把值存储到redis中
     * 1.Key-Value对象
     * 2.把key存储到一个set中,方便删除操作
     */
    String setLoadTime(LoadTime loadTime, long expire);

    /**
     * 从缓存中得到值
     * @param ltId
     * @return
     */
    LoadTime getLoadTime(final Long ltId);

    /**
     * 存储分页数量
     * @param t
     * @param count
     * @param expire
     * @return
     */
    <T> String setTotalLoadTime(T t, Long count, long expire) throws Exception;

    /**
     * 获取分页数量
     * @param t
     * @return
     */
    <T> Long getTotalLoadTime(T t) throws Exception;

    /**
     * 存储分页列表
     * @param t
     * @param list
     * @param expire
     * @return
     */
    <T> String setLoadTimeList(T t, List<LoadTime> list, long expire) throws Exception ;

    /**
     * 获取分页列表
     * @param t
     * @return
     */
    <T> JSONObject getLoadTimeList(T t) throws Exception ;

    /**
     * 根据id列表获取列表
     * @param list
     * @return
     * @throws Exception
     */
    List<LoadTime> getLoadTimeListByIds(List<Long> list) throws Exception;

    /**
     * 删除所有redis的值
     * 注意:慎用！！！！
     */
    void clearAllLoadTime();

    /**
     * 删除所有分页查询结果
     * 注意:慎用！！！！
     */
    void deleteAllHashSetByPage();

    /**
     * 根据主键删除 simulElecDistri对象
     * @return
     */
    void deleteLoadTime(Long ltId) ;
}

