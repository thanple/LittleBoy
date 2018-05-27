package com.thanple.little.boy.websocket.encode.webcode;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.*;
import io.netty.handler.codec.http.*;
import io.netty.handler.codec.http.websocketx.*;
import io.netty.util.CharsetUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.Map;

import static io.netty.handler.codec.http.HttpMethod.GET;
import static io.netty.handler.codec.http.HttpResponseStatus.BAD_REQUEST;
import static io.netty.handler.codec.http.HttpResponseStatus.FORBIDDEN;
import static io.netty.handler.codec.http.HttpVersion.HTTP_1_1;

/**
 * Create by Thanple at 2018/5/27 下午3:41
 */
public class WebSocketServerHandler extends SimpleChannelInboundHandler {

    private Logger logger = LoggerFactory.getLogger(WebSocketServerHandler.class);

    private WebSocketServerHandshaker handshaker;



    @Override
    protected void messageReceived(ChannelHandlerContext ctx, Object msg) throws Exception {
        if (msg instanceof FullHttpRequest) {
            handleHttpRequest(ctx, (FullHttpRequest) msg);
        } else if (msg instanceof WebSocketFrame) {

            WebSocketFrame webSocketFrame = (WebSocketFrame)msg;
            if(webSocketFrame instanceof CloseWebSocketFrame){
                logger.info("CloseWebSocketFrame");
                handshaker.close(ctx.channel(), (CloseWebSocketFrame) webSocketFrame.retain());
            }else if(webSocketFrame instanceof PingWebSocketFrame){
                logger.info("PingWebSocketFrame");
                ctx.channel().write(new PongWebSocketFrame(webSocketFrame.content().retain()));
            }else if(webSocketFrame instanceof TextWebSocketFrame){
                //这个地方 可以根据需求，加上一些业务逻辑
                TextWebSocketFrame txtReq = (TextWebSocketFrame) webSocketFrame;

                String msgStr = txtReq.text();
                logger.info("WebSocketFrame={}",txtReq);
                logger.info("WebSocketFrame.msgStr={}",msgStr);
                ctx.channel().writeAndFlush(new TextWebSocketFrame(msgStr));
            }

        }
    }



    /**
     * 掉线/离线的处理
     */
    @Override
    public void close(ChannelHandlerContext ctx, ChannelPromise promise) throws Exception {
        logger.info("close()....");

        super.close(ctx, promise);
    }

    /**
     * Description: http请求处理：第一次websocket验证是http请求
     */
    private void handleHttpRequest(ChannelHandlerContext ctx, FullHttpRequest req) {
        /* 错误请求 */
        if (!req.decoderResult().isSuccess() || (!"websocket".equals(req.headers().get("Upgrade")))) {
            sendHttpResponse(ctx, req, new DefaultFullHttpResponse(HTTP_1_1, BAD_REQUEST));
            return;
        }

        /* 只允许GET请求 */
        if (req.method() != GET) {
            sendHttpResponse(ctx, req, new DefaultFullHttpResponse(HTTP_1_1, FORBIDDEN));
            return;
        }

        logger.info("req.uri: " + req.uri());

        QueryStringDecoder queryStringDecoder = new QueryStringDecoder(req.uri());
        Map<String, List<String>> parameters = queryStringDecoder.parameters();


        logger.info("WebSocket Handshake parameters={}",parameters);

        /* 正常WebSocket的Http连接请求，构造握手响应返回 */
        WebSocketServerHandshakerFactory wsFactory = new WebSocketServerHandshakerFactory("ws://localhost:18080/websocket", null,
                true);//注意，这条地址别被误导了，其实这里填写什么都无所谓，WS协议消息的接收不受这里控制

        handshaker = wsFactory.newHandshaker(req);
        if (handshaker == null) {
        	/* 无法处理的websocket版本 */
            WebSocketServerHandshakerFactory.sendUnsupportedVersionResponse(ctx.channel());
        } else {
        	/* 向客户端发送websocket握手,完成握手 */
            ChannelFuture channelFuture = handshaker.handshake(ctx.channel(), req);

            /* 握手成功之后,业务逻辑 */
            if (channelFuture.isSuccess()) {

                logger.info("WebSocket Handshake successful" );
            }
        }
    }





    private static void sendHttpResponse(ChannelHandlerContext ctx, FullHttpRequest req, FullHttpResponse res) {
        if (res.status().code() != 200) {
            ByteBuf buf = Unpooled.copiedBuffer(res.status().toString(), CharsetUtil.UTF_8);
            res.content().writeBytes(buf);
            buf.release();
            HttpHeaderUtil.setContentLength(res, res.content().readableBytes());
        }

        ChannelFuture f = ctx.channel().writeAndFlush(res);
        if (!HttpHeaderUtil.isKeepAlive(req) || res.status().code() != 200) {
            f.addListener(ChannelFutureListener.CLOSE);
        }
    }
}
