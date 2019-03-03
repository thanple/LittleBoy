package com.thanple.little.boy.web.entity;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Create by Thanple at 2018/5/27 下午7:18
 * 这个bean是为了测试spring-context.xml文件中配置的bean,spring容器启动的时候就会加载这个bean
 */
public class StartBean {

    private static Logger log = LoggerFactory.getLogger(StartBean.class);

    private String initAttr;

    static {
        log.info("Startbean Class init");
    }

    public StartBean(String initAttr) {
        this.initAttr = initAttr;
        log.info("Startbean construct initAttr={}",initAttr);
    }

    public StartBean() {
        log.info("Startbean construct ...");
    }

    public String getInitAttr() {
        return initAttr;
    }

    public void setInitAttr(String initAttr) {
        this.initAttr = initAttr;
        log.info("· set initAttr={}",initAttr);
    }
}
