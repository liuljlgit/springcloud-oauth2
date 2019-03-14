package com.cloud.auth.authserver.cache.impl;

import java.util.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;
import org.springframework.beans.factory.annotation.Autowired;
import javax.annotation.PostConstruct;
import org.springframework.data.redis.core.RedisCallback;
import com.cloud.common.exception.BusiException;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.cloud.common.constant.IConst;
import com.cloud.common.redis.BaseRedis;
import com.cloud.common.utils.CommonUtil;
import com.cloud.common.complexquery.QueryExample;
import java.util.stream.Collectors;
import com.cloud.auth.authserver.entity.SysDept;
import com.cloud.auth.authserver.cache.inft.ISysDeptRedis;
import com.cloud.auth.authserver.dao.ISysDeptDao;

/**
 * 缓存实现类 SysDeptRedisImpl
 * 我们从v1版本中知道会有以下的优缺点:
 * 这个版本的缺陷点:频繁的删除（增、删、改就得删除所有）查询存储重复,比如一个分页查询。查询出来的结果会有重复的记录。造成内存压力
 * 改进办法：把查询中靶的数据存储到内存(ClassName:主键)中,同时使用（SET:ClassName）的set结构存储当前系统有哪些中标的记录（问题？如何保证这两个结构记录的同步性，redis的通知机制？一种办法：SET:ClassName把存储为永不过期，同时ClassName:主键过期时，清除对应set结构中的序号）。
 * 改进优点:分页查询只需要存储id,把分页查询查到的记录存储到成(ClassName::主键结构)。增、删、改只需要修改对应的记录，然后清除分页查询的id即可。（问题?如何保证再次排序。分页存储id需是有序性的）
 *
 * v2版本我们来改进以上的问题
 * @author lijun
 */
@Repository("sysDeptRedis")
public class SysDeptRedisImpl extends BaseRedis<String, SysDept> implements ISysDeptRedis{

    private static final Logger logger = LoggerFactory.getLogger(SysDeptRedisImpl.class);

    @Autowired
    private ISysDeptDao sysDeptDao;

    public final static String PRE_KEY = SysDept.class.getSimpleName();
    //SET:PRE_KEY结构先不启用
    private final static String SET_SYSDEPT_KEY = "SET:".concat(PRE_KEY);
    private final static String SEQ_SYSDEPT_KEY = "SEQ:".concat(PRE_KEY);
    private final static String PAGE_IDS_SYSDEPT_KEY = "PAGE:".concat(PRE_KEY).concat(":").concat("IDS");
    private final static String PAGE_TOTAL_SYSDEPT_KEY = "PAGE:".concat(PRE_KEY).concat(":").concat("TOTAL");
    private final static Long START_SYSDEPTID = 1000000L;

    /**
     * 获取SysDept的ID
     * @return
     */
    @Override
    public Long getSysDeptId(){
        redisTemplate.setEnableTransactionSupport(false);
        return redisTemplate.execute((RedisCallback<Long>) connection->{
            if ( !connection.exists(SEQ_SYSDEPT_KEY.getBytes())){
                Long id = sysDeptDao.selectMaxSysDeptId();
                id = ( null == id || id == 0) ?  START_SYSDEPTID +  Long.valueOf("1") : ++ id;
                if ( connection.setNX(SEQ_SYSDEPT_KEY.getBytes(), String.valueOf(id).getBytes())){
                    return  id;
                }
            }
            return connection.incr(redisTemplate.getStringSerializer().serialize(SEQ_SYSDEPT_KEY));
        });
    }

    /**
     * 在Spring容器初始化的时候，初始化该实体ID的最大值。
     * @return
     */
    @PostConstruct
    @Override
    public void initMaxSysDeptId(){
        redisTemplate.execute((RedisCallback<Long>) connection->{
            Long id = 0L;
            if ( !connection.exists(SEQ_SYSDEPT_KEY.getBytes())){
                id = sysDeptDao.selectMaxSysDeptId();
                if ( null == id || id == 0){
                    id = START_SYSDEPTID;
                }
                connection.setNX(SEQ_SYSDEPT_KEY.getBytes(), String.valueOf(id).getBytes());
            }
            return id;
        });
    }

    /**
     * 得到【ClassName:Id】值
     * @return
     * @throws Exception
     */
    public String getSysDeptKey(final Long sdId){
        return PRE_KEY.concat(":").concat(String.valueOf(sdId));
    }

    /**
     * 得到redis的key值
     * @param t
     * @param isEncrpt
     * @param isCountPage
     * @param <T>
     * @return
     * @throws Exception
     */
    private <T> String getRedisKey(T t,Boolean isEncrpt,Boolean isCountPage) throws Exception {
        String rsKey;
        if(t instanceof SysDept){
            rsKey = CommonUtil.createRedisKey(t,isEncrpt,isCountPage);
        } else if(t instanceof QueryExample){
            rsKey = CommonUtil.createExampleRedisKey(JSONObject.parseObject(JSON.toJSONString(t)),isEncrpt,isCountPage);
        }else{
            throw new BusiException("类型不正确");
        }
        return rsKey;
    }

    /**
     * 把值存储到redis中
     * 1.Key-Value对象
     * 2.把key存储到一个set中,方便删除操作
     */
    @Override
    public String setSysDept(SysDept sysDept, long expire){
        String primaryKey = getSysDeptKey(sysDept.getSdId());
        set(primaryKey, sysDept, expire);
        return primaryKey ;
    }

    /**
     * 从缓存中得到值
     * @param sdId
     * @return
     */
    @Override
    public SysDept getSysDept(final Long sdId){
        String primaryKey = getSysDeptKey(sdId);
        SysDept sysDept = get(primaryKey,SysDept.class);
        return sysDept;
    }

    /**
     * 存储分页数量
     * @param t
     * @param count
     * @param expire
     * @return
     */
    @Override
    public <T> String setTotalSysDept(T t, Long count, long expire) throws Exception {
        String rsKey = getRedisKey(t,true,true);
        hset(PAGE_TOTAL_SYSDEPT_KEY,rsKey,count,expire);
        return rsKey ;
    }

    /**
     * 获取分页数量
     * @param t
     * @return
     */
    @Override
    public <T> Long getTotalSysDept(T t) throws Exception {
        String rsKey = getRedisKey(t,true,true);
        Long count = hget(PAGE_TOTAL_SYSDEPT_KEY, rsKey, Long.class);
        return count;
    }

    /**
     * 存储分页列表
     * @param t
     * @param list
     * @param expire
     * @return
     */
    @Override
    public <T> String setSysDeptList(T t, List<SysDept> list, long expire) throws Exception {
        String rsKey = getRedisKey(t,true,false);
        JSONArray ids = new JSONArray();
        list.stream().forEach(e->{
            ids.add(e.getSdId());
            setSysDept(e, expire);
        });
        hset(PAGE_IDS_SYSDEPT_KEY,rsKey,ids.toJSONString(),expire);
        return rsKey ;
    }

    /**
     * 获取分页列表
     * @param t
     * @return
     */
    @Override
    public <T> JSONObject getSysDeptList(T t) throws Exception {
        String rsKey = getRedisKey(t,true,false);
        String hsetValue = hget(PAGE_IDS_SYSDEPT_KEY, rsKey, String.class);
        if(Objects.isNull(hsetValue)){
            return null;
        }
        //获取分页查询在redis中存储的id列表
        List<Long> ids = JSONArray.parseArray(hsetValue, Long.class);
        List<String> listKeys = ids.stream().map(e -> getSysDeptKey(e)).collect(Collectors.toList());
        //根据key列表从redis中获取值
        List<SysDept> sysDepts = listKeys.size() > 0 ? mget(listKeys, SysDept.class) : Collections.emptyList() ;
        //返回结果列表,这个列表还需要进行处理
        return new JSONObject(){{
           put("ids",JSON.toJSONString(ids));
           put("sysDepts",JSON.toJSONString(sysDepts));
        }};
    }

    /**
     * 根据id列表获取列表
     * @param list
     * @return
     * @throws Exception
     */
    @Override
    public List<SysDept> getSysDeptListByIds(List<Long> list) throws Exception {
        List<String> listKeys = list.stream().map(e -> getSysDeptKey(e)).collect(Collectors.toList());
        List<SysDept> sysDepts = listKeys.size() > 0 ? mget(listKeys, SysDept.class) : Collections.emptyList() ;
        return sysDepts;
    }

    /**
     * 删除所有redis的值
     * 注意:慎用！！！！
     */
    @Override
    public void clearAllSysDept() {
        clean(PRE_KEY.concat(":").concat("*"));
        deleteAllHashSetByPage();
    }

    /**
     * 删除所有分页查询结果
     * 注意:慎用！！！！
     */
    @Override
    public void deleteAllHashSetByPage() {
        delete(PAGE_IDS_SYSDEPT_KEY,true);
        delete(PAGE_TOTAL_SYSDEPT_KEY,true);
    }

    /**
     * 根据主键删除sysDept对象
     * @return
     */
    @Override
    public void deleteSysDept(Long sdId) {
        delete(new ArrayList<String>(){{
            add(getSysDeptKey(sdId));
        }}) ;
    }

}

