package com.thanple.little.boy.websocket.encode.webcode;

import com.thanple.little.boy.websocket.encode.IChannelEncoder;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.http.HttpObjectAggregator;
import io.netty.handler.codec.http.HttpServerCodec;
import io.netty.handler.codec.http.websocketx.extensions.compression.WebSocketServerCompressionHandler;

/**
 * Create by Thanple at 2018/5/26 下午3:23
 */
public class WebCodeChannelEncoder implements IChannelEncoder{
    @Override
    public void initChannel(SocketChannel ch) throws Exception {
        ChannelPipeline pipeline = ch.pipeline();

        pipeline.addLast(new HttpServerCodec());
        pipeline.addLast(new HttpObjectAggregator(65536));
        pipeline.addLast(new WebSocketServerCompressionHandler()); //websocket数据压缩
        pipeline.addLast(new WebSocketServerHandler());
    }
}
