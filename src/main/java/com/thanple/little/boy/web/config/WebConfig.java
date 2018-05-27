package com.thanple.little.boy.web.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.servlet.ErrorPage;
import org.springframework.boot.web.servlet.ErrorPageRegistrar;
import org.springframework.boot.web.servlet.ErrorPageRegistry;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ImportResource;
import org.springframework.http.HttpStatus;
import org.springframework.web.servlet.config.annotation.*;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created by Thanple on 2018/5/23.
 */
@Configuration
/**
 * ImportResource引入资源文件有三种方式：
 *     1.直接引入，该路径就是src/resources/下面的文件：file
 *     2.classpath引入：该路径就是src/java下面的配置文件：classpath:file
 *     3.引入本地文件：该路径是一种绝对路径：file:D://....
 */
@ImportResource(locations = {"spring-context.xml"}) //这里引入自定义的bean
public class WebConfig {

    @SuppressWarnings("SpringJavaAutowiringInspection")
    @Autowired
    private MyConfig myConfig;

    //MVC的拦截器
    @Bean
    public WebMvcConfigurer mvcConfigurer() {

        return new WebMvcConfigurerAdapter() {
            @Override
            public void addCorsMappings(CorsRegistry registry) {
                registry.addMapping("/api/**");
            }

            @Override
            public void addInterceptors(InterceptorRegistry registry) {
                registry.addInterceptor(new HandlerInterceptorAdapter() {
                    @Override
                    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
                        if(!myConfig.getAPI_ON_OFF()){
                            response.getOutputStream().print("API Service is closed");
                            return false;
                        }
                        return true;
                    }
                });
            }

            //Resource静态文件配置
            @Override
            public void addResourceHandlers(ResourceHandlerRegistry registry) {
                registry.addResourceHandler("/static/**").addResourceLocations("classpath:/static/");
                super.addResourceHandlers(registry);
            }
        };
    }


    //404 505拦截
    @Bean
    public ErrorPageRegistrar errorPageRegistrar(){
        return new ErrorPageRegistrar() {
            @Override
            public void registerErrorPages(ErrorPageRegistry errorPageRegistry) {
                errorPageRegistry.addErrorPages(new ErrorPage(HttpStatus.NOT_FOUND, "/404"));
                errorPageRegistry.addErrorPages(new ErrorPage(HttpStatus.INTERNAL_SERVER_ERROR, "/500"));
            }
        };
    }
}
