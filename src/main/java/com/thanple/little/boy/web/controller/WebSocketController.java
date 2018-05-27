package com.thanple.little.boy.web.controller;

import com.thanple.little.boy.web.entity.msg.WebSocketClient;
import com.thanple.little.boy.web.entity.msg.WebSocketServer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.annotation.SubscribeMapping;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Create by Thanple at 2018/5/27 下午5:25
 * 基于WebSocket的控制器
 */
@Controller
public class WebSocketController {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @MessageMapping("/sendTest")
    @SendTo("/topic/subscribeTest")
    public WebSocketServer sendDemo(WebSocketClient message) {
        logger.info("接收到了信息" + message.getName());
        return new WebSocketServer("你发送的消息为:" + message.getName());
    }

    @SubscribeMapping("/subscribeTest")
    public WebSocketServer sub() {
        logger.info("XXX用户订阅了我。。。");
        return new WebSocketServer("感谢你订阅了我。。。");
    }

    // localhost:8080/websocket_demo
    @RequestMapping("/websocket_demo")
    public String websocketView() {
        return "spring_websocket_demo";
    }
}
