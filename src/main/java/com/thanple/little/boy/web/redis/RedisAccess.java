package com.thanple.little.boy.web.redis;

import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ZSetOperations;

import java.util.List;
import java.util.Set;

/**
 * Created by Thanple on 2018/5/11.
 * redis数据类型：string,list,hash,set,zset
 * demo详见 https://blog.csdn.net/hutaodsb931211/article/details/79478235
 */
public interface RedisAccess<V> {

    void setClassType(Class<V> classz);
    RedisTemplate<String, V> getRedisTemplate();

    //string
    void set(String key, V value);
    <V> V get(String key);

    //list
    void leftPush(String key, V value);
    void rightPush(String key, V value);
    <V> List<V> range(String key, long start, long end);

    //hash
    void hash(String key, Object hashKey, V hashValue);
    <HK,HV> HV hashGet(String key, HK hashKey);

    //set
    void hset(String key, V value);
    boolean hsetContains(String key, V value);
    <V> Set<V> hsetGet(String key);

    //zset
    void zset(String key, Set<ZSetOperations.TypedTuple<V>> value);
    <V> Set<V> zsetRange(String key, long start, long end);
}
