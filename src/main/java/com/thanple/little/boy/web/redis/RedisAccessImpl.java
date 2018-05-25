package com.thanple.little.boy.web.redis;

import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ZSetOperations;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Set;

/**
 * Created by Thanple on 2018/5/11.
 */
@Service
public class RedisAccessImpl implements RedisAccess {
    @Resource
    private RedisTemplate<String, Object> redisTemplate;

    @Override
    public RedisTemplate<String, Object> getRedisTemplate() {
        return redisTemplate;
    }

    @Override
    public void set(String key, Object value) {
        redisTemplate.opsForValue().set(key,value);;
    }


    @SuppressWarnings("unchecked")
    @Override
    public <V> V get(String key) {
        return (V)redisTemplate.opsForValue().get(key);
    }

    @Override
    public void leftPush(String key, Object value){
        redisTemplate.opsForList().leftPush(key,value);
    }
    @Override
    public void rightPush(String key, Object value){
        redisTemplate.opsForList().rightPush(key,value);
    }

    @SuppressWarnings("unchecked")
    @Override
    public <V> List<V> range(String key, long start, long end) {
        return (List<V>)(List)redisTemplate.opsForList().range(key, start, end);
    }

    @Override
    public void hash(String key, Object hashKey, Object hashValue) {
        redisTemplate.opsForHash().put(key,hashKey,hashValue);
    }

    @SuppressWarnings("unchecked")
    @Override
    public <V> V hashGet(String key, Object hashKey) {
        return (V)redisTemplate.opsForHash().get(key,hashKey);
    }

    @Override
    public void hset(String key, Object value) {
        redisTemplate.opsForSet().add(key,value);
    }

    @Override
    public boolean hsetContains(String key, Object value) {
        return redisTemplate.opsForSet().isMember(key,value);
    }

    @SuppressWarnings("unchecked")
    @Override
    public <V> Set<V> hsetGet(String key) {
        return (Set<V>)(Set)redisTemplate.opsForSet().members(key);
    }

    @Override
    public void zset(String key, Set<ZSetOperations.TypedTuple<Object>> value) {
        redisTemplate.opsForZSet().add(key,value);
    }

    @SuppressWarnings("unchecked")
    @Override
    public <V> Set<V> zsetRange(String key,long start, long end) {
        return (Set<V>)(Set)redisTemplate.opsForZSet().range(key,start,end);
    }

}
