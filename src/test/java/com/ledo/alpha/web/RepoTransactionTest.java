package com.ledo.alpha.web;

import com.ledo.alpha.web.service.UserService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * Created by Thanple on 2018/5/24.
 * 这是一个测试MySQL事务的Demo
 */
@RunWith(SpringRunner.class)
@org.springframework.boot.test.context.SpringBootTest
public class RepoTransactionTest {

    @Autowired
    private UserService userService;

    @Test
    public void testTransation(){
        userService.insertDatas();
    }

}
