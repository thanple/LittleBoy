package com.thanple.little.boy.web;

import com.thanple.little.boy.web.entity.circlereference.StudentA;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * 测试循环引用问题
 * Create by Thanple at 2019/3/3 下午2:44
 */
public class CircleRefrenceTest {


    /** 通过构造器循环引用，失败 */
    @Test
    public void test1(){

        ApplicationContext context =
                new ClassPathXmlApplicationContext(
                "test/circlerefrence/application1.xml");
        System.out.println(context.getBean("a", StudentA.class));
    }

    /** 通过setter引用，成功 */
    @Test
    public void test2(){

        ApplicationContext context =
                new ClassPathXmlApplicationContext(
                        "test/circlerefrence/application2.xml");
        System.out.println(context.getBean("a", StudentA.class));
    }

    /** 通过setter多例引用，失败 */
    @Test
    public void test3(){

        ApplicationContext context =
                new ClassPathXmlApplicationContext(
                        "test/circlerefrence/application3.xml");
        System.out.println(context.getBean("a", StudentA.class));
    }
}
