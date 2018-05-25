package com.ledo.alpha.websocket.util;

/**
 * Created by Thanple on 2018/5/25.
 * 所有的常量写在这
 */
public class Constant {

    public static class Path {
        public static final String websocket_redis_properties = "websocket.redis.properties";
    }


    public static class Redis {
        public static String host = "redis.host";
        public static String port = "redis.port";
        public static String password = "redis.password";
        public static String timeout = "redis.timeout";
        public static String default_db = "redis.default.db";
        public static String max_total = "redis.maxTotal";
        public static String max_idle = "redis.maxIdle";
        public static String min_idle = "redis.minIdle";
        public static String max_wait_millis = "redis.maxWaitMillis";
        public static String test_on_borrow = "redis.testOnBorrow";
    }

}
