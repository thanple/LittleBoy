package com.thanple.little.boy.websocket;

import com.thanple.little.boy.websocket.encode.IChannelEncoder;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.socket.SocketChannel;

/**
 * Create by Thanple at 2018/5/26 下午12:31
 */
public class ChannelInitializerHandler extends ChannelInitializer<SocketChannel> {

    private IChannelEncoder channelEncoder;
    public ChannelInitializerHandler(IChannelEncoder channelEncoder){
        this.channelEncoder = channelEncoder;
    }

    @Override
    protected void initChannel(SocketChannel ch) throws Exception {
        channelEncoder.initChannel(ch);
    }
}