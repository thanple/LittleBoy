package com.thanple.little.boy.web;

import com.thanple.little.boy.web.config.MyConfig;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * Created by Thanple on 2018/5/11.
 * 这是一个测试自定义配置文件的Demo，来自MyConfig, 配置文件是 resources/myconfig.properties
 */
@RunWith(SpringRunner.class)
@org.springframework.boot.test.context.SpringBootTest
public class MyConfigTest {

    @Autowired
    private MyConfig myConfig;

    @Test
    public void testMyConfig(){
        Assert.assertNotNull(myConfig);
        Assert.assertEquals(myConfig.getValue1() , 1);
        Assert.assertEquals(myConfig.getValue2() , 2);
    }

}
