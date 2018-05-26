package com.thanple.little.boy.websocket.encode.protobuff;

import com.thanple.little.boy.websocket.encode.IChannelEncoder;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.protobuf.ProtobufVarint32FrameDecoder;
import io.netty.handler.codec.protobuf.ProtobufVarint32LengthFieldPrepender;

/**
 * Create by Thanple at 2018/5/26 下午3:21
 * 使用protobuff来实现报文的压缩和解压缩
 */
public class ProtobuffChannelEncoder implements IChannelEncoder{
    @Override
    public void initChannel(SocketChannel ch) throws Exception {
        ch.pipeline().addLast(new ProtobufVarint32FrameDecoder())
                .addLast(new MyProtobufDecoder()) //自定义Protobuf解码器
                .addLast(new ProtobufVarint32LengthFieldPrepender())    //添加32位int表示报文的长度
                .addLast(new MyProtobufEncoder()) //自定义Protobuf编码器
                .addLast(new ProtobuffServerHandler());//自定义handler处理消息
    }
}
