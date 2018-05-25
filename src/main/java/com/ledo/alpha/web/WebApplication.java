package com.ledo.alpha.web;

import com.ledo.alpha.web.config.MyConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.ComponentScan;

/**
 * Created by Thanple on 2018/5/8.
 */
@SpringBootApplication
@ComponentScan(basePackages={"com.ledo.alpha.web"})
public class WebApplication implements CommandLineRunner {

    private static Logger log = LoggerFactory.getLogger(WebApplication.class);

    @Autowired
    private MyConfig myConfig;

    public static void main(String[] args) throws Exception {
        ApplicationContext ctx = SpringApplication.run(WebApplication.class, args);
        MyConfig myConfig = (MyConfig) ctx.getBean("MyConfig");
        log.info("MyConfig ={}",myConfig);
    }

    @Override
    public void run(String... strings) throws Exception {
        log.info("MyConfig Value{},{}",myConfig.getValue1(),myConfig.getValue2());
    }
}
