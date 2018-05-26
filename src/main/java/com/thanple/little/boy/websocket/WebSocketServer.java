package com.thanple.little.boy.websocket;

import com.thanple.little.boy.websocket.encode.IChannelEncoder;
import com.thanple.little.boy.websocket.encode.webcode.WebCodeChannelEncoder;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Create by Thanple at 2018/5/26 下午12:03
 */
public class WebSocketServer {
    private static Logger log = LoggerFactory.getLogger(WebSocketServer.class);

    private int port;
    private IChannelEncoder channelEncoder;
    public WebSocketServer(int port) {
        this(port,new WebCodeChannelEncoder());
    }

    public WebSocketServer(int port, IChannelEncoder channelEncoder) {
        this.port = port;
        this.channelEncoder = channelEncoder;
    }

    public void start() {
        //bossGroup : NIO selector threadPool
        EventLoopGroup bossGroup = new NioEventLoopGroup();
        //workerGroup : socket data read-write worker threadPool
        EventLoopGroup workerGroup = new NioEventLoopGroup();
        try {
            ServerBootstrap bootstrap = new ServerBootstrap();
            bootstrap.group(bossGroup,workerGroup);
            bootstrap.channel(NioServerSocketChannel.class);
            bootstrap.childHandler(new ChannelInitializerHandler(channelEncoder));  //自定义ChannelHandler，Handler的处理入口类
            bootstrap.childOption(ChannelOption.TCP_NODELAY,true);

            log.info("---------- begin -------------");
            ChannelFuture future = bootstrap.bind(port).sync();


            //阻塞，直到channel.close
            future.channel().closeFuture().sync();
            log.info("---------- end -------------");
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            //辅助线程优雅退出
            workerGroup.shutdownGracefully();
            bossGroup.shutdownGracefully();
        }
    }
}
