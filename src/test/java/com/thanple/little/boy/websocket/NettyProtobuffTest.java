package com.thanple.little.boy.websocket;

import com.google.protobuf.MessageLite;
import com.thanple.little.boy.websocket.encode.protobuff.*;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.protobuf.ProtobufDecoder;
import io.netty.handler.codec.protobuf.ProtobufEncoder;
import io.netty.handler.codec.protobuf.ProtobufVarint32FrameDecoder;
import io.netty.handler.codec.protobuf.ProtobufVarint32LengthFieldPrepender;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.InetSocketAddress;

/**
 * Create by Thanple at 2018/5/26 下午3:37
 * 这个demo主要是测试netty整合potobuff的一套通信机制
 */
public class NettyProtobuffTest {
    private static Logger log = LoggerFactory.getLogger(NettyProtobuffTest.class);
    private static final int NETTY_TEST_PORT = 18080;

    @Test
    public void server(){
        WebSocketServer server = new WebSocketServer(NETTY_TEST_PORT,new ProtobuffChannelEncoder());
        server.start();
    }


    @Test
    public void client(){
        EventLoopGroup workerGroup = new NioEventLoopGroup();
        try {
            Bootstrap bootstrap = new Bootstrap();
            bootstrap.group(workerGroup)
                    .channel(NioSocketChannel.class)
                    .option(ChannelOption.CONNECT_TIMEOUT_MILLIS,10000)
                    .handler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        protected void initChannel(SocketChannel ch) throws Exception {
                            System.out.println("initChannel");
                            ch.pipeline().addLast(new ProtobufVarint32FrameDecoder())
                                    .addLast(new ProtobufDecoder(_GameServerSMsg.GameServerSMsg.getDefaultInstance()))
                                    .addLast(new ProtobufVarint32LengthFieldPrepender())
                                    .addLast(new ProtobufEncoder())
                                    .addLast(new ChannelHandlerAdapter(){
                                        @Override
                                        public void channelActive(ChannelHandlerContext ctx) throws Exception {
                                            //当channel就绪后，我们首先通过client发送一个数据。
                                            ctx.writeAndFlush(build());
                                        }
                                        @Override
                                        public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {

                                            _GameServerSMsg.GameServerSMsg serverSMsg = (_GameServerSMsg.GameServerSMsg)msg;

                                            _SPerson1.SPerson1 person = _SPerson1.SPerson1.parseFrom(serverSMsg.getMsg());

                                            log.info(person.toString());
                                        }

                                        @Override
                                        public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
                                            cause.printStackTrace();;
                                            ctx.close();
                                        }

                                        public MessageLite build() {



                                            _GameServerCMsg.GameServerCMsg.Builder clientMsgBuilder = _GameServerCMsg.GameServerCMsg.newBuilder();
//                                            String clsName = _CPerson1.CPerson1.class.getSimpleName();
                                            int id = 1;
                                            clientMsgBuilder.setId(id);

                                            _CPerson1.CPerson1.Builder personBuilder = _CPerson1.CPerson1.newBuilder();
                                            personBuilder.setEmail("lisi@gmail.com");
                                            personBuilder.setId(0);
                                            _CPerson1.CPerson1.PhoneNumber.Builder phone = _CPerson1.CPerson1.PhoneNumber.newBuilder();
                                            phone.setNumber("18610000000");

                                            personBuilder.setName("李四");
                                            personBuilder.addPhone(phone);

                                            clientMsgBuilder.setMsg(personBuilder.build().toByteString());

                                            return clientMsgBuilder.build();
                                        }
                                    });
                        }
                    });
            ChannelFuture future = bootstrap.connect(new InetSocketAddress("127.0.0.1", NETTY_TEST_PORT));
            log.info("-------------- Begin --------------");
            future.channel().closeFuture().sync();
            log.info("-------------- Closed --------------");
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            workerGroup.shutdownGracefully();
        }
    }

}
