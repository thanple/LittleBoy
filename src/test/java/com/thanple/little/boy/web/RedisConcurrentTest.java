package com.thanple.little.boy.web;

import com.thanple.little.boy.web.redis.RedisLock;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataAccessException;
import org.springframework.data.redis.core.RedisOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.SessionCallback;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;
import java.util.List;

/**
 * Created by Thanple on 2018/5/21.
 * 这里测试Redis的并发Demo
 * redis事务命令：
 * 1.multi: 使用后命令不会立即执行，而是加入一个队列，等exec执行后全部执行
 * 2.watch: 监控一个key或者一组key，如果执行exec的时候key对应的值改变了，事务将会失败，可以将这个命令作为乐观锁使用。
 * 3.unwatch: 与watch相反。
 * 4.exec: 执行multi事务队列中的指令，如果中间有一个指令失败，其他指令继续，也不会回滚。
 * 5.discard: 取消事务，如果已使用watch，discard将释放所有被watch的key。
 */
@RunWith(SpringRunner.class)
@org.springframework.boot.test.context.SpringBootTest
public class RedisConcurrentTest {

    private static Logger log = LoggerFactory.getLogger(RedisConcurrentTest.class);

    @Resource
    private RedisTemplate<String, Object> redisTemplate;

    /**
     * 这里如果RedisOperations里的事务如果成功，将会打印出来OK
     * @warning RedisOperations.exec()返回来的集合表示事务的成败，这个需要在JedisConnectionFactory.convertPipelineAndTxResults设置为false，否则"OK"也被转成空
     */
    @Test
    public void testRedisOperationsResult() {
        redisTemplate.execute(new SessionCallback(){
            @Override
            public Object execute(RedisOperations operations) throws DataAccessException {
                operations.watch("testRedisOperationsResult");
                operations.multi();
                operations.opsForValue().set("testRedisOperationsResult",1);
                operations.opsForValue().set("testRedisOperationsResult",2);
                Object rs = operations.exec();

                log.info("\n *********** Watch testRedisOperationsResult end rs="+rs);
                return rs;
            }
        });
    }

    /**
     * 这个demo可以测试watch命令和不使用watch命令的区别
     * 当事务运行失败的时候可以用这个demo
     * 1.使用thread1中的watch, 则thread1中的事务将失败，最后testWatch值为2
     * 2.不使用thread1中的watch, 则thread1中的事务将成功，最后testWatch值为1
     * @throws InterruptedException
     */
    @Test
    public void testWatch() throws InterruptedException {
        redisTemplate.opsForValue().set("testWatch",0);

        Thread thread1 = new Thread(){
            @Override
            public void run() {

                log.info("\n*********** Watch begin......");
                redisTemplate.watch("testWatch");    //这里的watch可以注释掉对比一下差异
                redisTemplate.multi();
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                redisTemplate.opsForValue().set("testWatch",1);
                List<Object> rs = redisTemplate.exec();

                log.info("\n *********** Watch end rs="+rs+" set testWatch="+ ((Integer)redisTemplate.opsForValue().get("testWatch") == 1) );
            }
        };

        Thread thread2 = new Thread(){
            @Override
            public void run() {
                try {
                    Thread.sleep(200);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                redisTemplate.opsForValue().set("testWatch",2);
            }
        };

        thread1.start();
        thread2.start();
        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        log.info("\n*********** testWatch result= "+ redisTemplate.opsForValue().get("testWatch") );
    }


    /**
     * 这里的测试与上面的testWatch一致，用乐观锁CAS来保证事务的一致性
     * 当业务需求事务尽量不失败的时候可以用这个demo
     */
    @Test
    public void testWatchCAS() throws InterruptedException {
        redisTemplate.opsForValue().set("testWatchCAS",0);

        Thread thread1 = new Thread(){
            @Override
            public void run() {
                redisTemplate.execute(new SessionCallback(){
                    @Override
                    public Object execute(RedisOperations operations) throws DataAccessException {

                        List<Object> rs;
                        do {
                            log.info("\n*********** Watch begin......");
                            operations.watch("testWatchCAS");    //这里的watch可以注释掉对比一下差异
                            operations.multi();
                            try {
                                Thread.sleep(1000);
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                            operations.opsForValue().set("testWatchCAS",1);
                            rs = operations.exec();

                            log.info("\n *********** Watch end rs="+rs+" set testWatchCAS="+ ((Integer)operations.opsForValue().get("testWatchCAS") == 1) );
                        }while ( null == rs || rs.isEmpty() );

                        return rs;
                    }
                });
            }
        };

        Thread thread2 = new Thread(){
            @Override
            public void run() {
                redisTemplate.execute(new SessionCallback(){
                    @Override
                    public Object execute(RedisOperations operations) throws DataAccessException {
                        try {
                            Thread.sleep(200);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                        operations.opsForValue().set("testWatchCAS",2);
                        return null;
                    }
                });
            }
        };

        thread1.start();
        thread2.start();
        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        log.info("\n*********** testWatchCAS result= "+ redisTemplate.opsForValue().get("testWatchCAS") );
    }


    /**
     * 测试redis 分布式锁
     */
    private static int count = 0;
    @Test
    public void testRedisLock(){
        String key = "testRedisLock";

        for(int i=0;i<100; i++) {
            new Thread(){
                @Override
                public void run() {
                    RedisLock lock = new RedisLock(redisTemplate, key );
                    try{
                        long startTime = System.currentTimeMillis();
                        if(lock.lock()){
                            count++;
                            long endTime = System.currentTimeMillis();
                            long passed = endTime - startTime;
                            log.info("\n********* get lock time={}ms, tryCout={}", passed, lock.getTryCount() );

                            Thread.sleep( (long)(Math.random() * 10) );
                        }else{
                            throw new RuntimeException("Cannot get the lock!!!");
                        }
                    }catch (InterruptedException e){
                        e.printStackTrace();
                    }finally{
                        lock.unlock();
                    }
                }
            }.start();
        }

        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        log.info("\n********* The final count={}",count);
    }
}
