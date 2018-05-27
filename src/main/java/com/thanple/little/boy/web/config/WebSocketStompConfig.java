package com.thanple.little.boy.web.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.AbstractWebSocketMessageBrokerConfigurer;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;

/**
 * Create by Thanple at 2018/5/27 下午5:21
 * Spring整合WebSocket Stomp
 * STOMP，Streaming Text Orientated Message Protocol，是流文本定向消息协议，是一种为MOM(Message Oriented Middleware，面向消息的中间件)设计的简单文本协议。
 * 它提供了一个可互操作的连接格式，允许STOMP客户端与任意STOMP消息代理(Broker)进行交互，类似于OpenWire(一种二进制协议)。
 * 由于其设计简单，很容易开发客户端，因此在多种语言和多种平台上得到广泛应用。其中最流行的STOMP消息代理是Apache ActiveMQ。
 *
 * STOMP协议工作于TCP协议之上，使用了下列命令：
 * SEND 发送
 * SUBSCRIBE 订阅
 * UNSUBSCRIBE 退订
 * BEGIN 开始
 * COMMIT 提交
 * ABORT 取消
 * ACK 确认
 * DISCONNECT 断开
 */

@Configuration
@EnableWebSocketMessageBroker
public class WebSocketStompConfig extends AbstractWebSocketMessageBrokerConfigurer {

    /**
     * 注册stomp的端点
     */
    @Override
    public void registerStompEndpoints(StompEndpointRegistry registry) {
        // 允许使用socketJs方式访问，访问点为webSocketServer，允许跨域
        // 在网页上我们就可以通过这个链接
        // http://localhost:8080/ws
        // 来和服务器的WebSocket连接
        registry.addEndpoint("/ws").setAllowedOrigins("*").withSockJS();
    }

    /**
     * 配置信息代理
     */
    @Override
    public void configureMessageBroker(MessageBrokerRegistry registry) {
        // 订阅Broker名称
        registry.enableSimpleBroker("/queue", "/topic");
        // 全局使用的消息前缀（客户端订阅路径上会体现出来）
        registry.setApplicationDestinationPrefixes("/app");
        // 点对点使用的订阅前缀（客户端订阅路径上会体现出来），不设置的话，默认也是/user/
        // registry.setUserDestinationPrefix("/user/");
    }

}