package com.ledo.alpha.websocket.redis;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

/**
 * Created by Thanple on 2018/5/25.
 *
 */
public class JedisAccessFactory {

    /** JedisPool，单例 */
    private static JedisPool jedisPool = null;
    static {
        RedisConfig config = RedisConfig.getRedisConfig();
        jedisPool = new JedisPool(config.getPoolConfig(), config.getHost(), config.getPort(), config.getTimeout(), config.getPassword());
    }

    private JedisAccessFactory(){}

    /**
     * @return 从jedisPool中获取一个Jedis访问实例，用完记得释放
     */
    public static Jedis getJedis() {
        return jedisPool.getResource();
    }

    /**
     * 返回JedisAccess的工厂方法，不要直接去new一个JedisAccess实例
     * @return
     */
    public static JedisAccess getJedisAccess(){
        return getJedisAccess(true);
    }

    public static JedisAccess getJedisAccess(boolean autoRelease){
        Jedis jedis = null;
        if( !autoRelease ) {
            jedis = getJedis();
        }
        JedisAccess jedisAccess = new JedisAccessImpl(jedis, autoRelease);
        return jedisAccess;
    }

}
