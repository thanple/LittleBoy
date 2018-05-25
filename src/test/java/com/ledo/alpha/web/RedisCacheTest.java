package com.ledo.alpha.web;

import com.ledo.alpha.web.entity.repo.User;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Date;

/**
 * Created by Thanple on 2018/5/11.
 * redis整合springcache注解
 * JUnit测试不出来，可以看看RepoDemoController这个demo
 */
@RunWith(SpringRunner.class)
@org.springframework.boot.test.context.SpringBootTest
@Deprecated
public class RedisCacheTest {


    @CachePut(value = "user_demo")
    public User insertUser() {
        User user = new User();
        user.setName("Tom");
        user.setEmail("12312312@qq.com");
        user.setCreateTime(new Date());

        return user;
    }


    @Cacheable(value = "user_demo" ) //放入demo的缓存
    public User getUser() {
        User user = new User();
        user.setName("Timmy");
        user.setEmail("123123122@qq.com");
        user.setCreateTime(new Date());
        return user;
    }

    @Test
    public void testInsertCache(){
        insertUser();
    }
    @Test
    public void testGetCache(){
        getUser();
    }
}
