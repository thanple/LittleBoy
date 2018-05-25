package com.thanple.little.boy.websocket.redis;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.thanple.little.boy.websocket.util.Constant;
import com.thanple.little.boy.websocket.util.PropertiesLoader;
import lombok.Data;
import redis.clients.jedis.JedisPoolConfig;

import java.util.Properties;

/**
 * Created by Thanple on 2018/5/25.
 */
@Data
public class RedisConfig {

    private String host;
    private int port;
    private int timeout;
    private String password;


    /** 基于 JedisPool的配置 */
    private JedisPoolConfig poolConfig;

    /**
     * Redis序列化方式：采用jackson2JsonRedisSerializer
     * @return
     */
    public static RedisSerializer getSerializer(){
        RedisSerializer<Object> jackson2JsonRedisSerializer = new RedisSerializer<Object>(Object.class);
        ObjectMapper om = new ObjectMapper();
        om.setVisibility(PropertyAccessor.ALL, JsonAutoDetect.Visibility.ANY);
        om.enableDefaultTyping(ObjectMapper.DefaultTyping.NON_FINAL);
        jackson2JsonRedisSerializer.setObjectMapper(om);
        return jackson2JsonRedisSerializer;
    }

    /**
     * 获得一个JedisPool的配置对象
     * @return
     */
    public static JedisPoolConfig getJedisPoolConfig(){
        Properties properties = PropertiesLoader.INSTANCE.getProperties(Constant.Path.websocket_redis_properties);
        String maxIdle = properties.getProperty(Constant.Redis.max_idle);
        String minIdle = properties.getProperty(Constant.Redis.min_idle);
        String maxWaitMillis = properties.getProperty(Constant.Redis.max_wait_millis);
        String testOnBorrow = properties.getProperty(Constant.Redis.test_on_borrow);
        String maxTotal= properties.getProperty(Constant.Redis.max_total);

        JedisPoolConfig config = new JedisPoolConfig();
        //控制一个pool可分配多少个jedis实例，通过pool.getResource()来获取；
        //如果赋值为-1，则表示不限制；如果pool已经分配了maxActive个jedis实例，则此时pool的状态为exhausted(耗尽)。
        config.setMaxTotal(Integer.parseInt(maxTotal));
        config.setMinIdle(Integer.parseInt(minIdle));
        //控制一个pool最多有多少个状态为idle(空闲的)的jedis实例。
        config.setMaxIdle(Integer.parseInt(maxIdle));
        //表示当borrow(引入)一个jedis实例时，最大的等待时间，如果超过等待时间，则直接抛出JedisConnectionException；
        config.setMaxWaitMillis(Long.parseLong(maxWaitMillis));
        //在borrow一个jedis实例时，是否提前进行validate操作；如果为true，则得到的jedis实例均是可用的；
        config.setTestOnBorrow(Boolean.valueOf(testOnBorrow));

        return config;
    }

    /**
     * 获得一个RedisConfig的配置对象，里面封装了JedisPoolConfig
     * @return
     */
    public static RedisConfig getRedisConfig(){
        Properties properties = PropertiesLoader.INSTANCE.getProperties(Constant.Path.websocket_redis_properties);
        String host = properties.getProperty(Constant.Redis.host);
        int port = Integer.parseInt(properties.getProperty(Constant.Redis.port));
        int timeout = Integer.parseInt(properties.getProperty(Constant.Redis.timeout));
        String password = properties.getProperty(Constant.Redis.password);

        RedisConfig redisConfig = new RedisConfig();
        redisConfig.host = host;
        redisConfig.port = port;
        redisConfig.timeout = timeout;
        redisConfig.password = "".equals(password) ? null : password;

        redisConfig.poolConfig = getJedisPoolConfig();
        return redisConfig;
    }
}
