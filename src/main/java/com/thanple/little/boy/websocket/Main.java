package com.thanple.little.boy.websocket;

import com.thanple.little.boy.websocket.util.Constant;
import com.thanple.little.boy.websocket.util.PropertiesLoader;

import java.util.Properties;

/**
 * Create by Thanple at 2018/5/26 下午12:16
 */
public class Main {

    public static void main(String[] args) {

        Properties serverProperties = PropertiesLoader.INSTANCE.getProperties(Constant.Path.websocket_sever_properties);
        int port = Integer.parseInt(serverProperties.getProperty(Constant.WebSocketServer.netty_port));

        WebSocketServer webSocketServer = new WebSocketServer(port);
        webSocketServer.start();
    }
}
