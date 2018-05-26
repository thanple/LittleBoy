package com.thanple.little.boy.websocket.redis;

import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Created by Thanple on 2018/5/25.
 */
public interface JedisAccess {

    /**
     * 删除
     * @param keys
     * @return
     */
    long del(String ...keys);

    /**
     * 发布消息
     * @param channel 频道，这里的channel就不序列化了，因为springboot那块也不会对频道序列化
     * @param message 需要发送的消息，正常序列化
     * @return
     */
    long publish(String channel, Object message);


    //string
    String set(String key, Object value);
    <V> V get(Class<V> classz, String key);

    //list
    long leftPush(String key, Object ... value);
    long rightPush(String key, Object ...value);
    <V> List<V> range(Class<V> classz, String key, long start, long end);

    //hash
    long hash(String key, Object hashKey, Object hashValue);
    <V> V hashGet(Class<V> classz, String key, Object hashKey);

    //set
    long setAdd(String key, Object ...value);
    boolean setContains(String key, Object value);

    //zset
    long zset(String key,  Map<Object,Double> value);
    <V> Set<V> zsetRange(Class<V> classz, String key, long start, long end);
}
