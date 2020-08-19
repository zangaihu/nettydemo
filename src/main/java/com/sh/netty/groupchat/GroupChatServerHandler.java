package com.sh.netty.groupchat;

import io.netty.channel.Channel;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.group.ChannelGroup;
import io.netty.channel.group.DefaultChannelGroup;
import io.netty.util.concurrent.EventExecutor;
import io.netty.util.concurrent.EventExecutorGroup;
import io.netty.util.concurrent.GlobalEventExecutor;
import jdk.nashorn.internal.objects.Global;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author sunhu
 * @date 2020/8/17 11:01
 */
public class GroupChatServerHandler extends SimpleChannelInboundHandler<String> {


    /**
     * 其它：list，hashMap管理
     * 定义一个channel组，管理所有channel
     */
    private static ChannelGroup channelGroup=new DefaultChannelGroup(GlobalEventExecutor.INSTANCE);
    SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");




    /**
     * 表明连接已建立，一旦连接，第一个执行
     * @param ctx
     * @throws Exception
     */
    @Override
    public void handlerAdded(ChannelHandlerContext ctx) throws Exception {
        Channel channel = ctx.channel();
        //发送给channelGroup中的所有channel
        channelGroup.writeAndFlush("[客户端]"+channel.remoteAddress()+"加入聊天"+sdf.format(new Date())+"\n");
        channelGroup.add(channel);
    }


    @Override
    public void handlerRemoved(ChannelHandlerContext ctx) throws Exception {
        Channel channel = ctx.channel();
        channel.writeAndFlush("[客户端]"+channel.remoteAddress()+"下线了\n");
        System.out.println("channelGroup size："+channelGroup.size());


    }


    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        System.out.println(ctx.channel().remoteAddress() + "上线了");
    }

    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        System.out.println(ctx.channel().remoteAddress() + "离线了");
    }

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, String s) throws Exception {
        Channel channel = ctx.channel();
        channelGroup.forEach(ch->{
            if (channel != ch){
                //不是当前channel,转发
                ch.writeAndFlush("[客户端]"+channel.remoteAddress()+" 发送了消息： "+s+"\n");
            }else {
                ch.writeAndFlush("[自己] 发送了消息： "+s+"\n");
            }
        });

    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        ctx.close();
    }
}
