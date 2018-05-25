package com.ledo.alpha.websocket.redis;

/**
 * Created by Thanple on 2018/5/25.
 */
public class RedisSerializationException extends RuntimeException{

    public RedisSerializationException(String message) {
        super(message);
    }

    public RedisSerializationException(String message, Throwable cause) {
        super(message, cause);
    }

    public RedisSerializationException(Throwable cause) {
        super(cause);
    }

    public RedisSerializationException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
