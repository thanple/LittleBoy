package com.ledo.alpha.web.redis;

import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ZSetOperations;

import java.util.List;
import java.util.Set;

/**
 * Created by Thanple on 2018/5/11.
 * redis数据类型：string,list,hash,set,zset
 * demo详见 https://blog.csdn.net/hutaodsb931211/article/details/79478235
 */
public interface RedisAccess {

    RedisTemplate<String, Object> getRedisTemplate();

    //string
    void set(String key, Object value);
    <V> V get(String key);

    //list
    void leftPush(String key, Object value);
    void rightPush(String key, Object value);
    <V> List<V> range(String key, long start, long end);

    //hash
    void hash(String key, Object hashKey, Object hashValue);
    <V> V hashGet(String key, Object hashKey);

    //set
    void hset(String key, Object value);
    boolean hsetContains(String key, Object value);
    <V> Set<V> hsetGet(String key);

    //zset
    void zset(String key, Set<ZSetOperations.TypedTuple<Object>> value);
    <V> Set<V> zsetRange(String key, long start, long end);
}
