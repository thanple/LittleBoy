package com.thanple.little.boy.websocket.util;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

/**
 * Created by Thanple on 2018/5/25.
 * properties加载器:单例模式
 */
public enum PropertiesLoader {
    INSTANCE;

    /** 所有Properties文件缓存 */
    private Map<String,Properties> propertiesMap = new HashMap<>();

    /**
     * 根据名字获取Properties文件
     * @param propertyFile
     * @return
     */
    public Properties getProperties(String propertyFile) {
        if( !propertiesMap.containsKey(propertyFile) ){
            synchronized (propertiesMap) {
                if( !propertiesMap.containsKey(propertyFile) ){
                    Properties newProperties = new Properties();
                    try {
                        InputStream inputStream = Constant.Path.class.getClassLoader().getResourceAsStream(propertyFile);
                        newProperties.load(inputStream);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                    propertiesMap.put(propertyFile,newProperties);
                }
            }
        }
        return propertiesMap.get(propertyFile);
    }
}
