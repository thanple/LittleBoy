package com.thanple.little.boy.websocket.encode;

import io.netty.channel.socket.SocketChannel;

/**
 * Create by Thanple at 2018/5/26 下午3:21
 */
public interface IChannelEncoder {
    void initChannel(SocketChannel ch) throws Exception;
}
