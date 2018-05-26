package com.thanple.little.boy.websocket.encode.protobuff;

import com.google.protobuf.ByteString;
import io.netty.channel.ChannelHandlerAdapter;
import io.netty.channel.ChannelHandlerContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Method;

/**
 * Create by Thanple at 2018/5/26 下午3:29
 */
public class ProtobuffServerHandler extends ChannelHandlerAdapter {

    private Logger log = LoggerFactory.getLogger(ProtobuffServerHandler.class);

    /**
     * 这里可以设计成一套走配置文件来控制 classId=>Class的一套映射
     * @param classId
     * @return
     */
    private Class<?> getClassById(int classId){
        if(classId == 1){
            return _CPerson1.CPerson1.class;
        }
        return null;
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {

        //先使用万能消息模板接收消息
        _GameServerCMsg.GameServerCMsg clientMsg = (_GameServerCMsg.GameServerCMsg)msg;
        int id = clientMsg.getId();
        ByteString data = clientMsg.getMsg();

        //反射万能消息模板的data得到协议消息对象(protobuf生成的)
        Class<?> protocolCls = this.getClassById(id);
        Method parseProtoFromByteString = protocolCls.getDeclaredMethod("parseFrom",ByteString.class);
        com.google.protobuf.MessageLite messageLite = (com.google.protobuf.MessageLite)parseProtoFromByteString.invoke(null,data);


        //...logic....
        log.info("Receive Msg id={}",id);
        log.info("Receive Msg {}={}",messageLite.getClass(), messageLite);

        //在实际业务中，这里可以为每一个协议写一个逻辑处理类
        if(messageLite instanceof _CPerson1.CPerson1) {
            _SPerson1.SPerson1.Builder sPerson1 = _SPerson1.SPerson1.newBuilder();
            sPerson1.setId(1);
            sPerson1.setName("Thanple");
            sPerson1.setEmail("asdasdasd@qq.com");

            _GameServerSMsg.GameServerSMsg.Builder builder = _GameServerSMsg.GameServerSMsg.newBuilder();
            builder.setId(2);
            builder.setMsg(sPerson1.build().toByteString());

            //写入通道
            ctx.writeAndFlush(builder.build());
        }


//        ChannelFuture future = ctx.writeAndFlush(build());
        //发送数据之后，我们手动关闭channel，这个关闭是异步的，当数据发送完毕后执行。
//        future.addListener(ChannelFutureListener.CLOSE);
    }



}