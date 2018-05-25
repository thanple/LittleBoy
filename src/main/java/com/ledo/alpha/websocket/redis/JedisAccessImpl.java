package com.ledo.alpha.websocket.redis;

import redis.clients.jedis.Jedis;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Created by Thanple on 2018/5/25.
 * 对Redis的访问类
 */
public class JedisAccessImpl implements JedisAccess {

    /** 每个方法使用完就释放，如果需要多个操作在一个事务中，则一般为false ，当为false时记得在finally中关闭连接 */
    private boolean autoRelease = true;
    private Jedis jedis;
    public JedisAccessImpl(Jedis jedis, boolean autoRelease) {
        this.jedis = jedis;
        this.autoRelease = autoRelease;
    }

    /**
     * 如果没有则从pool中获取一个Jedis实例
     * @return
     */
    public Jedis getJedisInstance() {
        return jedis == null ? JedisAccessFactory.getJedis() : jedis;
    }

    @Override
    public long del(String... keys) {
        Jedis jedis = getJedisInstance();

        long result = jedis.del(convertToByteArray(keys));
        if(autoRelease){
            jedis.close();
        }
        return result;
    }

    @Override
    public long publish(String channel, Object message) {
        Jedis jedis = getJedisInstance();

        long result = jedis.publish(channel.getBytes(), RedisConfig.getSerializer().serialize(message));
        if(autoRelease){
            jedis.close();
        }
        return result;
    }

    @Override
    public String set(String key, Object value) {
        Jedis jedis = getJedisInstance();
        String resStr = jedis.set(RedisConfig.getSerializer().serialize(key) , RedisConfig.getSerializer().serialize(value));
        if(autoRelease){
            jedis.close();
        }
        return resStr;
    }

    @SuppressWarnings("unchecked")
    public <V> V get(String key) {
        Jedis jedis = getJedisInstance();
        byte[] result = jedis.get(RedisConfig.getSerializer().serialize(key));
        if(autoRelease){
            jedis.close();
        }
        return (V)RedisConfig.getSerializer().deserialize(result);
    }

    private static byte[][] convertToByteArray(Object ...value) {
        byte[][] valueBytesArray = new byte[value.length][];
        for(int i=0; i<value.length ;i++) {
            valueBytesArray[i] = RedisConfig.getSerializer().serialize(value[i]);
        }
        return valueBytesArray;
    }

    @Override
    public long leftPush(String key, Object ...value) {
        Jedis jedis = getJedisInstance();

        long result = jedis.lpush(RedisConfig.getSerializer().serialize(key) , convertToByteArray(value) );
        if(autoRelease){
            jedis.close();
        }
        return result;
    }

    @Override
    public long rightPush(String key, Object ...value) {
        Jedis jedis = getJedisInstance();

        long result = jedis.rpush(RedisConfig.getSerializer().serialize(key) , convertToByteArray(value));
        if(autoRelease){
            jedis.close();
        }
        return result;
    }

    @SuppressWarnings("unchecked")
    @Override
    public <V> List<V> range(String key, long start, long end) {
        Jedis jedis = getJedisInstance();

        List<byte[]> bytesResult = jedis.lrange(RedisConfig.getSerializer().serialize(key),start,end);
        if(autoRelease){
            jedis.close();
        }
        List<Object> result = new ArrayList<>(bytesResult.size());
        for(int i=0; i<bytesResult.size() ;i++) {
            byte[] bytes = bytesResult.get(i);
            result.add(RedisConfig.getSerializer().deserialize(bytes));
        }
        return (List<V>)result;
    }

    @Override
    public long hash(String key, Object hashKey, Object hashValue) {
        Jedis jedis = getJedisInstance();

        long result = jedis.hset(RedisConfig.getSerializer().serialize(key) , RedisConfig.getSerializer().serialize(hashKey) , RedisConfig.getSerializer().serialize(hashValue));
        if(autoRelease){
            jedis.close();
        }
        return result;
    }


    @SuppressWarnings("unchecked")
    @Override
    public <V> V hashGet(String key, Object hashKey) {
        Jedis jedis = getJedisInstance();
        byte[] result = jedis.hget(RedisConfig.getSerializer().serialize(key) , RedisConfig.getSerializer().serialize(hashKey));
        if(autoRelease){
            jedis.close();
        }
        return (V)RedisConfig.getSerializer().deserialize(result);
    }

    @Override
    public long setAdd(String key, Object ...value) {
        Jedis jedis = getJedisInstance();

        long result = jedis.sadd(RedisConfig.getSerializer().serialize(key) , convertToByteArray(value));
        if(autoRelease){
            jedis.close();
        }
        return result;
    }

    @Override
    public boolean setContains(String key, Object value) {
        Jedis jedis = getJedisInstance();

        boolean result = jedis.sismember(RedisConfig.getSerializer().serialize(key) , RedisConfig.getSerializer().serialize(value));
        if(autoRelease){
            jedis.close();
        }
        return result;
    }


    @Override
    public long zset(String key, Map<Object,Double> value) {
        Jedis jedis = getJedisInstance();
        Map<byte[],Double> bytesHeightMap = new HashMap<byte[],Double>();
        for(Map.Entry<Object,Double> entry : value.entrySet()) {
            bytesHeightMap.put(RedisConfig.getSerializer().serialize(entry.getKey()) , entry.getValue());
        }
        long result = jedis.zadd(RedisConfig.getSerializer().serialize(key) , bytesHeightMap);
        if(autoRelease){
            jedis.close();
        }
        return result;
    }

    @SuppressWarnings("unchecked")
    @Override
    public <V> Set<V> zsetRange(String key, long start, long end) {
        Jedis jedis = getJedisInstance();

        Iterator<byte[]> bytesResultIt = jedis.zrange(RedisConfig.getSerializer().serialize(key),start,end).iterator();
        if(autoRelease){
            jedis.close();
        }
        Set<Object> result = new LinkedHashSet<Object>();
        while(bytesResultIt.hasNext()) {
            byte[] bytes = bytesResultIt.next();
            result.add(RedisConfig.getSerializer().deserialize(bytes));
        }
        return (Set<V>)result;
    }


}
