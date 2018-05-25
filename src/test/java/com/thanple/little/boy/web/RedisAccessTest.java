package com.thanple.little.boy.web;

import com.thanple.little.boy.web.redis.RedisAccess;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.DefaultTypedTuple;
import org.springframework.data.redis.core.ZSetOperations;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

/**
 * Created by Thanple on 2018/5/11.
 * 这是一个基于RedisTemplate的JUnit测试
 * redis数据类型有：string,list,hash,set,zset
 */
@RunWith(SpringRunner.class)
@org.springframework.boot.test.context.SpringBootTest
public class RedisAccessTest {

    @Autowired
    private RedisAccess redisAccess;

    /**
     * 测试string类型
     * 这里有个坑，如果采用Jackson2JsonRedisSerializer序列化方式，通过redis-cli查询的时候，key就不是为testString了，也不是"testString"
     * 而是"\"testString\"", \不是转义，也不能少，估计写入redis的时候写入的是 "\\"testString\\""
     * 读取出来的value为"\"tom\""，
     * 如果需要手动序列化和反序列：用RedisConfig.getSerializer()的serialize和deserialize不会有问题
     * 如果用StringRedisSerializer则不会有这个问题，但是没有Jackson2JsonRedisSerializer方便
     *
     * 相关redis命令:
     * 1.set key value 设置指定 key 的值
     * 2.get key 获取指定 key 的值。
     * 3.getrange key start end 返回 key 中字符串值的子字符
     * 4.getset key value 将给定 key 的值设为 value ，并返回 key 的旧值(old value)。
     * 5.getbit key offset 对 key 所储存的字符串值，获取指定偏移量上的位(bit)。
     * 6.mget key1 [key2..] 获取所有(一个或多个)给定 key 的值。
     * 7.setbit key offset value 对 key 所储存的字符串值，设置或清除指定偏移量上的位(bit)。
     * 8.setex key seconds value 将值 value 关联到 key ，并将 key 的过期时间设为 seconds (以秒为单位)。
     * 9.setnx key value 只有在 key 不存在时设置 key 的值。
     * 10.setrange key offset value 用 value 参数覆写给定 key 所储存的字符串值，从偏移量 offset 开始。
     * 11.strlen key 返回 key 所储存的字符串值的长度。
     * 12.mset key value [key value ...] 同时设置一个或多个 key-value 对。
     * 13.msetnx key value [key value ...] 同时设置一个或多个 key-value 对，当且仅当所有给定 key 都不存在。
     * 14.psetex key milliseconds value 这个命令和 setex 命令相似，但它以毫秒为单位设置 key 的生存时间，而不是像 setex 命令那样，以秒为单位。
     * 15.incr key 将 key 中储存的数字值增一。
     * 16.incrby key increment 将 key 所储存的值加上给定的增量值（increment） 。
     * 17.incrbyfloat key increment 将 key 所储存的值加上给定的浮点增量值（increment） 。
     * 18.decr key 将 key 中储存的数字值减一。
     * 19.decrby key decrement key 所储存的值减去给定的减量值（decrement） 。
     * 20.append key value 如果 key 已经存在并且是一个字符串， append 命令将指定的 value 追加到该 key 原来值（value）的末尾。
     */
    @Test
    public void testString(){
        redisAccess.set("testString","tom");
        String name = redisAccess.<String>get("testString");
        Assert.assertEquals(name,"tom");
    }

    /**
     * 测试list
     * 1.blpop key1 [key2 ] timeout 移出并获取列表的第一个元素， 如果列表没有元素会阻塞列表直到等待超时或发现可弹出元素为止。
     * 2.brpop key1 [key2 ] timeout 移出并获取列表的最后一个元素， 如果列表没有元素会阻塞列表直到等待超时或发现可弹出元素为止。
     * 3.brpoplpush source destination timeout 从列表中弹出一个值，将弹出的元素插入到另外一个列表中并返回它； 如果列表没有元素会阻塞列表直到等待超时或发现可弹出元素为止。
     * 4.lindex key index 通过索引获取列表中的元素
     * 5.linsert key before|after pivot value 在列表的元素前或者后插入元素
     * 6.llen key 获取列表长度
     * 7.lpop key 移出并获取列表的第一个元素
     * 8.lpush key value1 [value2] 将一个或多个值插入到列表头部
     * 9.lpushx key value 将一个值插入到已存在的列表头部
     * 10.lrange key start stop 获取列表指定范围内的元素
     * 11.lrem key count value 移除列表元素
     * 12.lset key index value 通过索引设置列表元素的值
     * 13.ltrim key start stop 对一个列表进行修剪(trim)，就是说，让列表只保留指定区间内的元素，不在指定区间之内的元素都将被删除。
     * 14.rpop key 移除并获取列表最后一个元素
     * 15.rpoplpush source destination 移除列表的最后一个元素，并将该元素添加到另一个列表并返回
     * 16.rpush key value1 [value2] 在列表中添加一个或多个值
     * 17.rpushx key value 为已存在的列表添加值
     */
    @Test
    public void testList(){
        redisAccess.getRedisTemplate().delete("testList");
        redisAccess.rightPush("testList","Tim");
        redisAccess.rightPush("testList","Cindy");
        redisAccess.leftPush("testList","Tommy");

        List<String> result = redisAccess.<String>range("testList",0,-1);
        Assert.assertEquals(result.get(0),"Tommy");
        Assert.assertEquals(result.get(1),"Tim");
        Assert.assertEquals(result.get(2),"Cindy");
    }

    /**
     * 测试hash
     * 相关redis命令:
     * 1.hdel key field1 [field2] 删除一个或多个哈希表字段
     * 2.hexists key field 查看哈希表 key 中，指定的字段是否存在。
     * 3.hget key field 获取存储在哈希表中指定字段的值。
     * 4.hgetall key 获取在哈希表中指定 key 的所有字段和值
     * 5.hincrby key field increment 为哈希表 key 中的指定字段的整数值加上增量 increment 。
     * 6.hincrbyfloat key field increment 为哈希表 key 中的指定字段的浮点数值加上增量 increment 。
     * 7.hkeys key 获取所有哈希表中的字段
     * 8.hlen key 获取哈希表中字段的数量
     * 9.hmget key field1 [field2] 获取所有给定字段的值
     * 10.hmset key field1 value1 [field2 value2 ] 同时将多个 field-value (域-值)对设置到哈希表 key 中。
     * 11.hset key field value 将哈希表 key 中的字段 field 的值设为 value 。
     * 12.hsetnx key field value 只有在字段 field 不存在时，设置哈希表字段的值。
     * 13.hvals key 获取哈希表中所有值
     * 14.hscan key cursor [match pattern] [count count] 迭代哈希表中的键值对。
     */
    @Test
    public void testHash(){
        redisAccess.getRedisTemplate().delete("testHash");
        redisAccess.hash("testHash","name","Tom");

        Assert.assertEquals(redisAccess.hashGet("testHash","name"),"Tom");
    }

    /**
     * 测试hash
     * 相关redis命令:
     * 1.sadd key member1 [member2] 向集合添加一个或多个成员
     * 2.scard key 获取集合的成员数
     * 3.sdiff key1 [key2] 返回给定所有集合的差集
     * 4.sdiffstore destination key1 [key2] 返回给定所有集合的差集并存储在 destination 中
     * 5.sinter key1 [key2] 返回给定所有集合的交集
     * 6.sinterstore destination key1 [key2] 返回给定所有集合的交集并存储在 destination 中
     * 7.sismember key member 判断 member 元素是否是集合 key 的成员
     * 8.smembers key 返回集合中的所有成员
     * 9.smove source destination member 将 member 元素从 source 集合移动到 destination 集合
     * 10.spop key 移除并返回集合中的一个随机元素
     * 11.srandmember key [count] 返回集合中一个或多个随机数
     * 12.srem key member1 [member2] 移除集合中一个或多个成员
     * 13.sunion key1 [key2] 返回所有给定集合的并集
     * 14.sunionstore destination key1 [key2] 所有给定集合的并集存储在 destination 集合中
     * 15.sscan key cursor [match pattern] [count count] 迭代集合中的元素
     */
    @Test
    public void testSet(){
        redisAccess.getRedisTemplate().delete("testSet");
        redisAccess.hset("testSet","Timmy");

        Assert.assertEquals(redisAccess.hsetContains("testSet","Timmy"),true);
        Assert.assertEquals(redisAccess.hsetGet("testSet").contains("Timmy"), true);
    }

    /**
     * 测试zset
     *
     * 相关redis命令:
     * 1.zadd key score1 member1 [score2 member2] 向有序集合添加一个或多个成员，或者更新已存在成员的分数
     * 2.zcard key 获取有序集合的成员数
     * 3.zcount key min max 计算在有序集合中指定区间分数的成员数
     * 4.zincrby key increment member 有序集合中对指定成员的分数加上增量 increment
     * 5.zinterstore destination numkeys key [key ...] 计算给定的一个或多个有序集的交集并将结果集存储在新的有序集合 key 中
     * 6.zlexcount key min max 在有序集合中计算指定字典区间内成员数量
     * 7.zrange key start stop [withscores] 通过索引区间返回有序集合成指定区间内的成员
     * 8.zrangebylex key min max [limit offset count] 通过字典区间返回有序集合的成员
     * 9.zrangebyscore key min max [withscores] [limit] 通过分数返回有序集合指定区间内的成员
     * 10.zrank key member 返回有序集合中指定成员的索引
     * 11.zrem key member [member ...] 移除有序集合中的一个或多个成员
     * 12.zremrangebylex key min max 移除有序集合中给定的字典区间的所有成员
     * 13.zremrangebyrank key start stop 移除有序集合中给定的排名区间的所有成员
     * 14.zremrangebyscore key min max 移除有序集合中给定的分数区间的所有成员
     * 15.zrevrange key start stop [withscores] 返回有序集中指定区间内的成员，通过索引，分数从高到底
     * 16.zrevrangebyscore key max min [withscores] 返回有序集中指定分数区间内的成员，分数从高到低排序
     * 17.zrevrank key member 返回有序集合中指定成员的排名，有序集成员按分数值递减(从大到小)排序
     * 18.zscore key member 返回有序集中，成员的分数值
     * 19.zunionstore destination numkeys key [key ...] 计算给定的一个或多个有序集的并集，并存储在新的 key 中
     * 20.zscan key cursor [match pattern] [count count] 迭代有序集合中的元素（包括元素成员和元素分值）
     */
    @Test
    public void testZSet(){
        redisAccess.getRedisTemplate().delete("testZSet");

        //这里根据后面的权值排序，9.2 < 9.6 < 9.7
        ZSetOperations.TypedTuple<Object> objectTypedTuple1 = new DefaultTypedTuple<Object>("zset-1",9.6);
        ZSetOperations.TypedTuple<Object> objectTypedTuple2 = new DefaultTypedTuple<Object>("zset-2",9.2);
        ZSetOperations.TypedTuple<Object> objectTypedTuple3 = new DefaultTypedTuple<Object>("zset-3",9.7);
        Set<ZSetOperations.TypedTuple<Object>> tuples = new HashSet<ZSetOperations.TypedTuple<Object>>();
        tuples.add(objectTypedTuple1);
        tuples.add(objectTypedTuple2);
        tuples.add(objectTypedTuple3);
        redisAccess.zset("testZSet",tuples);

        //上面已经排好序，这里直接验证
        Set<String> zsets = redisAccess.<String>zsetRange("testZSet",0, -1);
        Iterator<String> zsetIterator = zsets.iterator();
        Assert.assertEquals(zsetIterator.next(),"zset-2");
        Assert.assertEquals(zsetIterator.next(),"zset-1");
        Assert.assertEquals(zsetIterator.next(),"zset-3");
    }
}
