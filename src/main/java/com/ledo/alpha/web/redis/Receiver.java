package com.ledo.alpha.web.redis;

import com.ledo.alpha.web.entity.msg.RedisPubMsg;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by Thanple on 2018/5/24.
 */
public class Receiver {
    private static final Logger logger = LoggerFactory.getLogger(Receiver.class);



    public void receiveMessage(RedisPubMsg message) {
//        RedisPubMsg pubMsg = (RedisPubMsg)RedisConfig.getSerializer().deserialize(message.getBytes());
        logger.info("Received RedisPubMsg <" + message + ">");
    }
}
