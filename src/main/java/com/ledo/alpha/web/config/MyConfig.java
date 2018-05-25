package com.ledo.alpha.web.config;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

/**
 * Created by Thanple on 2018/5/11.
 * 自定义配置，配置文件再resources/myconfig.properties中，直接注入即可
 */
@Configuration
@PropertySource(value = "classpath:myconfig.properties")
@Data
public class MyConfig {

    @Bean("MyConfig")
    public MyConfig myConfig(){
        return this;
    }

    @Value("${MyConfig.value1}")
    private int value1;

    @Value("${MyConfig.value2}")
    private int value2;

    @Value("${MyConfig.API_ON_OFF}")
    private Boolean API_ON_OFF;
}
