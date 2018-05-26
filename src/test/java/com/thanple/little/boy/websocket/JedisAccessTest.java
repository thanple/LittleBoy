package com.thanple.little.boy.websocket;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.thanple.little.boy.web.entity.msg.RedisPubBean;
import com.thanple.little.boy.web.entity.msg.RedisPubMsg;
import com.thanple.little.boy.websocket.redis.JedisAccess;
import com.thanple.little.boy.websocket.redis.JedisAccessFactory;
import com.thanple.little.boy.websocket.redis.RedisSerializer;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.junit.Assert;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.*;

/**
 * Created by Thanple on 2018/5/25.
 * 这个测试和RedisAccessTest的区别是，当前JedisAccess没有Spring环境(spring-data-jedis)，不过原理一致，都是用jackson2JsonRedisSerializer序列化
 */
public class JedisAccessTest {

    private Logger log = LoggerFactory.getLogger(JedisAccessTest.class);
    private JedisAccess jedisAccess = JedisAccessFactory.getJedisAccess();


    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class ObjectClass1{
        private int id;
        private String name;
    }
    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class ObjectClass2{
        private int id;
        private String name;
    }
    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class ObjectClass3{
        private ObjectClass2 obj2;
        private int range;
    }

    /**
     * 将JSON字符串动态转换成一个对象
     */
    @Test
    public void testJaskson(){
        ObjectMapper mapper = new ObjectMapper();
        String jsonString = "{\"id\":1,\"name\":\"Tom2\"}";
        try {
            ObjectClass1 obj1 = mapper.readValue(jsonString, ObjectClass1.class);
            Assert.assertEquals(1,obj1.getId());
            Assert.assertEquals("Tom2",obj1.getName());
        } catch (JsonParseException e) {
            e.printStackTrace();
        } catch (JsonMappingException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }


        ObjectClass2 obj2 = new RedisSerializer<ObjectClass2>(ObjectClass2.class).deserialize(jsonString.getBytes());
        Assert.assertEquals(1,obj2.getId());
        Assert.assertEquals("Tom2",obj2.getName());
    }

    @Test
    public void testObject(){
        //测试ObjectClass1对象
        ObjectClass1 obj1 = new ObjectClass1(1,"ObjectTim");
        jedisAccess.set("testObject",obj1);
        obj1 = jedisAccess.get(ObjectClass1.class,"testObject");
        Assert.assertEquals(1,obj1.getId());
        Assert.assertEquals("ObjectTim", obj1.getName());

        //测试对象成员
        ObjectClass2 obj2 = new ObjectClass2(2,"Object2");
        ObjectClass3 obj3 = new ObjectClass3(obj2,100);
        jedisAccess.set("testObject",obj3);
        obj3 = jedisAccess.get(ObjectClass3.class,"testObject");
        Assert.assertEquals(obj2, obj3.getObj2());
        Assert.assertEquals(100, obj3.getRange());
    }

    @Test
    public void testString(){
        jedisAccess.set("testString","tom2");
        String name = jedisAccess.<String>get(String.class,"testString");
        Assert.assertEquals(name,"tom2");
    }

    @Test
    public void testList(){
        jedisAccess.del("testList");
        jedisAccess.rightPush("testList","Tim2");
        jedisAccess.rightPush("testList","Cindy2");
        jedisAccess.leftPush("testList","Tommy2");

        List<String> result = jedisAccess.<String>range(String.class,"testList",0,-1);
        Assert.assertEquals(result.get(0),"Tommy2");
        Assert.assertEquals(result.get(1),"Tim2");
        Assert.assertEquals(result.get(2),"Cindy2");
    }

    @Test
    public void testHash(){
        jedisAccess.del("testHash");
        jedisAccess.hash("testHash","name","Tom2");

        Assert.assertEquals(jedisAccess.hashGet(String.class,"testHash","name"),"Tom2");
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
        Set<String> zsets = jedisAccess.<String>zsetRange(String.class,"testZSet",0, -1);
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
        long result = jedisAccess.publish("sprinboot-redis-messaage", pubMsg);
        log.info("Publish message......,result={}",result);
    }
}
