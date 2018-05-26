package com.thanple.little.boy.websocket.encode.protobuff;

import com.google.protobuf.ExtensionRegistry;
import com.google.protobuf.ExtensionRegistryLite;
import com.google.protobuf.MessageLite;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.protobuf.ProtobufDecoder;

import java.util.List;

/**
 * Created by Thanple on 2017/1/22.
 */
public class MyProtobufDecoder extends ProtobufDecoder{
    public MyProtobufDecoder(MessageLite prototype) {
        super(prototype);
    }
    public MyProtobufDecoder(){
        super(_GameServerCMsg.GameServerCMsg.getDefaultInstance());   //使用统一解码器GameServerCMsg
    }


    public MyProtobufDecoder(MessageLite prototype, ExtensionRegistry extensionRegistry) {
        super(prototype, extensionRegistry);
    }
    public MyProtobufDecoder(MessageLite prototype, ExtensionRegistryLite extensionRegistry) {
        super(prototype, extensionRegistry);
    }

    @Override
    protected void decode(ChannelHandlerContext ctx, ByteBuf msg, List<Object> out) throws Exception {
        super.decode(ctx, msg, out);
    }
}
