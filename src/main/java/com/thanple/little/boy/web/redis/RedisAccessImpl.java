package com.thanple.little.boy.web.redis;

import com.thanple.little.boy.web.config.RedisConfig;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ZSetOperations;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Set;

/**
 * Created by Thanple on 2018/5/11.
 */
@Service
public class RedisAccessImpl<V> implements RedisAccess<V> {
    @Resource
    private RedisTemplate<String, V> redisTemplate;

    private Class<V> javaType;

    @Override
    public void setClassType(Class<V> classz){
        this.javaType = classz;
    }


    @Override
    public RedisTemplate<String, V> getRedisTemplate() {
        if(javaType != null) {
            //需要重新设置一遍序列化，因为动态序列化对象需要Class
            RedisSerializer<V> jackson2JsonRedisSerializer = RedisConfig.getSerializer(javaType);
            redisTemplate.setKeySerializer(jackson2JsonRedisSerializer);
            redisTemplate.setValueSerializer(jackson2JsonRedisSerializer);
            redisTemplate.setHashKeySerializer(jackson2JsonRedisSerializer);
            redisTemplate.setHashValueSerializer(jackson2JsonRedisSerializer);
        }
        return redisTemplate;
    }

    @Override
    public void set(String key, V value) {
        getRedisTemplate().opsForValue().set(key,value);;
    }


    @SuppressWarnings("unchecked")
    @Override
    public V get(String key) {
        return getRedisTemplate().opsForValue().get(key);
    }

    @Override
    public void leftPush(String key, V value){
        getRedisTemplate().opsForList().leftPush(key,value);
    }
    @Override
    public void rightPush(String key, V value){
        getRedisTemplate().opsForList().rightPush(key,value);
    }


    @Override
    public List<V> range(String key, long start, long end) {
        return getRedisTemplate().opsForList().range(key, start, end);
    }

    @Override
    public void hash(String key, Object hashKey, Object hashValue) {
        getRedisTemplate().opsForHash().put(key,hashKey,hashValue);
    }


    @Override
    public <HK,HV> HV hashGet(String key, HK hashKey) {
        return getRedisTemplate().<HK,HV>opsForHash().get(key,hashKey);
    }

    @Override
    public void hset(String key, V value) {
        getRedisTemplate().opsForSet().add(key,value);
    }

    @Override
    public boolean hsetContains(String key, Object value) {
        return getRedisTemplate().opsForSet().isMember(key,value);
    }

    @Override
    public Set<V> hsetGet(String key) {
        return getRedisTemplate().opsForSet().members(key);
    }

    @Override
    public void zset(String key, Set<ZSetOperations.TypedTuple<V>> value) {
        getRedisTemplate().opsForZSet().add(key,value);
    }

    @Override
    public Set<V> zsetRange(String key,long start, long end) {
        return getRedisTemplate().opsForZSet().range(key,start,end);
    }

}
