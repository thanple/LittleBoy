package com.ledo.alpha.web.controller;

import com.ledo.alpha.web.redis.RedisLock;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * Created by Thanple on 2018/5/23.
 */
@RestController
@RequestMapping("/redis")
public class RedisDemoController {
    private Logger log = LoggerFactory.getLogger(RedisDemoController.class);

    @Resource
    private RedisTemplate<String, Object> redisTemplate;

    private static int count = 0;

    /**
     * 这是一个基于redis分布式锁restful风格的并发加法计数器
     * @return
     */
    @RequestMapping("/count")
    @ResponseBody
    public String lockCount(){
        String key = "REDIS_COUNT";
        RedisLock lock = new RedisLock(redisTemplate, key );
        try{
            long startTime = System.currentTimeMillis();
            if(lock.lock()){
                long endTime = System.currentTimeMillis();
                long passed = endTime - startTime;
                log.info("\n********* get lock time={}ms, tryCout={}", passed, lock.getTryCount() );

                count++;
                Thread.sleep( (long)(Math.random() * 10) );
            }else{
                throw new RuntimeException("Cannot get the lock!!!");
            }
        }catch (InterruptedException e){
            e.printStackTrace();
        }finally{
            lock.unlock();
        }

        return String.valueOf(count);
    }
}
