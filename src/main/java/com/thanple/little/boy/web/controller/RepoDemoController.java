package com.thanple.little.boy.web.controller;

import com.thanple.little.boy.web.entity.repo.User;
import com.thanple.little.boy.web.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

/**
 * Created by Thanple on 2017/2/23.
 */

@RestController
@RequestMapping("/jpa")
public class RepoDemoController {

    @Autowired
    private UserRepository userRepository;

    /**
     * http://localhost:8080/jpa/insertUser
     * @CachePut 放入user_demo的缓存 ,放入缓存的数据必须经过Serializable序列化，或者是字符串
     * @return
     */
    @RequestMapping("/insertUser")
    @CachePut(value = "user_demo")
    public User insertUser() {
        List<String> nameRepository = Arrays.asList("Tom","Cindy","Linda","Jack","Rose");

        User user = new User();
        user.setName(nameRepository.get((int)(Math.random()*nameRepository.size())) + "" +String.format("%04d",(int)(Math.random() * 1000 )) );
        user.setEmail(String.format("%4d@qq.com",(int)(Math.random() * 100000000) ));
        user.setCreateTime(new Date());

        userRepository.save(user);
        return user;
    }

    /**
     * http://localhost:8080/jpa/insertUser
     * @Cacheable 如果缓存中有user_demo，则从缓存里面读取，否则调用本方法，可以加断点验证是否调用本方法
     * @return
     */
    @RequestMapping("/getUser")
    @Cacheable(value = "user_demo" ) //放入demo的缓存
    public User getUser() {
        List<User> users = userRepository.findAll();
        return users.get(users.size()-1);
    }
}
