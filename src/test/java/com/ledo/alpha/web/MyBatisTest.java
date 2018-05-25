package com.ledo.alpha.web;

import com.ledo.alpha.web.entity.repo.User;
import com.ledo.alpha.web.mapper.UserMapperAnnotation;
import com.ledo.alpha.web.mapper.UserMapperXml;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Date;
import java.util.List;

/**
 * Created by Thanple on 2018/5/11.
 * 这是一个MyBatis的Demo：包含Mapper的注解版和xml版
 */
@RunWith(SpringRunner.class)
@org.springframework.boot.test.context.SpringBootTest
public class MyBatisTest {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Autowired
    private UserMapperAnnotation userMapperAnnotation;

    @Autowired
    private UserMapperXml userMapperXml;

    /**
     * 测试mybatis插入数据：注解版
     */
    @Test
    public void testInsertUserAnnotation(){
        jdbcTemplate.execute("DROP TABLE IF EXISTS user");
        jdbcTemplate.execute("CREATE TABLE `user` (\n" +
                "  `id` bigint(20) NOT NULL AUTO_INCREMENT,\n" +
                "  `name` varchar(50) DEFAULT NULL,\n" +
                "  `email` varchar(50) DEFAULT NULL,\n" +
                "  `create_time` datetime DEFAULT NULL,\n" +
                "  PRIMARY KEY (`id`)\n" +
                ") ENGINE=InnoDB DEFAULT CHARSET=utf8;");

        userMapperAnnotation.insert("Tom","11111123123@qq.com",new Date());
        List<User> userList = userMapperAnnotation.find();

        Assert.assertNotNull(userList);

        User user = userList.get(0);
        Assert.assertNotNull(user);
        Assert.assertEquals(user.getName(), "Tom");
        Assert.assertEquals(user.getEmail(), "11111123123@qq.com");
    }
    /**
     * 测试mybatis插入数据：配置文件版
     */
    @Test
    public void testInsertUserXml(){
        jdbcTemplate.execute("DROP TABLE IF EXISTS user");
        jdbcTemplate.execute("CREATE TABLE `user` (\n" +
                "  `id` bigint(20) NOT NULL AUTO_INCREMENT,\n" +
                "  `name` varchar(50) DEFAULT NULL,\n" +
                "  `email` varchar(50) DEFAULT NULL,\n" +
                "  `create_time` datetime DEFAULT NULL,\n" +
                "  PRIMARY KEY (`id`)\n" +
                ") ENGINE=InnoDB DEFAULT CHARSET=utf8;");

        User user = new User();
        user.setName("TomXML");
        user.setEmail("11111123123@qq.com");
        user.setCreateTime(new Date());

        userMapperXml.insert(user);

        List<User> userList = userMapperXml.find();
        Assert.assertNotNull(userList);

        user = userList.get(0);
        Assert.assertNotNull(user);
        Assert.assertEquals(user.getName(), "TomXML");
        Assert.assertEquals(user.getEmail(), "11111123123@qq.com");
    }

}
