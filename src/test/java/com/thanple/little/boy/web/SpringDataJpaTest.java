package com.thanple.little.boy.web;

import com.thanple.little.boy.web.entity.repo.User;
import com.thanple.little.boy.web.repository.UserRepository;
import com.thanple.little.boy.web.repository.UserRepository2;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Random;

/**
 * Create by Thanple at 2018/5/27 下午7:44
 * 这是一个对SpringDataJPA的demo
 */

@RunWith(SpringRunner.class)
@org.springframework.boot.test.context.SpringBootTest
public class SpringDataJpaTest {

    private static Logger log = LoggerFactory.getLogger(SpringDataJpaTest.class);

    @Autowired
    private UserRepository dao1;
    @Autowired
    private UserRepository2 dao2;

    /**
     * 测试前先插入若干条数据
     */
    @Before
    public void beforeInsertData(){
        List<String> firstNameRepository = Arrays.asList("鹏","阳","峰","强","婷","美","丽","雪");
        List<String> lastNameRepository = Arrays.asList("刘","张","朱","欧阳","李","王","赵");

        Random random = new Random(47);
        for(int i=0;i<100 ;i++) {
            User user = new User();
            String firstName;
            if(random.nextBoolean()) {
                String firstName1 = firstNameRepository.get(random.nextInt(firstNameRepository.size()));
                String firstName2 = firstNameRepository.get(random.nextInt(firstNameRepository.size()));
                firstName = firstName1 + firstName2;
            }else {
                firstName = firstNameRepository.get(random.nextInt(firstNameRepository.size()));
            }
            String lastName = lastNameRepository.get(random.nextInt(lastNameRepository.size()));

            user.setName(lastName+firstName);
            user.setEmail(String.format("%4d@qq.com",(int)(Math.random() * 100000000) ));
            user.setCreateTime(new Date());
            dao1.save(user);
        }
    }

    @Test
    public void testFind(){
       User user1 = dao1.findById(1L);
       log.info("-----------------user1={}",user1);
        Assert.assertNotNull(user1);

        List<User> mrLius = dao2.searchNames("刘");
        for(User user : mrLius) {
            log.info("-----------------mrLiu={}",user);
        }
    }

}
