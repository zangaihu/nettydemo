package com.sh.netty.httpdemo;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.codec.http.*;
import io.netty.util.CharsetUtil;

import java.net.URI;

/**
 * @author sunhu
 * @date 2020/8/14 15:28
 */
public class TestHttpServerHandler extends SimpleChannelInboundHandler<HttpObject> {
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, HttpObject msg) throws Exception {
        if (msg instanceof HttpObject){
            System.out.println("pipeline hashcode: "+ctx.pipeline().hashCode());
            System.out.println("TestHttpServerHandler hashcode："+this.hashCode());
            System.out.println("客户端类型："+msg.getClass());
            System.out.println("地址："+ctx.channel().remoteAddress());


            HttpRequest httpRequest= (HttpRequest) msg;
            URI uri=new URI(httpRequest.uri());
            if ("/favicon.ico".equals(uri.getPath())){
                return;
            }

            ByteBuf content=Unpooled.copiedBuffer("服务器。。。", CharsetUtil.UTF_8);
            HttpVersion version;
            HttpResponseStatus status;
            FullHttpResponse response=
                    new DefaultFullHttpResponse(HttpVersion.HTTP_1_1, HttpResponseStatus.OK, content);
            response.headers().set(HttpHeaderNames.CONTENT_TYPE,"text/plain");
            response.headers().set(HttpHeaderNames.CONTENT_LENGTH,content.readableBytes());


            ctx.writeAndFlush(response);
        }


    }
}
