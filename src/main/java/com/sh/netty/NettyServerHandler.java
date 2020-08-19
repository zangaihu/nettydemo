package com.sh.netty;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.*;
import io.netty.util.CharsetUtil;
import io.netty.util.concurrent.EventExecutorGroup;

/**
 * 自定义一个handler，需要继承netty规定的某个HandlerAdapter规范
 *
 * @author sunhu
 * @date 2020/8/13 18:35
 */
public class NettyServerHandler extends ChannelInboundHandlerAdapter {
    /**
     *  读取数据
     * @param ctx 上下文对象，含有一系列额pipeline，channel，地址等
     * @param msg 客户端发送的信息
     * @throws Exception
     */
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        System.out.println("服务器读取线程"+Thread.currentThread().getName());
        //上下文获取通道channel
        Channel channel = ctx.channel();
        //上下文获取pipeline
        ChannelPipeline pipeline = ctx.pipeline();
        //将msg转换
        ByteBuf buf= (ByteBuf) msg;
        System.out.println("客户端消息："+buf.toString(CharsetUtil.UTF_8));
        System.out.println("客户端地址："+channel.remoteAddress());
    }

    /**
     * 数据读取完毕
     * @param ctx 上下文
     * @throws Exception
     */
    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
        //将数据写入缓存，并刷新缓存
        ctx.writeAndFlush(Unpooled.copiedBuffer("hello ,client",CharsetUtil.UTF_8));
    }

    /**
     * 异常处理
     * @param ctx 上下文
     * @param cause 原因
     * @throws Exception
     */
    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        ctx.close();
    }
}
