package com.thanple.little.boy.web;

import com.thanple.little.boy.web.entity.msg.RedisPubBean;
import com.thanple.little.boy.web.entity.msg.RedisPubMsg;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;

/**
 * Created by Thanple on 2018/5/24.
 * redis发布订阅消息demo
 */
@RunWith(SpringRunner.class)
@org.springframework.boot.test.context.SpringBootTest
public class RedisPublicMsgTest {
    private Logger log = LoggerFactory.getLogger(RedisPublicMsgTest.class);

    @Resource
    private RedisTemplate<String, Object> redisTemplate;

    /**
     * 测试发布一条redis消息，接收端在Springboot工程项目里面
     */
    @Test
    public void testPublish(){
        log.info("Sending message......");

        RedisPubMsg pubMsg = new RedisPubMsg();
        pubMsg.setId(1L);
        pubMsg.setPubBean( new RedisPubBean("Tom","Hello, SpringBoot redis message!!!!") );
//        String pubMsg = "Hello, SpringBoot redis message!!!!";
        redisTemplate.convertAndSend("sprinboot-redis-messaage", pubMsg);
    }

}
