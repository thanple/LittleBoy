package com.thanple.little.boy.web;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Scanner;

/**
 * Created by Thanple on 2018/5/23.
 */
public class RedisConcurrentClient {

    private static Logger log = LoggerFactory.getLogger(RedisConcurrentClient.class);

    private static int maxCount = 0;

    /**
     * 测试http://localhost:8080/redis/count 的redis并发加法
     * @throws IOException
     */
    @Test
    public void test() throws IOException {
        for(int i=0;i<100; i++) {
            new Thread(){
                @Override
                public void run() {
                    HttpURLConnection connection = null;
                    try {
                        connection = (HttpURLConnection) new URL("http://localhost:8080/redis/count").openConnection();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    Scanner input = null;
                    try {
                        input = new Scanner(connection.getInputStream(),"utf-8");
                        StringBuilder response = new StringBuilder();
                        while(input.hasNext()){
                            response.append(input.nextLine());
                            response.append("\n");
                        }
                        maxCount = Math.max(maxCount , Integer.parseInt(response.toString().replace("\n","") ) );
                        log.info("count ={}" , response.toString());
                    } catch (IOException e) {
                        e.printStackTrace();
                    }finally {
                        input.close();
                    }
                }
            }.start();
        }

        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        log.info("maxCount ={}" , maxCount);
    }
}
