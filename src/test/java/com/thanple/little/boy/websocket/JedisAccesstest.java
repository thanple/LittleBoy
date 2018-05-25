package com.thanple.little.boy.websocket;

import com.thanple.little.boy.web.entity.msg.RedisPubBean;
import com.thanple.little.boy.web.entity.msg.RedisPubMsg;
import com.thanple.little.boy.websocket.redis.JedisAccess;
import com.thanple.little.boy.websocket.redis.JedisAccessFactory;
import org.junit.Assert;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Created by Thanple on 2018/5/25.
 * 这个测试和RedisAccessTest的区别是，当前JedisAccess没有Spring环境(spring-data-jedis)，不过原理一致，都是用jackson2JsonRedisSerializer序列化
 */
public class JedisAccessTest {

    private Logger log = LoggerFactory.getLogger(JedisAccessTest.class);

    private JedisAccess jedisAccess = JedisAccessFactory.getJedisAccess();

    @Test
    public void testString(){
        jedisAccess.set("testString","tom2");
        String name = jedisAccess.<String>get("testString");
        Assert.assertEquals(name,"tom2");
    }

    @Test
    public void testList(){
        jedisAccess.del("testList");
        jedisAccess.rightPush("testList","Tim2");
        jedisAccess.rightPush("testList","Cindy2");
        jedisAccess.leftPush("testList","Tommy2");

        List<String> result = jedisAccess.<String>range("testList",0,-1);
        Assert.assertEquals(result.get(0),"Tommy2");
        Assert.assertEquals(result.get(1),"Tim2");
        Assert.assertEquals(result.get(2),"Cindy2");
    }

    @Test
    public void testHash(){
        jedisAccess.del("testHash");
        jedisAccess.hash("testHash","name","Tom2");

        Assert.assertEquals(jedisAccess.hashGet("testHash","name"),"Tom2");
    }

    @Test
    public void testSet(){
        jedisAccess.del("testSet");
        jedisAccess.setAdd("testSet","Timmy");

        Assert.assertEquals(jedisAccess.setContains("testSet","Timmy"),true);
    }


    @Test
    public void testZSet(){
        jedisAccess.del("testZSet");

        //这里根据后面的权值排序，9.2 < 9.6 < 9.7
        Map<Object,Double> paramsMap = new HashMap<Object,Double>();
        paramsMap.put("zset-1",9.6);
        paramsMap.put("zset-2",9.2);
        paramsMap.put("zset-3",9.7);
        jedisAccess.zset("testZSet",paramsMap);

        //上面已经排好序，这里直接验证
        Set<String> zsets = jedisAccess.<String>zsetRange("testZSet",0, -1);
        Iterator<String> zsetIterator = zsets.iterator();
        Assert.assertEquals(zsetIterator.next(),"zset-2");
        Assert.assertEquals(zsetIterator.next(),"zset-1");
        Assert.assertEquals(zsetIterator.next(),"zset-3");
    }


    /**
     * 测试发布一条消息
     */
    @Test
    public void testPublish(){

        RedisPubMsg pubMsg = new RedisPubMsg();
        pubMsg.setId(1L);
        pubMsg.setPubBean( new RedisPubBean("Tom","Hello, SpringBoot redis message!!!!") );
//        String pubMsg = "Hello, SpringBoot redis message!!!!";
        long result = jedisAccess.publish("sprinboot-redis-messaage", pubMsg);
        log.info("Publish message......,result={}",result);
    }
}
